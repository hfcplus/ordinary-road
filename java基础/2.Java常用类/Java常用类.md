# String

String 底层定义了 char[] value 来保存字符

## 创建String类

1. String s = "asdf";  

   >"asdf" 才是真正的String对象，在字符串常量池中创建"hello"对象,把这个对象的引用赋值给s

2. String s = new String(byte[] bytes);//使用当前默认的编码，将bytes数组传换成字符串

   ```java
   // String s = new String(byte[] bytes);
   byte[] bytes = {97, 98, 99, 100, 101, 102, 103, 104, 105};
   String s = new String(bytes);// "abcdefghi"
   
   // String s = new String(byte[] bytes, "编码");
   String s = new String(bytes, "GBK");
   ```

3. String s = new String(byte[] bytes, int offset, int length);// 将bytes数组的从下标为offset开始的length个字节转换成字符串

   ```java
   // String s = new String(byte[] bytes, int offset, int length);
   byte[] bytes = {97, 98, 99, 100, 101, 102, 103, 104, 105};
   String s = new String(bytes, 2, 5);// "cdefg"
   ```

4. String s = new String(char[] chars);//将字符数组chars全部转换成字符串

   ```java
   // String s = new String(char[] chars);
   char[] chars = {'a', 'b', 'c', '9', '陈', 97, 457};
   String s = new String(chars);// "abc9陈aǉ"
   ```

5. String s= new String(char[] chars, int offset, int count);//将chars的下标从offset开始的一共count个字符传换成字符串

   ```java
   // String s= new String(char[] chars, int offset, int count);
   char[] chars = {'a', 'b', 'c', '9', '陈', 97, 457};
   String s = new String(chars, 2, 5); // c9陈aǉ
   ```

## 常用方法

|                 方法                 |                 解释                  |
| :----------------------------------: | :-----------------------------------: |
|        char charAt(int index)        |         返回下标为index的字符         |
|             int length()             |           返回字符串的长度            |
|      int compareTo(String str)       |          两个字符串比较大小           |
| int compareToIgnoreCase(String str)  |          忽略大小写比较大小           |
|     boolean contains(String str)     |               是否包含                |
|    boolean startWith(String str)     |               以str开始               |
|     boolean endWith(String str)      |               以str结尾               |
|      boolean equals(String str)      |              值是否相等               |
| boolean equalsIgnoreCase(String str) |      值是否相等（不区分大小写）       |
|          byte[] getBytes();          |            转换为字节数组             |
| byte[] getBytes(String charSetName); | 以charSetName为编码格式转换成字节数组 |
|      String concat(String str)       |           返回原字符串+str            |

**compareTo**

![09 字符串比较大小](Java%E5%B8%B8%E7%94%A8%E7%B1%BB.assets/09%20%E5%AD%97%E7%AC%A6%E4%B8%B2%E6%AF%94%E8%BE%83%E5%A4%A7%E5%B0%8F.png)

asdf

# Date （反人类，能不用就不用）

## 创建Date

```java
Date date = new Date(); // 返回当前时间 Fri Feb 18 16:34:45 CST 2022
Date date = new Date(Long time); // 把time转换成 Date对象
```

## 常用方法

|       方法       |                    解释                    |
| :--------------: | :----------------------------------------: |
| Long getTime();  | Date对象距离1970-1-1 00:00:00 经过的毫秒数 |
| 其余的都被弃用了 |                                            |

## 把Date对象转换为指定格式的字符串

### SimpleDateFormat.format(Date date)

```java
Date date = new Date(); // 返回当前时间  Fri Feb 18 16:34:45 CST 2022
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String str = sdf.format(date);  // 2021-10-25 22:10:10
```

## 把字符串对象转换为Date

### SimpleDateFormat.format.parse(String text)

```java
String text = "2086年5月6日 7:28:58";
//根据日期格式创建SimpleDateFormat对象,
SimpleDateFormat another = new SimpleDateFormat("yyyy年M月d日 H:mm:ss");
//调用another对象的parse(String)方法可以把字符串解析为Date对象, 该方法有检查 异常需要预处理, 当前选择抛出处理. Alt+enter选择 Add exception to method signature. 运行程序后,如果 这一行产生了异常, 说明  another对象的格式串与text字符串不匹配
date = another.parse(text);
System.out.println( date);
```

