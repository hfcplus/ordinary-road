package com.monologue.service;

import com.monologue.entity.Permission;
import com.monologue.entity.Role;
import com.monologue.entity.User;

import java.util.List;

/**
 * Created by Monologue_zsj on 2021/3/7 15:58
 * Author：小脸儿红扑扑
 * Description：
 */
public interface UserService {

    void register(User user);
    User findByUserName(String username);

    //根据用户名查询角色
    User findRolesByUsername(String username);

    List<Permission> findPermissionByRoleId(Integer id);
}
