/**
*2016年12月11日, jim.huang, create
*/
package com.web;

import com.constants.Api;
import com.handler.basic.AbstractHandler;
import com.handler.basic.Validation;
import com.start.BeanLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
public class Server extends AbstractVerticle {

	private Logger logger = LoggerFactory.getLogger(Server.class);
	private static final String API = "api.json";
	private static final int port = 8088;

	@Override
	public void start() throws Exception {
		vertx.createHttpServer().requestHandler(router()::accept).listen(port, rs -> {
			logger.info("start listening : " + port);
		});
	}

	private Router router() {
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.route().handler(this::logRouter);
		JsonArray array = vertx.fileSystem().readFileBlocking(API).toJsonArray();
		array.forEach(node -> {
			JsonObject object = (JsonObject) node;
			String id = object.getString("id");
			String type = object.getString("type");
			AbstractHandler handler = (AbstractHandler) BeanLoader.getBean(id);
			handler.setVertx(vertx);
			if ("post".equalsIgnoreCase(type)) {
				router.post(id).handler(handler);
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
			logger.info("start route listener : " + type + " " + id);
		});
		router.route().last().handler((AbstractHandler) BeanLoader.getBean(Api.login));
		return router;
	}

	private void logRouter(RoutingContext routingContext) {
		logger.info("{} {} \n {}", routingContext.request().rawMethod(), routingContext.request().path(),
				requestMessage(routingContext).encodePrettily());
		routingContext.next();
	}

	private JsonObject requestMessage(final RoutingContext routingContext) {
		final JsonObject message = new JsonObject();
		message.put("param", multiMapToJsonObject(routingContext.request().params()));
		message.put("body", routingContext.getBody().length() > 0 ? routingContext.getBody().toString() : new JsonObject());
		return message;
	}

	private JsonObject multiMapToJsonObject(final MultiMap multiMap) {
		final JsonObject jsonObject = new JsonObject();
		multiMap.forEach(entry -> jsonObject.put(entry.getKey(), entry.getValue()));
		return jsonObject;
	}
}
