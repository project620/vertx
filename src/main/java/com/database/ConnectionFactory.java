/**
*2016年12月17日, jim.huang, create
*/
package com.database;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

/**
 * @Author jim.huang
 * @Date 2016年12月17日
 */
public class ConnectionFactory {

	public static void queryWithParams(JDBCClient client, final String sql, final JsonArray params,
			Handler<AsyncResult<ResultSet>> handler) {
		Future<SQLConnection> future = Future.future();
		client.getConnection(future.completer());
		future.compose((connection) -> {
			Future<ResultSet> resultSetFuture = Future.future();
			connection.queryWithParams(sql, params, resultSetFuture.completer());
			return resultSetFuture;
		}).setHandler(handler);
	}
}
