http://www.redis-doc.com/

# windows下载安装配置redis

1. **下载地址：**https://github.com/tporadowski/redis/releases

2. **配置环境变量**

   > * 系统变量path 添加 redis的安装路径
   > * D:\redis\Redis-x64-5.0.10

# Centos安装配置redis

1. 下载地址 ：http://download.redis.io/releases/

2. 安装redis

   >下载到：/usr/tools/redis/
   >
   >解压到：/usr/tools/redis/redis-6.2.6
   >
   >1. 安装gcc依赖
   >
   >  由于 redis 是用 C 语言开发，安装之前必先确认是否安装 gcc 环境（gcc -v），如果没有安装，执行以下命令进行安装
   >
   >  ```linux
   >  yum install -y gcc 
   >  ```
   >
   >2. 进入redis-6.2.6,然后make编译
   >
   >  ```linux
   >  [root@localhost redis]# cd redis-6.2.6/
   >  [root@localhost redis-6.2.6]# make
   >  ```
   >
   >3. 安装到指定目录 redis-6.2.6
   >
   >  ```linux
   >  [root@localhost redis-6.2.6]# make install PREFIX=/usr/tools/redis/redis-6.2.6
   >  ```
   >
   >4. 配置redis.conf，使其可以被远程连接
   >
   >  * #bind 127.0.0.1 -::1      解释：谁能连接redis，注释掉，谁都可以连
   >  * protected-mode yes 保护模式改为no
   >
   >  >我们在redis的配置文件中会遇到protected-mode，它直译为保护模式。
   >  >
   >  >如果设置为yes，那么只允许我们在本机的回环连接，其他机器无法连接。
   >  >
   >  >**线上Redis服务**，为了安全，我们建议将protected-mode设置为yes。
   >  >
   >  >protected-mode设置为yes的情况下，为了我们的应用服务可以正常访问Redis，我们需要设置Redis的bind参数或者密码参数requirepass。
   >
   >5. 开机启动redis
   >
   >  * 修改/redis-6.2.6/utils/下的redis_init_script文件里的EXEC，CLIEXEC，CONF参数如下
   >
   >    ```
   >    EXEC=/usr/tools/redis/redis-6.2.6/bin/redis-server
   >    CLIEXEC=/usr/tools/redis/redis-6.2.6/bin/redis-cli
   >    CONF="/usr/tools/redis/redis-6.2.6/bin/redis.conf"
   >    ```
   >
   >  * 讲redis_init_script文件cp到/etc/init.d/下
   >
   >    ```linux
   >    cp /usr/tools/redis/redis-6.2.6/utis/redis_init_script /etc/init.d/
   >    ```
   >
   >  * 进入/etc/init.d/目录，修改 redis_init_script 文件名称为**redisd**
   >
   >    ```linux
   >    [root@localhost utils]# cd /etc/init.d
   >    [root@localhost init.d]# mv redis_init_script redisd
   >    ```
   >
   >  *  把redis加入自启动服务
   >
   >    ```linux
   >    chkconfig --add redisd
   >    ```
   >
   >  * 开启redis开机自启服务
   >
   >    ```linux
   >    chkconfig redisd on 
   >    ```
   >
   >
   >
   >  * 下次电脑重启时就会自动启动redis

# redis远程客户端

Another Redis Desktop Manager

下载地址：https://github.com/qishibo/AnotherRedisDesktopManager/releases

# 启动redis

1. 启动redis

   ```java
   redis-server.exe
   ```

2. 连接本地redis服务 

   > ```java
   > redis-cli 或者
   > redis-cli.exe -h 127.0.0.1 -p 6379
   > ```
   
3. 连接远程redis服务

   > ```java
   > redis-cli -h host -p port -a password
   > 例子：redis-cli -h 127.0.0.1 -p 6379 -a "mypass"
   > ```
   
4. 如果有密码则需添加操作

   >```java
   >auth root
   >```

5. 可以设置密码

   > ```java
   > CONFIG SET requirepass "root"
   > ```

# 使用redis

1. 查看redis配置

   > ```tex
   > CONFIG GET CONFIG_SETTING_NAME
   > //例子
   > CONFIG GET loglevel
   > CONFIG GET *
   > ```
   >
   > 

   

2. 编辑配置

   > * 直接编辑config文件
   >
   > * 使用 **CONFIG set** 
   >
   >   ```tex
   >   CONFIG SET CONFIG_SETTING_NAME NEW_CONFIG_VALUE
   >   //例子
   >   CONFIG SET loglevel "notice"
   >   ```


# redis数据类型

1. String（字符串）(set get)

   >* string 是 redis 最基本的类型
   >
   >*  string 可以包含任何数据
   >
   >* string 类型的值最大能存储 512MB
   >
   >  ```java
   >  SET runoob "菜鸟教程"
   >  GET runoob
   >  ```
   >
   >  

2. Hash（哈希）(hmset hget)

   > * hash 特别适合用于存储对象
   >
   >   ```java
   >   HMSET runoob field1 "Hello" field2 "World"
   >   HGET runoob field1 //"Hello"
   >   HGET runoob field2 //"World"
   >   ```
   >
   >   

