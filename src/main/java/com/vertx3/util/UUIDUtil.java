/**
*2016/12/11, jim.huang, create
*/
package com.vertx3.util;

import java.util.UUID;

/**
 * @Author jim.huang
 * @Date 2016/12/11
 */
public class UUIDUtil {
	public static String getUUID() {
		final UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
