/**
*2016年12月16日, jim.huang, create
*/
package com.vertx3.handler.basic;

import org.springframework.stereotype.Component;

import com.vertx3.constants.Api;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

/**
 * @Author jim.huang
 * @Date 2016年12月16日
 */

@Component(Api.user_get)
public class GetUser extends AbstractHandler {
	private Logger logger = LoggerFactory.getLogger(GetUser.class);

	@Override
	public void handle(RoutingContext event) {
		logger.info("begin to do get/user");
		JsonObject object = new JsonObject();
		object.put("you", "sb");
		event.response().setStatusCode(200).end(object.toString());
	}

}
