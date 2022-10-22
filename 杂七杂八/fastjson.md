依赖

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.70</version>
</dependency>
```

对象转json，或者字符串

```java
public void toJSON(){
    Student student = new Student(2001, "hfc", "男", 25);
    Object o = JSONObject.toJSON(student);
    String string = JSONObject.toJSONString(student);//{"age":25,"id":2001,"name":"hfc","sex":"男"}
    System.out.println(o);
    System.out.println(string);
}
```

字符串或者json转为原对象

```java
 public void toObject(){
        String json = "{\"age\":25,\"id\":2001,\"name\":\"hfc\",\"sex\":\"男\"}";
        Student student = JSONObject.parseObject(json, Student.class);
        System.out.println(student);
    }
```

字符串或者json转为JSONObject(底层是map)

```java
 public void toObject(){
        String json = "{\"age\":25,\"id\":2001,\"name\":\"hfc\",\"sex\":\"男\"}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer id = jsonObject.getInteger("id");
        System.out.println(id);
        String name = jsonObject.getString("name");
        System.out.println(name);
    }
```

