/**
*2016年12月11日, jim.huang, create
*/
package com.vertx3.handler.basic;

import com.vertx3.service.UserService;
import com.vertx3.constants.Api;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

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
        vertx.eventBus().send(UserService.GET_USER, new JsonObject().put(UserService.USER_NAME, userId), rs -> {
            if (rs.succeeded()) {
                logger.info("end run user validation");
                context.response().setStatusCode(200).end("123");
            }
            if (rs.failed()) {
                logger.error(rs.cause());
                context.response().setStatusCode(400).end();
            }
        });
    }

}
