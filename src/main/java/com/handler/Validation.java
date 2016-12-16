/**
*2016年12月11日, jim.huang, create
*/
package com.handler;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.constants.Api;
import com.database.DataSources;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
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
		HttpServerRequest request = context.request();
		JsonObject userInfo = context.getBodyAsJson();
		String userId = userInfo.getString(Api.validator.validationUserId);
		String sql = "select * from verxt_user where name = '" + userId + "'";
		DataSource source = DataSources.getDefault();
		JDBCClient client = JDBCClient.create(vertx, source);
		client.getConnection(rs -> {
			if (rs.succeeded()) {
				SQLConnection connection = rs.result();
				connection.query(sql, result -> {
					if (result.succeeded()) {
						JsonObject object = new JsonObject();
						int lineNumer = result.result().getNumRows();
						object.put("result", lineNumer);
						request.response().setStatusCode(200).end(object.toString());
					}
					if (result.failed()) {
						logger.error("fail to execute sql.", result.cause());
						request.response().setStatusCode(400).end();
					}
				});
			}
			if (rs.failed()) {
				logger.info("fail to validate user");
				request.response().setStatusCode(400).end();
			}
		});
	}

}
