/**
*2016年12月16日, jim.huang, create
*/
package com.handler;

import org.springframework.stereotype.Component;

import com.constants.Api;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * @Author jim.huang
 * @Date 2016年12月16日
 */

@Component(Api.user_get)
public class GetUser extends AbstractHandler {

	@Override
	public void handle(RoutingContext event) {
		JsonObject object = new JsonObject();
		object.put("you", "sb");
		System.out.println("QQ唔菌坑吃大便！！！");
		event.response().setStatusCode(200).end(object.toString());
	}

}
