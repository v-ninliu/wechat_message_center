package com.oyo.wechat.message_center.utils;

import java.util.HashMap;
import java.util.Map;

public abstract class StringUtils {

  public static Map<String, String> createKeyValueMapFromString(String input, String keyValueSeparator,
      String itemSeparator) {

    Map<String, String> map = new HashMap<String, String>();

    for (String keyValue : input.split(itemSeparator)) {
      String[] pairs = keyValue.split(keyValueSeparator, 2);
      map.put(pairs[0], pairs.length == 1 ? "" : pairs[1]);
    }

    return map;
  }
}
