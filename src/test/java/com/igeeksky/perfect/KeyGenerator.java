package com.igeeksky.perfect;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Patrick.Lau
 * @since 0.0.4 2022-02-21
 */
public class KeyGenerator {

    public static Set<String> createKeys(int size) {
        Random random = new Random();
        Set<String> keys = new HashSet<>(size);
        while (keys.size() < size) {
            int length = random.nextInt(10);
            if (length > 5) {
                char[] chars = new char[length];
                for (int j = 0; j < length; ) {
                    int index = random.nextInt(91);
                    if (index >= 65) {
                        char c = (char) index;
                        chars[j] = c;
                        ++j;
                    }
                }
                keys.add(new String(chars));
            }
        }
        return keys;
    }

}
