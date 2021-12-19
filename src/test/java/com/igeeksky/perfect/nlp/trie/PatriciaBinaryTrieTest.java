package com.igeeksky.perfect.nlp.trie;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

/**
 * @author Patrick.Lau
 * @since 1.0.0 2021-12-09
 */
public class PatriciaBinaryTrieTest {

    @Test
    public void put() {
        PatriciaBinaryTrie<String> trie = new PatriciaBinaryTrie<>();
        Assert.assertNull(trie.get("grace"));

        String key = "ab";
        trie.put(key, key);

        String key2 = "ac";
        trie.put(key2, key2);

        String key3 = "hi";
        trie.put(key3, key3);

        String key4 = "ad";
        trie.put(key4, key4);

        String key5 = "bd";
        trie.put(key5, key5);
        trie.put(key5, key5);

        String key6 = "A";
        trie.put(key6, key6);

        String key7 = "F";
        trie.put(key7, key7);

        String key8 = "E";
        trie.put(key8, key8);

        String key9 = "F";
        trie.put(key9, key9);

        String key10 = "FU";
        trie.put(key10, key10);

        String key11 = "EU";
        trie.put(key11, key11);

        String key12 = "EUB";
        trie.put(key12, key12);

        trie.put("EUC", "EUC");
        trie.put("AB", "AB");
        trie.put("FB", "FB");

        Assert.assertEquals(key, trie.get(key));
        Assert.assertEquals(key2, trie.get(key2));
        Assert.assertEquals(key3, trie.get(key3));
        Assert.assertEquals(key4, trie.get(key4));
        Assert.assertEquals(key5, trie.get(key5));
        Assert.assertEquals(key6, trie.get(key6));
        Assert.assertEquals(key7, trie.get(key7));
        Assert.assertEquals(key8, trie.get(key8));
        Assert.assertEquals(key9, trie.get(key9));
        Assert.assertEquals(key10, trie.get(key10));
        Assert.assertEquals(key11, trie.get(key11));
        Assert.assertEquals(key12, trie.get(key12));
    }

    @Test
    public void remove() {
        PatriciaBinaryTrie<String> trie = new PatriciaBinaryTrie<>();
        String key = "abc";
        trie.put(key, key);
        String value = trie.get(key);
        Assert.assertEquals(key, value);

        value = trie.remove(key);
        Assert.assertEquals(key, value);

        value = trie.get(key);
        Assert.assertNull(value);
    }

    @Test
    public void size() {
        PatriciaBinaryTrie<String> trie = new PatriciaBinaryTrie<>();
        trie.put("abc", "abc");
        Assert.assertEquals(1, trie.size());

        trie.put("abc", "abc");
        Assert.assertEquals(1, trie.size());

        trie.put("abc2", "abc2");
        Assert.assertEquals(2, trie.size());

        trie.remove("abc");
        Assert.assertEquals(1, trie.size());
    }

    @Test
    public void testGetWithMap() {
        Random random = new Random();
        int size = 100000;
        Set<String> keys = new HashSet<>(size);
        while (keys.size() < size) {
            int length = random.nextInt(10);
            if (length > 1) {
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

        PatriciaBinaryTrie<String> trie = new PatriciaBinaryTrie<>();
        Map<String, String> map = new HashMap<>();
        for (String key : keys) {
            trie.put(key, key);
            map.put(key, key);
        }
        long t1 = System.currentTimeMillis();
        for (String k : keys) {
            Assert.assertEquals(map.get(k), trie.get(k));
        }
        long t2 = System.currentTimeMillis();
        System.out.println("PatriciaTrie-get:\t" + (t2 - t1));
    }

    @Test
    @Ignore
    public void performance() {
        PatriciaBinaryTrie<String> patriciaTrie = new PatriciaBinaryTrie<>();

        String finalKey = "abcdefghij";
        patriciaTrie.put(finalKey, finalKey);
        StandardTrie<String> standardTrie = new StandardTrie<>();
        standardTrie.put(finalKey, finalKey);

        // 2亿次
        int size = 200000000;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            patriciaTrie.get(finalKey);
        }

        long t2 = System.currentTimeMillis();
        System.out.println("PatriciaTrie-get:\t" + (t2 - t1));

        for (int i = 0; i < size; i++) {
            standardTrie.get(finalKey);
        }

        long t3 = System.currentTimeMillis();
        System.out.println("StandardTrie-get:\t" + (t3 - t2));
    }
}