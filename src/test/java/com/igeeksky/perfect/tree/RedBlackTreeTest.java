package com.igeeksky.perfect.tree;

import com.igeeksky.perfect.KeyGenerator;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

/**
 * @author Patrick.Lau
 * @since 0.0.4 2021-12-22
 */
public class RedBlackTreeTest {

    @Test
    public void put() {
        RedBlackTree<String, String> tree = createTree("abcdefghijklmnopqrstuvwxyz");
        String expected = "{\"red\":false, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":false, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\", \"left\":{\"red\":false, \"key\":\"a\", \"value\":\"a\"}, \"right\":{\"red\":false, \"key\":\"c\", \"value\":\"c\"}}, \"right\":{\"red\":false, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":false, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"red\":false, \"key\":\"p\", \"value\":\"p\", \"left\":{\"red\":true, \"key\":\"l\", \"value\":\"l\", \"left\":{\"red\":false, \"key\":\"j\", \"value\":\"j\", \"left\":{\"red\":false, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"red\":false, \"key\":\"k\", \"value\":\"k\"}}, \"right\":{\"red\":false, \"key\":\"n\", \"value\":\"n\", \"left\":{\"red\":false, \"key\":\"m\", \"value\":\"m\"}, \"right\":{\"red\":false, \"key\":\"o\", \"value\":\"o\"}}}, \"right\":{\"red\":true, \"key\":\"t\", \"value\":\"t\", \"left\":{\"red\":false, \"key\":\"r\", \"value\":\"r\", \"left\":{\"red\":false, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"red\":false, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"red\":false, \"key\":\"v\", \"value\":\"v\", \"left\":{\"red\":false, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"red\":true, \"key\":\"x\", \"value\":\"x\", \"left\":{\"red\":false, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}}}";
        Assert.assertEquals(expected, tree.toString());

        Assert.assertEquals("f", tree.get("f"));
        Assert.assertEquals(26, tree.size());

        tree.remove("f");
        Assert.assertNull(tree.get("f"));
        Assert.assertEquals(25, tree.size());

        tree.put("f", "f");
        Assert.assertEquals(26, tree.size());
    }

