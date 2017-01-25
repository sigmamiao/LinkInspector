package com.sigma.linkinspector.util;

import java.util.regex.Pattern;

/**
 * Created with IDEA
 * User: Omega
 * Date: 2017/1/25
 * Time: 16:35
 */
public class ToolsUtils {

    public static boolean isMobileNo(String mobileNo) {
        return Pattern.compile("^((13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$").matcher(mobileNo).matches();
    }

    public static boolean urlCheck(String link) {
        return !"".equals(link) && !link.contains("/xxx");
    }
}
