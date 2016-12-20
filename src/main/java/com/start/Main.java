/**
 *2016年12月11日, jim.huang, create
 */
package com.start;

import com.database.DataSources;
import com.log.Slf4jFactory;
import com.web.Server;
import io.vertx.core.AbstractVerticle;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
public class Main extends AbstractVerticle {
    public static void main(String[] args) {
        Runner.run(Main.class);
    }

    @Override
    public void start() throws Exception {
        super.start();
        vertx.deployVerticle(new Slf4jFactory());
        vertx.deployVerticle(new DataSources());
        vertx.deployVerticle(new Server());
    }
}
