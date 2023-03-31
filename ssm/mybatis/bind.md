## bind

name

value

mapper.java 传入 name，name通过拼接赋值给bind，sql语句引用bind

```xml
<select id="getUsersByNameLike" parameterType="string" resultType="users">
    <bind name="nameLike" value="'%' + name + '%'"/>
    select id, username, birthday, sex, address
    from users
    <where>
        <if test="userName != null and userName != ''">
            and username like #{nameLike};
        </if>
    </where>
</select>
```

