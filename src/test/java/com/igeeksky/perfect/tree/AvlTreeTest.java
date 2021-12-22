package com.igeeksky.perfect.tree;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

/**
 * @author Patrick.Lau
 * @since 0.0.4 2021-12-22
 */
public class AvlTreeTest {

    @Test
    public void put() {
        AvlTree<String, String> avl = createTree("abcdef");

        Assert.assertNull(avl.get("g"));
        Assert.assertEquals("f", avl.get("f"));
        Assert.assertEquals(6, avl.size());

        avl.remove("d");
        Assert.assertEquals(5, avl.size());
        Assert.assertNull(avl.get("d"));

        avl.put("f", "f");
        Assert.assertEquals(5, avl.size());
    }

    @Test
    public void remove() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");

        String expected = "{\"height\":4, \"key\":\"p\", \"left\":{\"height\":3, \"key\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"left\":{\"height\":1, \"key\":\"b\", \"left\":{\"height\":0, \"key\":\"a\"}, \"right\":{\"height\":0, \"key\":\"c\"}}, \"right\":{\"height\":1, \"key\":\"f\", \"left\":{\"height\":0, \"key\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"l\", \"left\":{\"height\":1, \"key\":\"j\", \"left\":{\"height\":0, \"key\":\"i\"}, \"right\":{\"height\":0, \"key\":\"k\"}}, \"right\":{\"height\":1, \"key\":\"n\", \"left\":{\"height\":0, \"key\":\"m\"}, \"right\":{\"height\":0, \"key\":\"o\"}}}}, \"right\":{\"height\":3, \"key\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"left\":{\"height\":0, \"key\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"left\":{\"height\":0, \"key\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"right\":{\"height\":0, \"key\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(26, avl.size());

        avl.remove("a");
        expected = "{\"height\":4, \"key\":\"p\", \"left\":{\"height\":3, \"key\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"left\":{\"height\":1, \"key\":\"b\", \"right\":{\"height\":0, \"key\":\"c\"}}, \"right\":{\"height\":1, \"key\":\"f\", \"left\":{\"height\":0, \"key\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"l\", \"left\":{\"height\":1, \"key\":\"j\", \"left\":{\"height\":0, \"key\":\"i\"}, \"right\":{\"height\":0, \"key\":\"k\"}}, \"right\":{\"height\":1, \"key\":\"n\", \"left\":{\"height\":0, \"key\":\"m\"}, \"right\":{\"height\":0, \"key\":\"o\"}}}}, \"right\":{\"height\":3, \"key\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"left\":{\"height\":0, \"key\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"left\":{\"height\":0, \"key\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"right\":{\"height\":0, \"key\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(25, avl.size());

        avl.remove("p");
        expected = "{\"height\":4, \"key\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"left\":{\"height\":1, \"key\":\"b\", \"right\":{\"height\":0, \"key\":\"c\"}}, \"right\":{\"height\":1, \"key\":\"f\", \"left\":{\"height\":0, \"key\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"l\", \"left\":{\"height\":1, \"key\":\"j\", \"left\":{\"height\":0, \"key\":\"i\"}, \"right\":{\"height\":0, \"key\":\"k\"}}, \"right\":{\"height\":1, \"key\":\"n\", \"left\":{\"height\":0, \"key\":\"m\"}}}}, \"right\":{\"height\":3, \"key\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"left\":{\"height\":0, \"key\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"left\":{\"height\":0, \"key\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"right\":{\"height\":0, \"key\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(24, avl.size());

        avl.remove("n");
        expected = "{\"height\":4, \"key\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"left\":{\"height\":1, \"key\":\"b\", \"right\":{\"height\":0, \"key\":\"c\"}}, \"right\":{\"height\":1, \"key\":\"f\", \"left\":{\"height\":0, \"key\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"l\", \"left\":{\"height\":1, \"key\":\"j\", \"left\":{\"height\":0, \"key\":\"i\"}, \"right\":{\"height\":0, \"key\":\"k\"}}, \"right\":{\"height\":0, \"key\":\"m\"}}}, \"right\":{\"height\":3, \"key\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"left\":{\"height\":0, \"key\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"left\":{\"height\":0, \"key\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"right\":{\"height\":0, \"key\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(23, avl.size());

        avl.remove("m");
        expected = "{\"height\":4, \"key\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"left\":{\"height\":1, \"key\":\"b\", \"right\":{\"height\":0, \"key\":\"c\"}}, \"right\":{\"height\":1, \"key\":\"f\", \"left\":{\"height\":0, \"key\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"j\", \"left\":{\"height\":0, \"key\":\"i\"}, \"right\":{\"height\":1, \"key\":\"l\", \"left\":{\"height\":0, \"key\":\"k\"}}}}, \"right\":{\"height\":3, \"key\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"left\":{\"height\":0, \"key\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"left\":{\"height\":0, \"key\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"right\":{\"height\":0, \"key\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(22, avl.size());

        avl.remove("c");
        expected = "{\"height\":4, \"key\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"left\":{\"height\":0, \"key\":\"b\"}, \"right\":{\"height\":1, \"key\":\"f\", \"left\":{\"height\":0, \"key\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"j\", \"left\":{\"height\":0, \"key\":\"i\"}, \"right\":{\"height\":1, \"key\":\"l\", \"left\":{\"height\":0, \"key\":\"k\"}}}}, \"right\":{\"height\":3, \"key\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"left\":{\"height\":0, \"key\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"left\":{\"height\":0, \"key\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"right\":{\"height\":0, \"key\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(21, avl.size());