3. List（列表）(lpush lrange)

   >* 是简单的字符串列表
   >
   >* 按照插入顺序排序
   >
   >* 添加一个元素到列表的头部（左边）或者尾部（右边）
   >
   >  ```java
   >  lpush runoob redis
   >  lpush runoob mongodb
   >  lpush runoob rabbitmq
   >  lrange runoob 0 10
   >  ```
   >
   >  
   
4. Set（集合）(sadd  smembers)

   > * string 类型的无序集合
   >
   > * 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)
   >
   > * 添加成功返回1，已经存在返回0
   >
   >   ```java
   >   sadd runoob redis
   >   sadd runoob mongodb
   >   sadd runoob rabbitmq //1
   >   sadd runoob rabbitmq //0
   >   smembers runoob
   >   ```
   >
   >   

5. zset（sorted set：有序集合）

   > * string类型元素的集合且不允许重复的成员
   >
   > * 每个元素都会关联一个double类型的分数,通过分数来为集合中的成员进行从小到大的排序
   >
   > * 添加成功返回1，已经存在返回0
   >
   >   ```java
   >   zadd runoob 0 redis
   >   zadd runoob 0 mongodb
   >   zadd runoob 0 rabbitmq  //1
   >   zadd runoob 0 rabbitmq  //0
   >   ZRANGEBYSCORE runoob 0 1000 
   >   ```
   >
   >   

# String

## 1.基础操作

|              命令               |                             描述                             |
| :-----------------------------: | :----------------------------------------------------------: |
|          set key value          |                      设置指定 key 的值                       |
|             del key             |                             删除                             |
|             get key             |      获取指定 key 的值（只能获取string类型，List不行）       |
|     GETRANGE key start end      |         返回 key 中字符串值的子字符// 0 -1 获取全部          |
|        GETSET key value         | 将给定 key 的值设为 value ，并返回 key 的旧值(key不存在返回nil) |
|        GETBIT key offset        |     对 key 所储存的字符串值，获取指定偏移量上的位(bit)。     |
|       MGET key1 [key2..]        |             获取所有(一个或多个)给定 key 的值。              |
|     SETBIT key offset value     |   对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)   |
|     SETEX key seconds value     | 将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位) |
|         SETNX key value         |              只有在 key 不存在时设置 key 的值。              |
| mset key1 value1 key2 vaule2... |                       添加修改多个数据                       |
|         mget key1 key2          |                         获取多个数据                         |
|             strlen              |                        获取字符串长度                        |
|        append key value         |                    如果原始信息存在就追加                    |

## 2.扩展操作

场景：分表操作，主键保持唯一

|         操作          |              描述              |
| :-------------------: | :----------------------------: |
|       incr key        |              加一              |
|    incrby key num     | 加num（num必须为整数可正可负） |
| incrbyfloat key float |            加浮点数            |
|       decr key        |                                |
|    decrby key num     |                                |

场景：设置生命周期（每天投一次票）

|             操作              |       描述       |
| :---------------------------: | :--------------: |
|    setex key seconds value    |   设置存活秒数   |
| psetex key milliseconds value | 设置存活得毫秒数 |
|                               |                  |
|                               |                  |
|                               |                  |



# Hash

