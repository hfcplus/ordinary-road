```yml
server:
  port: 8080
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver # 数据库驱动：
    url: jdbc:mysql://47.108.173.35:3306/travel2?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8 # 数据库连接地址
    username: root
    password: root

    #hikari连接池，springboot自带的连接池
    hikari:
      pool-name: myHikari
      minimum-idle: 5 #最小空闲连接数量
      idle-timeout: 180000 #空闲连接存活最大时间，默认600000（10分钟）
      maximum-pool-size: 10 #连接池最大连接数，默认是10
      auto-commit: true  #此属性控制从池返回的连接的默认自动提交行为,默认值：true
      max-lifetime: 1800000 #此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认1800000即30分钟
      connection-timeout: 30000 #数据库连接超时时间,默认30秒，即30000
      connection-test-query: SELECT 1

  #thymeleaf配置
  thymeleaf:
    cache: false # 开启模板缓存（默认值： true ）
    check-template: true # 检查模板是否存在，然后再呈现
    check-template-location: true # 检查模板位置是否正确（默认值 :true ）
    enabled: true # 开启 MVC Thymeleaf 视图解析（默认值： true ）
    encoding: UTF-8 # 模板编码
    excluded-view-names: # 要被排除在解析之外的视图名称列表，⽤逗号分隔
    mode: HTML5 # 要运⽤于模板之上的模板模式。另⻅ StandardTemplate-ModeHandlers( 默认值： HTML5)
    prefix: classpath:/templates/ # 在构建 URL 时添加到视图名称前的前缀（默认值： classpath:/templates/ ）
    suffix: .html # 在构建 URL 时添加到视图名称后的后缀（默认值： .html ）

  #redis配置
  redis:
    host: 127.0.0.1 #Redis服务器地址
    port: 6379 #Redis服务器连接端口
    database: 0 #Redis数据库索引（默认为0）
    jedis:
      pool:
        max-active: -20 #连接池最大连接数（使用负值表示没有限制）
        max-wait: 3000 #连接池最大阻塞等待时间（使用负值表示没有限制）
        max-idle: 20 #连接池中的最大空闲连接
        min-idle: 2 #连接池中的最小空闲连接
    timeout: 5000 #连接超时时间（毫秒）
    password:
  main:
    allow-bean-definition-overriding: true #bean是否可以重载

```

