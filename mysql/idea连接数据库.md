# 连接问题

```tex
server returns invalid timezone. Go to ‘Advanced’ tab and set ‘serverTimezon’
```

原理：

>时区错误，MySQL默认的时区是UTC时区，比北京时间晚8个小时。
>
>所以要修改MySQL的时长
>
>在MySQL的命令模式下，输入：
>
>``set global time_zone='+8:00';``

 

# 创建数据库

## 通过dos创建数据库，或者通过idea的console创建数据库

在安装数据库8.0的时候默认 CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

所以我们创建数据库:

```mysql
create database myDB;
show create database myDB; -- 查看怎么创建数据库mydb
| mydb     | CREATE DATABASE `mydb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */ |
+----------+---------
```

不确定数据库的编码时,推荐使用utf8mb4

```mysql
create database mybd2 character set utf8mb4 collate utf8mb4_general_ci;
show create database mydb2; -- 查看怎么创建数据库mydb2
mydb2  | CREATE DATABASE `mydb2` /*!40100 DEFAULT CHARACTER SET utf8mb4 */ /*!80016 DEFAULT ENCRYPTION='N' */ |
```

## 通过idea的图形 窗口创建数据库

![image-20220304212812866](idea%E8%BF%9E%E6%8E%A5%E6%95%B0%E6%8D%AE%E5%BA%93.assets/image-20220304212812866.png)

![image-20220311172424852](idea%E8%BF%9E%E6%8E%A5%E6%95%B0%E6%8D%AE%E5%BA%93.assets/image-20220311172424852.png)