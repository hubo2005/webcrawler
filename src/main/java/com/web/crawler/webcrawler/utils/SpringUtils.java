package com.web.crawler.webcrawler.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

    protected static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }


    public static <T> T getBean(String name) throws BeansException {
        return (T) CONTEXT.getBean(name);
    }

    public static <T> T getBean(Class<T> classType) {
        return CONTEXT.getBean(classType);
    }
}