# Calendar(日历类)

## 创建Calendar对象

```java
//创建Calendar对象, Calendar是抽象类, 提供了静态方法getInstance()返回本类的对象
Calendar calendar = Calendar.getInstance();
```

## 常用方法

|                方法                 |           解释           |
| :---------------------------------: | :----------------------: |
|     calendar.get(Calendar.YEAR)     |         返回年份         |
|    calendar.get(Calendar.MONTH)     | 返回月份，比正式月份小1  |
| calendar.get(Calendar.DAY_OF_MONTH) |            日            |
|     calendar.get(Calendar.HOUR)     |      12小时制的小时      |
| calendar.get(Calendar.HOUR_OF_DAY)  |      24小时制的小时      |
|    calendar.get(Calendar.MINUTE)    |           分钟           |
|    calendar.get(Calendar.SECOND)    |            秒            |
|       calendar.add(字段, 值);       | 在指定的字段上加上一个值 |
|  calendar.add(Calendar.YEAR, -2);   |         year - 2         |



# LocalDateTime（推荐使用）

## 创建LocalDateTime对象

```java
// LocalDateTime类的构造方法使用private修饰为私有的, 该类提供静态方法now()可以返回当前日期时间对象 
LocalDateTime localDateTime = LocalDateTime.now(); // 2022-02-18T21:50:15.062

// 返回指定的日期时间对象
LocalDateTime localDateTime2 = LocalDateTime.of(2022, 5, 1, 8, 28, 58, 222); // 2022-05-01T08:28:58.000000222
```

## 常用方法

|         方法         |             解释             |
| :------------------: | :--------------------------: |
|    int getYear();    |            返回年            |
|  Month get mouth();  | 返回枚举类Month （英文月份） |
| int getMonthValue(); |         返回月份的值         |
|  int getDayOfMonth   |           返回日子           |
|     int getHour      |       小时（24小时制）       |
|    int getMinute     |             分钟             |
|    int getSecond     |              秒              |
|     int getNano      |             纳秒             |



## 将LocalDateTime对象转换为指定格式的字符串

### DateTimeFormatter

```java
// 该类的构造方法没有public修饰, DateTimeFormatter类提供了静态方法ofPattern(String)创建指定格式的对象
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
String s = localDateTime.format(dtf); // 与 SimpleDateFormat相反
```

## 把字符串转换为LocalDateTime对象

```java
String text = "2086年5月6日 7:28:58";
//根据字符串格式创建DateTimeFormatter对象
DateTimeFormatter another = DateTimeFormatter.ofPattern("yyyy年M月d日 H:mm:ss");
//调用 LocalDateTime类的静态方法parse(String text, DateTimeFormatter)可以把字符串解析为日期时间对象, 运行后,如果 这一行产生了异常,说明DateTimeFormatte对象的格式串与字符串不匹配
LocalDateTime parseTime = LocalDateTime.parse(text, another);
```

# BigInteger和BigDecimal

```
BigInteger bigInteger = new BigInteger("321326546546543213213");
BigDecimal bigDecimal = new BigDecimal("321326546546543213213.123132156465");
```

## 常用方法

只能与相同类型的变量做运算

|                       方法                        |                             解释                             |
| :-----------------------------------------------: | :----------------------------------------------------------: |
|                       add()                       |                              加                              |
|                    subtract()                     |                              减                              |
|                    multiply()                     |                              乘                              |
| divide(BigDecimal b, int scale, int roundingMode) | 除//BigInteger相除结果为整数，BigDecimal相除结果可能为小数，除不尽时需要指定小数位数scale以及小数保留规则roundingMode：BigDecimal.ROUND_FLOOR... |

# 包装类

Byte, Short, Integer, Long, Float, Double, Character, Boolean



## 自动装箱与拆箱

### 	自动装箱

