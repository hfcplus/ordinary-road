导入依赖,导入mybatis-plus依赖后，要删除以前的所有的mybatis相关的依赖，防止冲突

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.1</version>
</dependency>
```

在yml文件中配置mybatis-plus

```yml
mybatis-plus:
  mapper-locations: classpath*:mapper/*Mapper.xml   # mapper.xml文件位置
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  # 打印SQL语句
```

在Application中添加mapper包扫描，就不需要在每个Mapper中使用@Mapper注解

```java
@SpringBootApplication
@MapperScan("plus.hf.mybatisplus.mapper")
public class MybatisPlusApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
    }
}
```



## Mapper继承BaseMapper使用他的CRUD方法

```java
public interface UserMapper extends BaseMapper<User> {
}
```

## Service继承IService，ServiceImpl继承ServiceImpl

Service:

```java
public interface UserService extends IService<User> {
}
```

ServiceImpl:

```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
}
```

## Mapper.xml

和mybatis的一样

## 使用mybatis-plus自带的分页

### 配置分页插件

```java
@Configuration
//@MapperScan("plus.hf.mybatisplus.mapper")
public class MyBatisPlusIPaginationInnerInterceptor  {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
```



### Mapper

1. 使用List接收数据

   ```java
   public interface UserMapper extends BaseMapper<User> {
       List<User> selectUserForPageByPlus(IPage<User> iPage);
   }
   ```

2. 使用Ipage接收数据

   ```java
   public interface UserMapper extends BaseMapper<User> {
       IPage<User> selectUserForPageByPlus2(IPage<User> iPage);
   }
   ```

### Test

1. 使用List接收数据

   ```java
   @Test
   void pageByMybatisPlus(){
       IPage<User> iPage = new Page<>(2,3);
       List<User> userPageByPlus = userService.getUserPageByPlus(iPage);
       System.out.println(userPageByPlus);
   }
   ```

   ![image-20220426093356527](%E6%95%B4%E5%90%88mybatis-plus.assets/image-20220426093356527.png)

   iPage的部分属性：

   * records: 查询的结果  null
   * total：查询的总行数
   * size：查询的总页数
   * current：现在的页数

   userPageByPlus：查询的结果一共有两条

2. 使用Ipage接收数据

   ```java
   @Test
   void pageByMybatisPlus2(){
       IPage<User> iPage = new Page<>(2,3);
       IPage<User> userIPage = userMapper.selectUserForPageByPlus2(iPage);
       if (iPage == userIPage) {
           System.out.println("iPage就是userIPage");
       }
   }
   ```

   ![image-20220426093923264](%E6%95%B4%E5%90%88mybatis-plus.assets/image-20220426093923264.png)

   iPage的部分属性：

   * records：查询的结果，一共有两条

   userIPage 和iPage引用了同一地址，他们两个是相等的 

​	