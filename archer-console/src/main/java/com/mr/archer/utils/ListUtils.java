package com.mr.archer.utils;

import java.util.List;

public class ListUtils {

  /**
   * 删除列表前index个元素
   * @param list
   * @param index
   * @return
   */
  public static List removeBefore(List list, int index) {
    for (int i = 0; i < index; i++) {
      list.remove(0);
    }

    return list;
  }

  /**
   * 删除列表后index个元素
   * @param list
   * @param index
   * @return
   */
  public static List removeAfter(List list, int index) {
    for (int i = 0; i < index; i++) {
      list.remove(list.size() - 1);
    }

    return list;
  }
}
