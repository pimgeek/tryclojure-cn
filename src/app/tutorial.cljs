(ns app.tutorial
  (:require
   [clojure.string :as string]
   [sci.impl.vars :refer [SciVar]]))

(def tutorial
  "Collection of map steps."
  [;; Strings
   {:title
    "打印 \"你好\" 字符串！"
    :content
    "> 学习原则，遵守原则，化解原则。 —— 李小龙
     
  让我们从基础开始学习。Clojure
提供了最常见的数据类型，如整数、布尔值和字符串。 字符串就是用 `\"` 包裹起来的文字。
    
  尝试创建一个字符串，向 **世界** 说声 **你好**，代码如下 `\"你好，世界\"`"
    :test #(= (string/lower-case %) "你好，世界")}
   ;; Lists
   {:title "拥抱列表语法"
    :content
    "> Lisp 是编程语言设计史上伟大的单一编程语言。<br> —— Alan Kay
    
  Lisp 是 **LIS**t **P**rocessing 的缩写 (含义：列表处理)，其特点是代码以 [列表 (list)](https://en.wikipedia.org/wiki/S-expression) 的语法形式编写。Clojure 是 Lisp 编程语言的一种方言，因此它使用类似的语法。

  列表是一些项目或元素的集合，比如 `(1 2 3)`。在 Clojure 中，您可以使用 `list` 或前置符号 `'` 来创建列表。

  例如，我们可以这样创建列表：`(list 1 2 3)` 或 `'(1 2 3)`"
    :test #(list? %)}
   ;; Math
   {:title "数学就是 (函数)"
    :content
    "在 Clojure 中，数学运算符就像普通函数一样。正如你已了解的，必须将它们包含在括号 `(...)` 中。

  因此，你需要输入 `(+ 4 2)`，而不是 `4 + 2`。接下来，你可以尝试用 `+ - / *` 输入数字运算式。"
    :test #(number? %)}
   ;; Functions
   {:title "Clojure 是函数式的"
    :content
    "> Lisp 是函数式的。在我看来，[编程语言的未来很可能是函数式的](https://blog.cleancoder.com/uncle-bob/2019/08/22/WhyClojure.html)。 —— Robert C. Martin
  
  列表的第一个参数必须是一个**函数**。**剩余的部分**是传递给该函数的参数。在表达式 `(not true)` 中，**not** 是用于表示否定的函数，而 **true** 是它的参数。
  
  尝试使用函数 `(my-name)`，然后将你的网名作为 \"字符串\" 传递给它，如 `(my-name \"小核桃\")`。"
    :test #(and (map? %) (contains? % :user-name) (string? (:user-name %)))}
   ;; Keywords
   {:title "别忘了那些关键字"
    :content
    "> 如今，问题不在于如何创新，而在于如何让社会采纳已有的好点子。 —— Douglas Engelbart
  
  你好，**[[user-name]]**！很高兴见到你。
  
  在 REPL 中，你输入一个表达式后，将会得到它的求值结果。如你所见，`:user-name ` 的形式比较特殊，它被称为**关键字**。要创建一个关键字，必须在单词之前加上 `:`。

  你可以使用关键字 `:next` 进入下一步。"
    :test #(= % :next)}
   ;; Exercise - 01
   {:title "每件事都有一个函数"
    :content
    "让我们调整一下界面！
  
  你已经知道如何调用函数，以及如何使用关键字和字符串。如果我告诉你可以改变提示符 `=>`，你是否感到新奇呢？

  你可以使用关键字参数调用函数，比如：`(create-dog :name \"帅帅\" :breed \"哈士奇\")`
  
  利用 `(set-prompt)` 函数可以设置提示符的颜色。它接受 `:color` 和 `:text` 这两个可选参数，参数类型为字符串。可以直接点击 `(set-prompt :color \"blue\" :text \"[=>]\")` 输入代码。"
    :test #(contains? % :prompt-color)}
   ;; 
   {:title "函数式编程实践者！"
    :content
    "祝贺你，你刚刚调用了一个函数并改变了这个应用程序的状态！而这一整条命令其实是……一个列表！

  Clojure 提供了多种处理列表的函数，例如 `reverse`。它可以反转一个集合。因此，如果你向它传递一个字符串，它就会将其视为字符的集合。

  输入并执行 `(reverse \"个十百千万\")` 进入下一步。"
    :test #(= % (reverse "个十百千万"))}
   ;; Vectors
   {:title "我们拥有向量"
    :content
    "> Lisp 值得一学，因为当你最终搞懂它时，你将获得深刻的启迪。 —— Eric Raymond

  **向量** (又称数组) 包含按次序排列的元素，与列表相比，它们的访问速度更快。

  要创建一个向量，你需要将项目或元素包含在方括号 `[]` 中，用空格分隔，不要使用任何其它分隔符号。

  下面请创建一个元素向量，比如你最喜欢的猫咪的名字：`[\"罗小黑\" \"汤姆\" \"胖虎\"]`。"
    :test #(vector? %)}
   ;; Variables
   {:title "定义你的变量"
    :content
    "> 优秀的程序员不仅编写程序。他们建立了一个工作词汇表。 —— Guy Steele

  使用 `def` 定义**全局变量**，变量的值可以是任何内容。

  请创建一个名为 `pot` 的全局变量，并对它赋值。例如：`(def pot \"money\")`"
    :test #(and (instance? SciVar %) (= "pot" (-> (.-meta %) :name str)))}
   ;; Let
   {:title "让变量本地化"
    :content
    "> Lisp
之所以如此伟大，并非因为它具备某种只有狂热信徒才能看到的神奇品质，而是单纯地因为：它是目前最强大的编程语言。 —— Paul Graham
  
  可以用 `let` 来定义**本地变量**。它们只能在 `let` 的词法上下文中使用。在表达式 `(let [x 1] x)`中，你只能在用 `()` 分隔的 `body` 部分中引用 `x`。

  创建数值变量并将它们相乘，如 `(let [a 2 b 3] (* a b))`。"
    :test #(number? %)}
   ;; Maps
   {:title "映射就是字典"
    :content
    "> 傻瓜都能写出计算机能理解的代码。好的程序员写出人类可以理解的代码。 —— Martin Fowler
     
  映射是一种集合，它将**键 (key) **映射到**值**。键和值被包裹在 `{}` 中。你可以把任何东西当作键来使用，但 Clojure 程序员大多使用关键字 (keyword) 作为键。

  下面创建一个以 `:country` 为关键字、以字符串形式表示国家名字的映射。例如 `{:country \"澳大利亚\"}`。"
    :test #(and (map? %) (contains? % :country) (string? (:country %)))}
   ;; F-list
   {:title "列表中的第一个元素"
    :content
    "Clojure 提供了一些从列表中提取内容的函数。例如，调用 `first` 函数可以返回第一个元素。

  请输入 `(first '(\"孙悟空\" \"猪八戒\" \"沙和尚\"))` 获取第一个元素。"
    :test #(and (string? %) (= "孙悟空" %))}
   ;; Range
   {:title "长度为 N 的区间"
    :content
    "用 Clojure 函数 `range` 可以创建一个从 `0` 到 `n` (不含 n) 的数字列表。因此 `(range 5)` 将返回从 0 到 4 的 5 个数字。使用 `(doc range)` 可打印 `range` 的说明文档。

  下面请创建 0 至 99 的区间，或点击 `(range 100)` :)"
    :test #(= % (range 100))}
   ;; Filter
   {:title "对列表做筛选"
    :content
    "我们可以把函数应用到一个列表。例如，使用 `filter`，我们可以从中删除所有不符合条件的元素。

  下面请尝试删除 0 到 50 之间的所有偶数。<br><div class=\"inline-block mt-1 text-gray-500\">小声告诉你…</div> `(filter odd? (range 50))`"
    :test #(= % (filter odd? (range 50)))}
   ;; Map
   {:title "把函数应用到列表中的所有元素"
    :content
    "如果我们想要获得一个小于 100 且可以被 11 整除的数字列表，找到它们的过程就是从 1 到 9
的每个数字 `n`，对它乘以 10 然后再加一个 `n`，例如 `5 * 10 + 5 = 55`。在 Clojure
中，我们可以使用 `map` 做同样的操作。

  `map` 会把一个函数应用到列表中的每个元素。

  因此，可以用 `(map (fn [n] (+ n (* n 10))) (range 1 10))` 来完成上述任务"
    :test #(= % (map (fn [n] (+ n (* n 10))) (range 1 10)))}
    ;; Inline functions
    {:title "内嵌的函数"
     :content
     "在上一步中，我们编写了一个内嵌的函数，并将其作为参数传递给 `map`。我指的是
`(fn [n] (+ n (* n 10))))`。这种技巧非常有用，可以用它来创建作为**实用功能**而存在的函数。对于这类需求，没必要把它们写成一些针对特定用例的函数。

现在，请创建一个接收参数 `h` 和 `w` 并返回矩形周长的函数：

`(fn [h w] (* (+ h w) 2))`"
     :test #(= (apply % [2 3]) 10)}
   ;; REPL
   {:title "REPL 驱动的开发模式"
    :content
    "> 学习一门新编程语言的唯一方法就是用它编写程序。 —— Kernighan and Ritchie

  你目前正通过在 REPL 中输入代码并进行测试来解决一系列问题。这正是 Clojurist (即 Clojure 开发者 / Clojure 工匠) 的工作！在输入代码的同时进行测试，比先编译再调试的方式更快！

  但 Clojure 的价值远不止这些。请输入 `(more)` 进入最后一步。"
    :test #(true? %)}
    {:title "现在正是学习 Clojure 的时机！"
     :content
     "> 初学者的心充满各种的可能性，老手的心却没有多少可能性。 —— 铃木俊隆《禅者的初心》

  Clojure **并没有**想象中那么难。括号、函数、不可变数据结构和 REPL 将成为你的朋友。只要保持初学者的心态即可！
      
  在这里我想推荐一些很好的入门资源给你：[Clojure 公案](http://clojurekoans.com/)，[4Clojure](https://4clojure.oxal.org/)，[exercism](https://exercism.org/tracks/clojure)。

  也可以直接向社区寻求支持，祝你好运！"
     :test #(true? false)}])
