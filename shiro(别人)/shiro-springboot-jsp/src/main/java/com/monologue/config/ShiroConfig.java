package com.monologue.config;

import com.monologue.shiro.cache.RedisCacheManager;
import com.monologue.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monologue_zsj on 2021/3/7 14:38
 * Author：小脸儿红扑扑
 * Description：整合Shiro框架相关的配置类
 */
@Configuration
public class ShiroConfig {

    //1、创建ShiroFilter (负责拦截所有请求)
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给Filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //配置系统受限资源和系统公共资源
        Map<String, String> map = new HashMap<>();
        map.put("/user/login", "anon");  //anon 指定url可以匿名访问
        map.put("/user/register", "anon");  //anon 指定url可以匿名访问
        map.put("/register.jsp", "anon");  //anon 指定url可以匿名访问
        map.put("/**", "authc");     //authc 请求这个资源需要认证和授权
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        //默认认证界面路径
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");

        return shiroFilterFactoryBean;
    }

    //2、创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getRealm") Realm realm) {

        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置Realm
        defaultWebSecurityManager.setRealm(realm);

        return defaultWebSecurityManager;
    }

    //3、创建自定义Realm
    @Bean
    public Realm getRealm() {
        CustomerRealm customerRealm = new CustomerRealm();
        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(credentialsMatcher);

        //开启缓存管理
        customerRealm.setCacheManager(new EhCacheManager());
//        customerRealm.setCacheManager(new RedisCacheManager());
        customerRealm.setCachingEnabled(true);  //开启全局缓存
        customerRealm.setAuthenticationCachingEnabled(true);     //开启认证缓存
        customerRealm.setAuthenticationCacheName("authenticationCache");
        customerRealm.setAuthorizationCachingEnabled(true);     //开启授权缓存
        customerRealm.setAuthorizationCacheName("authorizationCache");


        return customerRealm;
    }
}
