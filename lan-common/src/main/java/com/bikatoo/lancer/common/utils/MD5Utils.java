package com.bikatoo.lancer.common.utils;


import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class MD5Utils {
    /**
     * 带盐MD5
     */
    public static String md5(String text, String salt) {
        if (StringUtils.isBlank(text) && StringUtils.isBlank(salt)) {
            return text;
        }
        return DigestUtils.md5Hex((text + salt).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * MD5
     */
    public static String md5(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        return DigestUtils.md5Hex(text.getBytes(StandardCharsets.UTF_8));
    }
}
