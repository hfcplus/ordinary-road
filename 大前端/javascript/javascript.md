### Math

| 方法          | 描述                         |
| ------------- | ---------------------------- |
| Math.PI()     | 圆周率                       |
| Math.ceil()   | 向上取整(取该浮点数的最大值) |
| Math.random() | 取随机小数(0~1)              |
| Math.round()  | 四舍五入                     |

### Object

**基本数据类型（值类型）**：String 字符串、Number 数值、Boolean 布尔值、Null 空值、Undefined 未定义

**引用数据类型（引用类型）**：Object 对象。

**基本数据类型**：

基本数据类型的值直接保存在**栈内存**中，值与值之间是独立存在，修改一个变量不会影响其他的变量。

**对象**：

对象是保存到**堆内存**中的，每创建一个新的对象，就会在堆内存中开辟出一个新的空间。变量保存的是对象的内存地址（对象的引用）。

换而言之，对象的值是保存在**堆内存**中的，而对象的引用（即变量）是保存在**栈内存**中的。

**如果两个变量保存的是同一个对象引用，当一个通过一个变量修改属性时，另一个也会受到影响**。

>
>
>```javascript
>var obj1 = new Object();
>obj1.name = "孙悟空";
>
>var obj2 = obj1; // 将 obj1 的地址赋值给 obj2。从此， obj1 和 obj2 指向了同一个堆内存空间
>
>//修改obj2的name属性
>obj2.name = "猪八戒";
>console.log(obj1.name); // 猪八戒
>```
>
>```javascript
>var obj1 = {name: '孙悟空'};
>
>// 复制对象：把 obj1 赋值给 obj3。两者之间互不影响
>var obj3 = Object.assign({}, obj1);
>obj3.name = "猪八戒";
>console.log(obj1.name); // 孙悟空
>```
>

### String

#### 查找字符串

1. **indexOf()/lastIndexOf()**：获取字符串中指定内容的索引

   ```javascript
   indexOf('str', num);
   ```

   `indexOf()` 是从前向后查找字符串的位置。同理，`lastIndexOf()`是从后向前寻找。

   * 如果获取的索引值为 0，说明字符串是以查询的参数为开头的。
   * 如果获取的索引值为-1，说明这个字符串中没有指定的内容。
   * num: 不指定默认为0，指定则是检索的起始位置

   ```javascript
   const str = 'abcdea';
   str.indexOf('c'); // 从字符串的头开始，查找c，返回2
   str.indexOf('a',1); // 从字符串的下标为1开始，查找a ，返回 5
   ```

   

2. **search()：**获取字符串中指定内容的索引（参数里一般是正则）

   ```javascript
   索引值 = str.search(想要查找的字符串);
   索引值 = str.search(正则表达式);
   ```

   * 可以检索一个字符串中是否含有指定内容。如果字符串中含有该内容，则会返回其**第一次出现**的索引；如果没有找到指定的内容，则返回 -1

   ```javascript
   const name = 'abcdef';
   console.log(name.search('bc')); // 打印结果：1
   ```

   

3. **includes(**)：字符串中是否包含指定的内容

   ```javascript
   布尔值 = str.includes(想要查找的字符串, [position]);
   ```

   * 判断一个字符串中是否含有指定内容。如果字符串中含有该内容，则会返回 true；否则返回 false。
   * 参数中的 `position`：如果不指定，则默认为0；如果指定，则规定了检索的起始位置。

   ```javascript
   const name = 'qianguyihao';
   console.log(name.includes('yi')); // 打印结果：true
   console.log(name.includes('haha')); // 打印结果：false
   ```

   

