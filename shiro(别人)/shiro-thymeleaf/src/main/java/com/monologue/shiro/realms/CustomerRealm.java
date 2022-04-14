package com.monologue.shiro.realms;

import com.monologue.entity.Permission;
import com.monologue.entity.User;
import com.monologue.service.UserService;
import com.monologue.shiro.salt.MyByteSource;
import com.monologue.utils.ApplicationContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by Monologue_zsj on 2021/3/7 14:52
 * Author：小脸儿红扑扑
 * Description：自定义Realm
 */
public class CustomerRealm extends AuthorizingRealm {

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

        //获取身份信息
        String primaryPrincipal = (String) principal.getPrimaryPrincipal();
        //根据主身份信息获取角色 和 权限信息
        UserService userService = (UserService) ApplicationContextUtils.getBean("userServiceImpl");
        User user = userService.findRolesByUsername(primaryPrincipal);

        //根据主身份信息获取角色和权限信息
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            user.getRoles().forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getName());
                //权限信息
                List<Permission> permissions = userService.findPermissionByRoleId(role.getId());
                if (!CollectionUtils.isEmpty(permissions)) {
                    permissions.forEach(permission -> {
                        simpleAuthorizationInfo.addStringPermission(permission.getName());
                    });
                }
            });
            return simpleAuthorizationInfo;
        }

        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("_________________________");
        String principal = (String) token.getPrincipal();

        //在工厂中获取service对象
        UserService userService = (UserService) ApplicationContextUtils.getBean("userServiceImpl");

        User user = userService.findByUserName(principal);

        if (!ObjectUtils.isEmpty(user)) {
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), new MyByteSource(user.getSalt()), this.getName());
        }
        return null;
    }
}
