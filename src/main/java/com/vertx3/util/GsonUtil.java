package com.vertx3.util;

import com.google.gson.Gson;

/**
 * @author jim.huang
 *
 */
public class GsonUtil {
    private static Gson gson = new Gson();

    public static String toJson(final Object entity) {
        return gson.toJson(entity);
    }

    public static <T> T toEntity(final String json, final Class<T> entity){
        return gson.fromJson(json,entity);
    }

}
