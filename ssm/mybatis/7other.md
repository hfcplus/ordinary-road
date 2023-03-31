插入返回主键

插入对象users最开始没有id，插入完成后有id

```xml
 <insert id="addUsers" parameterType="users" useGeneratedKeys="true" keyProperty="id" keyColumn="id">
    insert into users values (#{id}, #{userName}, #{birthday}, #{sex}, #{address})
</insert>
```

