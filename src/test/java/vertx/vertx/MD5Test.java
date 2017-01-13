/**
*Jan 12, 2017, jim.huang, create
*/
package vertx.vertx;

import java.util.Date;

import com.vertx3.util.Md5Util;

/**
 * @author jim.huang
 */
public class MD5Test {

    /**
     * @param args
     */
    public static void main(final String[] args) {
        // TODO Auto-generated method stub
        final String password = "p";
        final String teString = "12";
        final Date date = new Date();
        final String salt = Md5Util.encodeSalt(date.toString());
        final String encodePassword = Md5Util.encodePassword(password, salt);
        System.out.println(Md5Util.isPasswordValid(password, salt, encodePassword));
        System.out.println(Md5Util.isPasswordValid(teString, salt, encodePassword));
    }

}