```java
Integer i = 123; // i变量是Integer类定义的引用类型变量, 不能把123整数保存到i变量中, 系统执行这一行时, 会根据整数123创建一个Integer对象，然后把对象的引用赋值给i
```

### 	自动拆箱

```java
Integer i = 123;
int num = i; //num是基本类型变量, i变量保存的是堆中对象的引用, 不能直接把i变量的值赋值给num, 系统执行这一行时, 把i变量引用 的Integer对象的value属性值赋值给num
 //大多数情况下, 基本类型与包装类对象可以混用       
```

## Integer面试

```java
//面试
Integer i2 = 66;
Integer i3 = 66;
System.out.println( i2 == i3 );     //true.  Java认为-128~127范围内的整数使用最频繁, 这个范围内的整数自动装箱后保存到共享池(缓存)中, 采用享元模式. 程序执行i2=66这一行时,先检查共享池中是否存在value值为66的Integer对象, 不存在则在共享池中创建一个value值为66的Integer对象,把这个对象的引用赋值给i2;   再执行i3=66时,系统检查到共享池中存在value值为66的Integer对象, 就直接把这个对象的引用赋值给i3;  现在i2与i3两个对象名都引用 了共享池中同一个对象

Integer i4 = new Integer(66);
System.out.println( i2 == i4 );     //false

Integer i5 = Integer.valueOf(66);
System.out.println( i2 == i5 );     //true

Integer i6 = Integer.valueOf("66");
System.out.println( i2 == i6 );     //true

Double d1 = 3.14;
Double d2 = 3.14;
System.out.println( d1 == d2 );     //false
```



# 集合

![Collection下的接口与继承类简易结构图](Java%E5%B8%B8%E7%94%A8%E7%B1%BB.assets/Collection%E4%B8%8B%E7%9A%84%E6%8E%A5%E5%8F%A3%E4%B8%8E%E7%BB%A7%E6%89%BF%E7%B1%BB%E7%AE%80%E6%98%93%E7%BB%93%E6%9E%84%E5%9B%BE.png)

## Iterable(接口)

|          方法          |  解释  |
| :--------------------: | :----: |
| Iterator<T> iterator() | 迭代器 |

#### 	Iterator （类）

|      定义的方法       |              解释               |
| :-------------------: | :-----------------------------: |
|   boolean hasNext()   |    判断游标后面是还还有元素     |
|       E next()        | 返回游标后面的元素,并且游标下移 |
| default void remove() |   把刚刚next()返回的元素删除    |



## Colletion（接口）

### 	创建Colletion集合

```java
//Collection是一个接口, 需要赋值实现类对象
//Collection collection = new HashSet();
Collection collection = new ArrayList();

// 使用泛型
// 泛型可以在编译阶段进行数据类型检查
Collection<String> stringCollection = new ArrrayList<>();

Collection<String> collection1 = new ArrayList<>();
Collection<String> collection2 = new ArrayList<>();
Collection<String> collection3 = new ArrayList<>();
// 分别向collection1， collection2中添加元素
//...
collection3.addAll(collection1);
collection3.addAll(collection2);
Collection<String> collection4 = new ArrayList<>(collection3); 
```

### 	方法

|                定义的方法                 |                            解释                             |
| :---------------------------------------: | :---------------------------------------------------------: |
|         Iterable接口中定义的方法          |                   全部继承，但是全未实现                    |
|             boolean add(E e)              |                            添加                             |
| boolean addAll(Collection<? extends E> c) |                          全部添加                           |
|               void clear()                |                          全部删除                           |
|        boolean contains(Object o)         |                          是否包含                           |
|         boolean equals(Object o)          |                          是否相等                           |
|             boolean isEmpty()             |                          是否为空                           |
|         boolean remove(Object o)          |   从集合中删除第一个匹配的元素,成功返回true,失败返回false   |
|    boolean removeAll(Collection<?> c)     | 删除与c中元素一致的所有元素（多个元素同一个值会被全部删除） |
|    boolean retainAll(Collection<?> c)     |  保留和c中元素相同的所有元素(多个元素同一个值会被全部保留)  |
|                int size()                 |                          返回个数                           |
|          <T> T[] toArray(T[] a)           |          把集合元素复制到数组a中，并且返回一个数组          |

