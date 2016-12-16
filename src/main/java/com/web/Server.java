/**
*2016年12月11日, jim.huang, create
*/
package com.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.handler.AbstractHandler;
import com.handler.Configuration;
import com.handler.Validation;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
public class Server extends AbstractVerticle {

	private ApplicationContext context;
	private Logger logger = LoggerFactory.getLogger(Server.class);
	private static final String API = "api.json";
	private static final int port = 6666;

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		context = new AnnotationConfigApplicationContext(Configuration.class);
		vertx.createHttpServer().requestHandler(router()::accept).listen(port);
	}

	private Router router() {
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		JsonArray array = vertx.fileSystem().readFileBlocking(API).toJsonArray();
		array.forEach(node -> {
			JsonObject object = (JsonObject) node;
			String id = object.getString("id");
			String type = object.getString("type");
			AbstractHandler handler = (AbstractHandler) context.getBean(id);
			handler.setVertx(vertx);
			if ("post".equalsIgnoreCase(type)) {
				router.post(id).consumes("application/json").handler(handler);
			}
			if ("get".equalsIgnoreCase(type)) {
				router.get(id).handler(handler);
			}
			if ("put".equalsIgnoreCase(type)) {
				AbstractHandler h = new Validation();
				h.setVertx(vertx);
				router.put(id).consumes("application/json").produces("application/json").handler(context -> {
					String body = context.getBodyAsString();
					logger.info(body);
				});
			}
			logger.info("start route listener : " + id);
		});
		logger.info("start listening : " + port);
		return router;
	}
}
