## 构造方法

### 无参构造（用得较多）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--由Spring容器进行对象的创建
        id: 创建的对象的名称
        class: 创建对象的类型
        程序启动时创建
     -->
    <bean id="school" class="plus.hf.pojo2.School"> <!-- 无参构造-->
        <property name="name" value="重庆工商"/> <!-- set方法注入值-->
        <property name="address" value="重庆五公里"/>
    </bean>
</beans>
```



### 有参构造 （用得极少）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

     <!-- （1）使用构造方法的参数名称注入值-->
    <bean id="stu" class="plus.hf.pojo3.Student"> <!-- 有参构造 -->
        <constructor-arg name="name" value="hfc"/> <!-- 构造函数的构造方法注入值 -->
        <constructor-arg name="age" value="22"/>
        <constructor-arg name="school" ref="school"/>
    </bean>
</beans>
```



## 工厂方法

### 静态工厂

```java
public class MyFactory {
   public static String myName(String name){ // 静态方法，不需要创建类对象就可以调用
      // 可以进行一些其他操作
      System.out.println("hahahah");
      return name;
   }
}
```

```xml
<bean id="myName" class="plus.hf.pojo4.MyFactory" factory-method="myName"> <!--MyFactory的静态方法myName创建对象-->
    <constructor-arg name="name" value="hfc"/> <!--myName方法需要参数，这里传递hfc，bean得到的值就是hfc-->
</bean>
```



### 实例工厂



```java
public class MyFactory {
   public String myName(String name){ // 实例方法，必须创建类，通过该类来调用
      // 可以进行一些其他操作
      System.out.println("hahahah");
      return name;
   }
}
```

```xml
<bean id="myFactory" class="plus.hf.pojo5.MyFactory"> </bean> <!-- 通过MyFactory的无参构造函数实例化 -->
<bean id="myName2" factory-bean="myFactory" factory-method="myName2"> <!-- 通过于myFactory的myName2方法来实例化 -->
    <constructor-arg name="name2" value="hfc2"/> <!-- myName2方法的参数 -->
</bean>
```

