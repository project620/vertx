/**
*2016年12月11日, jim.huang, create
*/
package com.handler.basic;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.constants.Api;
import com.database.ConnectionFactory;
import com.database.DataSources;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.RoutingContext;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
@Component(Api.user_validation)
public class Validation extends AbstractHandler {

	private Logger logger = LoggerFactory.getLogger(Validation.class);

	@Override
	public void handle(RoutingContext context) {
		logger.info("begin to do validation");
		JsonObject userInfo = context.getBodyAsJson();
		String userId = userInfo.getString(Api.validator.validationUserId);
		String sql = "select * from jim_user where userId = ?";
		JsonArray params = new JsonArray().add(userId);
		DataSource source = DataSources.getDefault();
		JDBCClient client = JDBCClient.create(vertx, source);
		ConnectionFactory.queryWithParams(client, sql, params, rs -> {
			if (rs.succeeded()) {
				JsonObject object = new JsonObject();
				logger.info("success on user validation");
				if (rs.result() != null) {
					object.put("result", Boolean.TRUE);
				} else {
					object.put("result", Boolean.FALSE);
				}
				context.response().setStatusCode(200).end(object.toString());
			} else {
				logger.error("fail to do user validation", rs.cause());
				context.response().setStatusCode(400).end(rs.cause().getMessage());
			}
		});
	}

}
