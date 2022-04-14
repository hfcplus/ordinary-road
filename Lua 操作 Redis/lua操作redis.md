# 1.过程

* #### 编写lua脚本 hello.lua

  ```lua
  local msg = "hello world!"
  return msg
  ```

* #### redis运行lua脚本

  ```lua
  redis-cli -h ****(ip) -p ***(port) eval "$(cat hello.lua)" 0
  ```

  

# 2.真正实际用到的

* #### 直接在redis中书写lua脚本

  ```lua
  EVAL luascript numkeys key [key ...] arg [arg ...]
  ```

  * `EVAL` 命令的关键字。
  * `luascript` Lua 脚本。
  * `numkeys` 指定的Lua脚本需要处理键的数量，其实就是 `key`数组的长度。
  * `key` 传递给Lua脚本零到多个键，空格隔开，在Lua 脚本中通过 `KEYS[INDEX]`来获取对应的值，其中`1 <= INDEX <= numkeys`。
  * `arg`是传递给脚本的零到多个附加参数，空格隔开，在Lua脚本中通过`ARGV[INDEX]`来获取对应的值，其中`1 <= INDEX <= numkeys`。

* #### 实例

  ```lua
  127.0.0.1:6379> set hello world
  OK
  127.0.0.1:6379> get hello
  "world"
  127.0.0.1:6379> EVAL "return redis.call('GET',KEYS[1])" 1 hello
  "world"
  127.0.0.1:6379> EVAL "return redis.call('GET','hello')" 0
  "world"
  ```

  * 从上面的演示代码中发现，`KEYS[1]`可以直接替换为`hello`,**但是Redis官方文档指出这种是不建议的，目的是在命令执行前会对命令进行分析，以确保Redis Cluster可以将命令转发到适当的集群节点**。

* #### 脚本管理

  * 加载脚本到缓存以达到重复使用，避免多次加载浪费带宽，每一个脚本都会通过SHA校验返回唯一字符串标识。需要配合`EVALSHA`命令来执行缓存后的脚本。

    ```lua
    127.0.0.1:6379> SCRIPT LOAD "return 'hello'"
    "1b936e3fe509bcbc9cd0664897bbe8fd0cac101b"
    127.0.0.1:6379> EVALSHA 1b936e3fe509bcbc9cd0664897bbe8fd0cac101b 0
    "hello"
    ```

* #### 清除脚本

  * ##### SCRIPT FLUSH

    > 清除所有的脚本缓存，所以在生产中一般不会再生产过程中使用该命令。

  * ##### SCRIPT EXISTS

    > 以SHA标识为参数检查一个或者多个缓存是否存在。

    ```lua
    127.0.0.1:6379> SCRIPT EXISTS 1b936e3fe509bcbc9cd0664897bbe8fd0cac101b  1b936e3fe509bcbc9cd0664897bbe8fd0cac1012
    1) (integer) 1
    2) (integer) 0
    ```

  * ##### SCRIPT KILL

    >终止正在执行的脚本。**但是为了数据的完整性此命令并不能保证一定能终止成功**。
    >
    >如果当一个脚本执行了一部分写的逻辑而需要被终止时，该命令是不凑效的。
    >
    >需要执行`SHUTDOWN nosave`在不对数据执行持久化的情况下终止服务器来完成终止脚本。

    

    
