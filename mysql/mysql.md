# 索引

```mysql
-- 查看索引
show index from table_name;

-- 添加索引
-- 1.添加PRIMARY KEY（主键索引）当设置该列为主键时，会自动添加主键索引
ALTER TABLE `table_name` ADD PRIMARY KEY ( `column` )

-- 2.添加UNIQUE(唯一索引)
ALTER TABLE `table_name` ADD UNIQUE (
`column`
)

-- 3.添加INDEX(普通索引)
ALTER TABLE `table_name` ADD INDEX index_name ( `column` )

-- 4.添加FULLTEXT(全文索引)
ALTER TABLE `table_name` ADD FULLTEXT ( `column`)

-- 5.添加多列索引
ALTER TABLE `table_name` ADD INDEX index_name ( `column1`, `column2`, `column3` )
```



# 时间函数

```mysql
-- 获取现在的时间
select now();  -- 2021-07-16 11:49:36

-- 获取现在的年月日
select current_date; -- 2021-07-16
select year(now());  
select month(now());
select day(now());

-- 获取现在的时分秒
select current_time;  -- 11:50:45

-- 将时间转换为天数
select to_days(now());  -- 738352

-- 获取时间的年月日
date(now());
DATE_FORMAT(now() ,'%Y-%m-%d')

```

![img](mysql.assets/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0xpX0ppYW5fSHVpXw==,size_16,color_FFFFFF,t_70.png)