4. **startsWith()**：字符串是否以指定的内容开头

   ```javascript
   布尔值 = str.startsWith(想要查找的内容, [position]);
   ```

   * 判断一个字符串是否以指定的子字符串开头。如果是，则返回 true；否则返回 false。
   * position : 如果不指定，则默认为0, 如果指定，则规定了**检索的起始位置**。

   ```javascript
   const name = 'abcdefg';
   
   console.log(name.startsWith('a')); // 打印结果：true
   console.log(name.startsWith('b')); // 打印结果：false
   
   // 因为指定了起始位置为3，所以是在 defg 这个字符串中检索。
   console.log(name.startsWith('d',3)); // 打印结果：true
   console.log(name.startsWith('c',3)); // 打印结果：false
   ```

   

5. **endsWith()**：字符串是否以指定的内容结尾

#### 获取指定位置的字符

1. **charAt(index)**

   ```javascript
   字符 = str.charAt(index);
   ```

   ```javascript
   const str = 'abcd';
   console.log(str.charAt(0));  // a
   ```

   

2. **str[index]**

#### 字符串截取

1. **slice()**

   ```javascript
   新字符串 = str.slice(开始索引, 结束索引); //两个参数都是索引值。包左不包右。
   ```

   * 返回截取到的内容,不更改原字符串
   * `(2, 5)` 截取时，包左不包右。
   * `(2)` 表示**从指定的索引位置开始，截取到最后**。
   * `(-3)` 表示从倒数第三个开始，截取到最后。
   * `(1, -1)` 表示从第一个截取到倒数第一个。
   * `(5, 2)` 表示前面的大，后面的小，返回值为空。

2. **substring()**

   ```javascript
   新字符串 = str.substring(开始索引, 结束索引); //两个参数都是索引值。包左不包右。
   ```

   * 从字符串中截取指定的内容。和`slice()`类似。
   * `substring()`不能接受负值作为参数。如果传递了一个**负值**，则默认使用 0。
   * `substring()`还会自动调整参数的位置，如果第二个参数小于第一个，则自动交换。比如说， `substring(1, 0)`相当于截取的是第一个字符。

3. **substr()**

   ```javascript
   字符串 = str.substr(开始索引, 截取的长度);
   ```

   * 从字符串中截取指定的内容。不会修改原字符串，而是将截取到的内容返回。
   * 这个方法的第二个参数**截取的长度**，不是结束索引。
   * `(2,4)` 从索引值为 2 的字符开始，截取 4 个字符。
   * `(1)` 从指定位置开始，截取到最后。
   * `(-3)` 从倒数第几个开始，截取到最后。
   * (-3, 2)从倒数第几个开始，截取2个。

#### concat()

​	字符串相加，没什么用

#### split()

​	字符串转换为数组

```javascript
新的数组 = str.split(分隔符);
```

#### replace()

```javascript
新的字符串 = str.replace(被替换的字符，新的字符);
```

* 将字符串中的指定内容，替换为新的内容并返回。不会修改原字符串。
* 默认只会替换第一个被匹配到的字符。如果要全局替换，需要使用正则。



#### repeat()

```javascript
newStr = str.repeat(重复的次数);
```

#### trim()

```javascript
let str = '   a   b   c   ';
console.log(str.trim()); // 'a b c'
```

### 数组

#### 创建数组

1. **使用字面量创建数组**

   ```javascript
   var arr1 = []; // 创建一个空的数组
   
   var arr2 = [1, 2, 3]; // 创建带初始值的数组
   ```

   

2. **使用构造函数创建数组**

   ```javascript
   let arr = new Array(参数);
   
   let arr = Array(参数);
   ```

   * 如果**参数为空**，则表示创建一个空数组；
   * 如果参数是**一个数值**时，表示数组的长度；
   * 如果有多个参数时，表示数组中的元素。

3. **Array.of()：创建数组**

   ```javascript
   Array.of(value1, value2, value3);
   ```

   * 根据参数里的内容，创建数组。
   * `new Array()`和 `Array.of()`的区别在于：当参数只有一个时，前者表示数组的长度，后者表示数组中的内容。

4. **Array.from(arryLike)**

   将伪数组转换为真的数组

#### 数组元素的添加与删除

