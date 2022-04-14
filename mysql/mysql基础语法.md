ddl

创建表

```mysql
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT, -- 自增
  `name` varchar(20) NOT NULL,      -- 不为空
  `sex` varchar(1) DEFAULT '男',     -- 默认值
  `age` int DEFAULT NULL,           -- 
  PRIMARY KEY (`id`)                -- 主键
)
```

![image-20220305104434753](mysql%E5%9F%BA%E7%A1%80%E8%AF%AD%E6%B3%95.assets/image-20220305104434753.png)

![image-20220305104623442](mysql%E5%9F%BA%E7%A1%80%E8%AF%AD%E6%B3%95.assets/image-20220305104623442.png)