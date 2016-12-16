/**
*2016年12月11日, jim.huang, create
*/
package com.database;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
public class DataSources extends AbstractVerticle {

	private static Map<String, DataSource> source = new HashMap<String, DataSource>();
	private final Logger logger = LoggerFactory.getLogger(DataSources.class);
	private static final String config = "config.json";

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		// TODO Auto-generated method stub
		vertx.fileSystem().readFile(config, rs -> {
			if (rs.succeeded()) {
				JsonArray array = rs.result().toJsonArray();
				array.forEach(node -> {
					JsonObject object = (JsonObject) node;
					String db = object.getString("type");
					Properties properties = new Properties();
					JsonObject pool = object.getJsonObject("pool");
					properties.putAll(pool.getMap());
					DataSource dataSource = new HikariDataSource(new HikariConfig(properties));
					source.put(db, dataSource);
				});
				startFuture.complete();
			}
			if (rs.failed()) {
				logger.error("fail to load dataSource", rs.cause());
				startFuture.fail(rs.cause());
			}
		});
	}

	public static DataSource getDataSource(String type) {
		return source.get(type);
	}

	public static DataSource getDefault() {
		return getDataSource("pgsql");
	}
}