### 	迭代

```java
// 快捷键 itco
// 使用请看Iterator 类的方法
```



## List (接口) 

```java
//1) List接口继承了Collection接口, Collection有的操作List集合都能继承到
//	 List接口赋值实现类对象, List集合通过泛型指定存储元素的类型,如创建List集合存储String字符串
List<String> stringList = new ArrayList<>();
```



### 	方法

|                    方法                     |                             解释                             |
| :-----------------------------------------: | :----------------------------------------------------------: |
|       Collection接口中定义的所有方法        |                           全部继承                           |
|       void add(int index, E element)        |                    在下标为index添加元素                     |
|              E get(int index)               |                    获取下标为index的元素                     |
|            int indexOf(Object o)            |                    该元素第一次出现的位置                    |
|          int lastIndexOf(Object o)          |                   该元素最后一次出现的位置                   |
|       ListIterator<E> listIterator()        |                     返回ListIterator对象                     |
|             E remove(int index)             |   删除下标为index的元素，集合从i+1开始的元素的索引值都减1    |
|         E set(int index, E element)         |                修改下标为index的元素为element                |
|                 int size()                  |                           获取大小                           |
| default void sort(Comparator<? super E> c)  |                             排序                             |
| List<E> subList(int fromIndex, int toIndex) | 获取 [fromIndex, toIndex)的元素视图(操作subList返回的集合会影响到原集合) |

### 	ListIterator(类)

|         方法          |          解释          |
| :-------------------: | :--------------------: |
|     继承Iterator      | 继承Iterator的所有方法 |
|     void add(E e)     |  在游标的位置添加元素  |
| boolean hasPrevious() |   游标前面是否有元素   |
|    int nextIndex()    |   返回后面元素的下标   |
|     E previous()      |     游标前面的元素     |
|  int previousIndex()  |     前面元素的下标     |
|     void set(E e)     |          修改          |

### 	sort（排序）

List.sort(Comparator); // 形参Comparator是个接口，实参可以是匿名内部类对象，也可以是实现类对象

>1. 需要排序的类继承Comparable接口,实现compareTo()方法，集合排序
>
>   ```java
>   static class Poker implements Comparable<Poker> {
>       Integer realSize; // 实际的大小，大王最大为17，小王16，2为15，A为14
>       @Override
>       public int compareTo(Poker o) {
>           return o.realSize - this.realSize;
>       }
>   }
>   // 这里有个pokerList需要排序
>   // 1 pokerList.sort();
>   pokerList.sort(Poker::compareTo);
>   // 2 Collections.sort();
>   Collections.sort(pokerList);
>   ```
>
>2.  创建比较器类，继承Comparator接口，实现compare()方法，其他类可以使用这个比较类来排序
>
>   ```java
>   static class PokerComparator implements Comparator<Poker> {
>       @Override
>       public int compare(Poker o1, Poker o2){
>           return o1.realSize - o2.realSize;
>       }
>   }
>   
>   // 这里有个pokerList需要排序
>   PokerComparator PokerComparator = new PokerComparator(); // 获得比较器
>   pokerList.sort(PokerComparator) // 实参是接口Comparator 的实现类对象
>   ```
>
>3.  List比较时，实参可以是匿名内部类对象
>
>   ```java
>   // 这里有个pokerList需要排序
>   pokerList.sort(new Comparator<Poker>() {
>       @Override
>       public int compare(Poker o1, Poker o2) {
>            return o1.realSize - o2.realSize;
>       }
>   });
>   ```
>
>   
>
>```java
>list.sort(new Comparator<String>() {
>@Override
>public int compare(String o1, String o2) {
>   //在匿名内部类中重写接口抽象方法compare(),在该方法中定义一个比较规则
>   //o1大于o2返回正数, o1等于o2返回0, o1小于o2返回负数, 对应 升序排序
>   //o1大于o2返回负数, o1等于o2返回0, o1小于o2返回正数, 对应 降序排序
>   //compare方法当前参数是String类型,因为list集合存储的是String字符串, String字符串调用compareTo()比较大小
>   return o1.compareTo(o2);    //o1比o2大返回正数,对应 升序
>}
>});
>```