        // 重复删除
        avl.remove("c");
        expected = "{\"height\":4, \"key\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"left\":{\"height\":0, \"key\":\"b\"}, \"right\":{\"height\":1, \"key\":\"f\", \"left\":{\"height\":0, \"key\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"j\", \"left\":{\"height\":0, \"key\":\"i\"}, \"right\":{\"height\":1, \"key\":\"l\", \"left\":{\"height\":0, \"key\":\"k\"}}}}, \"right\":{\"height\":3, \"key\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"left\":{\"height\":0, \"key\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"left\":{\"height\":0, \"key\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"right\":{\"height\":0, \"key\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(21, avl.size());

        avl.remove("j");
        expected = "{\"height\":4, \"key\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"left\":{\"height\":0, \"key\":\"b\"}, \"right\":{\"height\":1, \"key\":\"f\", \"left\":{\"height\":0, \"key\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\"}}}, \"right\":{\"height\":1, \"key\":\"k\", \"left\":{\"height\":0, \"key\":\"i\"}, \"right\":{\"height\":0, \"key\":\"l\"}}}, \"right\":{\"height\":3, \"key\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"left\":{\"height\":0, \"key\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"left\":{\"height\":0, \"key\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"right\":{\"height\":0, \"key\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(20, avl.size());

        avl.remove("t");
        expected = "{\"height\":4, \"key\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"left\":{\"height\":0, \"key\":\"b\"}, \"right\":{\"height\":1, \"key\":\"f\", \"left\":{\"height\":0, \"key\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\"}}}, \"right\":{\"height\":1, \"key\":\"k\", \"left\":{\"height\":0, \"key\":\"i\"}, \"right\":{\"height\":0, \"key\":\"l\"}}}, \"right\":{\"height\":3, \"key\":\"u\", \"left\":{\"height\":1, \"key\":\"r\", \"left\":{\"height\":0, \"key\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"right\":{\"height\":0, \"key\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"right\":{\"height\":0, \"key\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(19, avl.size());

        avl.remove("u");
        expected = "{\"height\":4, \"key\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"left\":{\"height\":0, \"key\":\"b\"}, \"right\":{\"height\":1, \"key\":\"f\", \"left\":{\"height\":0, \"key\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\"}}}, \"right\":{\"height\":1, \"key\":\"k\", \"left\":{\"height\":0, \"key\":\"i\"}, \"right\":{\"height\":0, \"key\":\"l\"}}}, \"right\":{\"height\":3, \"key\":\"v\", \"left\":{\"height\":1, \"key\":\"r\", \"left\":{\"height\":0, \"key\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"left\":{\"height\":0, \"key\":\"w\"}, \"right\":{\"height\":1, \"key\":\"y\", \"right\":{\"height\":0, \"key\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(18, avl.size());

        avl.remove("v");
        expected = "{\"height\":4, \"key\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"left\":{\"height\":0, \"key\":\"b\"}, \"right\":{\"height\":1, \"key\":\"f\", \"left\":{\"height\":0, \"key\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\"}}}, \"right\":{\"height\":1, \"key\":\"k\", \"left\":{\"height\":0, \"key\":\"i\"}, \"right\":{\"height\":0, \"key\":\"l\"}}}, \"right\":{\"height\":2, \"key\":\"w\", \"left\":{\"height\":1, \"key\":\"r\", \"left\":{\"height\":0, \"key\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"left\":{\"height\":0, \"key\":\"x\"}, \"right\":{\"height\":0, \"key\":\"z\"}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(17, avl.size());

        System.out.println(avl.getRoot());
    }

    private AvlTree<String, String> createTree(String text) {
        AvlTree<String, String> avl = new AvlTree<>();
        char[] chars = text.toCharArray();
        for (char c : chars) {
            String key = String.valueOf(c);
            avl.put(key, key);
        }
        return avl;
    }

    @Test
    public void isEmpty() {
        AvlTree<String, String> avl = new AvlTree<>();
        Assert.assertTrue(avl.isEmpty());

        avl.put("a", "a");
        Assert.assertFalse(avl.isEmpty());

        avl.clear();
        Assert.assertTrue(avl.isEmpty());
    }

    @Test
    public void testGetWithMap() {
        int size = 10000;
        List<String> keys = new ArrayList<>(size);
        keys.addAll(createKeys(size));

        AvlTree<String, String> avl = new AvlTree<>();
        Map<String, String> map = new HashMap<>();

        for (String key : keys) {
            avl.put(key, key);
            map.put(key, key);
        }
        Assert.assertEquals(map.size(), avl.size());

        for (String k : keys) {
            Assert.assertEquals(map.get(k), avl.get(k));
        }

        Random random = new Random();
        int offset = random.nextInt(size / 2);
        while (offset == 0) {
            offset = random.nextInt(size / 2);
        }

        keys.subList(offset, size).clear();

        keys.forEach(k -> {
            avl.remove(k);
            map.remove(k);
        });

        Assert.assertEquals(map.size(), avl.size());
        for (String k : keys) {
            Assert.assertEquals(map.get(k), avl.get(k));
        }
    }

    @Test
    @Ignore
    public void performance() {
        Set<String> keys = createKeys(10000000);

        long t1 = System.currentTimeMillis();
        AvlTree<String, String> avl = new AvlTree<>();
        for (String key : keys) {
            avl.put(key, key);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("avl-put:\t" + (t2 - t1));

        TreeMap<String, String> map = new TreeMap<>();
        for (String key : keys) {
            map.put(key, key);
        }
        long t3 = System.currentTimeMillis();
        System.out.println("map-put:\t" + (t3 - t2));

        for (String k : keys) {
            avl.get(k);
        }
        long t4 = System.currentTimeMillis();
        System.out.println("avl-get:\t" + (t4 - t3));

        for (String k : keys) {
            map.get(k);
        }
        long t5 = System.currentTimeMillis();
        System.out.println("map-get:\t" + (t5 - t4));
    }

    private Set<String> createKeys(int size) {
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