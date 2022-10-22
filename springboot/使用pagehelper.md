添加pagehelper依赖,SpringBoot需要添加start启动包

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.4.2</version>
</dependency>
```

基础使用方法

>在正常的查询方法前面添加``PageHelper.startPage(pageNo, pageSize);``即可
>
>```java
>@Service
>public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
>    @Autowired
>    UserMapper userMapper;
>    @Override
>    public List<User> selectUserForPage() {
>        PageHelper.startPage(1, 5); // 第一页的5行数据
>        List<User> users = userMapper.selectAll(); // 正常的sql没有写limit
>        PageInfo<User> pageInfo = new PageInfo<>(users);
>        long total = pageInfo.getTotal(); // 总行数
>        List<User> list = pageInfo.getList(); // 获取的数据
>        int pages = pageInfo.getPages(); // 总页数
>        int size = pageInfo.getSize(); // 当前页有几行数据
>        return users;
>    }
>}
>```

其他使用方法

>原理：PageHelper.startPage(xxx); // 他会在xxx自动找pageNum, pageSize属性
>
>map:
>
>```java
>Map<String, Object> map = new HashMap<>();
>map.put("pageNum", 1);
>map.put("pageSize", 2);
>PageHelper.startPage(map);
>```
>
>类：类的属性有pageSize， pageNum
>
>```java
>// 自定义的类
>public class PageUtils {
>    private Integer pageNum;
>    private Integer pageSize;
>    
>    public PageUtils(Integer pageNum, Integer pageSize) {
>        this.pageSize = pageSize;
>        this.pageNum = pageNum;
>    }
>}
>
>
>PageUtils pageUtils = new PageUtils(2, 3);
>PageHelper.startPage(pageUtils);
>
>```
>
>