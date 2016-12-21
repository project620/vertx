/**
 *2016年12月20日, jim.huang, create
 */
package com.handler.restaurant;

import org.springframework.stereotype.Component;

import com.constants.Api;
import com.handler.basic.AbstractHandler;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

/**
 * @Author jim.huang
 * @Date 2016年12月20日
 */
@Component(Api.login)
public class Login extends AbstractHandler {
	Logger logger = LoggerFactory.getLogger(Login.class);

	@Override
	public void handle(RoutingContext routingContext) {
		vertx.fileSystem().readFile("html/login.html", rs -> {
			if (rs.succeeded()) {
				logger.info("login page open");
				routingContext.response().setStatusCode(200).end(rs.result());
			} else {
				logger.error("fail to load login html.", rs.cause());
				routingContext.response().setStatusCode(404);
			}
		});
	}

}
