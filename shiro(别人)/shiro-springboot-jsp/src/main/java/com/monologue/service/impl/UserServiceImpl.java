package com.monologue.service.impl;

import com.monologue.entity.Permission;
import com.monologue.entity.Role;
import com.monologue.entity.User;
import com.monologue.mapper.UserMapper;
import com.monologue.service.UserService;
import com.monologue.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Monologue_zsj on 2021/3/7 15:59
 * Author：小脸儿红扑扑
 * Description：
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {

        //明文密码进行MD5 + salt + hash散列
        //1、生成随机盐
        String salt = SaltUtils.getSalt(8);
        //2、将随机盐保存的数据库
        user.setSalt(salt);

        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());

        userMapper.save(user);
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public User findRolesByUsername(String username) {
        return userMapper.findRolesByUsername(username);
    }

    @Override
    public List<Permission> findPermissionByRoleId(Integer id) {
        return userMapper.findPermissionByRoleId(id);
    }
}
