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
>   @Override
>   public int compare(String o1, String o2) {
>       return o1.compareTo(o2);
>   }
>});
>// 使用lambda表达式简写
>stringList.sort((o1, o2) -> o1.compareTo(o2)); // 运用规则1，3，4
>```
>
>```java
>stringList.forEach(new Consumer<String>() {
>   @Override
>   public void accept(String s) {
>       System.out.println(s);
>   }
>});
>// 使用lambda表达式简写
>stringList.forEach(s -> System.out.println(s)); // 运用规则2，3
>```

