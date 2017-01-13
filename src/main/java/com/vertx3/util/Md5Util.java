/**
*Jan 10, 2017, jim.huang, create
*/
package com.vertx3.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Nullable;

import org.apache.commons.codec.binary.Hex;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author jim.huang
 */
public class Md5Util {

    /**
     * 1.get salt,like key -> "salt"
     * 2.merge orgin password and salt, like ("p", "salt") -> "p,salt"
     * 3.get password,encode("p,salt"), DB save salt and encodePassword
     */
    private static final String MD5 = "md5";
    private static final String seperator = ";";
    private static Logger logger = LoggerFactory.getLogger(Md5Util.class);

    public static String encodePassword(final String password, final String salt) {
        final String originPassword = mergePasswordAndSalt(password, salt);
        final String result = encode(originPassword);
        return result;
    }

    public static String encodeSalt(final String key) {
        return encode(key);
    }

    public static Boolean isPasswordValid(final String originPassword, final String salt, final String encodePassword) {
       final String  mergedPassword = mergePasswordAndSalt(originPassword, salt);
       final String encode = encode(mergedPassword);
       return encode.equals(encodePassword);
    }

    @SuppressWarnings("static-access")
    private static String encode(final String key) {
        String result = null;
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(MD5);
            final byte[] salt = messageDigest.digest(key.getBytes());
            result = new Hex().encodeHexString(salt);
        } catch (final NoSuchAlgorithmException e) {
            logger.error("fail to get md5 messageDigests", e);
        }
        return result;
    }

    @Nullable
    private static String mergePasswordAndSalt( final String password,  final String salt) {
        final String result = password + seperator + salt;
        return result;
    }

}
