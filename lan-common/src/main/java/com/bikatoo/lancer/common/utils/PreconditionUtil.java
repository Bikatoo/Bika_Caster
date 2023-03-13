package com.bikatoo.lancer.common.utils;

import com.bikatoo.lancer.common.exception.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

public class PreconditionUtil {

    public static void checkNonBlankTextAndThrow(String text, @NonNull GlobalException e) {
        if (StringUtils.isBlank(text)){
            throw e;
        }
    }

    public static void checkConditionAndThrow(boolean b, @NonNull GlobalException e) {
        if (!b) {
            throw e;
        }
    }

    public static void checkNonNullAndThrow(Object o, @NonNull GlobalException e) {
        if (o == null) {
            throw e;
        }
    }

    public static void checkNoneNullAndThrow(@NonNull GlobalException e, Object... os) {
        if (os == null || os.length <= 0) {
            throw e;
        }

        for (Object o : os) {
            if (o == null) {
                throw e;
            }
        }
    }

    public static void checkNoneNullAndThrow(Object[] objectAttr, @NonNull GlobalException e) {
        if (objectAttr == null || objectAttr.length <= 0) {
            throw e;
        }

        for (Object o : objectAttr) {
            if (o == null) {
                throw e;
            }
        }
    }

    public static void checkStringNonBlankAndThrow(String str, @NonNull GlobalException e) {
        if (str == null || "".equals(str)) {
            throw e;
        }
    }
}
