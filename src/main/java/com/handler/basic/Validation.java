/**
*2016年12月11日, jim.huang, create
*/
package com.handler.basic;

import org.springframework.stereotype.Component;

import com.constants.Api;
import com.constants.Sql;
import com.database.SqlExecutor;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
@Component(Api.user_validation)
public class Validation extends AbstractHandler {

    private final Logger logger = LoggerFactory.getLogger(Validation.class);

    @Override
    public void handle(final RoutingContext context) {
        logger.info("begin to do validation");
        final JsonObject userInfo = context.getBodyAsJson();
        final String userId = userInfo.getString(Api.validator.validationUserId);
        final String sql = "select * from jim_user where userId = ?";
        final JsonArray params = new JsonArray().add(userId);
        final JsonObject object = new JsonObject();
        object.put(Sql.SQL, sql);
        object.put(Sql.PARAMS, params);
        vertx.eventBus().send(SqlExecutor.ADDRESS_NO_TX, object, rs -> {
            if (rs.succeeded()) {
                context.response().setStatusCode(200).end(rs.result().toString());
            }
            if (rs.failed()) {
                context.response().setStatusCode(400).end();
            }
        });
    }

}