1. **push()**

   向数组的**最后面**插入一个或多个元素，返回结果为新数组的**长度**。会改变原数组

   ```javascript
   新数组的长度 = 数组.push(元素);
   ```

2. **pop()**

   删除数组中的**最后一个**元素，返回结果为**被删除的元素**。

   ```javascript
   被删除的元素 = 数组.pop();
   ```

3. **unshift()**

   在数组**最前面**插入一个或多个元素，返回结果为新数组的**长度**。会改变原数组

   ```javascript
   新数组的长度 = 数组.unshift(元素);
   ```

   ```javascript
   var arr = ['王一', '王二', '王三'];
   var result2 = arr.unshift('王五', '王六'); // 最前面插入多个元素
   console.log(JSON.stringify(arr)); // 打印结果：["王五","王六","王一","王二","王三"]
   ```

   

4. **shift()**

   删除数组中的**第一个**元素，返回结果为**被删除的元素**。

   ```javascript
   被删除的元素 = 数组.shift();
   ```

5. **slice()**

   从数组中**提取**指定的一个或者多个元素，返回结果为**新的数组**（不会改变原来的数组）。

   ```javascript
   新数组 = 原数组.slice(开始位置的索引, 结束位置的索引); //注意：包含开始索引，不包含结束索引
   // 参考字符串的slice
   ```

   

6. **splice()**

   从数组中**删除**指定的一个或多个元素，返回结果为**被删除元素组成的新数组**（会改变原来的数组）。

   ```javascript
   新数组 = 原数组.splice(起始索引index, 需要删除的个数);
   
   新数组 = 原数组.splice(起始索引index, 需要删除的个数, 新的元素1, 新的元素2...);
   ```

#### 数组的合并和拆分

1. **concat()**

   连接两个或多个数组，返回结果为**新的数组**。不会改变原数组。`concat()`方法的作用是**数组合并**。

   ```javascript
   新数组 = 数组1.concat(数组2, 数组3 ...);
   ```

   

2. **数组合并的另一种方式**：...

   ```javascript
   const arr1 = [1, 2, 3];
   
   const result = ['a', 'b', 'c', ...arr1];
   console.log(JSON.stringify(result)); // 打印结果：["a","b","c",1,2,3]
   let a1 = [1,2,3];
   let a2 = [4,5,6];
   let a3 = [...a1,...a2];
   console.log(a3);//[1,2,3,4,5,6]
   ```

   

3. **join()**

   将数组转换为字符串，返回结果为**转换后的字符串**（不会改变原来的数组）。

   ```javascript
   新的字符串 = 原数组.join(参数); // 参数选填
   ```

   * `join()`方法可以指定一个**字符串**作为参数，这个字符串将会成为数组中元素的**连接符**；如果不指定连接符，则默认使用 `,` 作为连接符，此时和 `toString()的效果是一致的`。

   ```javascript
   var arr = ['a', 'b', 'c'];
   var result1 = arr.join(); // 这里没有指定连接符，所以默认使用 , 作为连接符
   var result2 = arr.join('-'); // 使用指定的字符串作为连接符
   console.log('arr =' + JSON.stringify(arr));         // arr =["a","b","c"]
   console.log('result1 =' + JSON.stringify(result1)); // result1 =a,b,c
   console.log('result2 =' + JSON.stringify(result2)); // result2 =a-b-c
   ```

#### reverse()

反转数组，返回结果为**反转后的数组**（会改变原来的数组）。

```javascript
反转后的数组 = 数组.reverse();
```

#### sort()方法

对数组的元素进行从小到大来排序（会改变原来的数组）。

1. 如果在使用 sort() 方法时不带参，则默认按照**Unicode 编码**，从小到大进行排序。

   ```javascript
   let arr2 = [5, 2, 11, 3, 4, 1];
   let result = arr2.sort(); // 将数组 arr2 进行排序
   console.log('arr2 =' + JSON.stringify(arr2));      // arr2 =[1,11,2,3,4,5]
   console.log('result =' + JSON.stringify(result));  // result =[1,11,2,3,4,5]
   ```