### 	注意

>1. 一次性删除多个元素时推荐使用迭代器Iterator，不会出现下标越界的问题
>
>2. 因为List新增了remove(int index)的方法，所以删除元素时，默认使用下标删除，删除Integer的集合的某个元素时可能会报错
>
>   ```java
>   // 推荐使用迭代器删除，这样就不会出现下面的问题
>   List<Integer> list = new List<Integer>();
>   list.add(123);
>   list.remove(Integer.valueOf(123)); // 删除value为123的Integer对象，不会删除下标为123的对象
>   ```

## Queue（队列接口）

|        方法        |             解释             |
| :----------------: | :--------------------------: |
| 继承Collection接口 |             ....             |
|      offer(o)      |    把o元素添加到链表尾部     |
|       poll()       | 把链表的第一个元素删除并返回 |
|       peek()       |    返回第一个元素,不删除     |

## Deque（双端队列接口）

|         方法          |                  解释                   |
| :-------------------: | :-------------------------------------: |
|     offerFirst()      |                                         |
|      offerLast()      |                                         |
|      pollFirst()      |                                         |
|      pollLast()       |                                         |
|      peekFirst()      |                                         |
|      peekLast()       |                                         |
|        push()         | 入栈, 把元素o添加到链表头部(第一个元素) |
|         pop()         |  出栈, 就是把链表第一个元素删除并返回   |
| (add,get,remove)First |                                         |
| (add,get,remove)Last  |                                         |

## LinkedList (双向链表)

|           方法           | 解释 |
| :----------------------: | :--: |
| 实现了List接口的所有方法 | ...  |
| 实现了Deque双端队列接口  |      |

### 	注意

>当LinkedList 为空时，调用(add,get,remove)First会报错，peek,poll,(peek,poll)First会返回null

## ArrayList与Vector

ArrayList与Vector底层数据结构都是数组; 数组的初始化容量: 10;     扩容: ArrayList按1.5倍大小扩容, Vector按2倍大小扩容; 区别在于Vector是线程安全的,ArrayList不是 线程安全的

## Set (接口 无序不重复)

|      方法       | 解释 |
| :-------------: | :--: |
| 继承了Colletion | ...  |

## HashSet

HashSet集合存储顺序与元素的哈希码有关

```java
// Student类没有重写equals()/hashCode()方法，Student对象的哈希码就是对象在堆中的地址 ,  equals()方法默认使用==判断
HashSet<Student> hashSet = new HashSet<>();
hashSet.add(new Student("lisi", 24));
Student stu =new Student("lisi", 24);
System.out.println( hashSet.contains(stu) ); // false
```

```java
// Student类了重写equals()/hashCode()方法，Student对象的哈希码就是对象在堆中的地址 ,  equals()方法默认使用==判断
HashSet<Student> hashSet = new HashSet<>();
hashSet.add(new Student("lisi", 24));
Student stu =new Student("lisi", 24);
System.out.println( hashSet.contains(stu) ); //true. 在调用HashSet集合的contains(stu)方法时, 先根据stu对象的哈希码计算 存储位置i, 来到i位置查看是否存在equals相等的元素,
// 因为重写了equals()/hashCode()，所以现在stu对象的哈希码与添加时(lisi,24)对象的哈希码一样, 计算出来的存储位置就一样, 进行equals()判断也相等
```

### 注意

>1. 当集合中对象的属性值修改后,可能在集合中找不到这个元素了
>
>   ```java
>   // Student类重写了equals()/hashcode()方法
>   HashSet<Student> hashSet = new HashSet<>();
>   Student stu = new Student("chenqi", 28);
>   hashSet.add( stu );
>   System.out.println( hashSet.contains(stu));     //true
>   stu.age = 66;
>   System.out.println( hashSet.contains(stu) );    //false
>   //在调用HashSet集合的contains(stu)方法时, 先根据stu对象的哈希码计算 存储位置i, 来到i位置查看是否存在equals相等的元素,
>   //修改了stu对象的属性值, 导致现在stu对象的哈希码与第18时添加时的哈希码不一样了, 现在计算出来的存储位置与第18时添加时的存储位置不一样
>   ```

