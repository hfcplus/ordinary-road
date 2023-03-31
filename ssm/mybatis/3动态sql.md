## sql

将经常使用的sql语句定义为一个常量，用include标签进行调用

```xml
<sql id="columns">
    id,username,birthday,sex,address
</sql>
<sql id = "selectUserById">
	select * from Users where id = #{id}
</sql>
```

## include

引用sql标签

```xml
<select id="getById" parameterType="int" resultType="users">
    <include refid="selectById"/>
</select>
```

## if

```xml
<if test="userName != null and userName != '' ">
    and username like concat('%',#{userName},'%')
</if>
```

## where

可以自动去除第一个 and,or

如果所有得if都不满足，则自动省略where，查询所有

```xml
<select id="getByCondition" resultType="users" parameterType="users">
    select <include refid="allColumns"></include>
    from users
    <where>
        <if test="userName != null and userName != '' ">
            and username like concat('%',#{userName},'%')
        </if>
        <if test="birthday != null ">
            and birthday = #{birthday}
        </if>
        <if test="sex != null and sex !=''">
            and sex = #{sex}
        </if>
    </where>
</select>
```

## set

自动去除最后一个 ,

注意： 至少更新一列，如果全部不满足就会报错

update users set where id = ?

```xml
<update id="updateBySet" parameterType="users">
    update users
    <set>
        <if test="userName != null and userName != ''">
            username = #{userName},
        </if>
        <if test="birthday != null">
            birthday = #{birthday},
        </if>
        <if test="sex != null and sex != ''">
            sex =#{sex},
        </if>
    </set>
    where id = #{id}
</update>

```

## foreach

collection: list, array, map

item: 循环的具体对象，item是list或array中的对象，在map中是value

index：在list和array中是下标， 在map中是 key

open：以什么开始

close：以什么结束

separator：中间的隔离符

### 批量删除

```xml
<delete id="deleteBatch" >
    delete from users where id in
    <foreach collection="array" item="id" close=")" open="(" separator=",">
        #{id}
    </foreach>
</delete>
```

### 批量增加

```xml
<insert id="insertBatch" >
    insert into users(username,birthday,sex,address) values
    <foreach collection="list" separator="," item="u">
        (#{u.userName},#{u.birthday},#{u.sex},#{u.address})
    </foreach>
</insert>
```

### 批量更新

循环一次，一条sql，所以批量更新会一次性产生多条sql，jdbc.url 中需要添加``&allowMultiQueries=true``

```xml
<update id="updateSet"  >
    <foreach collection="list" item="u" separator=";">
        update users
        <set>
            <if test="u.userName != null  and u.userName != ''">
                username=#{u.userName},
            </if>
            <if test="u.birthday != null">
                birthday = #{u.birthday},
            </if>
            <if test="u.sex != null  and u.sex != ''">
                sex = #{u.sex},
            </if>
            <if test="u.address != null  and u.address != ''">
                address = #{u.address}
            </if>
        </set>
        where id = #{u.id}
    </foreach>
</update>
```

## ${}和#{}

#{} 是占位符，可以用于参数的占位，不能用于表名。入参为简单类型，#{}里面写入参的名称；入参为对象#{}里面写对象的成员变量名称

${} 是拼接符，什么地方都可以用

```xml
<select id="login" resultType="users">
    select id, username, birthday, sex, address
    from ${tableName} where
    id = ${id} and #{columName} = #{name}
</select>
```

## @Param

## 入参为Map

#{},${},取的Map的key

```xml
<select id="getByMap" parameterType="map" resultType="users">
    select <include refid="columns"></include>
    from users
    where birthday between #{zarbegin} and #{zarend}
</select>
```

## 返回Map

没有合适的返回对象时可以使用map，但是一般不推荐使用。查询出来的列名，就是Map的key

返回单列

```java
Map<String,Object> getReturnMapOne(int id);
```

```xml
<select id="getReturnMapOne" resultType="map" parameterType="int">
    select id myid,username myusername,sex mysex,address myaddress,birthday mybirthday
    from users
    where id=#{id}
</select>

```

返回多列

```java
List<Map<String, Object>> getReturnMap();
```

```xml
<select id="getReturnMapOne" resultType="map">
    select id myid,username myusername,sex mysex,address myaddress,birthday mybirthday
    from users
</select>
```

## 列名与类中成员变量名称不一致

比如java中成员变量为name，Mysql中colum为userName

第一种方法，用别名

```xml
<select id="getAll" resultType="book">
    select bookid id, bookname name
    from book
</select>
```

第二种方法，定义resultMap

```xml
<!--
	property: 类的属性名称，区分大小写
	column: 表的列名，不区分大小写
-->
<resultMap id="bookMap" type="book">
    <id property="id" column="bookid"/>
    <result property="name" column="bookname"/>
</resultMap>

<!-- 使用resultMap -->
<select id="getAll" resultMap="bookMap">
    select bookid, bookname
    from book
</select>
```

