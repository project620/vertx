package com.vertx3.util;

import com.vertx3.annotation.TableName;

import io.vertx.core.json.JsonObject;

/**
 * @author jim.huang
 */

@Deprecated
public class SqlUtil {

    public static JsonObject doInsert(final Object entity) {
        final TableName table = entity.getClass().getAnnotation(TableName.class);
        final String tableName = table.value();
        final String json = GsonUtil.toJson(entity);
        final JsonObject jsonEntity = new JsonObject(json);
        return null;
    }

}