2. sort()方法中带参，我们就可以**自定义**排序规则

   我们可以在 sort()添加一个回调函数，来指定排序规则。回调函数中需要定义两个形参，浏览器将会分别使用数组中的元素作为实参去调用回调函数。

   浏览器根据回调函数的返回值来决定元素的排序：（重要）

   -   如果返回一个大于 0 的值，则元素会交换位置
   -   **如果返回一个小于 0 的值，则元素位置不变**
   -   如果返回一个等于 0 的值，则认为两个元素相等，则不交换位置

   ```javascript
   var arr = [5, 2, 11, 3, 4, 1];
   
   // 自定义排序规则
   var result1 = arr.sort(function (a, b) {
       if (a > b) {
           // 如果 a 大于 b，则交换 a 和 b 的位置
           return 1;
       } else if (a < b) {
           // 如果 a 小于 b，则位置不变
           return -1;
       } else {
           // 如果 a 等于 b，则位置不变
           return 0;
       }
   });
   
   var result2 = arr.sort(function(a,b){
       return a-b;
   })
   
   var result3 = arr.sort((a, b) => a - b);

#### indexOf(),lastindexOf()

`indexOf()` 是从前向后查找元素的位置。同理，`lastIndexOf()`是从后向前寻找。

可以检索一个数组中是否含有指定的元素。如果数组中含有该元素，则会返回其**第一次出现**的索引；如果没有找到指定的内容，则返回 -1。

和字符串的类似

```javascript
索引值 = 数组.indexOf(想要查询的元素);

索引值 = 数组.lastIndexOf(想要查询的元素);
```

#### includes()

判断一个数组中是否包含指定的元素。如果是，则会返回 true；否则返回 false。

和字符串的类似

```javascript
布尔值 = arr.includes(想要查找的元素, [position]);
```

#### find()

找出**第一个**满足「指定条件返回 true」的元素；如果没找到，则返回 undefined。

```javascript
find((item, index, arr) => {
    return true;
});
```

```javascript
let arr = [2, 3, 2, 5, 7, 6];

let result = arr.find((item, index) => {
    return item > 4; //遍历数组arr，一旦发现有第一个元素大于4，就把这个元素返回
});

console.log(result); //打印结果：5
```

#### findIndex()

找出**第一个**满足「指定条件返回 true」的元素的 index。

```javascript
findIndex((item, index, arr) => {
    return true;
});
```

```javascript
let arr = [2, 3, 2, 5, 7, 6];

let result = arr.findIndex((item, index) => {
    return item > 4; //遍历数组arr，一旦发现有第一个元素大于4，就把这个元素的index返回
});

console.log(result); //打印结果：3
```

#### every()

对数组中每一项运行回调函数，如果都返回 true，every 就返回 true；如果有一项返回 false，则停止遍历，此方法返回 false。

```javascript
var arr1 = ['千古', '宿敌', '南山忆', '素颜'];
var bool1 = arr1.every(function (element, index, array) {
    if (element.length > 2) {
        return false;
    }
    return true;
});
console.log(bool1); //输出结果：false。只要有一个元素的长度是超过两个字符的，就返回false

var arr2 = ['千古', '宿敌', '南山', '素颜'];
var bool2 = arr2.every(function (element, index, array) {
    if (element.length > 2) {
        return false;
    }
    return true;
});
console.log(bool2); //输出结果：true。因为每个元素的长度都是两个字符。
```

#### some()

对数组中每一个元素运行回调函数，只要有一个元素返回 true，则停止遍历，此方法返回 true。

#### 数组的遍历

遍历数组的方法包括：every()、filter()、forEach()、map()等。

```javascript
数组/boolean/无 = 数组.every/filter/forEach/map/some(
                        function(item, index, arr){
                                        程序和返回值；
                        })
