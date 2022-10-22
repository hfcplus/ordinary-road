

```java
    public static void main(String[] args) {

        List<String> ls = new ArrayList<>();
        ls.add("小白");
        ls.add("小黑子");
        ls.add("蓝天白");
        ls.add("小红");

        //过滤出长度为3的字符串
        ls.stream().filter( s -> s.length()==3 );

        //foreach循环打印
        ls.stream().filter( s -> s.length()==3 ).forEach(System.out::println);

        //count计数
        long count = ls.stream().filter(s -> s.length() == 3).count();
        System.out.println(count);
        
        //limit获取前几个
        ls.stream().filter(s -> s.length() == 3).limit(1).forEach(System.out::println);

        //skip跳过前几个
        ls.stream().filter(s -> s.length() == 3).skip(1).forEach(System.out::println);

        //map映射，变为新的字符串
        ls.stream().map(a -> a + "新的字符串").forEach(System.out::println);

        //map转为一个学生对象
        ls.stream().map( b -> new Student(b,22)).forEach(System.out::println);

    }

```

### filter

```javascript
List<Student> studentList = studentService.findAll();
List<Student> result = studentList.stream().
								  .filter(student -> student.age < 18 && student.height > 160 && student.weight > 45)
								   .collect(Collectors.toList());
List<Student> result2 = studentList.stream()
								   .filter(student -> student.age < 18)
								   .filter(Student.heightBiggerThan160 .and(weightBiggerThan45))
 								   .collect(Collectors.toList());
// 上面两个等价
List<Student> result3 = studentList.stream()
								   .filter(Student.heightBiggerThan160.negate() .and(weightBiggerThan45) .negate()) // 取反
 								   .collect(Collectors.toList());
```

```java
public class Student{
    private String name;
    private float age;
    private float height;
    private float weight;
    public static Predicate<Student> heightBiggerThan160 = student -> student.height > 160;
    public static Predicate<Student> weightBiggerThan45 = student -> student.weight > 45;
    
}
```

```java
filter(xxxx.negate()xxxxx.negate());//.negate()对前面的所有取反
```

