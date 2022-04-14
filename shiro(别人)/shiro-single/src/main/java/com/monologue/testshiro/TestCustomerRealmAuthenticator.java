package com.monologue.testshiro;

import com.monologue.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * Created by Monologue_zsj on 2021/3/7 10:46
 * Author：小脸儿红扑扑
 * Description：使用自定义Realm
 */
public class TestCustomerRealmAuthenticator {
    public static void main(String[] args) {

        //创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //设置自定义Realm
        defaultSecurityManager.setRealm(new CustomerRealm());
        //给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //通过安全工具类获取subject
        Subject subject = SecurityUtils.getSubject();
        //创建token
        UsernamePasswordToken token = new UsernamePasswordToken("xiaozhang", "123");
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("认证失败========> 用户名不存在...");
        }catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("认证失败========> 密码错误...");
        }

    }
}
