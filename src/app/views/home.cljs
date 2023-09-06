(ns app.views.home
  (:require
   [reagent.core :as r]
   [clojure.string :as string]
   [app.repl.core :as repl]
   [markdown.core :refer [md->html]]
   [app.session :as session]
   [app.tutorial :refer [tutorial]]))

(def intro-title
  "有 5 分钟时间吗？")

(def intro-content
  "> 如果你希望一切都感觉熟悉，你就永远学不到新东西。<br>—— Rich Hickey

让我们做点好玩的事情！

<span id=\"location-of-editor\">注意看右边，这是一个</span> **REPL**，或者说具备 **读取-求值-打印循环** (Read-Eval-Print Loop) 功能的命令行工具。你输入的所有内容都将被求值。

试着输入一些表达式，比如 `(+ 1 2)`，或者直接点击示例代码，它会被自动粘贴到右边 (注：按**回车键 ⏎** 执行)。如果想了解更多命令，可以输入 `(help)`。
   
如果你已经准备好，就输入 `(start)` 启动教程吧!")

(defn compute-step
  "Returns a list of `title` and `content`
  based on the current step stored into the session."
  []
  (if (repl/tutorial-active?)
    (let [step-idx (session/get :step)
          step (nth tutorial step-idx)]
      [(:title step) (:content step)])
    [intro-title intro-content]))

(defn- link-target
  "Add target=_blank to link in markdown."
  [text state]
  [(string/replace text #"<a " "<a target=\"_blank\" ")
   state])

;; Regex to find [[var]] pattern in strings
(def re-doublebrackets #"(\[\[(.+)\]\])")

(defn- session-vars
  "Replace `[[var]]` in markdown text using session
  variables."
  [text state]
  [(let [res (re-find re-doublebrackets text)]
     (if res
       (let [k (keyword (last res))
             v (if (session/has? k)
                 (session/get k)
                 "unset")]
         (string/replace text re-doublebrackets v))
       text))
   state])

(defn- parse-md [s]
  (md->html s :custom-transformers [link-target session-vars]))

;; -------------------------
;; Views
;; -------------------------

(defn- handle-tutorial-click
  "When user click of `code` DOM node, fill the REPL input
  with the code content and focus on it."
  [e]
  (let [target (.-target e)
        node-name (.-nodeName target)]
    (when (= node-name "CODE")
      (->> (.-textContent target)
           (reset! repl/repl-input))
      (repl/focus-input))))

(defn tutorial-view [[title content]]
  [:div {:class ["bg-gray-200"
                 "text-black"
                 "dark:text-white"
                 "dark:bg-gray-800"
                 "shadow-lg"
                 "sm:rounded-l-md"
                 "xs:rounded-t-md"
                 "w-full"
                 "md:p-8"
                 "p-6"
                 "min-h-[200px]"
                 "opacity-95"]
         :on-click handle-tutorial-click}
   [:h1 {:class ["text-3xl" "mb-4" "font-bold" "tracking-tight"]}
    title]
   [:div {:class ["leading-relaxed" "last:pb-0"]
          :dangerouslySetInnerHTML #js{:__html (parse-md content)}}]])

(defn view []
  (r/create-class
   {:display-name "home-view"

    :component-did-mount
    (fn []
      ;; Focus on input after first rendered
      (repl/focus-input))

    :reagent-render
    (fn []
      [:div {:class ["flex"
                     "sm:flex-row"
                     "flex-col"
                     "items-center"
                     "justify-center"
                     "xl:-mt-32"
                     "lg:-mt-8"
                     "mt-0"]}
       [:div {:class ["flex-1" "z-0"]}
        [tutorial-view (compute-step)]]
       [:div {:class ["flex-1"
                      "z-10"
                      "sm:w-auto"
                      "w-full"
                      "sm:mt-0"
                      "mt-7"
                      "sm:mb-0"
                      "mb-14"]}
        [repl/view]]])}))

(defn- update-location-of-editor []
  (let [window-width (. js/window -innerWidth)
        location-of-editor-dom (.getElementById js/document "location-of-editor")]
    (set! (. location-of-editor-dom -innerHTML)
          (if (< window-width 640) "注意看下方，这是一个" "注意看右边，这是一个"))))

(.addEventListener js/window "load" update-location-of-editor)
(.addEventListener js/window "resize" update-location-of-editor)
