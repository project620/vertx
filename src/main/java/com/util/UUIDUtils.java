/**
*2016年12月11日, jim.huang, create
*/
package com.util;

import java.util.UUID;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
public class UUIDUtils {
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
