## Spring的事务管理

进行事务的处理一般是在业务逻辑层， 即 Service 层

Spring进行事务管理的两种方式，大多数使用基于注解的开发

* Spring的事务注解
* Aspectj的AOP配置管理事务

## 事务的隔离级别

* 读未提交（read uncommitted）

  > 允许一个事务可以看到其他事务未提交的修改。
  >
  > 如果这个事务读取了另一个事务未提交的数据，另一个事务发生回滚就会产生脏读

* 读已提交（read committed） 

  >允许一个事务只能看到其他事务已经提交的修改，未提交的修改是不可见的。
  >
  >不会产生脏读
  >
  >现在这个事务A进行查询，查询完毕，另一个事务B提交更新（update, delete）数据，事务A再一次查询，两次查询的结果不一致，这就是不可重复读

* 可重复读（repeated read） 

  >该隔离级别为InnoDB的缺省设置。InnoDB又是MySQL的默认引擎，所以也就是MySQL的默认隔离级别
  >
  >确保如果在一个事务中执行两次相同的SELECT语句，都能得到相同的结果，不管其他事务是否提交这些修改。
  >
  >事务A正在执行，事务B插入(insert)一条数据，因为该级别的隔离导致事务A查询的结果始终不变，所以当A重复插入这条数据时(如果设置了唯一约束)会失败，但是A又查不出重复的数据，这就是幻像读。
  >
  >但是Innodb解决了幻读的问题

* 串行化（serializable） 【序列化】

  >事务最高隔离级别，效率很低，不常使用
  >
  >将一个事务与其他事务完全地隔离。

## spring中事务的传播特性

事务传播特性，就是多个事务方法调用时如何定义方法间事务的传播。Spring 定义了 7 种传播行为：

- propagation_requierd：如果当前没有事务，就新建一个事务，如果已存在一个事务中，加入到这个事务中，这是Spring默认的选择。
- propagation_supports：支持当前事务，如果没有当前事务，就以非事务方法执行。
- propagation_mandatory：使用当前事务，如果没有当前事务，就抛出异常。
- propagation_required_new：新建事务，如果当前存在事务，把当前事务挂起。
- propagation_not_supported：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
- propagation_never：以非事务方式执行操作，如果当前事务存在则抛出异常。
- propagation_nested：如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与propagation_required类似的操作。

## @Transactional的参数讲解

| 参数名称               | 参数解释                                 | 例子                                                         |
| ---------------------- | ---------------------------------------- | ------------------------------------------------------------ |
| readOnly               | 默认为false，是否为只读                  | @Transactional(readOnly = true)                              |
| rollbackFor            | 当方法抛出异常类数组中的异常时，回滚     | 单一异常：@Transactional(rollbackFor=RuntimeException.class)<br />多个异常：@Transactional(rollbackFor={RuntimeException.class,Exception.class}) |
| rollbackForClassName   | 当方法抛出异常名称数组中的异常时，回滚   | 单一异常：@Transactional(rollbackForClassName=RuntimeExecption)<br />多个异常：@Transactional(rollbackForClassName={RunTimeExecption,Execption}) |
| noRollbackFor          | 当方法抛出异常类数组中的异常时，不回滚   |                                                              |
| noRollbackForClassName | 当方法抛出异常名称数组中的异常时，不回滚 |                                                              |
| propagation            | 事务的传播行为                           | @Transactional(propagation=Propagation.REQUIRED)             |
| timeout                | 设置事务超时秒数，默认-1，永不超时       |                                                              |

@Transactional使用注意事项

* 只能声明在public的类上，或者public的方法上。声明在类上对类中所有的public方法有效，声明在public方法上只对该方法有效。原因是spring是通过JDK代理或者CGLIB代理的，生成的代理类，只能处理public方法

* 不能被类内部方法调用，类内部方法调用不会经过代理类



## spring整合mybatis

