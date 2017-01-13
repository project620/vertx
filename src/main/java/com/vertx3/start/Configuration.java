/**
*2016/12/11, jim.huang, create
*/
package com.vertx3.start;

import org.springframework.context.annotation.ComponentScan;

import com.vertx3.cache.JedisManager;
import com.vertx3.database.VertxJedisPool;
import com.vertx3.handler.basic.Validation;
import com.vertx3.handler.customer.Order;
import com.vertx3.handler.restaurant.Login;
import com.vertx3.service.UserService;

/**
 * @Author jim.huang
 */
@org.springframework.context.annotation.Configuration
@ComponentScan(basePackageClasses = {Configuration.class, Login.class, Order.class, UserService.class, Validation.class,
        VertxJedisPool.class, JedisManager.class})
public class Configuration {

}
