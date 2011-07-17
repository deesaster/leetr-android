package com.leetr.util;

import java.util.List;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-09
 * Time: 8:24 PM
 */
public class StringUtils {
    public static final String EMTPY_SEPARATOR = "";

    public static String join(List<String> list, String separator) {

        if (list == null) {
            return null;
        }

        if (separator == null) {
            separator = EMTPY_SEPARATOR;
        }

        StringBuffer sb = new StringBuffer();

        int counter = 0;

        for (String str : list) {
            if (counter > 0) {
                sb.append(separator);
            }

            sb.append(str);
            counter++;
        }

        return sb.toString();
    }
}
