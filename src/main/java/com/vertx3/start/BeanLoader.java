package com.vertx3.start;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Created by jim.huang on 2016/12/24.
 */
public class BeanLoader extends AbstractVerticle {
    private static ApplicationContext context = null;
    private final Logger logger = LoggerFactory.getLogger(BeanLoader.class);

    @Override
    public void start() throws Exception {
        context = new AnnotationConfigApplicationContext(Configuration.class);
        final String[] names = context.getBeanDefinitionNames();
        for (final String name : names) {
            logger.info("Bean: {} is ready.", name);
        }
        logger.info("bean loader is ready to work");
    }

    public static <T> T getBean(final Class<T> t) {
        return context.getBean(t);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(final String name) {
        final T t = (T) context.getBean(name);
        return t;
    }
}
