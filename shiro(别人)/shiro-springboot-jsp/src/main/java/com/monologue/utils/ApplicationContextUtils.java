package com.monologue.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by Monologue_zsj on 2021/3/7 16:19
 * Author：小脸儿红扑扑
 * Description：根据beanName获取工厂中指定的bean对象
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }
}
