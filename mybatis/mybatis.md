```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.wayn.commom.dao.TeamDao">
    <resultMap id="teamVo" type="com.wayn.commom.domain.vo.TeamVo">
        <id column="id" property="id"/>
        <result column="teamName" property="teamName" />
        <!--一对一  property是类中的属性，column是sql查询的结果字段重命名 -->
        <association property="teamLeader" javaType="com.wayn.commom.domain.User">
            <id property="id" column="leaderId2"/>
            <result column="leaderName" property="name"/>
            <result column="leaderUserName" property="userName"/>
        </association>
        
        <!-- 一对多 -->
        <collection property="teamMembers" ofType="com.wayn.commom.domain.User">
            <id column="memberId" property="id"/>
            <result column="memberName" property="name"/>
            <result column="memberUserName" property="userName"/>
        </collection>
    </resultMap>

    <select id="pageListAll" resultMap="teamVo">
        select st.*,
               su1.id leaderId2,
               su1.name leaderName,
               su1.userName leaderUserName ,
               su2.id memberId,
               su2.name memberName,
               su2.userName memberUserName
        from sys_team st,sys_user su1,sys_user su2 where st.leaderId = su1.id and st.id = su2.teamId
        <if test="searchWord != null and searchWord != ''">
            and (st.teamName like CONCAT('%',#{searchWord},'%') or su.name like CONCAT('%',#{searchWord},'%'))
        </if>
        <if test="userId = null and userId != ''">
            and (st.leaderId = #{userId} or su.id = #{userId})
        </if>
    </select>
</mapper>
```

## 分页

1. controller

   ```java
   public String xxx(){
       Page<Map<String,Object>> page =new Page<>(1, Integer.MAX_VALUE);
   }
   
   ```

   

2. dao

   ```java
   public interface StudentDao extends BaseMapper<Student> {
       List<Map<String, Object>> getStudent(Pagination page, Condition condition);//condition是自己写的条件类，可有可无
   }
   ```

   

3. serviceImpl

   ```java
   @Service
   public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {
   // 如果StudentDao 已经继承了BaseMapper,上面这句话可以这样写
   //public class StudentServiceImpl implements StudentService {
       @Autowired
       private StudentDao studentDao;
   
       public List<Student> getStudent(Page page, Conditon donditon){
           return studentDao.getStudent(page, condition);
       }
   }
   ```

   

4. mapper.xml

   ```xml
   // 和mybatis的查询一模一样，不用管是否分页
   ```

   

## 插入返回主键

useGeneratedKeys: 取出生成的主键

keyProperty：将生成的主键赋值给属性，多个用,分割。在这里赋值给User的id

```xml
<insert id="insertUserReturnId" parameterType="plus.hf.domain.User" useGeneratedKeys="true" keyProperty="id" keyColumn="id">
    insert into u_user (phone, login_password, add_time) values (#{phone}, #{loginPassword}, #{addTime})
</insert>
```

