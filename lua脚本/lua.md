# 1.下载安装SciTE

使用一个叫"SciTE"的IDE环境来执行lua程序，下载地址为：

- 本站下载地址：[LuaForWindows_v5.1.4-46.exe](https://static.runoob.com/download/LuaForWindows_v5.1.4-46.exe)
- Github 下载地址：https://github.com/rjpcomputing/luaforwindows/releases
- Google Code下载地址 : https://code.google.com/p/luaforwindows/downloads/list



# 2.数据类型

| 数据类型 | 描述                                                         |
| :------- | :----------------------------------------------------------- |
| nil      | 这个最简单，只有值nil属于该类，表示一个无效值（在条件表达式中相当于false）。 |
| boolean  | 包含两个值：false和true。                                    |
| number   | 表示双精度类型的实浮点数                                     |
| string   | 字符串由一对双引号或单引号来表示                             |
| function | 由 C 或 Lua 编写的函数                                       |
| userdata | 表示任意存储在变量中的C数据结构                              |
| thread   | 表示执行的独立线路，用于执行协同程序                         |
| table    | Lua 中的表（table）其实是一个"关联数组"（associative arrays），数组的索引可以是数字、字符串或表类型。在 Lua 里，table 的创建是通过"构造表达式"来完成，最简单构造表达式是{}，用来创建一个空表。 |

**补充**

* nil 和 false 都为假 ，其余都是真
* “6”+2 ==》8  
* 字符串拼接：.. 
* \# 来计算字符串的长度，放在字符串前面 str="asdf1as2d1" ; a= #str;
* table的索引可以是数字，也可以是字符串，table下标从1开始
* 没初始的 table 都是 nil。

# 3.变量

局部变量与全局变量

* 全局变量 a = 10；
* 局部变量 local b = 20;
* 



变量的赋值

* a = "hello" .. "world"
* t.n = t.n + 1
* a, b = 10, 2*x       <-->       a=10; b=2*x
* x, y = y, x                     -- swap 'x' for 'y'
* a[i], a[j] = a[j], a[i]         -- swap 'a[i]' for 'a[i]'
* a. 变量个数 > 值的个数             按变量个数补足nil
* b. 变量个数 < 值的个数             多余的值会被忽略 
* a, b = f()   f()返回两个值，则分别赋值给ab



索引

* t[i]
* t.i                 -- 当索引为字符串类型时的一种简化写法

# 4.循环

| 循环类型                                                     | 描述                                                         |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [while 循环](https://www.w3cschool.cn/lua/lua-while-loop.html) | 在条件为 true 时，让程序重复地执行某些语句。执行语句前会先检查条件是否为 true。 |
| [for 循环](https://www.w3cschool.cn/lua/lua-for-loop.html)   | 重复执行指定语句，重复次数可在 for 语句中控制。              |
| [Lua repeat...until](https://www.w3cschool.cn/lua/lua-repeat-until-loop.html) | 重复执行循环，直到 指定的条件为真时为止                      |
| [循环嵌套](https://www.w3cschool.cn/lua/lua-nested-loops.html) | 可以在循环内嵌套一个或多个循环语句（while、for、do..while）  |

* #### **while循环**

  ```lua
  while(condition)
  do
     statements
  end
  ```

* #### **数值for循环**

  ```lua
  for var=exp1,exp2,exp3 do  
      <执行体>  
  end  
  ```

  ##### 说明：

  * var从exp1变化到exp2，每次变化以exp3为步长递增var，并执行一次"执行体"。exp3是可选的，如果不指定，默认为1。
  * for的三个表达式在循环开始前一次性求值，以后不再进行求值。比如上面的f(x)只会在循环开始前执行一次，其结果用在后面的循环中。

* #### **泛型for循环**

  ```lua
  --打印数组a的所有值  
  for i,v in ipairs(a) 
  	do print(v) 
  end  
  ```

  ##### 说明：

  * i是数组索引值，v是对应索引的数组元素值。ipairs是Lua提供的一个迭代器函数，用来迭代数组。

* #### repeat…until 循环

  ```LUA
  repeat
    循环体语句
  until( condition )
  ```

  

* #### 嵌套循环(没意思)

  ```lua
  for init,max/min value, increment
  do
     for init,max/min value, increment
     do
        statements
     end
     statements
  end
  
  ```

* #### break

# 5.流程控制

* #### if...else

  ```lua
  if(布尔表达式)
  then
     --[ 布尔表达式为 true 时执行该语句块 --]
  else
     --[ 布尔表达式为 false 时执行该语句块 --]
  end
  ```

  

* #### if...else if ...

  ```lua
  if( 布尔表达式 1)
  then
     --[ 在布尔表达式 1 为 true 时执行该语句块 --]
  
  else if( 布尔表达式 2)
     --[ 在布尔表达式 2 为 true 时执行该语句块 --]
  
  else if( 布尔表达式 3)
     --[ 在布尔表达式 3 为 true 时执行该语句块 --]
  else 
     --[ 如果以上布尔表达式都不为 true 则执行该语句块 --]
  end
  
  ```

  

# 6.函数

* #### 函数定义

  ```lua
  optional_function_scope function function_name( argument1, argument2, argument3..., argumentn)
     function_body
   return result_params_comma_separated
  end
  ```

  ##### 解析：

  * **optional_function_scope**
  * 该参数是可选的制定函数是全局函数还是局部函数？未设置该参数默认为全局函数，如果你需要设置函数为局部函数需要使用关键字local

  - **function_name:**
  - 指定函数名称。

  - **argument1, argument2, argument3..., argumentn:**
  - 函数参数，多个参数以逗号隔开，函数也可以不带参数。

  - **function_body:**
  - 函数体，函数中需要执行的代码语句块。

  - **result_params_comma_separated:**
  - 函数返回值，Lua语言函数可以返回多个值，每个值以逗号隔开。

  ##### 可以将函数作为参数传递给函数，如下实例：

  ```lua
  myprint = function(param)
     print("这是打印函数 -   ##",param,"##")
  end
  
  function add(num1,num2,functionPrint)
     result = num1 + num2
     -- 调用传递的函数参数
     functionPrint(result)
  end
  myprint(10)
  -- myprint 函数作为参数传递
  add(2,5,myprint)
  
  ```

* #### 多返回值

* #### 参数可变

  ```lua
  function average(...)
     result = 0
     local arg={...}
     for i,v in ipairs(arg) do
        result = result + v
     end
     print("总共传入 " .. #arg .. " 个数")
     return result/#arg
  end
  
  print("平均值为",average(10,5,3,4,5,6))
  
  ```

  

# 7.运算符

* #### 关系运算符

  | 操作符 | 描述                                                         | 实例                  |
  | :----- | :----------------------------------------------------------- | :-------------------- |
  | ==     | 等于，检测两个值是否相等，相等返回 true，否则返回 false      | (A == B) 为 false。   |
  | ~=     | 不等于，检测两个值是否相等，相等返回 false，否则返回 true<   | (A ~= B) 为 true。    |
  | >      | 大于，如果左边的值大于右边的值，返回 true，否则返回 false    | (A > B) 为 false。    |
  | <      | 小于，如果左边的值大于右边的值，返回 false，否则返回 true    | (A < B) 为 true。     |
  | >=     | 大于等于，如果左边的值大于等于右边的值，返回 true，否则返回 false | (A >= B) is not true. |
  | <=     | 小于等于， 如果左边的值小于等于右边的值，返回 true，否则返回 false | (A <= B) is true.     |

* #### 逻辑运算符

  | 操作符 | 描述                                                         | 实例                   |
  | ------ | ------------------------------------------------------------ | ---------------------- |
  | and    | 逻辑与操作符。 如果两边的操作都为 true 则条件为 true。       | (A and B) 为 false。   |
  | or     | 逻辑或操作符。 如果两边的操作任一一个为 true 则条件为 true。 | (A or B) 为 true。     |
  | not    | 逻辑非操作符。与逻辑运算结果相反，如果条件为 true，逻辑非为 false。 | not(A and B) 为 true。 |

* #### 其他运算符

  | 操作符 | 描述                               | 实例                                                         |
  | :----- | :--------------------------------- | :----------------------------------------------------------- |
  | ..     | 连接两个字符串                     | a..b ，其中 a 为 "Hello " ， b 为 "World", 输出结果为 "Hello World"。 |
  | #      | 一元运算符，返回字符串或表的长度。 | #"Hello" 返回 5                                              |

* #### 运算符优先级

  ```lua
  ^
  not    - (unary)
  *      /
  +      -
  ..
  <      >      <=     >=     ~=     ==
  and
  or
  
  ```

# 8.字符串



字符串或串(String)是由数字、字母、下划线组成的一串字符。

Lua 语言中字符串可以使用以下三种方式来表示：

- 单引号间的一串字符。
- 双引号间的一串字符。
- [[和]]间的一串字符。

以上三种方式的字符串实例如下：

```
string1 = "Lua"
print("\"字符串 1 是\"",string1)
string2 = 'w3cschool.cn'
print("字符串 2 是",string2)
string3 = [["Lua 教程"]]
print("字符串 3 是",string3)
```

转义字符用于表示不能直接显示的字符，比如后退键，回车键，等。如在字符串转换双引号可以使用 "\""。

所有的转义字符和所对应的意义：

| 转义字符 | 意义                                | ASCII码值（十进制） |
| -------- | ----------------------------------- | ------------------- |
| \a       | 响铃(BEL)                           | 007                 |
| \b       | 退格(BS) ，将当前位置移到前一列     | 008                 |
| \f       | 换页(FF)，将当前位置移到下页开头    | 012                 |
| \n       | 换行(LF) ，将当前位置移到下一行开头 | 010                 |
| \r       | 回车(CR) ，将当前位置移到本行开头   | 013                 |
| \t       | 水平制表(HT) （跳到下一个TAB位置）  | 009                 |
| \v       | 垂直制表(VT)                        | 011                 |
| \\       | 代表一个反斜线字符''\'              | 092                 |
| \'       | 代表一个单引号（撇号）字符          | 039                 |
| \"       | 代表一个双引号字符                  | 034                 |
|          | 空字符(NULL)                        | 000                 |
| \ddd     | 1到3位八进制数所代表的任意字符      | 三位八进制          |
| \xhh     | 1到2位十六进制所代表的任意字符      | 二位十六进制        |

* #### Lua 字符串

Lua 提供了很多的方法来支持字符串的操作：

| 序号 | 方法 & 用途                                                  |
| :--- | :----------------------------------------------------------- |
| 1    | **string.upper(argument):** 字符串全部转为大写字母。         |
| 2    | **string.lower(argument):** 字符串全部转为小写字母。         |
| 3    | **string.gsub(mainString,findString,replaceString,num)** 在字符串中替换,mainString为要替换的字符串， findString 为被替换的字符，replaceString 要替换的字符，num 替换次数（可以忽略，则全部替换），如：`> string.gsub("aaaa","a","z",3); zzza 3 ` |
| 4    | **string.find (str, substr, [init, [end]])** 在一个指定的目标字符串中搜索指定的内容(第三个参数为索引),返回其具体位置。不存在则返回 nil。`> string.find("Hello Lua user", "Lua", 1)  7 9 ` |
| 5    | **string.reverse(arg)** 字符串反转`> string.reverse("Lua") auL` |
| 6    | **string.format(...)** 返回一个类似printf的格式化字符串`> string.format("the value is:%d",4) the value is:4 ` |
| 7    | **string.char(arg) 和 string.byte(arg[,int])** char 将整型数字转成字符并连接， byte 转换字符为整数值(可以指定某个字符，默认第一个字符)。`> string.char(97,98,99,100) abcd string.byte("ABCD",4) 68 string.byte("ABCD") 65 ` |
| 8    | **string.len(arg)** 计算字符串长度。`string.len("abc") 3 `   |
| 9    | **string.rep(string, n))** 返回字符串string的n个拷贝`> string.rep("abcd",2) abcdabcd ` |
| 10   | **..** 链接两个字符串`> print("www.w3cschool"..".cn") www.w3cschool.cn ` |

# 9.迭代器

* #### 泛型for迭代器

  ```lua
  for k, v in pairs(t) do
      print(k, v)
  end
  k, v为变量列表；pairs(t)为表达式列表。
  ```

  * 首先，初始化，计算in后面表达式的值，表达式应该返回范性for需要的三个值：迭代函数、状态常量、控制变量；与多值赋值一样，如果表达式返回的结果个数不足三个会自动用nil补足，多出部分会被忽略。
  * 第二，将状态常量和控制变量作为参数调用迭代函数（注意：对于for结构来说，状态常量没有用处，仅仅在初始化时获取他的值并传递给迭代函数）。
  * 第三，将迭代函数返回的值赋给变量列表。
  * 第四，如果返回的第一个值为nil循环结束，否则执行循环体。
  * 第五，回到第二步再次调用迭代函数

# 10.table

* #### 创建赋值删除表

  ```lua
  -- 初始化表
  mytable = {}
  
  -- 指定值
  mytable[1]= "Lua"
  
  -- 移除引用
  mytable = nil
  -- lua 垃圾回收会释放内存
  ```

* #### 表的操作

  | 序号 | 方法 & 用途                                                  |
  | :--- | :----------------------------------------------------------- |
  | 1    | **table.concat (table [, step [, start [, end]]]):**concat是concatenate(连锁, 连接)的缩写. table.concat()函数列出参数中指定table的数组部分从start位置到end位置的所有元素, 元素间以指定的分隔符(sep)隔开。 |
  | 2    | **table.insert (table, [pos,] value):**在table的数组部分指定位置(pos)插入值为value的一个元素. pos参数可选, 默认为数组部分末尾. |
  | 3    | **table.maxn (table)**指定table中所有正数key值中最大的key值. 如果不存在key值为正数的元素, 则返回0。(**Lua5.2之后该方法已经不存在了,本文使用了自定义函数实现**) |
  | 4    | **table.remove (table [, pos])**返回table数组部分位于pos位置的元素. 其后的元素会被前移. pos参数可选, 默认为table长度, 即从最后一个元素删起。 |
  | 5    | **table.sort (table [, comp])**对给定的table进行升序排序。   |

  * concat

    ```lua
    fruits = {"banana","orange","apple"}
    -- 返回 table 连接后的字符串
    print("连接后的字符串 ",table.concat(fruits))  --连接后的字符串 	bananaorangeapple
    
    -- 指定连接字符
    print("连接后的字符串 ",table.concat(fruits,", "))  --连接后的字符串 	banana, orange, apple
    
    -- 指定索引来连接 table
    print("连接后的字符串 ",table.concat(fruits,", ", 2,3))  --连接后的字符串 	orange, apple
    ```

  * 插入移除

    ```lua
    fruits = {"banana","orange","apple"}
    -- 在末尾插入
    table.insert(fruits,"mango")
    print("索引为 4 的元素为 ",fruits[4])
    
    -- 在索引为 2 的键处插入
    table.insert(fruits,2,"grapes")
    print("索引为 2 的元素为 ",fruits[2])
    
    print("最后一个元素为 ",fruits[5])
    table.remove(fruits)
    print("移除后最后一个元素为 ",fruits[5])
    
    ```

  * 排序

    ```lua
    fruits = {"banana","orange","apple","grapes"}
    table.sort(fruits)
    ```

    

# 11.模块

* 创建模块

  ```lua
  -- 定义一个名为 module 的模块
  module = {}
   
  -- 定义一个常量
  module.constant = "这是一个常量"
   
  -- 定义一个函数
  function module.func1()
      io.write("这是一个公有函数！\n")
  end
   
  local function func2()
      print("这是一个私有函数！")
  end
   
  function module.func3()
      func2()
  end
   
  return module
  ```

* 加载模块

  ```lua
  require("module")
  或者
  require "module"
  或者
  local m = require("module")
  ```

  