package com.igeeksky.perfect.nlp.trie;


import com.igeeksky.xtool.core.function.tuple.Tuple2;
import com.igeeksky.xtool.core.nlp.Found;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Patrick.Lau
 * @since 1.0.0 2021-11-30
 */
public class BasicTrieTest {

    @Test
    public void put() {
        BasicTrie<String> trie = new BasicTrie<>();
        String key = "abc";
        trie.put(key, key);
        String value = trie.get(key);
        Assert.assertEquals(key, value);

        value = trie.get("ab");
        Assert.assertNull(value);
    }

    @Test
    public void remove() {
        BasicTrie<String> trie = new BasicTrie<>();
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
        BasicTrie<String> trie = new BasicTrie<>();
        trie.put("abc", "abc");
        Assert.assertEquals(1, trie.size());
        trie.put("abc2", "abc2");
        Assert.assertEquals(2, trie.size());
        trie.remove("abc");
        Assert.assertEquals(1, trie.size());
    }

    @Test
    public void prefixMatch() {
        BasicTrie<String> trie = new BasicTrie<>();
        trie.put("abc", "abc");
        trie.put("abd", "abd");
        Tuple2<String, String> tuple2 = trie.prefixMatch("abcd");
        System.out.println(tuple2);
        Assert.assertEquals("abc", tuple2.getT2());
    }

    @Test
    public void keysWithPrefix() {
        BasicTrie<String> trie = new BasicTrie<>();
        trie.put("ab", "ab");
        trie.put("abc", "abc");
        trie.put("abcd", "abcd");
        trie.put("abd", "abd");
        trie.put("bcd", "bcd");
        trie.put("cda", "cda");
        List<Tuple2<String, String>> keysWithPrefix = trie.keysWithPrefix("ab");
        System.out.println(keysWithPrefix);
        Assert.assertEquals("[[ab, ab], [abc, abc], [abcd, abcd], [abd, abd]]", keysWithPrefix.toString());
    }

    @Test
    public void matchAll() {
        BasicTrie<String> trie = new BasicTrie<>();
        trie.put("abc", "abc");
        trie.put("abcd", "abcd");
        trie.put("bcd", "bcd");
        trie.put("abedd", "abedd");
        List<Found<String>> matchAll = trie.matchAll("xxabcdefxx");
        System.out.println(matchAll);
        String expected = "[{\"start\":2, \"end\":4, \"key\":\"abc\", \"value\":\"abc\"},"
                + " {\"start\":2, \"end\":5, \"key\":\"abcd\", \"value\":\"abcd\"},"
                + " {\"start\":3, \"end\":5, \"key\":\"bcd\", \"value\":\"bcd\"}]";
        Assert.assertEquals(expected, matchAll.toString());
    }

    @Test
    @Ignore
    public void performance() {
        BasicTrie<String> trie = new BasicTrie<>();

        String finalKey = "abcdefghij";
        trie.put(finalKey, finalKey);
        Map<String, String> map = new HashMap<>();
        map.put(finalKey, finalKey);

        // 2亿次
        int size = 200000000;
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            // 为了避免 String 缓存 hashcode，使得hashmap无需计算，导致性能对比不公平，重新生成相同的key
            array[i] = new String(finalKey.toCharArray());
        }

        long t1 = System.currentTimeMillis();
        for (String key : array) {
            trie.get(key);
        }

        long t2 = System.currentTimeMillis();
        System.out.println("trie-get:\t" + (t2 - t1));

        for (String key : array) {
            map.get(key);
        }
        long t3 = System.currentTimeMillis();
        System.out.println(" map-get:\t" + (t3 - t2));
    }
}