package com.boris1993.timesyncntp.utils;

public class HexUtils {
    private HexUtils() {
    }

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

    public static String padLeft(final String str, final int expectedLength) {
        String result = str;

        while (result.length() < expectedLength) {
            result = "0".concat(result);
        }

        return result;
    }

    public static byte[] booleanArrayToByteArray(boolean[] booleanArray) {
        byte[] bytes = new byte[booleanArray.length / 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int bit = 0; bit < 8; bit++) {
                if (booleanArray[i * 8 + bit]) {
                    bytes[i] |= (128 >> bit);
                }
            }
        }

        return bytes;
    }
}
