## 1. 创建公共项目

定义接口和实体类

## 2.创建提供者

实现接口，用@DubboService暴露服务

依赖

```xml
<!--公共项目的依赖-->
<dependency>
    <groupId>plus.hf</groupId>
    <artifactId>dubbo-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>

<!--dubbo-->
<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-spring-boot-starter</artifactId>
    <version>3.0.8</version>
</dependency>
<!--zookeeper-->
<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-dependencies-zookeeper</artifactId>
    <version>3.0.8</version>
    <type>pom</type>
    <exclusions>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

实现接口

```java
package plus.hf.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import plus.hf.domain.Student;
import plus.hf.service.StudentService;

/**
 * @author : HFC
 * @date : 2022/6/6 18:06
 * @description :
 */
@DubboService(version = "1.0")
public class StudentServiceImpl implements StudentService {
    @Override
    public Student getById(Integer id) {
        System.out.println("服务提供者查询学生");
        Student student = new Student(id, "hfc", 25);
        return student;
    }
}
```

核心配置文件

```yaml
spring:
  application:
    name: provider

#dubbo配置
dubbo:
  registry:
    address: zookeeper://localhost:2181
  scan:
    base-packages: plus.hf.service.impl #注解扫描
  protocol:
    name: dubbo #协议
    port: 20880 #端口号
  provider:
    timeout: 5000 #超时时间
    version: 1.0.0 #service default version
    retries: 0

```

启动类添加注解@EnableDubbo

```java
package plus.hf;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }

}
```



## 3.创建消费者

@DubboReference远程调用服务

依赖一样的

远程调用服务

```java
package plus.hf.dubboconsumer.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.hf.domain.Student;
import plus.hf.service.StudentService;

import java.util.Date;

/**
 * @author : HFC
 * @date : 2022/6/6 18:24
 * @description :
 */
@RestController
public class DubboController {

    @DubboReference(version = "1.0")
    private StudentService studentService;

    @GetMapping("student")
    public Student getStudentById(Integer id) {
        System.out.println(new Date());
        System.out.println("消费者查询");
        Student student = studentService.getById(id);
        return student;
    }
}
```

核心配置文件

```yaml
spring:
  application:
    name: consumer

dubbo:
  registry:
    address: zookeeper://localhost:2181
  scan:
    base-packages: plus.hf.dubboconsumer.controller.DubboController
  consumer:
    timeout: 5000
    retries: 0
    check: false

```

启动类添加 @EnableDubbo 注解

```java
package plus.hf.dubboconsumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class DubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class, args);
    }

}
```