```

##### forEach()

```javascript
let myArr = ['王一', '王二', '王三'];

myArr.forEach((item, index, arr) => {
    console.log('item:' + item);
    console.log('index:' + index);
    console.log('arr:' + JSON.stringify(arr));
    console.log('----------');
});


myArr.forEach(item =>{
     console.log('item:' + item);
})
```

* item: 就是当前正在遍历的元素
* index: 就是当前正在遍历的元素的索引
* arr:  遍历的数组

##### map();

对数组中每一项运行回调函数，返回该函数的结果，组成的新数组（返回的是**加工之后**的新数组）。不会改变原数组。

```javascript
arr.map(function (item, index, arr) {
    return newItem;
});
```

```javascript
var arr1 = [1, 3, 6, 2, 5, 6];

var arr2 = arr1.map(function (item, index) {
    return item + 10; //让arr1中的每个元素加10
});
console.log(arr2); // 11,13,16,12,15,16
```

```javascript
const arr1 = [
    { name: '千古壹号', age: '28' },
    { name: '许嵩', age: '32' },
];

// 将数组 arr1 中的 name 属性，存储到 数组 arr2 中
const arr2 = arr1.map((item) => item.name);

// 将数组 arr1 中的 name、age这两个属性，改一下“键”的名字，存储到 arr3中
const arr3 = arr1.map((item) => ({
    myName: item.name,
    myAge: item.age,
})); // 将数组 arr1 中的 name 属性，存储到 数组 arr2 中

console.log('arr1:' + JSON.stringify(arr1)); // arr1:[{"name":"千古壹号","age":"28"},{"name":"许嵩","age":"32"}]
console.log('arr2:' + JSON.stringify(arr2)); // arr2:["千古壹号","许嵩"]
console.log('arr3:' + JSON.stringify(arr3)); // arr3:[{"myName":"千古壹号","myAge":"28"},{"myName":"许嵩","myAge":"32"}]
```



```javascript
const arr = [
    {
        name: "qianguyihao1",
        age: 22,
    },
    {
        name: "qianguyihao2",
        age: 23,
    },
];

arr.map((item) => {
    item.name = "haha"; // 修改 item 里的某个属性
    return item;
});
console.log(JSON.stringify(arr));//[{"name":"haha","age":22},{"name":"haha","age":23}]
```

##### filter()

对数组中的**每一项**运行回调函数，该函数返回结果是 true 的项，将组成新的数组（返回值就是这个新的数组）。不会改变原数组。

```javascript
arr.filter(function (item, index, arr) {
    return true;
});
```

```javascript
let arr1 = [1, 3, 6, 2, 5, 6];

let arr2 = arr1.filter((item) => item > 4); // 将arr1中大于4的元素返回，组成新的数组

console.log(JSON.stringify(arr1)); // 打印结果：[1,3,6,2,5,6]
console.log(JSON.stringify(arr2)); // 打印结果：[6,5,6]
```

##### reduce()

reduce() 方法接收一个函数作为累加器，数组中的每个值（从左到右）开始缩减，最终计算为一个值。返回值是回调函数累计处理的结果。

```javascript
arr.reduce(function (previousValue, currentValue, currentIndex, arr) {
    
}, initialValue);
```

-   previousValue：必填，上一次调用回调函数时的返回值
-   currentValue：必填，当前正在处理的数组元素
-   currentIndex：选填，当前正在处理的数组元素下标
-   arr：选填，调用 reduce()方法的数组
-   initialValue：选填，可选的初始值（作为第一次调用回调函数时传给 previousValue 的值）

```javascript
const arr = [2, 0, 1, 9, 6];
// 数组求和
const total = arr.reduce((prev, item) => {
    return prev + item;
});

console.log('total:' + total); // 打印结果：18



// 定义方法：统一 value 这个元素在数组 arr 中出现的次数
function repeatCount(arr, value) {
    if (!arr || arr.length == 0) return 0;

    return arr.reduce((totalCount, item) => {
        totalCount += item == value ? 1 : 0;
        return totalCount;
    }, 0);
}

