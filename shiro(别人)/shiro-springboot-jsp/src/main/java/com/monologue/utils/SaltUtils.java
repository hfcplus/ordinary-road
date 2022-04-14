package com.monologue.utils;

import java.util.Random;

/**
 * Created by Monologue_zsj on 2021/3/7 16:02
 * Author：小脸儿红扑扑
 * Description：
 */
public class SaltUtils {

    public static String getSalt(int n) {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789(),./!@#$%^&*_+*".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            stringBuilder.append(aChar);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(getSalt(4));
    }
}
