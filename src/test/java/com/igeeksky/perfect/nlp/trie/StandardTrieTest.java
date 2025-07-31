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
public class StandardTrieTest {

    @Test
    public void put() {
        StandardTrie<String> trie = new StandardTrie<>();
        String key = "abc";
        trie.put(key, key);
        String value = trie.get(key);
        Assert.assertEquals(key, value);

        value = trie.get("ab");
        Assert.assertNull(value);

        value = trie.get("abcd");
        Assert.assertNull(value);
    }

    @Test
    public void remove() {
        StandardTrie<String> trie = new StandardTrie<>();
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
        StandardTrie<String> trie = new StandardTrie<>();
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
    public void prefixMatch() {
        StandardTrie<String> trie = createStandardTrie();
        Tuple2<String, String> tuple2 = trie.prefixMatch("bades");
        Assert.assertEquals("[bade, bade]", tuple2.toString());
        tuple2 = trie.prefixMatch("bcd");
        Assert.assertNull(tuple2);
    }

    @Test
    public void keysWithPrefix() {
        StandardTrie<String> trie = createStandardTrie();
        List<Tuple2<String, String>> keysWithPrefix = trie.keysWithPrefix("ca");
        Assert.assertEquals("[[ca, ca], [cad, cad]]", keysWithPrefix.toString());
        keysWithPrefix = trie.keysWithPrefix("cbd");
        Assert.assertEquals("[]", keysWithPrefix.toString());
    }

    @Test
    public void matchAll() {
        StandardTrie<String> trie = createStandardTrie();
        List<Found<String>> matchAll = trie.matchAll("xxbcaxx");

        String expected = "[{\"start\":3, \"end\":4, \"key\":\"ca\", \"value\":\"ca\"}]";
        Assert.assertEquals(expected, matchAll.toString());
    }

    private static StandardTrie<String> createStandardTrie() {
        StandardTrie<String> trie = new StandardTrie<>();
        trie.put("bad", "bad");
        trie.put("bade", "bade");
        trie.put("bed", "bed");
        trie.put("ca", "ca");
        trie.put("cad", "cad");
        trie.put("dad", "dad");
        return trie;
    }

    @Test
    @Ignore
    public void performance() {
        StandardTrie<String> trie = new StandardTrie<>();

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
            String ignore = map.get(key);
        }

        long t3 = System.currentTimeMillis();
        System.out.println(" map-get:\t" + (t3 - t2));
    }

    @Test
    public void toBinaryString() {
        String b = Integer.toBinaryString('你');
        String c = Integer.toBinaryString('好');
        System.out.println(b + " " + c);
        System.out.println('你');
        System.out.println(Integer.toBinaryString((0b1111111100000000 & '你') >> 8));
        System.out.println(Integer.toBinaryString(0b0000000011111111 & '你'));
    }
}