let arr1 = [1, 2, 6, 5, 6, 1, 6];

console.log(repeatCount(arr1, 6)); // 打印结果：3
```

### 函数

#### 函数的定义

##### 命名函数

```javascript
function fun1(a, b){
	return a+b;
}
```

##### 匿名函数

```javascript
var fun2 = function() {
	console.log("我是匿名函数中封装的代码");
};
```

##### new Function()

```javascript
垃圾，不推荐
```

#### 函数的调用

##### 普通函数的调用

```javascript
function fn1() {
	console.log('我是函数体里面的内容1');
}

function fn2() {
	console.log('我是函数体里面的内容2');
}

fn1(); // 调用函数

fn2.call(); // 调用函数
```

##### 通过对象的方法来调用

```javascript
var obj = {
	a: 'qianguyihao',
	fn2: function() {
		console.log('千古壹号，永不止步!');
	},
};

obj.fn2(); // 调用函数
```

##### 立即执行函数

```javascript
(function() {
	console.log('我是立即执行函数');
})();


(function(a, b) {
    console.log("a = " + a);
    console.log("b = " + b);
})(123, 456);
```

##### 通过构造函数来调用

```javascript
function Fun3() {
	console.log('千古壹号，永不止步~');
}

new Fun3();
```

##### 绑定事件函数

```javascript
var btn = document.getElementById('btn');
//2.绑定事件
btn.onclick = function() {
    console.log('点击按钮后，要做的事情');
};
```

##### 定时器函数

```javascript
let num = 1;
setInterval(function () {
    num ++;
    console.log(num);
}, 1000);
```

#### 类数组 arguments

在调用函数时，浏览器每次都会传递进两个隐含的参数：

- 1.函数的上下文对象 this
- 2.**封装实参的对象** arguments

arguments 代表的是**实参**,arguments**只在函数中使用**。

```javascript
function getMaxValue() {
    var max = arguments[0];
    // 通过 arguments 遍历实参
    for (var i = 0; i < arguments.length; i++) {
        if (max < arguments[i]) {
            max = arguments[i];
        }
    }
    return max;
}

console.log(getMaxValue(1, 3, 7, 5));
```

#### 函数内 this 的指向

- 1.以函数的形式（包括普通函数、定时器函数、立即执行函数）调用时，this 的指向永远都是 window。比如`fun();`相当于`window.fun();`
- 2.以方法的形式调用时，this 指向调用方法的那个对象
- 3.以构造函数的形式调用时，this 指向实例对象
- 4.以事件绑定函数的形式调用时，this 指向**绑定事件的对象**
- 5.使用 call 和 apply 调用时，this 指向指定的那个对象

```javascript
function fun() {
    console.log(this);
    console.log(this.name);
}

var obj1 = {
    name: 'smyh',
    sayName: fun,
};

var obj2 = {
    name: 'vae',
    sayName: fun,
};

var name = '全局的name属性';

//以函数形式调用，this是window
fun(); //可以理解成 window.fun()
```

>```javascript
>    Window
>    全局的name属性
>```

```javascript
function fun() {
    console.log(this);
    console.log(this.name);
}

var obj1 = {
    name: 'smyh',
    sayName: fun,
};

var obj2 = {
    name: 'vae',
    sayName: fun,
};

var name = '全局的name属性';

//以方法的形式调用，this是调用方法的对象
obj2.sayName();
```

>```javascript
>    Object
>    vae

### call()

call() 方法的作用：可以**调用**一个函数，与此同时，它还可以改变这个函数内部的 this 指向。

call() 方法的另一个应用：**可以实现继承**。之所以能实现继承，其实是利用了上面的作用。

```javascript
fn1.call(想要将this指向哪里, 函数实参1, 函数实参2);
```

#### 调用函数

