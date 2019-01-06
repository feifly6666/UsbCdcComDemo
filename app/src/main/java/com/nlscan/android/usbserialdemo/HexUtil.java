package com.nlscan.android.usbserialdemo;

import android.util.Log;

public class HexUtil {
    private static final String TAG="HexUtil";
    /**
     * An array of lowercase characters used to create the output of hexadecimal characters
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * An uppercase character array used to create the output of hexadecimal characters
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * Convert a byte array to a hexadecimal character array
     *
     * @param data a byte array
     * @return a hexadecimal character array
     */
    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    /**
     * Convert a byte array to a hexadecimal character array
     *
     * @param data  a byte array
     * @param toLowerCase <code>true</code> to lowercase characters ， <code>false</code> to uppercase characters
     * @return a hexadecimal character array
     */
    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * Convert a byte array to a hexadecimal character array
     *
     * @param data a byte array
     * @param toDigits DIGITS_LOWER or DIGITS_UPPER
     * @return a hexadecimal character array
     */
    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    /**
     * Convert a byte array to a hex string
     *
     * @param data a byte array
     * @return a hexadecimal String
     */
    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }

    /**
     * Convert a byte array to a hex string
     *
     * @param data  a byte array
     * @param toLowerCase <code>true</code> to lowercase characters ， <code>false</code> to uppercase characters
     * @return a hexadecimal String
     */
    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * Convert a byte array to a hex string
     *
     * @param data  a byte array
     * @param toDigits DIGITS_LOWER or DIGITS_UPPER
     * @return a hexadecimal String
     */
    protected static String encodeHexStr(byte[] data, char[] toDigits) {
        if (data == null) {
            Log.e(TAG,"this data is null.");
            return "";
        }
        return new String(encodeHex(data, toDigits));
    }

    /**
     * Convert a hex string to a byte array
     *
     * @param data
     * @return
     */
    public static byte[] decodeHex(String data) {
        if (data == null) {
            Log.e(TAG,"this data is null.");
            return new byte[0];
        }
        return decodeHex(data.toCharArray());
    }

    /**
     * Convert a hexadecimal character array to a byte array
     *
     * @param data a byte array
     * @return a byte array
     * @throws RuntimeException If the source hexadecimal character array is a strange length, a runtime exception will be thrown
     */
    public static byte[] decodeHex(char[] data) {

        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    /**
     * Convert hexadecimal characters to an integer
     *
     * @param ch    a hexadecimal character
     * @param index The position of the hexadecimal character in the character array
     * @return An integer
     * @throws RuntimeException throws a runtime exception when ch is not a valid hexadecimal character
     */
    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

    /**
     * Intercept byte array
     *
     * @param src a byte array
     * @param begin The starting position of the source array,from 0.
     * @param count Intercept length
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

}
