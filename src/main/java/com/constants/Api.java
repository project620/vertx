/**
*2016年12月11日, jim.huang, create
*/
package com.constants;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
public interface Api {
	String user_validation = "/user/validation";
	String user_get = "/user/:id";
	String login = "/login";

	interface validator {
		String validationUserId = "name";
		String validationUserPassword = "password";
	}
}
