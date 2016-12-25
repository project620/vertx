/**
*2016年12月11日, jim.huang, create
*/
package com.vertx3.start;

import com.vertx3.handler.restaurant.Login;
import com.vertx3.handler.basic.Validation;
import com.vertx3.handler.customer.Order;
import com.vertx3.service.UserService;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
@org.springframework.context.annotation.Configuration
@ComponentScan(basePackageClasses = { Configuration.class, Login.class, Order.class, UserService.class, Validation.class})
public class Configuration {

}
