package com.monologue.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * Created by Monologue_zsj on 2021/3/7 12:23
 * Author：小脸儿红扑扑
 * Description：使用自定义Realm加入 md5 + salt + hash
 */
public class CustomerMD5Realm extends AuthorizingRealm {

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

        String primaryPrincipal = (String) principal.getPrimaryPrincipal();
        System.out.println("身份信息(用户名)===============》" + primaryPrincipal);
        //根据身份信息获取当前用户的角色信息，以及权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //将数据库中查询角色信息赋给权限对象
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addRole("user");

        //将数据库中查询权限信息赋值个给权限对象
        simpleAuthorizationInfo.addStringPermission("user:*:01");
        simpleAuthorizationInfo.addStringPermission("product:*:*");

        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取身份信息
        String principal = (String) token.getPrincipal();
        //根据用户名查询数据库
        if ("xiaozhang".equals(principal)) {
            /**
             * 参数1：数据库用户名
             * 参数2：数据库md5 + salt之后的密码
             * 参数3：注册时的随机盐
             * 参数4：realm的名字
             */
            return new SimpleAuthenticationInfo(principal,
                    "955224a95d4161ad8bd84f7ede979c02",
                    ByteSource.Util.bytes("X0*7ps"),
                    this.getName());
        }
        return null;
    }
}