    @Test
    public void testPut() {
        RedBlackTree<String, String> tree = createTree("wabzlocudefhijkmngqstpvxry");
        String expected = "{\"red\":false, \"key\":\"l\", \"value\":\"l\", \"left\":{\"red\":true, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\", \"left\":{\"red\":false, \"key\":\"a\", \"value\":\"a\"}, \"right\":{\"red\":false, \"key\":\"c\", \"value\":\"c\"}}, \"right\":{\"red\":false, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":true, \"key\":\"i\", \"value\":\"i\", \"left\":{\"red\":false, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":true, \"key\":\"g\", \"value\":\"g\"}}, \"right\":{\"red\":false, \"key\":\"j\", \"value\":\"j\", \"right\":{\"red\":true, \"key\":\"k\", \"value\":\"k\"}}}}}, \"right\":{\"red\":false, \"key\":\"s\", \"value\":\"s\", \"left\":{\"red\":true, \"key\":\"o\", \"value\":\"o\", \"left\":{\"red\":false, \"key\":\"m\", \"value\":\"m\", \"right\":{\"red\":true, \"key\":\"n\", \"value\":\"n\"}}, \"right\":{\"red\":false, \"key\":\"q\", \"value\":\"q\", \"left\":{\"red\":true, \"key\":\"p\", \"value\":\"p\"}, \"right\":{\"red\":true, \"key\":\"r\", \"value\":\"r\"}}}, \"right\":{\"red\":true, \"key\":\"w\", \"value\":\"w\", \"left\":{\"red\":false, \"key\":\"u\", \"value\":\"u\", \"left\":{\"red\":true, \"key\":\"t\", \"value\":\"t\"}, \"right\":{\"red\":true, \"key\":\"v\", \"value\":\"v\"}}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"left\":{\"red\":true, \"key\":\"x\", \"value\":\"x\"}, \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void remove() {
        RedBlackTree<String, String> tree = createTree("abcdefghijklmnopqrstuvwxyz");

        String expected = "{\"red\":false, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":false, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\", \"left\":{\"red\":false, \"key\":\"a\", \"value\":\"a\"}, \"right\":{\"red\":false, \"key\":\"c\", \"value\":\"c\"}}, \"right\":{\"red\":false, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":false, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"red\":false, \"key\":\"p\", \"value\":\"p\", \"left\":{\"red\":true, \"key\":\"l\", \"value\":\"l\", \"left\":{\"red\":false, \"key\":\"j\", \"value\":\"j\", \"left\":{\"red\":false, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"red\":false, \"key\":\"k\", \"value\":\"k\"}}, \"right\":{\"red\":false, \"key\":\"n\", \"value\":\"n\", \"left\":{\"red\":false, \"key\":\"m\", \"value\":\"m\"}, \"right\":{\"red\":false, \"key\":\"o\", \"value\":\"o\"}}}, \"right\":{\"red\":true, \"key\":\"t\", \"value\":\"t\", \"left\":{\"red\":false, \"key\":\"r\", \"value\":\"r\", \"left\":{\"red\":false, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"red\":false, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"red\":false, \"key\":\"v\", \"value\":\"v\", \"left\":{\"red\":false, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"red\":true, \"key\":\"x\", \"value\":\"x\", \"left\":{\"red\":false, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}}}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(26, tree.size());

        tree.remove("a");
        expected = "{\"red\":false, \"key\":\"p\", \"value\":\"p\", \"left\":{\"red\":false, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":false, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\", \"right\":{\"red\":true, \"key\":\"c\", \"value\":\"c\"}}, \"right\":{\"red\":true, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":false, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"red\":true, \"key\":\"l\", \"value\":\"l\", \"left\":{\"red\":false, \"key\":\"j\", \"value\":\"j\", \"left\":{\"red\":false, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"red\":false, \"key\":\"k\", \"value\":\"k\"}}, \"right\":{\"red\":false, \"key\":\"n\", \"value\":\"n\", \"left\":{\"red\":false, \"key\":\"m\", \"value\":\"m\"}, \"right\":{\"red\":false, \"key\":\"o\", \"value\":\"o\"}}}}, \"right\":{\"red\":false, \"key\":\"t\", \"value\":\"t\", \"left\":{\"red\":false, \"key\":\"r\", \"value\":\"r\", \"left\":{\"red\":false, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"red\":false, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"red\":false, \"key\":\"v\", \"value\":\"v\", \"left\":{\"red\":false, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"red\":true, \"key\":\"x\", \"value\":\"x\", \"left\":{\"red\":false, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(25, tree.size());

        tree.remove("p");
        expected = "{\"red\":false, \"key\":\"o\", \"value\":\"o\", \"left\":{\"red\":false, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":false, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\", \"right\":{\"red\":true, \"key\":\"c\", \"value\":\"c\"}}, \"right\":{\"red\":true, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":false, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"red\":false, \"key\":\"l\", \"value\":\"l\", \"left\":{\"red\":true, \"key\":\"j\", \"value\":\"j\", \"left\":{\"red\":false, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"red\":false, \"key\":\"k\", \"value\":\"k\"}}, \"right\":{\"red\":false, \"key\":\"n\", \"value\":\"n\", \"left\":{\"red\":true, \"key\":\"m\", \"value\":\"m\"}}}}, \"right\":{\"red\":false, \"key\":\"t\", \"value\":\"t\", \"left\":{\"red\":false, \"key\":\"r\", \"value\":\"r\", \"left\":{\"red\":false, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"red\":false, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"red\":false, \"key\":\"v\", \"value\":\"v\", \"left\":{\"red\":false, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"red\":true, \"key\":\"x\", \"value\":\"x\", \"left\":{\"red\":false, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(24, tree.size());

        tree.remove("n");
        expected = "{\"red\":false, \"key\":\"o\", \"value\":\"o\", \"left\":{\"red\":false, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":false, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\", \"right\":{\"red\":true, \"key\":\"c\", \"value\":\"c\"}}, \"right\":{\"red\":true, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":false, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"red\":false, \"key\":\"l\", \"value\":\"l\", \"left\":{\"red\":true, \"key\":\"j\", \"value\":\"j\", \"left\":{\"red\":false, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"red\":false, \"key\":\"k\", \"value\":\"k\"}}, \"right\":{\"red\":false, \"key\":\"m\", \"value\":\"m\"}}}, \"right\":{\"red\":false, \"key\":\"t\", \"value\":\"t\", \"left\":{\"red\":false, \"key\":\"r\", \"value\":\"r\", \"left\":{\"red\":false, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"red\":false, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"red\":false, \"key\":\"v\", \"value\":\"v\", \"left\":{\"red\":false, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"red\":true, \"key\":\"x\", \"value\":\"x\", \"left\":{\"red\":false, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(23, tree.size());

        tree.remove("m");
        expected = "{\"red\":false, \"key\":\"o\", \"value\":\"o\", \"left\":{\"red\":false, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":false, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\", \"right\":{\"red\":true, \"key\":\"c\", \"value\":\"c\"}}, \"right\":{\"red\":true, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":false, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"red\":false, \"key\":\"j\", \"value\":\"j\", \"left\":{\"red\":false, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"red\":false, \"key\":\"l\", \"value\":\"l\", \"left\":{\"red\":true, \"key\":\"k\", \"value\":\"k\"}}}}, \"right\":{\"red\":false, \"key\":\"t\", \"value\":\"t\", \"left\":{\"red\":false, \"key\":\"r\", \"value\":\"r\", \"left\":{\"red\":false, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"red\":false, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"red\":false, \"key\":\"v\", \"value\":\"v\", \"left\":{\"red\":false, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"red\":true, \"key\":\"x\", \"value\":\"x\", \"left\":{\"red\":false, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(22, tree.size());

        tree.remove("c");
        expected = "{\"red\":false, \"key\":\"o\", \"value\":\"o\", \"left\":{\"red\":false, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":false, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\"}, \"right\":{\"red\":true, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":false, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"red\":false, \"key\":\"j\", \"value\":\"j\", \"left\":{\"red\":false, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"red\":false, \"key\":\"l\", \"value\":\"l\", \"left\":{\"red\":true, \"key\":\"k\", \"value\":\"k\"}}}}, \"right\":{\"red\":false, \"key\":\"t\", \"value\":\"t\", \"left\":{\"red\":false, \"key\":\"r\", \"value\":\"r\", \"left\":{\"red\":false, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"red\":false, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"red\":false, \"key\":\"v\", \"value\":\"v\", \"left\":{\"red\":false, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"red\":true, \"key\":\"x\", \"value\":\"x\", \"left\":{\"red\":false, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(21, tree.size());

        // 重复删除
        tree.remove("c");
        expected = "{\"red\":false, \"key\":\"o\", \"value\":\"o\", \"left\":{\"red\":false, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":false, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\"}, \"right\":{\"red\":true, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":false, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"red\":false, \"key\":\"j\", \"value\":\"j\", \"left\":{\"red\":false, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"red\":false, \"key\":\"l\", \"value\":\"l\", \"left\":{\"red\":true, \"key\":\"k\", \"value\":\"k\"}}}}, \"right\":{\"red\":false, \"key\":\"t\", \"value\":\"t\", \"left\":{\"red\":false, \"key\":\"r\", \"value\":\"r\", \"left\":{\"red\":false, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"red\":false, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"red\":false, \"key\":\"v\", \"value\":\"v\", \"left\":{\"red\":false, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"red\":true, \"key\":\"x\", \"value\":\"x\", \"left\":{\"red\":false, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(21, tree.size());

        tree.remove("j");
        expected = "{\"red\":false, \"key\":\"o\", \"value\":\"o\", \"left\":{\"red\":false, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":false, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\"}, \"right\":{\"red\":true, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":false, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"red\":false, \"key\":\"k\", \"value\":\"k\", \"left\":{\"red\":false, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"red\":false, \"key\":\"l\", \"value\":\"l\"}}}, \"right\":{\"red\":false, \"key\":\"t\", \"value\":\"t\", \"left\":{\"red\":false, \"key\":\"r\", \"value\":\"r\", \"left\":{\"red\":false, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"red\":false, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"red\":false, \"key\":\"v\", \"value\":\"v\", \"left\":{\"red\":false, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"red\":true, \"key\":\"x\", \"value\":\"x\", \"left\":{\"red\":false, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(20, tree.size());

        tree.remove("t");
        expected = "{\"red\":false, \"key\":\"o\", \"value\":\"o\", \"left\":{\"red\":false, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":false, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\"}, \"right\":{\"red\":true, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":false, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"red\":false, \"key\":\"k\", \"value\":\"k\", \"left\":{\"red\":false, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"red\":false, \"key\":\"l\", \"value\":\"l\"}}}, \"right\":{\"red\":false, \"key\":\"v\", \"value\":\"v\", \"left\":{\"red\":false, \"key\":\"s\", \"value\":\"s\", \"left\":{\"red\":false, \"key\":\"r\", \"value\":\"r\", \"left\":{\"red\":true, \"key\":\"q\", \"value\":\"q\"}}, \"right\":{\"red\":false, \"key\":\"u\", \"value\":\"u\"}}, \"right\":{\"red\":false, \"key\":\"x\", \"value\":\"x\", \"left\":{\"red\":false, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(19, tree.size());

        tree.remove("u");
        expected = "{\"red\":false, \"key\":\"o\", \"value\":\"o\", \"left\":{\"red\":false, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":false, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\"}, \"right\":{\"red\":true, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":false, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"red\":false, \"key\":\"k\", \"value\":\"k\", \"left\":{\"red\":false, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"red\":false, \"key\":\"l\", \"value\":\"l\"}}}, \"right\":{\"red\":false, \"key\":\"v\", \"value\":\"v\", \"left\":{\"red\":false, \"key\":\"r\", \"value\":\"r\", \"left\":{\"red\":false, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"red\":false, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"red\":false, \"key\":\"x\", \"value\":\"x\", \"left\":{\"red\":false, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(18, tree.size());

        tree.remove("v");
        expected = "{\"red\":false, \"key\":\"o\", \"value\":\"o\", \"left\":{\"red\":true, \"key\":\"h\", \"value\":\"h\", \"left\":{\"red\":false, \"key\":\"d\", \"value\":\"d\", \"left\":{\"red\":false, \"key\":\"b\", \"value\":\"b\"}, \"right\":{\"red\":true, \"key\":\"f\", \"value\":\"f\", \"left\":{\"red\":false, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"red\":false, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"red\":false, \"key\":\"k\", \"value\":\"k\", \"left\":{\"red\":false, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"red\":false, \"key\":\"l\", \"value\":\"l\"}}}, \"right\":{\"red\":false, \"key\":\"s\", \"value\":\"s\", \"left\":{\"red\":false, \"key\":\"r\", \"value\":\"r\", \"left\":{\"red\":true, \"key\":\"q\", \"value\":\"q\"}}, \"right\":{\"red\":true, \"key\":\"x\", \"value\":\"x\", \"left\":{\"red\":false, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"red\":false, \"key\":\"y\", \"value\":\"y\", \"right\":{\"red\":true, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(17, tree.size());
    }

