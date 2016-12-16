/**
*2016年12月11日, jim.huang, create
*/
package com.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
public abstract class AbstractHandler implements Handler<RoutingContext> {
	protected Vertx vertx;

	public void setVertx(Vertx vertx) {
		this.vertx = vertx;
	}
}
