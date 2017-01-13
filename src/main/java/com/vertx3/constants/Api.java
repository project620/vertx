/**
*2016骞�12鏈�11鏃�, jim.huang, create
*/
package com.vertx3.constants;

/**
 * @Author jim.huang
 * @Date 2016骞�12鏈�11鏃�
 */
public interface Api {
    String user_validation = "validation";
    String user_get = "/user/:id";
    String login = "/login";
    String user_signUp = "signUp";

    interface validator {
        String validationUserId = "name";
        String validationUserPassword = "password";
    }
}
