package com.vertx3.service;

import java.util.Map;

import com.vertx3.database.mapper.UserMapper;
import com.vertx3.start.BeanLoader;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Created by jim.huang on 2016/12/23.
 */
public class UserService extends AbstractVerticle {
    public static final String GET_USER = "get user/:id";
    public static final String USER_NAME = "username";
    private static UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public void start() throws Exception {
        userMapper = BeanLoader.getBean(UserMapper.class);
        logger.info("User Service ready to work");
        vertx.eventBus().consumer(GET_USER, this::handlerGetUser);
    }

    private void handlerGetUser(final Message<JsonObject> message) {
        final JsonObject body = message.body();
        final String username = body.getString(USER_NAME);
        final Map<String, Object> result = userMapper.getUserByName(username);
        message.reply(new JsonObject(result));
    }
}