## TreeSet

TreeSet实现了SortedSet接口, 可以对集合中的元素自然排序, 要求集合中的元素必须可比较大小

1. 新建TreeSet类时指定Comparator比较器

   ```java
   TreeSet<Student> treeSet1 = new TreeSet<>(new Comparator<Student>() {
       @Override 
       public int compare(Student o1, Student o2) {
           return o2.name.compareTo(o1.name); // 倒序
       }
   });
   ```

2. 元素的类实现Comparable接口

   ```java
   TreeSet<String> treeSet2 = new TreeSet<>(); // String字符串本身能够比较大小,实现Comparable接口.
   ```




## Collections类的方法

在java.util包中定义Collections类, 该类提供一组操作集合的方法

|                             方法                             |                     解释                     |
| :----------------------------------------------------------: | :------------------------------------------: |
| static <T> boolean **addAll**(Collection<? super T> c, T... elements) |            向集合c中添加若干数据             |
| static <T> void **sort**(List<T> list, Comparator<? super T> c) |       对c集合排序,使用Comparator比较器       |
| static <T extends Comparable<? super T>> void sort(List<T> list) | 对集合c排序, 要求集合中的元素本身具有 可比性 |
|                                                              |                                              |
|                                                              |                                              |
|                                                              |                                              |
|                                                              |                                              |



# Map

![Map继承](Java%E5%B8%B8%E7%94%A8%E7%B1%BB.assets/Map%E7%BB%A7%E6%89%BF.png)

### 	方法

|                             方法                             |                       解释                        |
| :----------------------------------------------------------: | :-----------------------------------------------: |
|                         void clear()                         |                       清空                        |
|               boolean containsKey(Object key)                |                    是否包含键                     |
|             boolean containsValue(Object value)              |                    是否包含值                     |
|                Set<Map.Entry<K,V>> entrySet()                |         获取所有的entry，entry就是键值对          |
|                   boolean equals(Object o)                   |                                                   |
| default void forEach(BiConsumer<? super K,? super V> action) |                                                   |
|                      V get(Object key)                       |                                                   |
|                      boolean isEmpty()                       |                                                   |
|                       Set<K> keySet()                        |                   获取所有的key                   |
|                    V put(K key, V value)                     | 如果key相同就覆盖并且返回旧的值，否则添加返回null |
|                     V remove(Object key)                     |             通过键删除，并且返回value             |
|              default V replace(K key, V value)               |                       修改                        |
|                          int size()                          |                       大小                        |
|                    Collection<V> values()                    |                     所有的值                      |

### 注意

> 1. Map集合中键值对存储顺序与添加顺序可能不一样
>
> 2. Map集合的键不允许重复, put(k,v)添加键值对时,如果k键已存在, 则使用新的v值替换掉map中k键的值, 返回旧的value值.  如果put添加时k键不存在,是第一次添加,那么方法返回null
>
>    ```java
>    System.out.println(map.put("first", 1)); // 第一次的添加 返回null
>    System.out.println(map.put("first", 2)); // 返回1，键为first的map的value为2
>    ```
>
> 3. keySet(), values(), entrySet()这些方法并没有创建新的集合,对它们进行操作时, 实际上是对map对应的数据操作

## Properties

### 读取.properties的属性文件，读取属性

```java
//1) 创建Properties
Properties properties = new Properties();

//2)在当前程序与属性文件之间建立字节流通道. 可以使用当前类的字节码的类加载器创建
InputStream in = Test.class.getClassLoader().getResourceAsStream("resources/myconfig.properties");

//3) 加载, 调用load(InputStream)方法,该方法有检查异常需要预处理,当前选择抛出处理. 运行程序后,如果这一行产生了异常,说明上面属性文件路径不正确
properties.load(in);

//4) 读取
System.out.println( properties.getProperty("username"));
System.out.println( properties.get("password"));
System.out.println( properties.getProperty("国籍"));
System.out.println( properties.getProperty("usernameeeeee"));   //null, 属性名不存在

//5)关闭流
in.close();
```

