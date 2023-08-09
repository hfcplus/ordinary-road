# 数组

## 定义数组与赋值

数据类型[] 数组名 = new 数组类型[元素个数]；

```java
int[] numbers = new int[20]; // 后面动态赋值
```

数据类型[] 数组名 = {值1， 值2， 值3 ......};

```java
int[] ints = {12, 34, 56, 78};
```

## 遍历数组

```java
// 快捷键 iter
for (int i : ints1) {  
    System.out.print(i + "   ");
}
```

## 数组默认值

| 数据类型 | 默认值                |
| -------- | --------------------- |
| int      | 0                     |
| double   | 0.0                   |
| char     | 码值为0的字符'\u0000' |
| 对象     | null                  |

## 数组扩容

1. 定义大数组

2. 将原来的数组元素复制到大数组中

   ```java
   System.arraycopy(src, srcPos, dest, destPos, length);
   // 把src（原数组）从srcPos（起始下标）开始长度为length的元素复制到dest（目标数组）数组的destPos（起始下标）的位置上
   System.arraycopy(small, 3, big, 5, small.length-4);//把small数组从下标为3到最后的元素复制到big的下标为5的位置上
   ```

3. 将原来的数组指向大数组

## 数组插入元素与删除元素

```java
int[] small = {1, 2, 3, 4, 5, 6};
int[] big = new int[small.length + 1];
System.arraycopy(small, 0, big, 0, 3);
big[3] = 9;
System.arraycopy(small, 3, big, 4, 3);
small = big;
for (int i : small) {
    System.out.print(i + "  ");
}
System.out.println();
```

## 变长参数可以当作数组使用

```java
// 一个函数只能有一个变长参数，且变长参数只能是最后一个参数
// 相同类型的数组可以当作变长参数
public static void sum(int... data) {
    // 变长参数
    int length = data.length;
    for (int i = 0; i < data.length; i++) {
        System.out.println(data[i]); // 把变长参数当作数组使用
    }
    
    int sum = 0;
    for (int datum : data) {
        sum += datum;
    }
    System.out.println(sum);
}
sum();
sum(1, 2 , 3 ,4);
int[] nums = {1, 2, 3, 4, 5};
sum(nums);
```

## Java自带Arrays类的常用方法

|                 方法                  |                             解释                             |
| :-----------------------------------: | :----------------------------------------------------------: |
|        Arrays.toString(array)         |                       将数组变成字符串                       |
|  Arrays.copyOf(oldArray, newLength)   |          将旧的数组复制成新的数组，长度为newLength           |
| Arrays.copyOfRange(array, start, end) |            将旧的数组 [start, end) 复制给新的数组            |
|          Arrays.sort(array)           |                      将数组从小到大排序                      |
|    Arrays.binarySearch(array, key)    |  二分查找key，找到返回第一次找到的下标，没找到返回小于0的数  |
|         Arrays.asList(T...a)          | 可以把若干数据转换为List集合, 方法形参是变长参数, 可以接收任意个数据,也可以接收一个数组 |



1. Arrays.toString(array); // 将数组变成字符串

   ```java
   int[] array = {1, 2, 3, 10, 5};
   System.out.println(Arrays.toString(array));
   // [1, 2, 3, 10, 5]
   ```

   

2. Arrays.copyOf(oldArray, newLength); // 将旧的数组复制成新的数组，长度为newLength

   ```java
   int[] array = {1, 2, 3};
   int[] newArray = Arrays.copyOf(array, 6);
   //[1, 2, 3, 0, 0, 0]
   ```

   

3. Arrays.copyOfRange(array, start, end); //将旧的数组 [start, end) 复制给新的数组

   ```java
   int[] array = {1, 2, 3, 4};
   int[] newArray = Arrays.copyOfRange(array, 1, 3);
   //[2, 3]
   ```

   

4. Arrays.sort(array); // 将数组从小到大排序

   ```java
   int[] array = {3, 2, 4};
   Arrays.sort(array);
   // [2, 3, 4]
   ```

   

5. Arrays.binarySearch(array, key);// 二分查找key，找到返回第一次找到的下标，没找到返回小于0的数

   ```java
   int[] array = {3, 2, 4};
   int temp1 = Arrays.binarySearch(array, 2); // 1
   int temp2 = Arrays.binarySearch(array, 6); // -1
   ```


6. static <T> List<T> asList(T... a) ; // 可以把若干数据转换为List集合, 方法形参是变长参数, 可以接收任意个数据, 也可以接收数组

   ```java
   List<Integer> integerList = Arrays.asList(32, 45, 56, 786, 89, 90);
   Integer[] array = {32, 401, 12};
   List<Integer> integers = Arrays.asList(array);
   ```

   

# 二维数组（too easy to write）
