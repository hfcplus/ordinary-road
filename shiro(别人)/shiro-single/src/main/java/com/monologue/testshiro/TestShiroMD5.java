package com.monologue.testshiro;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Created by Monologue_zsj on 2021/3/7 12:15
 * Author：小脸儿红扑扑
 * Description：MD5加密
 */
public class TestShiroMD5 {
    public static void main(String[] args) {

        //创建一个md5算法
//        Md5Hash md5Hash = new Md5Hash();
//        md5Hash.setBytes("123456".getBytes());
//        String hex = md5Hash.toHex();
//        System.out.println(hex);

        //使用MD5
        Md5Hash md5Hash = new Md5Hash("123456");
        System.out.println(md5Hash.toHex());

        //使用MD5 + salt 处理
        Md5Hash md5Salt = new Md5Hash("123456", "X0*7ps");
        System.out.println(md5Salt);

        //使用MD5 + salt + hash散列(散列次数)
        Md5Hash hash = new Md5Hash("123456", "X0*7ps", 1024);
        System.out.println(hash);
    }
}
