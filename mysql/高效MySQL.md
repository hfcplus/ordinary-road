## 一

### 并发

**锁的**种类：共享锁（shared lock）和排他锁（exclusive lock），也叫读锁（read lock）和写锁（write lock）。

**锁的粒度**：行级锁（row level lock）

**锁的策略**：行级锁（low lock，在存储引擎实现），表锁（table lock）

**死锁**：innodb检测到循环依赖就会立即返回错误信息。如果发生死锁，innodb会将拥有最少行锁的事务回滚

### 事务

**事务日志**：存储引擎强更改的记录写入日志（日志保存到硬盘：追加的形式，顺序的I/O（比较快）），日志可恢复数据

### MVCC

MVCC的工作原理是使用数据在某个时间点的快照来实现的。这意味着，无论事务运行多长时间，都可以看到数据的一致视图，也意味着不同的事务可以在同一时间看到同一张表中的不同数据







现在有A，B，C，D，E 5张表，ABCD四张表是一对一，AE是一对多

现在的需求是一对多取一条

* select 列中运用子查询

  > 优点：子查询内会走索引？
  >
  > 缺点：重复执行子查询？

  ```sql
  select A.id,.......,(select max(E.name) from E where A.id = E.id) 
  from A,B,C,D where A.id = B.id and A.id = C.id and A.id = D.id
  ```

  

* 左外连接

  >优点：只读一次E表？
  >
  >缺点：子查询形成的零时表没有索引，慢

```sql
select A.id,......., E.name from A 
join B on A.id = B.id
join C on A.id = C.id 
join D on A.id = E.id
left join (select max(E.name) name, E.id from E group by E.id) temp on temp.id = A.id
```

