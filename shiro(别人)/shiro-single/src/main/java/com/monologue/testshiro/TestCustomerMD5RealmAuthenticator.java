package com.monologue.testshiro;

import com.monologue.realm.CustomerMD5Realm;
import com.monologue.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

/**
 * Created by Monologue_zsj on 2021/3/7 10:46
 * Author：小脸儿红扑扑
 * Description：使用自定义Realm
 */
public class TestCustomerMD5RealmAuthenticator {
    public static void main(String[] args) {

        //创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //注入Realm
        CustomerMD5Realm realm = new CustomerMD5Realm();
        //设置Realm使用hash凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //使用的算法
        credentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数
        credentialsMatcher.setHashIterations(1024);
        realm.setCredentialsMatcher(credentialsMatcher);
        //设置自定义Realm
        defaultSecurityManager.setRealm(realm);
        //给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //通过安全工具类获取subject
        Subject subject = SecurityUtils.getSubject();
        //创建token，认证
        UsernamePasswordToken token = new UsernamePasswordToken("xiaozhang", "123456");
        try {
            subject.login(token);
            System.out.println("登录成功...");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("认证失败========> 用户名不存在...");
        }catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("认证失败========> 密码错误...");
        }

        //认证的用户进行授权
        if (subject.isAuthenticated()) {    //认证成功
            //基于角色的权限控制
            System.out.println("hasRole：" + subject.hasRole("admin"));
            System.out.println("__________________________________________________________________");
            //基于多角色的权限控制
            System.out.println("hasAllRoles：" + subject.hasAllRoles(Arrays.asList("admin", "user")));
            System.out.println("__________________________________________________________________");
            //是否具有其中一个角色
            for (boolean role : subject.hasRoles(Arrays.asList("admin", "user", "super"))) {
                System.out.println("hasRoles：" + role);
            }
            System.out.println("__________________________________________________________________");
            //基于权限字符串的访问控制      资源标识符:操作:资源类型
            System.out.println("isPermitted：" + subject.isPermitted("user:*:01"));
            System.out.println("__________________________________________________________________");
            //分别具有那些权限
            for (boolean permitted : subject.isPermitted("user:*:01", "order:*:10")) {
                System.out.println(permitted);
            }
            System.out.println("__________________________________________________________________");
            //同时具有那些权限
            System.out.println(subject.isPermittedAll("user:*:01", "product:*"));
        }
    }
}
