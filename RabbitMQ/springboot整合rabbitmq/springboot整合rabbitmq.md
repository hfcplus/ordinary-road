导入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

配置yml

```yml
# RabbitMQ配置
  rabbitmq:
    host: 47.108.173.35 # 单机
    port: 5672  # 单机
    username: root
    password: root
    virtual-host: /
    publisher-confirm-type: correlated #确认消息已发送到交换机(Exchange)
    publisher-returns: true #确认消息已发送到队列(Queue)
    
    # addresses:47.108.173.35:5672  #集群

```

# 消息发送者

### 1.创建交换机，并绑定队列（推荐使用配置类的形式）

* #### fanout(配置类实现)

  ```java
  package com.example.springboorrabbitmqproducer.config;
  import org.springframework.amqp.core.Binding;
  import org.springframework.amqp.core.BindingBuilder;
  import org.springframework.amqp.core.FanoutExchange;
  import org.springframework.amqp.core.Queue;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  /**
   * @author: currentdraw
   * @date: 2021/6/2
   * @description: 扇型交换机  这个交换机在接收到消息后，会直接转发到绑定到它上面的所有队列。
   */
  
  @Configuration
  public class FanoutRabbitConfig {
      /**
       *  创建三个队列 ：fanout.A   fanout.B  fanout.C
       *  将三个队列都绑定在交换机 fanoutExchange 上
       *  因为是扇型交换机, 路由键无需配置,配置也不起作用
       */
  
      @Bean
      public Queue queueA() {
          return new Queue("fanout.A");
      }
  
      @Bean
      public Queue queueB() {
          return new Queue("fanout.B");
      }
  
      @Bean
      public Queue queueC() {
          return new Queue("fanout.C");
      }
  
      //创建名为fanoutExchange的主题交换机
      @Bean
      FanoutExchange fanoutExchange() {
          // 声明交换机的名称，是否持久化，时候自动删除
          return new FanoutExchange("fanout_order_exchange",true,false);
      }
  
      //分别将三个队列与交换机绑定
      @Bean
      Binding bindingExchangeA() {
          return BindingBuilder.bind(queueA()).to(fanoutExchange());
      }
  
      @Bean
      Binding bindingExchangeB() {
          return BindingBuilder.bind(queueB()).to(fanoutExchange());
      }
  
      @Bean
      Binding bindingExchangeC() {
          return BindingBuilder.bind(queueC()).to(fanoutExchange());
      }
  }
  ```

* #### Direct（配置类实现）

  ```java
  package com.hfcplus.xuexi.RabbitMQ.config;
  import com.rabbitmq.client.AMQP;
  import org.springframework.amqp.core.Binding;
  import org.springframework.amqp.core.BindingBuilder;
  import org.springframework.amqp.core.DirectExchange;
  import org.springframework.amqp.core.Queue;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  /**
   * @author: currentdraw
   * @date: 2021/6/2
   * @description: RabbitMQ的直连型交换机，一个交换机可以和多个队列绑定，
   */
  @Configuration
  public class DirectRabbitConfig {
  
      //队列 起名：TestDirectQueue
      @Bean
      public Queue TestDirectQueue(){
          // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
          // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
          // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
          //   return new Queue("TestDirectQueue",true,true,false);
          //一般设置一下队列的持久化就好,其余两个就是默认false
          return new Queue("TestDirectQueue",true);
      }
  
      @Bean
      public Queue TestDirectQueue2(){
          return new Queue("TestDirectQueue2",true);
      }
  
      //Direct交换机 起名：TestDirectExchange
      @Bean
      DirectExchange TestDirectExchange(){
          //  return new DirectExchange("TestDirectExchange",true,true);
          return new DirectExchange("TestDirectExchange",true,false);
      }
  
      //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
      @Bean
      Binding bindingDirect() {
          return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
      }
  
      //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting2
      @Bean
      Binding bindingDirect2() {
          return BindingBuilder.bind(TestDirectQueue2()).to(TestDirectExchange()).with("TestDirectRouting2");
      }
      
      @Bean
      DirectExchange lonelyDirectExchange() {
          return new DirectExchange("lonelyDirectExchange");
      }
  
  }
  ```

  

* #### Topic（配置类实现）

  ```java
  package com.hfcplus.xuexi.RabbitMQ.config;
  
  
  import org.springframework.amqp.core.Binding;
  import org.springframework.amqp.core.BindingBuilder;
  import org.springframework.amqp.core.Queue;
  import org.springframework.amqp.core.TopicExchange;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  
  /**
   * @author: currentdraw
   * @date: 2021/6/2
   * @description: 主题交换机 可以一个交换机匹配多个队列
   */
  @Configuration
  public class TopicRabbitConfig {
      //绑定键
      public final static String man = "topic.man";
      public final static String woman = "topic.woman";
  
      @Bean
      public Queue firstQueue() {
          return new Queue(TopicRabbitConfig.man);
      }
  
      @Bean
      public Queue secondQueue() {
          return new Queue(TopicRabbitConfig.woman);
      }
  
      @Bean
      TopicExchange exchange() {
          return new TopicExchange("topicExchange");
      }
  
      //将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
      //这样只要是消息携带的路由键是topic.man,才会分发到该队列
      @Bean
      Binding bindingExchangeMessage() {
          return BindingBuilder.bind(firstQueue()).to(exchange()).with(man);
      }
  
      //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
      // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
      @Bean
      Binding bindingExchangeMessage2() {
          return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.#");
      }
  }
  ```

* #### Topic（注解实现）

  ```java
  @RabbitListener(bindings = @QueueBinding(
          value = @Queue(value = "queueName",durable = "true",autoDelete = "false"),
          exchange = @Exchange(value = "exchangeName",type = ExchangeTypes.TOPIC),
          key = "#.email.#"
  ))
  public clss receive{
      ........
  }
  ```

  

### 2.发送消息

```java
@Autowired 
private RabbitTemplate rabbitTemplate;

  @GetMapping("sendDirectMessage")
    public String sendDirectMessage(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        //将消息携带绑定键值：TestDirectRouting2 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting2", map);
        return "ok";
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);

        //将消息携带绑定键值：topic.man 发送到交换机topicExchange
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);

        //将消息携带绑定键值：topic.woman 发送到交换机topicExchange
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
        return "ok";
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        //发送消息到主题交换机上fanoutExchange
        rabbitTemplate.convertAndSend("fanoutExchange", null, map);
        return "ok";
    }
```



# 消息接收

* ### fanout

  ```java
  package com.example.springbootrabbitmqconsumer.service.fanout;
  
  import org.springframework.amqp.rabbit.annotation.RabbitHandler;
  import org.springframework.amqp.rabbit.annotation.RabbitListener;
  
  /**
   * @author: currentdraw
   * @date: 2021/7/24
   * @description:
   */
  @RabbitListener(queues = {"fanout.A"})
  public class EmailFanoutService {
  
      @RabbitHandler
      public void receiveMessage(String message){
          System.out.println("我是邮箱服务，我从队列中拿出了消息"+message);
      }
  }
  ```

  

