# 下载安装zookeeper

1. 将下载后的压缩包解压

2. 进入config目录，复制zoo_sample.cfg文件为zoo.cfg

3. 修改zoo.cfg文件内容 dataDir,clientPort,admin.serverPort

   ```cfg
   # The number of milliseconds of each tick
   tickTime=2000
   # The number of ticks that the initial 
   # synchronization phase can take
   initLimit=10
   # The number of ticks that can pass between 
   # sending a request and getting an acknowledgement
   syncLimit=5
   # the directory where the snapshot is stored.
   # do not use /tmp for storage, /tmp here is just 
   # example sakes.
   dataDir=D:/tools/apache-zookeeper-3.8.0-bin.tar/apache-zookeeper-3.8.0-bin/data
   # the port at which the clients will connect
   clientPort=2181
   # the maximum number of client connections.
   # increase this if you need to handle more clients
   #maxClientCnxns=60
   #
   # Be sure to read the maintenance section of the 
   # administrator guide before turning on autopurge.
   #
   # https://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
   #
   # The number of snapshots to retain in dataDir
   #autopurge.snapRetainCount=3
   # Purge task interval in hours
   # Set to "0" to disable auto purge feature
   #autopurge.purgeInterval=1
   
   ## Metrics Providers
   #
   # https://prometheus.io Metrics Exporter
   #metricsProvider.className=org.apache.zookeeper.metrics.prometheus.PrometheusMetricsProvider
   #metricsProvider.httpHost=0.0.0.0
   #metricsProvider.httpPort=7000
   #metricsProvider.exportJvmInfo=true
   admin.serverPort=8888
   ```

4. 进入bin目录，zkServer.cmd问windows启动，zkServer.sh为linux启动

   linux启动启动和停止命令

   ```linux
   ./zkServer.sh start
   ./zkServer.sh stop
   ```

# 使用zookeeper

## 启动zookeeper

## 创建公共项目

集中定义接口，数据类，异常

## 创建提供者

1. 提供者加入公共项目的依赖，dubbo依赖，zookeeper依赖

   ```xml
   <dependency>
       <groupId>plus.hf</groupId>
       <artifactId>dubbo-03-common</artifactId>
       <version>1.0-SNAPSHOT</version>
   </dependency>
   
   <!--dubbo-->
   <dependency>
       <groupId>org.apache.dubbo</groupId>
       <artifactId>dubbo</artifactId>
       <version>3.0.7</version>
   </dependency>
   
   <!--zookeeper-->
   <!-- https://mvnrepository.com/artifact/org.apache.curator/curator-framework -->
   <dependency>
       <groupId>org.apache.curator</groupId>
       <artifactId>curator-framework</artifactId>
       <version>5.2.1</version>
   </dependency>
   
   <!-- https://mvnrepository.com/artifact/org.apache.curator/curator-recipes -->
   <dependency>
       <groupId>org.apache.curator</groupId>
       <artifactId>curator-recipes</artifactId>
       <version>5.2.1</version>
   </dependency>
   
   <dependency>
       <groupId>org.apache.curator</groupId>
       <artifactId>curator-x-discovery</artifactId>
       <version>5.1.0</version>
   </dependency>
   ```

2. 实现自己需要的接口

3. 配置xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
          xsi:schemaLocation="http://www.springframework.org/schema/beans        http://www.springframework.org/schema/beans/spring-beans-4.3.xsd        http://dubbo.apache.org/schema/dubbo        http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
   
       <!-- 提供方应用信息，用于计算依赖关系 -->
       <dubbo:application name="zk-shop-orderservice"/>
   
       <!-- 使用multicast广播注册中心暴露服务地址 -->
       <dubbo:registry address="zookeeper://localhost:2181"/>
   
       <!-- 用dubbo协议在20880端口暴露服务 -->
       <dubbo:protocol name="dubbo" port="20881"/>
   
       <!-- 声明需要暴露的服务接口 -->
       <dubbo:service interface="plus.hf.service.OrderService"
                      ref="orderServiceImpl"
                      version="1.0.0"
       />
   
       <!-- 和本地bean一样实现服务 -->
       <bean id="orderServiceImpl" class="plus.hf.service.impl.OrderServiceImpl"/>
   </beans>
   ```

4. 启动服务

## 创建消费者

1. 消费者加入公共项目的依赖，dubbo依赖，zookeeper依赖，dubbo依赖太高了，会报错，所以添加较低的依赖

   ```xml
   <dependency>
       <groupId>plus.hf</groupId>
       <artifactId>dubbo-03-common</artifactId>
       <version>1.0-SNAPSHOT</version>
   </dependency>
   
   <dependency>
       <groupId>org.apache.dubbo</groupId>
       <artifactId>dubbo</artifactId>
       <version>2.7.15</version>
   </dependency>
   
   <!--zookeeper-->
   <!-- https://mvnrepository.com/artifact/org.apache.curator/curator-framework -->
   <dependency>
       <groupId>org.apache.curator</groupId>
       <artifactId>curator-framework</artifactId>
       <version>5.2.1</version>
   </dependency>
   
   <!-- https://mvnrepository.com/artifact/org.apache.curator/curator-recipes -->
   <dependency>
       <groupId>org.apache.curator</groupId>
       <artifactId>curator-recipes</artifactId>
       <version>5.2.1</version>
   </dependency>
   
   <dependency>
       <groupId>org.apache.curator</groupId>
       <artifactId>curator-x-discovery</artifactId>
       <version>5.1.0</version>
   </dependency>
   ```

2. 配置xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
          xsi:schemaLocation="http://www.springframework.org/schema/beans        http://www.springframework.org/schema/beans/spring-beans-4.3.xsd        http://dubbo.apache.org/schema/dubbo        http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
   
       <!-- 消费方应用名，用于计算依赖关系，不是匹配条件，不要与提供方一样 -->
       <dubbo:application name="consumer"/>
   
       <!-- 使用multicast广播注册中心暴露发现服务地址 -->
       <dubbo:registry address="zookeeper://localhost:2181"/>
   
       <!-- 生成远程服务代理，可以和本地bean一样使用demoService -->
       <dubbo:reference id="remoteOrderService"
                        interface="plus.hf.service.OrderService"
                        check="false"
                        version="1.0.0"
       />
       <dubbo:reference id="remoteUserService"
                        interface="plus.hf.service.UserService"
                        url="dubbo://localhost:20882"
                        check="false"
       />
   </beans>
   ```

3. 调用提供者的方法

   ```java
   package plus.hf;
   
   import org.springframework.context.support.ClassPathXmlApplicationContext;
   import plus.hf.domain.Address;
   import plus.hf.domain.Order;
   import plus.hf.service.OrderService;
   import plus.hf.service.UserService;
   
   import java.util.UUID;
   
   /**
    * @author : HFC
    * @date : 2022/6/1 15:39
    * @description :
    */
   public class App {
       public static void main(String[] args) throws InterruptedException {
           ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-consumer.xml");
           context.start();
           OrderService orderService = (OrderService) context.getBean("remoteOrderService");
           UserService userService = (UserService) context.getBean("remoteUserService");
           while (true) {
               System.out.println("消费者使用远程服务");
               Order order = orderService.createOrder(1000, 1, UUID.randomUUID().toString(), 50);
               System.out.println(order.toString());
               Address address = userService.queryByUserId(1000);
               System.out.println(address.toString());
               Thread.sleep(5000);
           }
       }
   }
   ```

   