| 序号 |                             命令                             |                           描述                           |
| ---- | :----------------------------------------------------------: | :------------------------------------------------------: |
| 1    | [ HDEL key field1 [field2\]](https://www.runoob.com/redis/hashes-hdel.html) |                 删除一个或多个哈希表字段                 |
| 2    | [HEXISTS key field](https://www.runoob.com/redis/hashes-hexists.html) |         查看哈希表 key 中，指定的字段是否存在。          |
| 3    | [HGET key field](https://www.runoob.com/redis/hashes-hget.html) |             获取存储在哈希表中指定字段的值。             |
| 4    | [HGETALL key](https://www.runoob.com/redis/hashes-hgetall.html) |          获取在哈希表中指定 key 的所有字段和值           |
| 5    | [ HINCRBY key field increment](https://www.runoob.com/redis/hashes-hincrby.html) |  为哈希表 key 中的指定字段的整数值加上增量 increment 。  |
| 6    | [ HINCRBYFLOAT key field increment](https://www.runoob.com/redis/hashes-hincrbyfloat.html) | 为哈希表 key 中的指定字段的浮点数值加上增量 increment 。 |
| 7    | [ HKEYS key](https://www.runoob.com/redis/hashes-hkeys.html) |                  获取所有哈希表中的字段                  |
| 8    |  [ HLEN key](https://www.runoob.com/redis/hashes-hlen.html)  |                  获取哈希表中字段的数量                  |
| 9    | [ HMGET key field1 [field2\]](https://www.runoob.com/redis/hashes-hmget.html) |                   获取所有给定字段的值                   |
| 10   | [HMSET key field1 value1 [field2 value2 \]](https://www.runoob.com/redis/hashes-hmset.html) |  同时将多个 field-value (域-值)对设置到哈希表 key 中。   |
| 11   | [HSET key field value](https://www.runoob.com/redis/hashes-hset.html) |      将哈希表 key 中的字段 field 的值设为 value 。       |
| 12   | [ HSETNX key field value](https://www.runoob.com/redis/hashes-hsetnx.html) |     只有在字段 field 不存在时，设置哈希表字段的值。      |
| 13   | [HVALS key](https://www.runoob.com/redis/hashes-hvals.html)  |                   获取哈希表中所有值。                   |

# List

| 序号 | 命令及描述                                                   |
| :--- | :----------------------------------------------------------- |
| 1    | [BLPOP key1 [key2 \] timeout](https://www.runoob.com/redis/lists-blpop.html) 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。 |
| 2    | [BRPOP key1 [key2 \] timeout](https://www.runoob.com/redis/lists-brpop.html) 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。 |
| 3    | [BRPOPLPUSH source destination timeout](https://www.runoob.com/redis/lists-brpoplpush.html) 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。 |
| 4    | [LINDEX key index](https://www.runoob.com/redis/lists-lindex.html) 通过索引获取列表中的元素 |
| 5    | [LINSERT key BEFORE\|AFTER pivot value](https://www.runoob.com/redis/lists-linsert.html) 在列表的元素前或者后插入元素 |
| 6    | [LLEN key](https://www.runoob.com/redis/lists-llen.html) 获取列表长度 |
| 7    | [LPOP key](https://www.runoob.com/redis/lists-lpop.html) 移出并获取列表的第一个元素 |
| 8    | [LPUSH key value1 [value2\]](https://www.runoob.com/redis/lists-lpush.html) 将一个或多个值插入到列表头部 |
| 9    | [LPUSHX key value](https://www.runoob.com/redis/lists-lpushx.html) 将一个值插入到已存在的列表头部 |
| 10   | [LRANGE key start stop](https://www.runoob.com/redis/lists-lrange.html) 获取列表指定范围内的元素 |
| 11   | [LREM key count value](https://www.runoob.com/redis/lists-lrem.html) 移除列表元素 |
| 12   | [LSET key index value](https://www.runoob.com/redis/lists-lset.html) 通过索引设置列表元素的值 |
| 13   | [LTRIM key start stop](https://www.runoob.com/redis/lists-ltrim.html) 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。 |
| 14   | [RPOP key](https://www.runoob.com/redis/lists-rpop.html) 移除列表的最后一个元素，返回值为移除的元素。 |
| 15   | [RPOPLPUSH source destination](https://www.runoob.com/redis/lists-rpoplpush.html) 移除列表的最后一个元素，并将该元素添加到另一个列表并返回 |
| 16   | [RPUSH key value1 [value2\]](https://www.runoob.com/redis/lists-rpush.html) 在列表中添加一个或多个值 |
| 17   | [RPUSHX key value](https://www.runoob.com/redis/lists-rpushx.html) 为已存在的列表添加值 |

# Set

| 序号 | 命令及描述                                                   |
| :--- | :----------------------------------------------------------- |
| 1    | [SADD key member1 [member2\]](https://www.runoob.com/redis/sets-sadd.html) 向集合添加一个或多个成员 |
| 2    | [SCARD key](https://www.runoob.com/redis/sets-scard.html) 获取集合的成员数 |
| 3    | [SDIFF key1 [key2\]](https://www.runoob.com/redis/sets-sdiff.html) 返回第一个集合与其他集合之间的差异。 |
| 4    | [SDIFFSTORE destination key1 [key2\]](https://www.runoob.com/redis/sets-sdiffstore.html) 返回给定所有集合的差集并存储在 destination 中 |
| 5    | [SINTER key1 [key2\]](https://www.runoob.com/redis/sets-sinter.html) 返回给定所有集合的交集 |
| 6    | [SINTERSTORE destination key1 [key2\]](https://www.runoob.com/redis/sets-sinterstore.html) 返回给定所有集合的交集并存储在 destination 中 |
| 7    | [SISMEMBER key member](https://www.runoob.com/redis/sets-sismember.html) 判断 member 元素是否是集合 key 的成员 |
| 8    | [SMEMBERS key](https://www.runoob.com/redis/sets-smembers.html) 返回集合中的所有成员 |
| 9    | [SMOVE source destination member](https://www.runoob.com/redis/sets-smove.html) 将 member 元素从 source 集合移动到 destination 集合 |
| 10   | [SPOP key](https://www.runoob.com/redis/sets-spop.html) 移除并返回集合中的一个随机元素 |
| 11   | [SRANDMEMBER key [count\]](https://www.runoob.com/redis/sets-srandmember.html) 返回集合中一个或多个随机数 |
| 12   | [SREM key member1 [member2\]](https://www.runoob.com/redis/sets-srem.html) 移除集合中一个或多个成员 |
| 13   | [SUNION key1 [key2\]](https://www.runoob.com/redis/sets-sunion.html) 返回所有给定集合的并集 |
| 14   | [SUNIONSTORE destination key1 [key2\]](https://www.runoob.com/redis/sets-sunionstore.html) 所有给定集合的并集存储在 destination 集合中 |
| 15   | [SSCAN key cursor [MATCH pattern\] [COUNT count]](https://www.runoob.com/redis/sets-sscan.html) 迭代集合中的元素 |

# sorted set(Zset)

| 序号 | 命令及描述                                                   |
| :--- | :----------------------------------------------------------- |
| 1    | [ZADD key score1 member1 [score2 member2\]](https://www.runoob.com/redis/sorted-sets-zadd.html) 向有序集合添加一个或多个成员，或者更新已存在成员的分数 |
| 2    | [ZCARD key](https://www.runoob.com/redis/sorted-sets-zcard.html) 获取有序集合的成员数 |
| 3    | [ZCOUNT key min max](https://www.runoob.com/redis/sorted-sets-zcount.html) 计算在有序集合中指定区间分数的成员数 |
| 4    | [ZINCRBY key increment member](https://www.runoob.com/redis/sorted-sets-zincrby.html) 有序集合中对指定成员的分数加上增量 increment |
| 5    | [ZINTERSTORE destination numkeys key [key ...\]](https://www.runoob.com/redis/sorted-sets-zinterstore.html) 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 destination 中 |
| 6    | [ZLEXCOUNT key min max](https://www.runoob.com/redis/sorted-sets-zlexcount.html) 在有序集合中计算指定字典区间内成员数量 |
| 7    | [ZRANGE key start stop [WITHSCORES\]](https://www.runoob.com/redis/sorted-sets-zrange.html) 通过索引区间返回有序集合指定区间内的成员 |
| 8    | [ZRANGEBYLEX key min max [LIMIT offset count\]](https://www.runoob.com/redis/sorted-sets-zrangebylex.html) 通过字典区间返回有序集合的成员 |
| 9    | [ZRANGEBYSCORE key min max [WITHSCORES\] [LIMIT]](https://www.runoob.com/redis/sorted-sets-zrangebyscore.html) 通过分数返回有序集合指定区间内的成员 |
| 10   | [ZRANK key member](https://www.runoob.com/redis/sorted-sets-zrank.html) 返回有序集合中指定成员的索引 |
| 11   | [ZREM key member [member ...\]](https://www.runoob.com/redis/sorted-sets-zrem.html) 移除有序集合中的一个或多个成员 |
| 12   | [ZREMRANGEBYLEX key min max](https://www.runoob.com/redis/sorted-sets-zremrangebylex.html) 移除有序集合中给定的字典区间的所有成员 |
| 13   | [ZREMRANGEBYRANK key start stop](https://www.runoob.com/redis/sorted-sets-zremrangebyrank.html) 移除有序集合中给定的排名区间的所有成员 |
| 14   | [ZREMRANGEBYSCORE key min max](https://www.runoob.com/redis/sorted-sets-zremrangebyscore.html) 移除有序集合中给定的分数区间的所有成员 |
| 15   | [ZREVRANGE key start stop [WITHSCORES\]](https://www.runoob.com/redis/sorted-sets-zrevrange.html) 返回有序集中指定区间内的成员，通过索引，分数从高到低 |
| 16   | [ZREVRANGEBYSCORE key max min [WITHSCORES\]](https://www.runoob.com/redis/sorted-sets-zrevrangebyscore.html) 返回有序集中指定分数区间内的成员，分数从高到低排序 |
| 17   | [ZREVRANK key member](https://www.runoob.com/redis/sorted-sets-zrevrank.html) 返回有序集合中指定成员的排名，有序集成员按分数值递减(从大到小)排序 |
| 18   | [ZSCORE key member](https://www.runoob.com/redis/sorted-sets-zscore.html) 返回有序集中，成员的分数值 |
| 19   | [ZUNIONSTORE destination numkeys key [key ...\]](https://www.runoob.com/redis/sorted-sets-zunionstore.html) 计算给定的一个或多个有序集的并集，并存储在新的 key 中 |
| 20   | [ZSCAN key cursor [MATCH pattern\] [COUNT count]](https://www.runoob.com/redis/sorted-sets-zscan.html) |

# HyperLogLog

| 序号 | 命令及描述                                                   |
| :--- | :----------------------------------------------------------- |
| 1    | [PFADD key element [element ...\]](https://www.runoob.com/redis/hyperloglog-pfadd.html) 添加指定元素到 HyperLogLog 中。 |
| 2    | [PFCOUNT key [key ...\]](https://www.runoob.com/redis/hyperloglog-pfcount.html) 返回给定 HyperLogLog 的基数估算值。 |
| 3    | [PFMERGE destkey sourcekey [sourcekey ...\]](https://www.runoob.com/redis/hyperloglog-pfmerge.html) 将多个 HyperLogLog 合并为一个 HyperLogLog |

# Redis 发布订阅

>1. 第一个 redis-cli 客户端 创建了订阅频道名为 **runoobChat**
>
>   ```java
>   SUBSCRIBE runoobChat
>   ```
>
>   
>
>2. 第二个 redis-cli 客户端 在频道 **runoobChat** 发布消息
>
>   ```java
>   PUBLISH runoobChat "Redis PUBLISH test"
>   ```
>
>
>
>3. 第一个redis-cli接受信息
>
>   ```java
>   1) "message"
>   2) "runoobChat"
>   3) "Redis PUBLISH test"
>   ```
>
>   
>
>4. 常用命令
>
> | 序号 | 命令及描述                                                   |
> | :--- | :----------------------------------------------------------- |
> | 1    | [PSUBSCRIBE pattern [pattern ...\]](https://www.runoob.com/redis/pub-sub-psubscribe.html) 订阅一个或多个符合给定模式的频道。 |
> | 2    | [PUBSUB subcommand [argument [argument ...\]]](https://www.runoob.com/redis/pub-sub-pubsub.html) 查看订阅与发布系统状态。 |
> | 3    | [PUBLISH channel message](https://www.runoob.com/redis/pub-sub-publish.html) 将信息发送到指定的频道。 |
> | 4    | [PUNSUBSCRIBE [pattern [pattern ...\]]](https://www.runoob.com/redis/pub-sub-punsubscribe.html) 退订所有给定模式的频道。 |
> | 5    | [SUBSCRIBE channel [channel ...\]](https://www.runoob.com/redis/pub-sub-subscribe.html) 订阅给定的一个或多个频道的信息。 |
> | 6    | [UNSUBSCRIBE [channel [channel ...\]]](https://www.runoob.com/redis/pub-sub-unsubscribe.html) 指退订给定的频道。 |
>
>   

# Redis 事务(没啥用)

>redis事务理解:事务可以理解为一个打包的批量执行脚本，但批量指令并非原子化的操作，中间某条指令的失败不会导致前面已做指令的回滚，也不会造成后续的指令不做。
>
>1. 开启事务
>
>   ```java
>   MULTI
>   ```
>
>   
>
>2. 编写命令
>
>   ```java
>   SET book-name "Mastering C++ in 21 days"
>   GET book-name
>   SADD tag "C++" "Programming" "Mastering Series"    
>   ```
>
>   
>
>3. 触发事务
>
>   ```java
>   EXEC
>   ```
>
>   
>
>4. 执行之前编写的命令
>
>5. 常用命令
>
> | 序号 | 命令及描述                                                   |
> | :--- | :----------------------------------------------------------- |
> | 1    | [DISCARD](https://www.runoob.com/redis/transactions-discard.html) 取消事务，放弃执行事务块内的所有命令。 |
> | 2    | [EXEC](https://www.runoob.com/redis/transactions-exec.html) 执行所有事务块内的命令。 |
> | 3    | [MULTI](https://www.runoob.com/redis/transactions-multi.html) 标记一个事务块的开始。 |
> | 4    | [UNWATCH](https://www.runoob.com/redis/transactions-unwatch.html) 取消 WATCH 命令对所有 key 的监视。 |
> | 5    | [WATCH key [key ...\]](https://www.runoob.com/redis/transactions-watch.html) 监视一个(或多个) key ，如果在事务执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断。 |

# Redis 脚本

>**Eval命令**
>
>> ```java
>> EVAL script numkeys key [key ...] arg [arg ...] 
>> ```
>>
>> **参数说明：**
>>
>> * **script**： 参数是一段 Lua 5.1 脚本程序。脚本不必(也不应该)定义为一个 Lua 函数。
>> * **numkeys**： 用于指定键名参数的个数。
>> * **key [key ...]**： 从 EVAL 的第三个参数开始算起，表示在脚本中所用到的那些 Redis 键(key)，这些键名参数可以在 Lua 中通过全局变量 KEYS 数组，用 1 为基址的形式访问( KEYS[1] ， KEYS[2] ，以此类推)。
>> * **arg [arg ...]**： 附加参数，在 Lua 中通过全局变量 ARGV 数组访问，访问的形式和 KEYS 变量类似( ARGV[1] 、 ARGV[2] ，诸如此类)。
>
>**Evalsha 命令**
>
>>```java
>>EVALSHA sha1 numkeys key [key ...] arg [arg ...] 
>>```
>>
>>参数说明：
>>
>>* **sha1** ： 通过 SCRIPT LOAD 生成的 sha1 校验码。
>>
>>* **numkeys**： 用于指定键名参数的个数。
>>
>>* **key [key ...]**： 从 EVAL 的第三个参数开始算起，表示在脚本中所用到的那些 Redis 键(key)，这些键名参数可以在 Lua 中通过全局变量 KEYS 数组，用 1 为基址的形式访问( KEYS[1] ， KEYS[2] ，以此类推)。
>>
>>* **arg [arg ...]**： 附加参数，在 Lua 中通过全局变量 ARGV 数组访问，访问的形式和 KEYS 变量类似( ARGV[1] 、 ARGV[2] ，诸如此类)。
>>
>>  
>
>**载入脚本**
>
>```java
>SCRIPT LOAD "return 'hello moto'"    # 载入一个脚本
>"232fd51614574cf0867b83d384a5e898cfd24e5a" #
>```
>
> **Script Exists 命令**
>
>```
>SCRIPT EXISTS 232fd51614574cf0867b83d384a5e898cfd24e5a
>```
>
>**Script FLUSH命令**
>
>```java
>SCRIPT FLUSH     # 清空缓存
>```
>
>**Script kill 命令**   #当且仅当这个脚本没有执行过任何 **写** 操作时，这个命令才生效。
>
>```java
>SCRIPT KILL
>```
>
>**Script Load 命令**  #将脚本 script 添加到脚本缓存中，但并不立即执行这个脚本。
>
>```java
>SCRIPT LOAD script
>```
>
>
>
>

# Redis 连接

> 1. 为redis设置连接密码
>
>    ```java
>    CONFIG SET requirepass "root"
>    ```
>
> 2. 通过密码连接redis
>
>    ```java
>    redis-cli
>    auth root
>    ```
>
> 3. 断开连接
>
>    ```java
>    quit
>    ```
>
> 4. 选择数据库
>
>    * redis默认0号数据库
>
>      ```java
>      在0号数据库新建类型
>      SET key1 value1
>      ```
>
>      
>
>    * 选择1号数据库
>
>      ```java
>      select 1
>      获取 key1 失败
>      ```
>
>      
>
>    * 选择0号数据库
>
>      ```java
>      select 0
>      获取 key1 成功
>      ```
>
>      
>
>    

# redis 服务器命令

| 序号 | 命令及描述                                                   |
| :--- | :----------------------------------------------------------- |
| 1    | [BGREWRITEAOF](https://www.runoob.com/redis/server-bgrewriteaof.html) 异步执行一个 AOF（AppendOnly File） 文件重写操作 |
| 2    | [BGSAVE](https://www.runoob.com/redis/server-bgsave.html) 在后台异步保存当前数据库的数据到磁盘 |
| 3    | [CLIENT KILL [ip:port\] [ID client-id]](https://www.runoob.com/redis/server-client-kill.html) 关闭客户端连接 |
| 4    | [CLIENT LIST](https://www.runoob.com/redis/server-client-list.html) 获取连接到服务器的客户端连接列表 |
| 5    | [CLIENT GETNAME](https://www.runoob.com/redis/server-client-getname.html) 获取连接的名称 |
| 6    | [CLIENT PAUSE timeout](https://www.runoob.com/redis/server-client-pause.html) 在指定时间内终止运行来自客户端的命令 |
| 7    | [CLIENT SETNAME connection-name](https://www.runoob.com/redis/server-client-setname.html) 设置当前连接的名称 |
| 8    | [CLUSTER SLOTS](https://www.runoob.com/redis/server-cluster-slots.html) 获取集群节点的映射数组 |
| 9    | [COMMAND](https://www.runoob.com/redis/server-command.html) 获取 Redis 命令详情数组 |
| 10   | [COMMAND COUNT](https://www.runoob.com/redis/server-command-count.html) 获取 Redis 命令总数 |
| 11   | [COMMAND GETKEYS](https://www.runoob.com/redis/server-command-getkeys.html) 获取给定命令的所有键 |
| 12   | [TIME](https://www.runoob.com/redis/server-time.html) 返回当前服务器时间 |
| 13   | [COMMAND INFO command-name [command-name ...\]](https://www.runoob.com/redis/server-command-info.html) 获取指定 Redis 命令描述的数组 |
| 14   | [CONFIG GET parameter](https://www.runoob.com/redis/server-config-get.html) 获取指定配置参数的值 |
| 15   | [CONFIG REWRITE](https://www.runoob.com/redis/server-config-rewrite.html) 对启动 Redis 服务器时所指定的 redis.conf 配置文件进行改写 |
| 16   | [CONFIG SET parameter value](https://www.runoob.com/redis/server-config-set.html) 修改 redis 配置参数，无需重启 |
| 17   | [CONFIG RESETSTAT](https://www.runoob.com/redis/server-config-resetstat.html) 重置 INFO 命令中的某些统计数据 |
| 18   | [DBSIZE](https://www.runoob.com/redis/server-dbsize.html) 返回当前数据库的 key 的数量 |
| 19   | [DEBUG OBJECT key](https://www.runoob.com/redis/server-debug-object.html) 获取 key 的调试信息 |
| 20   | [DEBUG SEGFAULT](https://www.runoob.com/redis/server-debug-segfault.html) 让 Redis 服务崩溃 |
| 21   | [FLUSHALL](https://www.runoob.com/redis/server-flushall.html) 删除所有数据库的所有key |
| 22   | [FLUSHDB](https://www.runoob.com/redis/server-flushdb.html) 删除当前数据库的所有key |
| 23   | [INFO [section\]](https://www.runoob.com/redis/server-info.html) 获取 Redis 服务器的各种信息和统计数值 |
| 24   | [LASTSAVE](https://www.runoob.com/redis/server-lastsave.html) 返回最近一次 Redis 成功将数据保存到磁盘上的时间，以 UNIX 时间戳格式表示 |
| 25   | [MONITOR](https://www.runoob.com/redis/server-monitor.html) 实时打印出 Redis 服务器接收到的命令，调试用 |
| 26   | [ROLE](https://www.runoob.com/redis/server-role.html) 返回主从实例所属的角色 |
| 27   | [SAVE](https://www.runoob.com/redis/server-save.html) 同步保存数据到硬盘 |
| 28   | [SHUTDOWN [NOSAVE\] [SAVE]](https://www.runoob.com/redis/server-shutdown.html) 异步保存数据到硬盘，并关闭服务器 |
| 29   | [SLAVEOF host port](https://www.runoob.com/redis/server-slaveof.html) 将当前服务器转变为指定服务器的从属服务器(slave server) |
| 30   | [SLOWLOG subcommand [argument\]](https://www.runoob.com/redis/server-showlog.html) 管理 redis 的慢日志 |
| 31   | [SYNC](https://www.runoob.com/redis/server-sync.html) 用于复制功能(replication)的内部命令 |

# GEO

> 主要用于存储地理位置信息，并对存储的信息进行操作.
>
> 操作方法:
>
> * **geoadd**：添加地理位置的坐标。
>
>   ```java
>   #添两个个地理坐标赋值给 Sicily
>   GEOADD Sicily 13.361389 38.115556 "Palermo" 15.087269 37.502669 "Catania"
>   ```
>
>   
>
> * **geopos**：获取地理位置的坐标。
>
>   ```java
>   #获取坐标值，第三个不存在返回nil
>   GEOPOS Sicily Palermo Catania NonExisting
>   ```
>
>   
>
> * **geodist**：计算两个位置之间的距离。
>
>   ```java
>   GEODIST key member1 member2 [m|km|ft|mi]
>   ```
>
>   * m 米默认单位
>   * km 千米
>   * mi 英里
>   * ft 千尺
>
>   ```java
>   #计算两个地点的距离，默认单位是米
>   GEODIST Sicily Palermo Catania
>   ```
>
>   
>
> * **georadius**：根据用户给定的经纬度坐标来获取指定范围内的地理位置集合。
>
>   ```java
>   GEORADIUS key longitude latitude radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count] [ASC|DESC] [STORE key] [STOREDIST key]
>   ```
>
>   * WITHCOORD  将位置元素的经度和维度也一并返回
>   * WITHDIST       在返回位置元素的同时， 将位置元素与中心之间的距离也一并返回
>   * WITHHASH     以 52 位有符号整数的形式， 返回位置元素经过原始 geohash 编码的有序集合分值。 这个选项主要用于底层应用或者调试， 实际中的作用并不大。
>   * COUNT           限定返回的记录数
>   * ASC                 查找结果根据距离从近到远排序。
>   * DESC: 查找结果根据从远到近排序。
>
>   ```java
>   GEORADIUS Sicily 15 37 200 km WITHDIST
>   ```
>
>   
>
> * **georadiusbymember**：根据储存在位置集合里面的某个地点获取指定范围内的地理位置集合。
>
>   ```java
>   GEORADIUSBYMEMBER key member radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count] [ASC|DESC] [STORE key] [STOREDIST key]
>   ```
>
>   ```java
>   GEORADIUSBYMEMBER Sicily Agrigento 100 km
>   ```
>
>   
>
> * **geohash**：返回一个或多个位置对象的 geohash 值。
>
>   ```java
>   GEOHASH key member [member ...]
>   ```
>
>   ```java
>   GEOHASH Sicily Palermo Catania
>   ```
>
>   

# Stream

> Stream 是 Redis 5.0 版本新增加的数据结构。
>
> Stream 主要用于消息队列（MQ，Message Queue）
>
>  Stream 提供了消息的持久化和主备复制功能，可以让任何客户端访问任何时刻的数据，并且能记住每一个客户端的访问位置，还能保证消息不丢失。
>
> Stream 的结构如下所示，它有一个消息链表，将所有加入的消息都串起来，每个消息都有一个唯一的 ID 和对应的内容：
>
> <img src="redis.assets/%[LF{K@}O`]JZ}[%}68R32U.png" alt="img" style="zoom:150%;" />
>
> 
>
> 每个 Stream 都有唯一的名称，它就是 Redis 的 key，在我们首次使用 xadd 指令追加消息时自动创建。
>
> 图片解析：
>
> * **Consumer Group** ：消费组，使用 XGROUP CREATE 命令创建，一个消费组有多个消费者(Consumer)。
> * **last_delivered_id** ：游标，每个消费组会有个游标 last_delivered_id，任意一个消费者读取了消息都会使游标 last_delivered_id 往前移动。
> * **pending_ids** ：消费者(Consumer)的状态变量，作用是维护消费者的未确认的 id。 pending_ids 记录了当前已经被客户端读取的消息，但是还没有 ack (Acknowledge character：确认字符）。
>
> **消息队列相关命令：**
>
> - **XADD** - 添加消息到末尾--向队列添加消息，如果指定的队列不存在，则创建一个队列
>
>   ```java
>   XADD key ID field value [field value ...]
>   ```
>
>   * **key** ：队列名称，如果不存在就创建
>   * **ID** ：消息 id，我们使用 * 表示由 redis 生成，可以自定义，但是要自己保证递增性。
>   * **field value** ： 记录。
>
>   ```java
>   XADD mystream * name Sara surname OConnor   #创建一个队列
>   XADD mystream * field1 value1 field2 value2 field3 value3  # 添加消息到末尾
>   XLEN mystream    #取队列长度
>   XRANGE mystream - +  #输出队列
>   ```
>
>   
>
> ***
>
> - **XTRIM** - 对流进行修剪，限制长度, 取后面得数据
>
>   ```java
>   XTRIM key MAXLEN [~] count
>   ```
>
>   - **key** ：队列名称
>   - **MAXLEN** ：长度
>   - **count** ：数量
>
>   ```java
>   XTRIM mystream MAXLEN 2
>   ```
>
>   
>
> ***
>
> 
>
> - **XDEL** - 删除消息
>
>   ```java
>   XDEL key ID [ID ...]
>   ```
>
>   ```java
>   #先输出所有队列
>   XRANGE mystream - +  #输出队列
>   XDEL mystream 1538561700640-0  #根据id删除
>   ```
>
>   
>
> ***
>
> - **XLEN** - 获取流包含的元素数量，即消息长度
>
>   ```java
>   XLEN mystream    #取队列长度
>   ```
>
>   
>
> ***
>
> - **XRANGE** - 获取消息列表，会自动过滤已经删除的消息  从前往后输出
>
>   ```java
>   XRANGE key start end [COUNT count]
>   ```
>
>   - **key** ：队列名
>   - **start** ：开始值， **-** 表示最小值
>   - **end** ：结束值， **+** 表示最大值
>   - **count** ：数量
>
>   ```java
>   XRANGE writers - + COUNT 2 
>   ```
>
>   
>
> ****
>
> - **XREVRANGE** - 反向获取消息列表，ID 从大到小 从后往前输出
>
>   ```java
>   XREVRANGE key end start [COUNT count]
>   ```
>
>   ```java
>   XREVRANGE writers + - COUNT 1  #输出最后一个
>   ```
>
>   
>
> ***
>
> - **XREAD** - 以阻塞或非阻塞方式获取消息列表
>
>   ```java
>   XREAD [COUNT count] [BLOCK milliseconds] STREAMS key [key ...] id [id ...]
>   ```
>
>   - **count** ：数量
>   - **milliseconds** ：可选，阻塞毫秒数，没有设置就是非阻塞模式
>   - **key** ：队列名
>   - **id** ：消息 ID
>
>   ```java
>   XREAD COUNT 2 STREAMS mystream writers 0-0 0-0
>   ```
>
>   
>
> ***
>
> **消费者组相关命令：**
>
> - **XGROUP CREATE** - 创建消费者组
>
>   ```java
>   XGROUP [CREATE key groupname id-or-$] [SETID key groupname id-or-$] [DESTROY key groupname] [DELCONSUMER key groupname consumername]
>   ```
>
>   - **key** ：队列名称，如果不存在就创建
>   - **groupname** ：组名。
>   - **$** ： 表示从尾部开始消费，只接受新消息，当前 Stream 消息会全部忽略。
>
>   从头开始消费
>
>   ```java
>   XGROUP CREATE mystream consumer-group-name 0-0  
>   ```
>
>   从尾开始消费
>
>   ```java
>   XGROUP CREATE mystream consumer-group-name $
>   ```
>
>   
>
> ***
>
>   
>
> - **XREADGROUP GROUP** - 读取消费者组中的消息
>
>   ```java
>   XREADGROUP GROUP group consumer [COUNT count] [BLOCK milliseconds] [NOACK] STREAMS key [key ...] ID [ID ...]
>   ```
>
>   - **group** ：消费组名
>   - **consumer** ：消费者名。
>   - **count** ： 读取数量。
>   - **milliseconds** ： 阻塞毫秒数。
>   - **key** ： 队列名。
>   - **ID** ： 消息 ID。
>
>   ```java
>   XREADGROUP GROUP consumer-group-name consumer-name COUNT 1 STREAMS mystream >
>   ```
>
> ***
>
> 
>
> - **XACK** - 将消息标记为"已处理"
> - **XGROUP SETID** - 为消费者组设置新的最后递送消息ID
> - **XGROUP DELCONSUMER** - 删除消费者
> - **XGROUP DESTROY** - 删除消费者组
> - **XPENDING** - 显示待处理消息的相关信息
> - **XCLAIM** - 转移消息的归属权
> - **XINFO** - 查看流和消费者组的相关信息；
> - **XINFO GROUPS** - 打印消费者组的信息；
> - **XINFO STREAM** - 打印流信息
>
> 

# Redis 数据备份与恢复

>* **save或者bgsave**
>
>  > 该命令将在 redis 安装目录中创建dump.rdb文件。
>
>* 恢复
>
>  > 只需将备份文件 (dump.rdb) 移动到 redis 安装目录并启动服务即可
>
>* 获取redis安装目录
>
>  >```java
>  >CONFIG GET dir
>  >```

# redis 安全

> https://learnku.com/articles/50446
>
> redis没有实现访问控制这个功能，但是它提供了一个轻量级的认证方式，可以编辑redis.conf配置来启用认证。
>
> ```java
> config set requirepass 123456 #设置验证密码 123456
> auth 123456 #通过密码
> 如果配置文件中没添加密码 那么redis重启后，密码失效；
> AUTH命令跟其他redis命令一样，是没有加密的；阻止不了攻击者在网络上窃取你的密码；
> ```
>
> 
