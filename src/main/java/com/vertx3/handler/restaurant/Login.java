/**
 *2016年12月20日, jim.huang, create
 */
package com.vertx3.handler.restaurant;

import com.vertx3.constants.Api;
import com.vertx3.handler.basic.AbstractHandler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;
import org.springframework.stereotype.Component;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * @Author jim.huang
 * @Date 2016年12月20日
 */
@Component(Api.login)
public class Login extends AbstractHandler {
	Logger logger = LoggerFactory.getLogger(Login.class);
	ThymeleafTemplateEngine engine;
	ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
	@Override
	public void handle(RoutingContext routingContext) {
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		engine = ThymeleafTemplateEngine.create();
		engine.getThymeleafTemplateEngine().setTemplateResolver(resolver);
		engine.render(routingContext,"/html/login", rs -> {
			if (rs.succeeded()) {
				logger.info("end load page");
				routingContext.response().setStatusCode(200).end(rs.result());
			}
			else {
				logger.error("fail to load page", rs.cause());
			}
		});
	}

}
