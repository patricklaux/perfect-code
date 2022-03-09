package com.igeeksky.perfect.nlp.string;

/**
 * 字符串键的位操作辅助类
 *
 * @author Patrick.Lau
 * @since 1.0.0 2021-12-12
 */
public class KeyBits {

    private static final int BIT_SHIFT = 0b1000000000000000;
    private static final int CHAR_BIT_SIZE = Character.SIZE;
    private static final int CHAR_BIT_MASK = 15;
    private static final char END_OF_KEY = '\0';

    /**
     * 获取字符串指定下标位置的 bit
     *
     * @param key      键
     * @param bitIndex 下标位置
     * @return 0 或 1；
     */
    public static int bitAt(String key, int bitIndex) {
        int index = bitIndex / 16;
        if (index >= key.length()) {
            // END_OF_KEY 的 bit全部为0，直接返回 0
            return 0;
        }
        char c = key.charAt(index);
        int shift = bitIndex & CHAR_BIT_MASK;
        return (c & (BIT_SHIFT >> shift)) > 0 ? 1 : 0;
    }

    /**
     * 查找两个键的差异点的下标位置
     *
     * @param newKey 新增键
     * @param oldKey 原键
     * @return 返回值大于等于0，表示有差异，返回差异的位索引；小于0表示无差异。
     */
    public static int diffAt(String newKey, String oldKey) {
        int newLen = newKey.length();
        int oldLen = oldKey.length();
        int maxLen = Math.max(newLen, oldLen);
        for (int i = 0; i < maxLen; i++) {
            char newChar = END_OF_KEY;
            char oldChar = END_OF_KEY;
            if (i < newLen) {
                newChar = newKey.charAt(i);
            }
            if (i < oldLen) {
                oldChar = oldKey.charAt(i);
            }
            if (newChar != oldChar) {
                int x = newChar ^ oldChar;
                return CHAR_BIT_SIZE * i + Integer.numberOfLeadingZeros(x) - CHAR_BIT_SIZE;
            }
        }
        return -1;
    }
}
