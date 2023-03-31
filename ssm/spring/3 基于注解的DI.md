在spring配置文件中添加注解扫描器

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!--  基于注解的开发，添加包扫描  -->
    <context:component-scan base-package="plus.hf.pojo"/>
</beans>
```

指定多个包的三种方式

* 使用多个context:component-scan

  ```xml
   <context:component-scan base-package="plus.hf.pojo"/>
   <context:component-scan base-package="plus.hf.vo"/>
  ```

* 使用一个context:component-scan，多个包用，；空格符号分割（不推荐使用空格符）

  ```xml
  <context:component-scan base-package="plus.hf.pojo,plus.hf.vo"/>
  ```

  ```xml
  <context:component-scan base-package="plus.hf.pojo;plus.hf.vo"/>
  ```

* 指定到父级包名,容器会自动扫描该包以及它的子包，填写父包即可

  ```xml
   <context:component-scan base-package="plus.hf"/>
  ```

## 常用的注解

### 创建对象的注解

@Component :创建所有对象都可以使用此注解,除了控制器,业务逻辑层,数据访问层的对象

 @Controller:创建控制器层的对象,此对象可以**接收用户请求,返回处理结果**

 @Service:创建业务逻辑层的对象,此对象可施**事务控制,向上给控制器返回数据,向下调用数据访问层**

 @Repository:创建数据访问层的对象 ,对**数据库中的数据进行增删改查操作**

### 给对象赋值的注解

 @Value:给简单类型赋值

 @Autowired:给引用类型按类型注入

 @Qualifier:给引用类型按名称注入

### @Component 定义 Bean 的注解

在类上使用该注解，有value属性，value的值是该bean的id，不指定value值，则bean的id为类名首字母小写

```java
@Component("stu")
public class Student {
    private String name;
    private Integer age;
}
```

### @value 简单类型属性注入

用在属性上或者setter方法上，为属性注入值

用在属性上，该属性可以没有seter方法

```java
@Component
public class Student {
    
    @Value("lihua")
    private String name;
    
    @Value("22")
    private Integer age;
    
    private String sex;
    
    @Value("男")
    public void setSex(String sex) {
        this.sex = sex;
    }
}
```

### @Autowired 引用类型自动注入

@Autowired有一个属性required,默认为true，匹配失败程序终止。设置为false后，如果匹配失败，程序继续执行，未匹配的属性为null

该注解默认使用byType自动装配Bean。使用该注解完成属性注入时，类中无需 setter。当然，若属性有 setter，则也可将其加

到 setter 上

![image-20230329093631587](3%20%E5%9F%BA%E4%BA%8E%E6%B3%A8%E8%A7%A3%E7%9A%84DI.assets/image-20230329093631587.png)



**如果可注入的类型大于1个，则再按照byName进行二次匹配，如果没有匹配到则报错**

先按类型匹配到两个，再按名称匹配到唯一的

![image-20230329095014354](3%20%E5%9F%BA%E4%BA%8E%E6%B3%A8%E8%A7%A3%E7%9A%84DI.assets/image-20230329095014354.png)

### @Qualifier 引用类型按名称自动注入

这个注解需要和@Autowired联合使用。@Qualifier的value值用来匹配Bean的id

![image-20230329095915657](3%20%E5%9F%BA%E4%BA%8E%E6%B3%A8%E8%A7%A3%E7%9A%84DI.assets/image-20230329095915657.png)



### @Resource JDK自动注入

Spring 提供了对jdk 中@Resource 注解的支持，既可以按名称也可以按类型注入。默认是按名称，JDK必须是6以上

* 默认名称和类型

  ```java
  @Component("stu")
  public class Student {
      @Value("hfc")  
      private String name;
      @Value("22")
      private Integer age;
      @Resource
      private School school;
  }
  ```

  ```java
  @Component("mySchool")
  public class School {
      private String name;
      private String address;
  }
  ```

  先按照默认名称查找id为school的bean，没有找到，再按照类型查找，找到id为mySchool的bean，然后自动注入

* 指定名称

  ```java
  @Component("stu")
  public class Student {
      @Value("hfc")  
      private String name;
      @Value("22")
      private Integer age;
      @Resource(name="mySchool")
      private School school;
  }
  ```

  查找id为mySchool的bean，找到后自动注入

