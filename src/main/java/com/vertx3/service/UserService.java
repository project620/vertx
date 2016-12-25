package com.vertx3.service;

import com.vertx3.database.mapper.UserMapper;
import com.vertx3.start.BeanLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.Map;

/**
 * Created by jim.huang on 2016/12/23.
 */
public class UserService extends AbstractVerticle {
    public static final String GET_USER = "get user/:id";
    public static final String USER_NAME = "username";
    private static UserMapper userMapper;
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Override
    public void start() throws Exception {
        userMapper = BeanLoader.getBean(UserMapper.class);
        logger.info("User Service ready to work");
        vertx.eventBus().consumer(GET_USER, this::handlerGetUser);
    }
    private void handlerGetUser(Message<JsonObject> message) {
        JsonObject body = message.body();
        String username = body.getString(USER_NAME);
        Map result = userMapper.getUserByName(username);
        message.reply(new JsonObject(result));
    }
}
