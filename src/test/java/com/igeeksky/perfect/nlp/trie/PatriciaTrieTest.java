package com.igeeksky.perfect.nlp.trie;


import com.igeeksky.xtool.core.function.tuple.Tuple2;
import com.igeeksky.xtool.core.nlp.Found;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * @author Patrick.Lau
 * @since 1.0.0 2021-12-09
 */
public class PatriciaTrieTest {

    @Test
    public void put() {
        PatriciaTrie<String> trie = new PatriciaTrie<>();
        String key = "abc";
        trie.put(key, key);

        String key2 = "abcd";
        trie.put(key2, key2);

        String key3 = "abcdef";
        trie.put(key3, key3);

        String key4 = "abcdeg";
        trie.put(key4, key4);

        String key5 = "a";
        trie.put(key5, key5);

        String key6 = "abce";
        trie.put(key6, key6);

        String value = trie.get(key);
        Assert.assertEquals(key, value);

        value = trie.get("ab");
        Assert.assertNull(value);

        value = trie.get("abcde");
        Assert.assertNull(value);
    }

    @Test
    public void remove() {
        PatriciaTrie<String> trie = new PatriciaTrie<>();
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
        PatriciaTrie<String> trie = new PatriciaTrie<>();
        trie.put("abc", "abc");
        Assert.assertEquals(1, trie.size());

        trie.put("abc", "abc");
        Assert.assertEquals(1, trie.size());

        trie.put("abc2", "abc2");
        Assert.assertEquals(2, trie.size());

        trie.remove("abc");
        Assert.assertEquals(1, trie.size());
    }

    /**
     * 查找输入字符串的最长前缀
     */
    @Test
    public void prefixMatch() {
        PatriciaTrie<String> trie = new PatriciaTrie<>();
        trie.put("abc", "abc");
        trie.put("abd", "abd");
        Tuple2<String, String> tuple2 = trie.prefixMatch("abcd");
        Assert.assertEquals("abc", tuple2.getT2());
        tuple2 = trie.prefixMatch("bcd");
        Assert.assertNull(tuple2);
    }

    /**
     * 查找所有以给定字符串为前缀的key
     */
    @Test
    public void keysWithPrefix() {
        PatriciaTrie<String> trie = new PatriciaTrie<>();
        trie.put("ab", "ab");
        trie.put("abc", "abc");
        trie.put("abcd", "abcd");
        trie.put("abd", "abd");
        trie.put("bcd", "bcd");
        trie.put("cda", "cda");
        List<Tuple2<String, String>> keysWithPrefix = trie.keysWithPrefix("ab");
        Assert.assertEquals("[[ab, ab], [abc, abc], [abcd, abcd], [abd, abd]]", keysWithPrefix.toString());
        keysWithPrefix = trie.keysWithPrefix("abcde");
        Assert.assertEquals("[]", keysWithPrefix.toString());
    }

    /**
     * 查找给定文本段中包含的键
     */
    @Test
    public void matchAll() {
        PatriciaTrie<String> trie = new PatriciaTrie<>();
        trie.put("abcd", "abcd");
        trie.put("bcdef", "bcdef");
        trie.put("abe", "abe");
        List<Found<String>> matchAll = trie.matchAll("abcdefg");

        String expected = "[{\"start\":0, \"end\":3, \"key\":\"abcd\", \"value\":\"abcd\"}, {\"start\":1, \"end\":5, \"key\":\"bcdef\", \"value\":\"bcdef\"}]";
        Assert.assertEquals(expected, matchAll.toString());
    }

    @Test
    @Ignore
    public void performance() {
        PatriciaTrie<String> patriciaTrie = new PatriciaTrie<>();

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