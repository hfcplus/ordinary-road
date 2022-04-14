package com.monologue.shiro.salt;


import org.apache.shiro.util.SimpleByteSource;

import java.io.Serializable;

/**
 * Created by Monologue_zsj on 2021/3/7 20:50
 * Author：小脸儿红扑扑
 * Description：自定义Salt 实现序列化接口
 */
public class MyByteSource extends SimpleByteSource implements Serializable {

    public MyByteSource(String string) {
        super(string);
    }
}
