/**
 *2016/12/11, jim.huang, create
 */
package com.vertx3.start;

import com.vertx3.service.UserService;
import com.vertx3.web.Server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @Author jim.huang
 * @Date 2016/12/11
 */
public class Main extends AbstractVerticle {
    public static void main(final String[] args) {
        Runner.run(Main.class);
    }

    @Override
    public void start(final Future<Void> future) throws Exception {
        vertx.deployVerticle(new Slf4jFactory());
        final Future<String> future1 = Future.future();
        vertx.deployVerticle(new DataSources(), future1.completer());
        future1.compose(f1 -> {
            final Future<Void> future2 = Future.future();
            vertx.deployVerticle(new BeanLoader(), result2 -> {
                if (result2.succeeded()) {
                    future2.complete();
                } else {
                    future2.fail(result2.cause());
                }
            });
            return future2;
        }).compose(f2 -> {
            final Future<Void> future3 = Future.future();
            vertx.deployVerticle(new UserService(), result3 -> {
                if (result3.succeeded()) {
                    future3.complete();
                } else {
                    future3.fail(result3.cause());
                }
            });
            return future3;
        }).compose(f3 -> {
            final Future<Void> future4 = Future.future();
            vertx.deployVerticle(new Server(), result4 -> {
                if (result4.succeeded()) {
                    future4.complete();
                } else {
                    future4.fail(result4.cause());
                }
            });
            return future4;
        }).setHandler(rs -> {
            final Logger logger = LoggerFactory.getLogger(Main.class);
            if (rs.succeeded()) {
                future.complete();
                logger.info("all service are ready");
            }
            if (rs.failed()) {
                logger.error("fail to start server", rs.cause());
            }
        });
    }
}
