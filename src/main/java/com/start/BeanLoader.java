package com.start;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by jim.huang on 2016/12/24.
 */
public class BeanLoader extends AbstractVerticle {
    private static ApplicationContext context = null;
    private Logger logger = LoggerFactory.getLogger(BeanLoader.class);
    @Override
    public void start() throws Exception {
        context = new AnnotationConfigApplicationContext(Configuration.class);
        String [] names = context.getBeanDefinitionNames();
        for (String name : names) {
            logger.info("Bean : " + name);
        }
        logger.info("bean loader is ready to work");
    }
    public static <T>T getBean(Class<T> t) {
        return context.getBean(t);
    }
    public static <T>T getBean(String name) {
        T t = (T) context.getBean(name);
        return t;
    }
}
