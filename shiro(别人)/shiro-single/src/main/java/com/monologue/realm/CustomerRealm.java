package com.monologue.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by Monologue_zsj on 2021/3/7 10:43
 * Author：小脸儿红扑扑
 * Description：自定义Realm实现，将认证/授权的数据的来源转为数据库的实现
 */
public class CustomerRealm extends AuthorizingRealm {

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //在token中获取用户名
        String principal = (String) token.getPrincipal();
        //根据身份信息使用jdbc/mybatis...查询相关数据库
        if ("xiaozhang".equals(principal)) {
            /**
             * 参数1：返回数据库中正确的用户名
             * 参数2：返回数据库中正确的密码
             * 参数3：提供当前Realm的名字 this.getName()
             */
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, "123", getName());

            return simpleAuthenticationInfo;
        }
        return null;
    }
}
