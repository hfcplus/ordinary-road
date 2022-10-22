# 缓存穿透

请求到服务，服务查redis数据库，redis没有返回null，然后就查mysql数据库

当一瞬间大量的(上面的)请求访问服务，redis都没有数据，这些请求就访问mysql数据库，mysql数据库承受不住这样的大的压力，就很有可能崩溃。



## 解决办法

方案一 缓存空结果

> 当redis没有数据，mysql也没有数据时，就把那条数据在redis中存为null

方案二 布隆过滤器

>
>
>

# 缓存击穿

高并发的情况下，对于**热点数据**（一般情况下，80%的请求都是访问某些热点数据，也就是访问某些热点key），因为redis的数据在保存时一般会加时间限制，当时间到了，热点数据失效的一瞬间，或者刚开始redis还没缓存热点数据时，短时间大量的请求发送到mysql去查询，导致数据库被压垮

## 解决办法

方案一 分布式锁

>加锁 + 双重检测
>
>代码解释的比较清楚
>
>```java
>public BaseInfo queryBaseInfo() {
>    // 1.先查询redis，如果有就取出来，没有访问数据库
>    String key = RedisKeyConstants.BASE_INFO;
>    BoundValueOperations<String, String> ops = stringRedisTemplate.boundValueOps(key);
>    String baseInfoString = ops.get();
>    BaseInfo baseInfo;
>    if (baseInfoString == null || "".equals(baseInfoString)) {
>        // 2.加锁
>        synchronized (this) { 
>            // 3.再次访问redis(双重检测)
>            baseInfoString = ops.get(); 
>            if (baseInfoString == null || "".equals(baseInfoString)) {
>                // 4.redis中没有，就访问数据库
>                baseInfo = baseInfoMapper.selectBaseInfo();
>                if(baseInfo != null) {
>                    // 5.如果重数据库查询出了，就写入redis
>                    String jsonString = JSONObject.toJSONString(baseInfo);
>                    BoundValueOperations<String, String> stringStringBoundValueOperations = stringRedisTemplate.boundValueOps(RedisKeyConstants.BASE_INFO);
>                    stringStringBoundValueOperations.set(jsonString, 60, TimeUnit.MINUTES);
>                }
>            } else {
>                baseInfo = JSONObject.parseObject(baseInfoString, BaseInfo.class);
>            }
>        }
>    } else {
>        baseInfo = JSONObject.parseObject(baseInfoString, BaseInfo.class);
>    }
>    return baseInfo;
>}
>```
>
>

方法二 对即将失效的热点数据主动更新

>设置定时任务，定时主动更新热点数据，保证热点数据不失效

方法三 将热点数据设为永不过期

# 缓存雪崩

大面积缓存失效，不只是热点数据失效
