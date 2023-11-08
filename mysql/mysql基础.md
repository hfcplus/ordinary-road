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

## Mysql 执行顺序

from ---- where ---- group by having ---- select ---- order by ---- limit 



## 增删改表结构

为表增加字段 add

```mysql
alter table t_student add  contact_tel varchar(40);
```

删除字段 drop

```mysql
alter table t_student drop contact_tel;
```

修改字段类型

```mysql
alter table t_student modify student_name varchar(100);
```

修改字段名称

```mysql
alter table t_student change sex gender char(2) not null;
```



![image-20220305104434753](mysql%E5%9F%BA%E7%A1%80%E8%AF%AD%E6%B3%95.assets/image-20220305104434753.png)

![image-20220305104623442](mysql%E5%9F%BA%E7%A1%80%E8%AF%AD%E6%B3%95.assets/image-20220305104623442.png)

## limit

```mysql
select * from user limit 1, 4; -- 第一行开始的后面4行数据
select * from user limit 4 offset 1 -- 第一行开始的后面四行数据

```

## and or

再处理or之前会优先处理and

```mysql
select * from products where vend_id = '1002' or vend_id = '1003' and prod_price >= 10;
--  先处理and 后处理or ，所以上面的sql等同于
select * from products where (vend_id = '1003' and prod_price >= 10) or vend_id = '1002';
```

## 通配符 % _

要匹配字符串中的百分号和下划线，需要在通配符中，使用右斜线“\”对百分号和下划线，进行转义

如果没有like字段，_就是字符，不需要转义

例如，name 字段值为 \_ABCD，_A_CD，我们想查第三个字符为\_的name

```mysql
select * from user where name like '__\_%';   -- 第一二个_为通配符，第三个_为字符
```

技巧：

1. 能不用就不用
2. 必须用的话，放在最后

## 正则表达式

正则表达式匹配的是列中的值，like是匹配一整行

正则表达式不区分大小写，如果需要区分，需要加上BINARY关键字`select * from temp where name regexp binary 'abc'`



### 基本字符匹配

```mysql
select * from user where name regexp 'abc';		-- 查询包含abc的列
select * from user where name regexp '.abc';	-- 查询任意字符abc的列 
```

### or匹配 |

```mysql
select * from user where name regexp 'abc|123|asd'; -- 查询包含abc或者123或者asd的列
select * from user where name regexp '1|2|3asd'; -- 查询包含1或者2或者3asd的列
```

### 匹配几个字符之一 []

```mysql
select * from user where name regexp '[123]ads'; -- 查询1ads，2ads，3ads
```

### 范围匹配

```mysql
select * from user where name regexp '[1-5]ads'; -- 查询1ads，2ads，3ads,4ads,5ads
```

### 匹配特殊字符 

使用`\\`的原因：mysql使用一个`\`解释,正则使用一个`\`解释，所以一共需要两个，mysql的通配符解释只需要mysql解释，所以只用一个`\`。

```mysql
select * from user where name regexp '\\.'; -- 查询.
```

### 匹配多个实例

| 元字符 | 说明           |
| ------ | -------------- |
| *      | 0个或多个      |
| +      | 1个或多个      |
| ？     | 0个或1个       |
| {n}    | 匹配n个        |
| {n,}   | 大于等于n个    |
| {n,m}  | n到m，m最多255 |

### 定位符

| 元字符 | 说明       |
| ------ | ---------- |
| ^      | 文档的开始 |
| $      | 文档的结尾 |
|        |            |



## 窗口函数

ROW_NUMBER(),连续不重复，over 里面的order by 和sql的order by 要保持一致

```mysql
select *,row_number() OVER(order by number ) as row_num from num 
```

![img](mysql%E5%9F%BA%E7%A1%80%E8%AF%AD%E6%B3%95.assets/1623029160-OIBonQ-2.png)

rank() 排序的值相同的归为一组且每组序号一样，排序不会连续执行

```mysql
select *,rank() OVER(order by number ) as row_num from num 
```

![`](mysql%E5%9F%BA%E7%A1%80%E8%AF%AD%E6%B3%95.assets/1623029183-THUYoe-4.png)

Dense_rank() 排序是连续的，也会把相同的值分为一组且每组排序号一样

```mysql
select *,dense_rank() OVER(order by number ) as row_num from num 
```

![](mysql%E5%9F%BA%E7%A1%80%E8%AF%AD%E6%B3%95.assets/1623029200-APlyUj-5.png)

Ntile(group_num) 将所有记录分成group_num个组，每组序号一样

```mysql
select *,ntile(2) OVER(order by number ) as row_num from num 
```

![](mysql%E5%9F%BA%E7%A1%80%E8%AF%AD%E6%B3%95.assets/1623029213-kgvvyl-6.png)

## 子查询

根据子查询的返回结果不同分为：

* 标量子查询（查询结果位单个值）
* 列子查询（查询结果位一列）
* 行子查询（查询结果为一行）
* 表子查询（查询结果为多列多行）