```javascript
const obj1 = {
    nickName: 'qianguyihao',
    age: 28,
};
function fn1() {
    console.log(this);
    console.log(this.nickName);
}
fn1.call(this); // this的指向并没有被改变，此时相当于 fn1();
/*
	window
	undefined
*/
```

#### 改变this指向

```javascript
var obj1 = {
    nickName: 'qianguyihao',
    age: 28,
};

function fn1(a, b) {
    console.log(this);
    console.log(this.nickName);
    console.log(a + b);
}

fn1.call(obj1, 2, 4); // 先将 this 指向 obj1，然后执行 fn1() 函数
/*
	obj1
    qianguyihao
    6
*/
```

#### 继承

```javascript
// 给 Father 增加 name 和 age 属性
function Father(myName, myAge) {
    this.name = myName;
    this.age = myAge;
}

function Son(myName, myAge) {
    	this.id = 'son';
    // 【下面这一行，重要代码】
    // 通过这一步，将 father 里面的 this 修改为 Son 里面的 this；另外，给 Son 加上相应的参数，让 Son 自动拥有 Father 里的属性。最终实现继承
    Father.call(this, myName, myAge);
}

const son1 = new Son('千古壹号', 28);
console.log(JSON.stringify(son1));  // {"id":"son","name":"千古壹号","age":28}
```

### apply()

可以**调用**一个函数，与此同时，它还可以改变这个函数内部的 this 指向。这一点，和 call()类似。

```javascript
fn1.apply(想要将this指向哪里, [函数实参1, 函数实参2]);
```

* apply() 里面传入的**实参，必须是数组（或者伪数组）**

```javascript
var obj1 = {
    nickName: 'qianguyihao',
    age: 28,
};

function fn1(a) {
    console.log(this);
    console.log(this.nickName);
    console.log(a);
}

fn1.apply(obj1, ['hello']); // 先将 this 指向 obj1，然后执行 fn1() 函数
/*
	obj1
    qianguyihao
	hello
*/
```

### bind() 

如果有些函数，我们不需要立即调用，但是又想改变这个函数内部的this指向，此时用 bind() 是最为合适的。

```javascript
新函数 = fn1.bind(想要将this指向哪里, 函数实参1, 函数实参2);
```

### 面向对象

#### 创建对象

##### 对象字面量

```javascript
const obj2 = {
            name: "千古壹号",
            age: 26,
            isBoy: true,
            // 还可以存放一个嵌套的对象
            test: {
                id: 123,
                tel: 180
            }
		    //我们还可以在对象中增加一个方法。以后可以通过obj.sayName()的方式调用这个方法
            sayName: function() {
                console.log(this.name);
            }
        };

console.log(JSON.stringify(obj));
```

##### 利用构造函数

```javascript
//利用构造函数自定义对象
var stu1 = new Student('smyh');
console.log(stu1);
stu1.sayHi();

var stu2 = new Student('vae');
console.log(stu2);
stu2.sayHi();

// 创建一个构造函数
function Student(name) {
    this.name = name; //this指的是当前对象实例【重要】
    this.sayHi = function () {
        console.log(this.name + '厉害了');
    };
}
```

#### 构造函数

```javascript
// 创建一个构造函数，专门用来创建Person对象
function Person(name, age, gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.sayName = function () {
        alert(this.name);
    };
}

var per = new Person('孙悟空', 18, '男');
var per2 = new Person('玉兔精', 16, '女');
var per3 = new Person('奔波霸', 38, '男');
```

#### 获取对象属性

for in：遍历对象的属性

```javascript
for (var key in obj) {
    console.log(key); // 这里的 key 是：对象属性的键（也就是属性名）
    console.log(obj[key]); // 这里的 obj[key] 是：对象属性的值（也就是属性值）
}
```



1. fasd
2. f
3. asd
4. f
5. asdf
6. a
7. sdf
8. a
9. sdf
10. asd
11. f
12. asd
13. fa
14. sdf
15. asd
16. asdf
17. asd
18. fasfd

