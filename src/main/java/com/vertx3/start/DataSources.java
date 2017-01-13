/** *2016/12/11, jim.huang, create */package com.vertx3.start;import java.util.HashMap;import java.util.Map;import java.util.Properties;import javax.sql.DataSource;import org.springframework.stereotype.Component;import com.vertx3.database.SqlExecutor;import com.zaxxer.hikari.HikariConfig;import com.zaxxer.hikari.HikariDataSource;import io.vertx.core.AbstractVerticle;import io.vertx.core.Future;import io.vertx.core.buffer.Buffer;import io.vertx.core.json.JsonArray;import io.vertx.core.json.JsonObject;import io.vertx.core.logging.Logger;import io.vertx.core.logging.LoggerFactory;/** * @Author jim.huang * @Date 2016/12/11 */@Componentpublic class DataSources extends AbstractVerticle {    /**     * load properties from file, initialize dataSource, use hikariCp in default     */    private static Map<String, DataSource> source = new HashMap<String, DataSource>();    private final Logger logger = LoggerFactory.getLogger(DataSources.class);    private static final String config = "db.json";    @Override    public void start(final Future<Void> startFuture) throws Exception {        final Future<Buffer> fileFuture = Future.future();        vertx.fileSystem().readFile(config, fileFuture.completer());        fileFuture.compose((result) -> {            dataSourceHandler(result);            final Future<String> future = Future.future();            vertx.deployVerticle(new SqlExecutor(), future.completer());            return future;        }).setHandler(rs -> {            if (rs.succeeded()) {                logger.info("sql executor ready to work");                startFuture.complete();            } else {                logger.error("sql executor fail to work", rs.cause());                startFuture.fail(rs.cause());            }        });    }    public static DataSource getDataSource(final String type) {        return source.get(type);    }    private void dataSourceHandler(final Buffer result) {        final JsonArray array = result.toJsonArray();        array.forEach(node -> {            final JsonObject object = (JsonObject) node;            final String db = object.getString("type");            final Properties properties = new Properties();            final JsonObject pool = object.getJsonObject("pool");            properties.putAll(pool.getMap());            final DataSource dataSource = new HikariDataSource(new HikariConfig(properties));            source.put(db, dataSource);        });        logger.info("data source ready");    }    public static DataSource getDefault() {        return getDataSource("default");    }}