### ResourceBundle读取属性

```java
//创建ResourceBundle对象, ResourceBundle类是抽象类, 该类提供了一个静态方法getBundle(基础路径)返回本类对象, 注意基础路径不需要扩展名
ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/myconfig");   //如果这行产生异常说明属性文件路径不正确

//调用 ResourceBundle 对象的getString(属性名)获得属性值
System.out.println( resourceBundle.getString("username"));
System.out.println( resourceBundle.getString("password"));
System.out.println( resourceBundle.getString("国籍"));
```

## TreeMap

### 只能根据键排序, 不能根据值排序

注意：修改了treeMap的键的值时候，可能containsKey()找不到该键

1. 在构造方法中指定Comparator比较器

   ```java
   //创建TreeMap存储<姓名,成绩>, 在构造方法中指定Comparator比较器,根据姓名降序排序
   TreeMap<String,Integer> treeMap1 = new TreeMap<>(new Comparator<String>() {
       @Override
       public int compare(String o1, String o2) {
           return o2.compareTo(o1);
       }
   });
   ```

2. 如果不在构造方法指定Comparator,要求键本身具有可比性,即键的类要实现Comparable接口. 学生姓名是String字符串,String类已经实现了Comparble接口, 即String字符串具有可比性

### TreeMap增加了针对第一个/最后一个元素的操作

```java
//TreeMap增加了针对第一个/最后一个元素的操作
String firstKey = treeMap2.firstKey();  //返回第一个键
Map.Entry<String, Integer> firstEntry = treeMap2.firstEntry();  //返回第一个Entry
treeMap2.pollFirstEntry();  //删除第一个
treeMap2.pollLastEntry();   //删除最后一个
```

## HashMap

注意：HashMap的键是自定义类型, 修改键属性值后,导致键对象的哈希码变化了,从集合中找不到这个键了

```java
// Car类重写eqlues()和hashCode()方法
Map<Car, Object> stringObjectMap = new HashMap<>();
Car mycar = new Car("wahaha", 160);
stringObjectMap.put(mycar, "hfc");
System.out.println(stringObjectMap.containsKey(mycar));  // true
mycar.price = 159;
System.out.println(stringObjectMap.containsKey(mycar));  // false
//向HashMap集合中put添加键值对时,根据键的哈希码计算存储位置;  调用containsKey(key)方法时,也是根据key键的哈希码计算存储位置;  修改了mycar对象的价格导致了mycar对象的哈希码发生了变化, 根据当前mycar对象的属性计算的哈希码与最开始添加时计算出来的哈希码不一样了. 出现了put添加时存储到i位置, 再第32行查找时来到x位置查找, 不存在
mycar.price = 160;
System.out.println(stringObjectMap.containsKey(mycar));  //true
```



## Lambda 表达式

### 	使用时机

>实现接口参数时，重写接口方法，接口方法只有一个时

### 使用规则

>1. 参数的类型可以省略
>2. 当参数只有一个时，可以省略小括号
>3. 当lambda表达式只有一条语句时可以省略大括号
>4. 当lambda表达式只有一条语句时可以省略大括号，那条唯一的语句是return时，return也必须省略
>
>```java
>stringList.sort(new Comparator<String>() {
>    @Override
>    public int compare(String o1, String o2) {
>        return o1.compareTo(o2);
>    }
>});
>// 使用lambda表达式简写
>stringList.sort((o1, o2) -> o1.compareTo(o2)); // 运用规则1，3，4
>```
>
>```java
>stringList.forEach(new Consumer<String>() {
>    @Override
>    public void accept(String s) {
>        System.out.println(s);
>    }
>});
>// 使用lambda表达式简写
> stringList.forEach(s -> System.out.println(s)); // 运用规则2，3
>```
>
