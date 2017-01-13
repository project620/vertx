/**
*2016/12/17, jim.huang, create
*/
package com.vertx3.database;

import javax.sql.DataSource;

import com.vertx3.constants.Sql;
import com.vertx3.start.DataSources;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

/**
 * @Author jim.huang
 * @Date 2016/12/17
 */
public class SqlExecutor extends AbstractVerticle {

    public static String ADDRESS_NO_TX = "com.database.SqlexEcutor";
    private static JDBCClient client = null;

    public static void queryWithParams(final JDBCClient client, final String sql, final JsonArray params,
            final Handler<AsyncResult<ResultSet>> handler) {
        final Future<SQLConnection> future = Future.future();
        client.getConnection(future.completer());
        future.compose((connection) -> {
            final Future<ResultSet> resultSetFuture = Future.future();
            connection.queryWithParams(sql, params, resultSetFuture.completer());
            return resultSetFuture;
        }).setHandler(handler);
    }

    @Override
    public void start() throws Exception {
        final DataSource source = DataSources.getDefault();
        client = JDBCClient.create(vertx, source);
        vertx.eventBus().consumer(ADDRESS_NO_TX, this::handleNoTx);
    }

    private void handleNoTx(final Message<JsonObject> object) {
        final JsonObject json = object.body();
        final String sql = json.getString(Sql.SQL);
        final JsonArray params = json.getJsonArray(Sql.PARAMS);
        final Future<SQLConnection> future = Future.future();
        client.getConnection(future.completer());
        future.compose((connection) -> {
            final Future<ResultSet> resultSetFuture = Future.future();
            connection.queryWithParams(sql, params, resultSetFuture.completer());
            return resultSetFuture;
        }).setHandler(handler -> {
            if (handler.succeeded()) {
                final JsonArray result = handler.result().getOutput();
                object.reply(result);
            } else {
                object.fail(1, "fail to execute no tx sql," + sql);
            }
        });
    }
}
