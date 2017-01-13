/**
 *2016骞�12鏈�20鏃�, jim.huang, create
 */
package com.vertx3.handler.restaurant;

import org.springframework.stereotype.Component;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.vertx3.constants.Api;
import com.vertx3.handler.basic.AbstractHandler;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.impl.CookieImpl;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;

/**
 * @Author jim.huang
 * @Date 2016骞�12鏈�20鏃�
 */
@Component(Api.login)
public class Login extends AbstractHandler {
    Logger logger = LoggerFactory.getLogger(Login.class);
    ThymeleafTemplateEngine engine;
    ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();

    @Override
    public void handle(final RoutingContext routingContext) {
        resolver.setSuffix(".html");
        final Session session = routingContext.session();
        final String result = session.get("session");
        session.put("session", "hello");
        final Cookie test = routingContext.getCookie("test");
        if (test == null) {
            logger.info("cookie null");
        }
        final Cookie cookie = new CookieImpl("test", "result");
        routingContext.addCookie(cookie);
        resolver.setTemplateMode("HTML5");
        engine = ThymeleafTemplateEngine.create();
        engine.getThymeleafTemplateEngine().setTemplateResolver(resolver);
        engine.render(routingContext, "/html/login", rs -> {
            if (rs.succeeded()) {
                logger.info("end load page.{}, {}, {}", session, result, session.get("session"));
                routingContext.response().setStatusCode(200).end(rs.result());
            } else {
                logger.error("fail to load page", rs.cause());
            }
        });
    }

}
