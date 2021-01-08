/*
 * Copyright (c) 2020 极客之道(daogeek.io).
 * All rights reserved.
 */

package io.geekshop.common.utils;

/**
 * Created on Nov, 2020 by @author bobo
 */
public class HexUtil {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}