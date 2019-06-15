package com.mr.archer.utils;

public class KeyUtils {
  /**
   * 应用唯一编号
   */
  private static String AppID;
  /**
   * 每毫秒序列号
   */
  private static long SequenceNo = 0L;
  /**
   * 上一次毫秒
   */
  private static long LastTimeStamp = -1L;

  public KeyUtils() {
    AppID = System.getProperty("clusterid");
  }

  public static String getKey() {
    return getKey(null);
  }

  public static String getKey(String sPrefix) {
    return getKey(sPrefix, null);
  }

  public synchronized static String getKey(String sPrefix, String sSuffix) {
    if (sPrefix == null) {
      sPrefix = "";
    }
    if (sSuffix == null) {
      sSuffix = "";
    }
    if (AppID == null) {
      AppID = "";
    }
    long timestamp = timeGen();
    if (LastTimeStamp == timestamp) {
      SequenceNo = (SequenceNo + 1);
    } else {
      SequenceNo = 0;
    }
    if (timestamp < LastTimeStamp) {
      try {
        throw new Exception(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", LastTimeStamp
                - timestamp));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    LastTimeStamp = timestamp;
    String sSequenceNo = getFormatSerialNo(SequenceNo, 2);
    String nextId = sPrefix + "T" + timestamp + "A" + AppID + "N" + sSequenceNo + sSuffix;

    return nextId;
  }

  private static long timeGen() {
    return System.currentTimeMillis();
  }

  private static String getFormatSerialNo(long iNow, int iCount) {
    String s = String.valueOf(iNow);
    int length = s.length();
    if (length >= iCount) {
      return s;
    } else {
      int i = iCount - length;
      for (;i > 0;i--) {
        s = "0" + s;
      }
    }
    return s;
  }

  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      String sKey = KeyUtils.getKey();
      System.out.println(sKey);
    }
  }

}
