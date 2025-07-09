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

## 实现FactoryBean接口

```java
public class MyFactory implements FactoryBean<String> {

    @Override
    public String getObject() throws Exception {
        System.out.println("执行getObject()方法");
        return "实现FactoryBean接口获取延迟bean";
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
```

```xml
<bean id="myFactory" class="plus.hf.pojo6.MyFactory"> </bean>
<!-- 定义myFactory bean，当容器初始化时不会创建实例化这个bean，当使用这个bean时才会会实例化这个bean -->
```

> 



# bean的实例化过程

1. Spring容器在进行初始化时，会将xml配置的<bean>的信息封装成一个BeanDefinition对象
2. 所有的BeanDefinition存储到beanDefinitionMap的Map集合
3. Spring框架在对该Map进行遍历，使用反射创建Bean实例对象，并创建好的Bean对象存储singletonObjects的Map集合中
4. 调用getBean方法 时则最终从该Map集合中取出Bean实例对象返回。



简略过程图

![img](https://cdn.nlark.com/yuque/0/2025/png/34997279/1740060770724-6a796662-5524-4e4c-9af8-ffbb95a47338.png)



# Bean工厂后处理器

## BeanFactoryPostProcessor

在第2步：BeanDefinition存储到beanDefinitionMap的Map集合前

* 可以注册BeanDefinition
* 可以修改BeanDefinition

```java
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        BeanDefinition beanDefinition = new RootBeanDefinition(); // 创建BeanDefinition
        beanDefinition.setBeanClassName("plus.hf.processor.pojo1.Student"); // BeanDefinition设置class路径
        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues(); // 获取PropertyValues
        propertyValues.add("name", "hfc1"); // 给beanDefinition设置属性
        propertyValues.add("age", 22);// 给beanDefinition设置属性
        ((DefaultListableBeanFactory) configurableListableBeanFactory).registerBeanDefinition("stu1", beanDefinition); // 强转DefaultListableBeanFactory然后注册BeanDefinition（stu）到beanDefinitionMap
    }
}
```

```xml
<bean class="plus.hf.processor.pojo1.MyBeanFactoryPostProcessor"> </bean> <!-- 注册MyBeanFactoryPostProcessor -->
```

```java
public void test() {
    ApplicationContext ac = new ClassPathXmlApplicationContext("processor/pojo1/applicationContext.xml");
    Object stu = ac.getBean("stu1");  // name:hfc age:22
    System.out.println(stu);
}
```



## BeanDefinitionRegistryPostProcessor

BeanFactoryPostProcessor的子接口，在BeanFactoryPostProcessor前，更方便的注册BeanDefinition

* 可以注册BeanDefinition（比上面的更方便）
* 可以修改BeanDefinition

```java
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        BeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClassName("plus.hf.processor.pojo1.Student");
        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.add("name", "hfc2");
        propertyValues.add("age", 22);
        beanDefinitionRegistry.registerBeanDefinition("stu2", beanDefinition); // 不用强转，直接注册
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
```



## BeanPostProcessor

在第3步：每个Bean对象添加到singletonObjects集合前

* 可以修改bean