### 引入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>plus.hf</groupId>
  <artifactId>spring_mybatis</artifactId>
  <version>1.0-SNAPSHOT</version>

  <name>spring_mybatis</name>
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
      <version>5.3.16</version>
    </dependency>
    <!--spring核心ioc-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.3.16</version>
    </dependency>

    <!--做spring事务用到的-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>5.3.16</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>5.3.16</version>
    </dependency>

    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.6</version>
    </dependency>
    <!-- mybatis和spring的集成依赖 -->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>2.0.5</version>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.28</version>
    </dependency>

    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.9</version>
    </dependency>

    <dependency>
      <groupId>com.github.pagehelper</groupId>
      <artifactId>pagehelper</artifactId>
      <version>5.3.2</version>
    </dependency>

  </dependencies>

  <build>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
        <!-- clean lifecycle, see https://maven.apache.org/ref/current/maven-core/lifecycles.html#clean_Lifecycle -->
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.1.0</version>
        </plugin>
        <!-- default lifecycle, jar packaging: see https://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_jar_packaging -->
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.8.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.22.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-jar-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-install-plugin</artifactId>
          <version>2.5.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>2.8.2</version>
        </plugin>
        <!-- site lifecycle, see https://maven.apache.org/ref/current/maven-core/lifecycles.html#site_Lifecycle -->
        <plugin>
          <artifactId>maven-site-plugin</artifactId>
          <version>3.7.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-project-info-reports-plugin</artifactId>
          <version>3.0.0</version>
        </plugin>
      </plugins>
    </pluginManagement>

    <resources>
      <resource>
        <directory>src/main/java</directory>
        <includes>
          <include>**/*.xml</include>
          <include>**/*.properties</include>
        </includes>
      </resource>

      <resource>
        <directory>src/main/resources</directory>
        <includes>
          <include>**/*.xml</include>
          <include>**/*.properties</include>
        </includes>
      </resource>
    </resources>
  </build>
</project>

```

### Mybatis核心配置文件 MybatisConfig.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 设置日志输出 -->
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>

</configuration>
```

### SpringContext_Mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!--读取属性文件-->
    <context:property-placeholder location="jdbc.properties"/>

    <!--创建数据源-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${hf.plus.driverClassName}"/>
        <property name="url" value="${hf.plus.url}"/>
        <property name="username" value="${hf.plus.username}"/>
        <property name="password" value="${hf.plus.password}"/>
    </bean>

    <!--配置MyBatis的核心文件-->
    <bean class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--创建数据源-->
        <property name="dataSource" ref="dataSource"/>
        <!--配置核心配置文件-->
        <property name="configLocation" value="MybatisConfig.xml"/>
         <!--插件 pagehleper-->
        <property name="plugins">
            <array>
                <bean class="com.github.pagehelper.PageInterceptor">
                    <property name="properties">
                        <value>
                            helperDialect=mysql
                        </value>
                    </property>
                </bean>
            </array>
        </property>
        <!--注册实体类-->
        <property name="typeAliasesPackage" value="plus.hf.pojo"/>
    </bean>

    <!--注册mapper文件-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="plus.hf.mapper"/>
    </bean>
</beans>
```



### SpringContext_Service.xml

* 第一种方式Spring的注解方式

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--导入mybatis的配置文件-->
    <import resource="SpringContext_Mapper.xml"/>

    <!--  添加包扫描   扫描xxxServiceImpl  -->
    <context:component-scan base-package="plus.hf.service.impl"/>
    <!--  添加事务处理  -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--  使用注解事务  -->
    <tx:annotation-driven transaction-manager="transactionManager"/>
</beans>
```

然后再service层加上@Transactional注解就可以

* 第二种方式Aspectj的AOP配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--导入mapper.xml配置文件-->
    <import resource="SpringContext_Mapper.xml"></import>
    <!--添加包扫描-->
    <context:component-scan base-package="plus.hf.service.impl"></context:component-scan>
    <!--添加事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--配置数据源-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    
    <!--添加事务切面(哪些方法)-->
    <tx:advice id="myadvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="*find*" read-only="true"/>
            <tx:method name="*select*" read-only="true"/>
            <tx:method name="*search*" read-only="true"/>
            <tx:method name="*get*" read-only="true"/>
            <tx:method name="*insert*" propagation="REQUIRED" no-rollback-for="ArithmeticException"></tx:method>
            <tx:method name="*add*" propagation="REQUIRED"></tx:method>
            <tx:method name="*save*" propagation="REQUIRED" no-rollback-for="ArithmeticException"></tx:method>
            <tx:method name="*update*" propagation="REQUIRED"></tx:method>
            <tx:method name="*change*" propagation="REQUIRED"></tx:method>
            <tx:method name="*modify*" propagation="REQUIRED"></tx:method>
            <tx:method name="*set*" propagation="REQUIRED"></tx:method>
            <tx:method name="*drop*" propagation="REQUIRED"></tx:method>
            <tx:method name="*delete*" propagation="REQUIRED"></tx:method>
            <tx:method name="*remove*" propagation="REQUIRED"></tx:method>
            <tx:method name="*clear*" propagation="REQUIRED"></tx:method>
            <tx:method name="*" propagation="SUPPORTS"></tx:method>
        </tx:attributes>
    </tx:advice>
    <!--绑定切面和切入点-->
    <aop:config>
        <aop:pointcut id="mycut" expression="execution(* plus.hf.service.impl.*.*(..))"></aop:pointcut>
        <aop:advisor advice-ref="myadvice" pointcut-ref="mycut"></aop:advisor>
    </aop:config>

</beans>
```

上面的

