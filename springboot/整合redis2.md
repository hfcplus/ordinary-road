redis的java客户端，SpringBoot框架默认使用的lettuce客户端

pom.xml

```xml
<!--redis依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

yml

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: Tai1997!
```

操作

```java
package plus.hf.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : HFC
 * @date : 2022/5/26 16:15
 * @description :
 */
@RestController
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @RequestMapping("string/add")
    public String addString (String key, String value){
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key); // 可以对同一的
        boundValueOperations.set(value);
//        ValueOperations valueOperations = redisTemplate.opsForValue();
        return "添加成功";
    }

    @RequestMapping("string/read")
    public String readString(String key) {
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key);
        Object o = boundValueOperations.get();
        return o.toString();
    }
    @RequestMapping("str/add")
    public String addStr(String key, String value) {
        // 单独设置key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key); // 可以对同一的
        boundValueOperations.set(value);
//        ValueOperations valueOperations = redisTemplate.opsForValue();
        return "添加成功";
    }

    @RequestMapping("str/read")
    public String readStr(String key) {
        // 单独设置key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key); // 可以对同一的
        boundValueOperations.get().toString();
//        ValueOperations valueOperations = redisTemplate.opsForValue();
        return boundValueOperations.get().toString();
    }

    @RequestMapping("stringtemplate/add")
    public String addStringtemplate(String key, String value){
        BoundValueOperations<String, String> stringStringBoundValueOperations = stringRedisTemplate.boundValueOps(key);
        stringStringBoundValueOperations.set(value);
        return "添加成功";
    }
}
```

