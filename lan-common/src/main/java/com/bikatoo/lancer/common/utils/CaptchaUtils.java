package com.bikatoo.lancer.common.utils;

import com.bikatoo.lancer.common.exception.GlobalException;
import java.util.Random;

public class CaptchaUtils {

  public static final char[] charArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

  public static String generate(int len) {

    if (len <= 0) {
      throw new GlobalException("incorrect length");
    }

    StringBuilder sb = new StringBuilder();
    Random r = new Random();
    for (int i = 0 ; i < len ; i++) {
      sb.append(charArr[r.nextInt(charArr.length)]);
    }
    return sb.toString();
  }
}
