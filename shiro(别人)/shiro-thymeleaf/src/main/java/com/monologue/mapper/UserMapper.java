package com.monologue.mapper;

import com.monologue.entity.Permission;
import com.monologue.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Monologue_zsj on 2021/3/7 15:53
 * Author：小脸儿红扑扑
 * Description：
 */
@Mapper
@Repository
public interface UserMapper {

    int save(User user);

    @Select("select * from shiro_user where username = #{username}")
    User findByUserName(String username);

    //根据用户名查询角色
    User findRolesByUsername(String username);

    //根据角色id查询权限集合
    List<Permission> findPermissionByRoleId(Integer id);
}