    private RedBlackTree<String, String> createTree(String text) {
        RedBlackTree<String, String> avl = new RedBlackTree<>();
        char[] chars = text.toCharArray();
        for (char c : chars) {
            String key = String.valueOf(c);
            avl.put(key, key);
        }
        return avl;
    }

    @Test
    public void isEmpty() {
        RedBlackTree<String, String> tree = new RedBlackTree<>();
        Assert.assertTrue(tree.isEmpty());

        tree.put("a", "a");
        Assert.assertFalse(tree.isEmpty());

        tree.clear();
        Assert.assertTrue(tree.isEmpty());
    }

    @Test
    public void testGetWithMap() {
        int size = 10000;
        List<String> keys = new ArrayList<>(size);
        keys.addAll(KeyGenerator.createKeys(size));

        RedBlackTree<String, String> tree = new RedBlackTree<>();
        Map<String, String> map = new HashMap<>();

        for (String key : keys) {
            tree.put(key, key);
            map.put(key, key);
        }
        Assert.assertEquals(map.size(), tree.size());

        for (String k : keys) {
            Assert.assertEquals(map.get(k), tree.get(k));
        }

        Random random = new Random();
        int offset = random.nextInt(size / 2);
        while (offset == 0) {
            offset = random.nextInt(size / 2);
        }

        keys.subList(offset, size).clear();

        keys.forEach(k -> Assert.assertEquals(map.remove(k), tree.remove(k)));

        Assert.assertEquals(map.size(), tree.size());
        for (String k : keys) {
            Assert.assertEquals(map.get(k), tree.get(k));
        }
    }

    @Test
    @Ignore
    public void performance() {
        Set<String> keys = KeyGenerator.createKeys(10000000);

        long t1 = System.currentTimeMillis();
        RedBlackTree<String, String> rbt = new RedBlackTree<>();
        for (String key : keys) {
            rbt.put(key, key);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("rbt-put:\t" + (t2 - t1));

        AvlTree<String, String> avl = new AvlTree<>();
        for (String key : keys) {
            avl.put(key, key);
        }
        long t3 = System.currentTimeMillis();
        System.out.println("avl-put:\t" + (t3 - t2));

        for (String k : keys) {
            rbt.get(k);
        }
        long t4 = System.currentTimeMillis();
        System.out.println("rbt-get:\t" + (t4 - t3));

        for (String k : keys) {
            avl.get(k);
        }
        long t5 = System.currentTimeMillis();
        System.out.println("avl-get:\t" + (t5 - t4));

        for (String k : keys) {
            rbt.remove(k);
        }
        long t6 = System.currentTimeMillis();
        System.out.println("rbt-del:\t" + (t6 - t5));

        for (String k : keys) {
            avl.get(k);
        }
        long t7 = System.currentTimeMillis();
        System.out.println("avl-del:\t" + (t7 - t6));
    }
}