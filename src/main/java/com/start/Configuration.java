/**
*2016年12月11日, jim.huang, create
*/
package com.start;

import com.handler.basic.Validation;
import com.handler.customer.Order;
import com.handler.restaurant.Login;
import com.service.UserService;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
@org.springframework.context.annotation.Configuration
@ComponentScan(basePackageClasses = { Configuration.class, Login.class, Order.class, UserService.class, Validation.class})
public class Configuration {

}
