/**
*2016年12月11日, jim.huang, create
*/
package com.vertx3.web;

import com.vertx3.constants.Api;
import com.vertx3.handler.basic.AbstractHandler;
import com.vertx3.start.BeanLoader;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
public class Server extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final String API = "api.json";
    private static final String STATIC = "static.json";
    private static final String REQUEST = "request";
    private static final String PATH = "path";
    private static final int port = 8088;
    // private static final int LowOrder = 9;
    // private static final int midOrder = 5;
    // private static final int highOrder = 4;

    @Override
    public void start(final Future<Void> future) throws Exception {
        vertx.createHttpServer().requestHandler(router()::accept).listen(port, rs -> {
            logger.info("start listening : {}", port);
            future.complete();
        });
    }

    private Router router() {
        final Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().handler(CookieHandler.create());
        router.route().handler(this::logRouter);
        createSessionHandler(router);
        createStaticRouterHandler(router);
        createDoHandler(router);
        createApiHandler(router);
        router.route().last().handler(BeanLoader.getBean(Api.login));
        return router;
    }

    /**
     * static resource handler, like image
     *
     * @param router
     */
    private void createStaticRouterHandler(final Router router) {
        vertx.fileSystem().readFile(STATIC, rs -> {
            if (rs.succeeded()) {
                final JsonArray node = rs.result().toJsonArray();
                node.forEach(each -> {
                    final JsonObject handler = (JsonObject) each;
                    final String request = handler.getString(REQUEST);
                    final String path = handler.getString(PATH);
                    router.route(request).handler(StaticHandler.create(path));
                    logger.debug("static handler {} create for {}.", path, request);
                });
            } else {
                logger.error("fail to load static handler.", rs.cause());
            }
        });
    }

    /**
     * create handler for api define in api.json
     *
     * @param router
     */
    private void createApiHandler(final Router router) {
        final JsonArray array = vertx.fileSystem().readFileBlocking(API).toJsonArray();
        array.forEach(node -> {
            final JsonObject object = (JsonObject) node;
            final String id = object.getString("id");
            final String type = object.getString("type");
            final AbstractHandler handler = (AbstractHandler) BeanLoader.getBean(id);
            if (handler == null) {
                return;
            }
            handler.setVertx(vertx);
            if ("post".equalsIgnoreCase(type)) {
                router.post(id).handler(handler);
            }
            if ("get".equalsIgnoreCase(type)) {
                router.get(id).handler(handler);
            }
            if ("put".equalsIgnoreCase(type)) {
                router.put(id).handler(handler);
            }
            logger.info("start route listener : " + type + " " + id);
        });
    }

    /**
     * create handler for action, use spring, if action does not exist, then pass
     *
     * @param router
     */
    private void createDoHandler(final Router router) {
        router.route().pathRegex(".*.do").handler(rs -> {
            final HttpServerRequest request = rs.request();
            final String path = request.path();
            final String action = path.substring(1, path.length() - 3).intern();
            final AbstractHandler handler = BeanLoader.getBean(action);
            if (handler == null) {
                rs.next();
                logger.info("no found such action : {}.", action);
            }
            handler.setVertx(vertx);
            handler.handle(rs);
        });
    }

    private void logRouter(final RoutingContext routingContext) {
        final SocketAddress address = routingContext.request().remoteAddress();
        logger.debug("{} {} \n {} \n {}", routingContext.request().rawMethod(), routingContext.request().path(),
                requestMessage(routingContext).encodePrettily(), address);
        routingContext.next();
    }

    private JsonObject requestMessage(final RoutingContext routingContext) {
        final JsonObject message = new JsonObject();
        message.put("param", multiMapToJsonObject(routingContext.request().params()));
        message.put("body",
                routingContext.getBody().length() > 0 ? routingContext.getBody().toString() : new JsonObject());
        return message;
    }

    private JsonObject multiMapToJsonObject(final MultiMap multiMap) {
        final JsonObject jsonObject = new JsonObject();
        multiMap.forEach(entry -> jsonObject.put(entry.getKey(), entry.getValue()));
        return jsonObject;
    }

    private void createSessionHandler(final Router router) {
        final SessionStore store = LocalSessionStore.create(vertx);
        final SessionHandler sessionHandler = SessionHandler.create(store);
        sessionHandler.setSessionTimeout(30 * 60 * 1000);
        router.route().handler(sessionHandler);
        logger.info("Session Handler create.");
    }
}
