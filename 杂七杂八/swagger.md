swagger提供了新的依赖,既有swagger，也有swagger-ui

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```



配置swagger

```yaml
spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher  #swagger
```



swagger的个性配置,可以不要

```java
package plus.hf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author : HFC
 * @date : 2022/5/28 15:58
 * @description :
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.OAS_30);
        ApiInfo apiInfo = new ApiInfoBuilder().title("文档标题")
                .version("1.0.1")
                .description("描述balababala")
                .contact(new Contact("name:联系人的姓名", "url:可以是官网地址", "email：邮箱"))
                .build();
        docket = docket.apiInfo(apiInfo);

        // 设置哪些包参与api的文档生成
        docket = docket.select().apis(RequestHandlerSelectors.basePackage("plus.hf.controller")).build();

        //禁用文档的生成，当项目结束时可以禁用
//        docket.enable(false);
        return docket;
    }
}
```



启动类开启swagger

```java
package plus.hf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication
public class SomethingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SomethingApplication.class, args);
    }

}
```

## 实体类

```java
package plus.hf.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

/**
 * @author : HFC
 * @date : 2022/5/28 15:20
 * @description :
 */
@ApiModel(value = "学生实体类")
public class Student {
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "姓名")
    private String name;
    private String sex;
    private Integer age;
}

```

## 控制层

如果使用了swagger，就不要使用@RequestMapping(),不然会生成许多不必要的结果

```java
package plus.hf.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import plus.hf.domain.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : HFC
 * @date : 2022/5/28 16:10
 * @description :
 */
@Api(tags = "student的控制层")
@RestController
@RequestMapping("student")
public class StudentController {

    @ApiOperation(value = "查询学生", notes = "按学生主键查询信息")
    @ApiImplicitParam(name = "id", value = "学生主键")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = Student.class),
            @ApiResponse(code = 500, message = "查询失败")
    })
    @GetMapping("queryById")
    public Student student(Integer id) {
        return new Student();
    }

    @ApiOperation(value = "查询学生", notes = "按学生主键查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键"),
            @ApiImplicitParam(name = "name", value = "姓名")
    })
    @GetMapping("queryByMap")
    public Student student(Integer id, String name) {
        return new Student();
    }

    @ApiOperation(value = "查询学生", notes = "按学生主键查询信息")
    @GetMapping("queryByIds")
    public List<Student> student(List<Integer> ids) {
        return new ArrayList<Student>();
    }

    @ApiOperation(value = "查询学生", notes = "按学生姓名查询信息")
    @GetMapping("queryByName")
    public List<Student> queryByName(String name) {
        return new ArrayList<Student>();
    }

    @PostMapping("addStudent")
    public String addStudent(Student student) {
        return "添加成功";
    }

    @DeleteMapping("delete")
    public String deleteStudent(Integer id) {
        return "删除成功";
    }

    @PutMapping("update")
    public String updateStudent(Student student) {
        return "修改成功";
    }
}
```

新版生成的文档地址

http://localhost:9000/swagger-ui/

http://localhost:9000/swagger-ui/index.html