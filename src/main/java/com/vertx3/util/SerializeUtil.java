/**
*Jan 12, 2017, jim.huang, create
*/
package com.vertx3.util;

import io.vertx.core.json.Json;

/**
 * @author jim.huang
 */
public class SerializeUtil {

    public static String serialize(final Object object) {
        final String encode = Json.encode(object);
        return encode;
    }

    public static <T> T deSerialize(final String key) {
        return null;

    }
}
