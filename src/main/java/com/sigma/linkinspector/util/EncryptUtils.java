package com.sigma.linkinspector.util;

import java.security.MessageDigest;

/**
 * Created with IDEA
 * User: Omega
 * Date: 2017/1/25
 * Time: 16:37
 */
public class EncryptUtils {

    public static String makeMD5Encrypt(String content) {
        char hexDigitss[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = content.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigitss[byte0 >>> 4 & 0xf];
                str[k++] = hexDigitss[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
