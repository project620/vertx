/**
*2016年12月11日, jim.huang, create
*/
package com.handler.basic;

import org.springframework.context.annotation.ComponentScan;

import com.handler.customer.Order;
import com.handler.restaurant.Login;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
@org.springframework.context.annotation.Configuration
@ComponentScan(basePackageClasses = { Configuration.class, Login.class, Order.class })
public class Configuration {

}
