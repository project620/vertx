/**
*2016年12月11日, jim.huang, create
*/
package com.vertx3.start;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
public class Slf4jFactory extends AbstractVerticle {

	private static final String Slf4j = "io.vertx.core.logging.SLF4JLogDelegateFactory";
	private static final String logProperties = "vertx.logger-delegate-factory-class-name";

	@Override
	public void start() throws Exception {
		System.setProperty(logProperties, Slf4j);
		LoggerFactory.initialise();
		Logger logger = LoggerFactory.getLogger(Slf4jFactory.class);
		logger.info("slf4j initialize end");
	}

}
