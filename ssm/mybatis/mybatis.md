# 配置mybatis

* 在resources目录下新建jdbc.properties

  ```properties
  mysql5.driverClassName=com.mysql.jdbc.Driver
  mysql8.driverClassName=com.mysql.cj.jdbc.Driver
  jdbc.driverClassName=com.mysql.cj.jdbc.Driver
  jdbc.url=jdbc:mysql://localhost:3306/ssm?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
  jdbc.username=root
  jdbc.password=root
  hf.plus.driverClassName=com.mysql.cj.jdbc.Driver
  hf.plus.url=jdbc:mysql://47.108.173.35:3306/ssm?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
  hf.plus.username=root
  hf.plus.password=root
  ```

  

* 在resources目录下新建MybatisConfig.cml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <!--必须放在第一位
          读取属性文件标签：properties
          resource:resource目录下的文件，
          url:绝对路径
       -->
      <properties resource="jdbc.properties"/>
  
      <!--如果有，必须放在第二
  		打印sql语句
  	-->
      <settings>
          <setting name="logImpl" value="STDOUT_LOGGING"/>
      </settings>
  
      <!--如果有必须放在第三
          注册别名
      -->
      <typeAliases>
          <!--单个注册别名，不实用-->
          <!--<typeAlias type="plus.hf.pojo.Student" alias="student"/>-->
          <!--批量注册别名，使用类名的驼峰命名法作为别名，推荐使用-->
          <package name="plus.hf.pojo"/>
      </typeAliases>
  
      <!--  环境变量配置(数据库连接哪个)
          参数：default:选择使用哪套配置
      -->
      <environments default="development">
          <!--    具体的数据库配置 id为名称自己起 (开发中使用这个配置)    -->
          <environment id="development">
              <!--配置事务
                  参数：
                      type:指定事务配置的类型
                           JDBC:由程序员决定事务的提交和回滚
                           MANAGED:由容器(Spring)来决定事务的处理
                -->
              <transactionManager type="JDBC"/>
              <!--配置数据源
                  参数:
                      type:决定数据源的类型
                          JNDI:
                          POOLED:使用MyBatis框架提供的数据库连接池，默认即可
                          UNPOOLED:不使用数据库连接池
              -->
              <dataSource type="POOLED">
                  <property name="driver" value="${jdbc.driverClassName}"/>
                  <property name="url" value="${jdbc.url}"/>
                  <property name="username" value="${jdbc.username}"/>
                  <property name="password" value="${jdbc.password}"/>
              </dataSource>
          </environment>
          <!--在家开发的配置-->
  <!--        <environment id="home">-->
  <!--            <transactionManager type=""></transactionManager>-->
  <!--            <dataSource type=""></dataSource>-->
  <!--        </environment>-->
  <!--        &lt;!&ndash;上线的配置&ndash;&gt;-->
  <!--        <environment id="online">-->
  <!--            <transactionManager type=""></transactionManager>-->
  <!--            <dataSource type=""></dataSource>-->
  <!--        </environment>-->
      </environments>
      <!--  注册mapper,放在最后  -->
      <mappers>
  <!--        <mapper class="plus.hf.mapper.UsersMapper"/>-->
  <!--        <mapper resource="UsersMapper.xml"/>-->
  <!--        <mapper url="plus/hf/mapper/UsersMapper.xml"/>-->
          <package name="plus.hf.mapper"/>
      </mappers>
  </configuration>
  ```

  