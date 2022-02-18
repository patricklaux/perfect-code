package com.igeeksky.perfect.tree;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Patrick.Lau
 * @since 1.0.0 2022-01-14
 */
public class BTreeTest {

    @Test
    public void put() {
        BTree<String, String> tree = createTree("abcdefghijklmnopqrstuvwxyz", 4);
        String expected = "{\"size\":1, \"items\":[{\"key\":\"h\", \"value\":\"h\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"d\", \"value\":\"d\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"b\", \"value\":\"b\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"a\", \"value\":\"a\"}]}, {\"size\":1, \"items\":[{\"key\":\"c\", \"value\":\"c\"}]}]}, {\"size\":1, \"items\":[{\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"e\", \"value\":\"e\"}]}, {\"size\":1, \"items\":[{\"key\":\"g\", \"value\":\"g\"}]}]}]}, {\"size\":3, \"items\":[{\"key\":\"l\", \"value\":\"l\"}, {\"key\":\"p\", \"value\":\"p\"}, {\"key\":\"t\", \"value\":\"t\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"j\", \"value\":\"j\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"i\", \"value\":\"i\"}]}, {\"size\":1, \"items\":[{\"key\":\"k\", \"value\":\"k\"}]}]}, {\"size\":1, \"items\":[{\"key\":\"n\", \"value\":\"n\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"m\", \"value\":\"m\"}]}, {\"size\":1, \"items\":[{\"key\":\"o\", \"value\":\"o\"}]}]}, {\"size\":1, \"items\":[{\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"q\", \"value\":\"q\"}]}, {\"size\":1, \"items\":[{\"key\":\"s\", \"value\":\"s\"}]}]}, {\"size\":2, \"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"u\", \"value\":\"u\"}]}, {\"size\":1, \"items\":[{\"key\":\"w\", \"value\":\"w\"}]}, {\"size\":2, \"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void testPut() {
        BTree<String, String> tree = createTree("wabzlocudefhijkmngqstpvxry", 4);
        String expected = "{\"size\":3, \"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"i\", \"value\":\"i\"}, {\"key\":\"o\", \"value\":\"o\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"b\", \"value\":\"b\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"a\", \"value\":\"a\"}]}, {\"size\":1, \"items\":[{\"key\":\"c\", \"value\":\"c\"}]}]}, {\"size\":1, \"items\":[{\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"e\", \"value\":\"e\"}]}, {\"size\":2, \"items\":[{\"key\":\"g\", \"value\":\"g\"}, {\"key\":\"h\", \"value\":\"h\"}]}]}, {\"size\":1, \"items\":[{\"key\":\"k\", \"value\":\"k\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"j\", \"value\":\"j\"}]}, {\"size\":3, \"items\":[{\"key\":\"l\", \"value\":\"l\"}, {\"key\":\"m\", \"value\":\"m\"}, {\"key\":\"n\", \"value\":\"n\"}]}]}, {\"size\":3, \"items\":[{\"key\":\"q\", \"value\":\"q\"}, {\"key\":\"u\", \"value\":\"u\"}, {\"key\":\"w\", \"value\":\"w\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"p\", \"value\":\"p\"}]}, {\"size\":3, \"items\":[{\"key\":\"r\", \"value\":\"r\"}, {\"key\":\"s\", \"value\":\"s\"}, {\"key\":\"t\", \"value\":\"t\"}]}, {\"size\":1, \"items\":[{\"key\":\"v\", \"value\":\"v\"}]}, {\"size\":3, \"items\":[{\"key\":\"x\", \"value\":\"x\"}, {\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void testPut1() {
        BTree<String, String> tree = createTree("wabzlocudefhijkmngqstpvxry", 6);
        String expected = "{\"size\":1, \"items\":[{\"key\":\"l\", \"value\":\"l\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"c\", \"value\":\"c\"}, {\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"a\", \"value\":\"a\"}, {\"key\":\"b\", \"value\":\"b\"}]}, {\"size\":2, \"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"e\", \"value\":\"e\"}]}, {\"size\":5, \"items\":[{\"key\":\"g\", \"value\":\"g\"}, {\"key\":\"h\", \"value\":\"h\"}, {\"key\":\"i\", \"value\":\"i\"}, {\"key\":\"j\", \"value\":\"j\"}, {\"key\":\"k\", \"value\":\"k\"}]}]}, {\"size\":3, \"items\":[{\"key\":\"o\", \"value\":\"o\"}, {\"key\":\"t\", \"value\":\"t\"}, {\"key\":\"w\", \"value\":\"w\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"m\", \"value\":\"m\"}, {\"key\":\"n\", \"value\":\"n\"}]}, {\"size\":4, \"items\":[{\"key\":\"p\", \"value\":\"p\"}, {\"key\":\"q\", \"value\":\"q\"}, {\"key\":\"r\", \"value\":\"r\"}, {\"key\":\"s\", \"value\":\"s\"}]}, {\"size\":2, \"items\":[{\"key\":\"u\", \"value\":\"u\"}, {\"key\":\"v\", \"value\":\"v\"}]}, {\"size\":3, \"items\":[{\"key\":\"x\", \"value\":\"x\"}, {\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void testPut2() {
        BTree<String, String> tree = createTree("abcdefghijklmnopqrstuvwxyz", 5);
        String expected = "{\"size\":2, \"items\":[{\"key\":\"i\", \"value\":\"i\"}, {\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"c\", \"value\":\"c\"}, {\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"a\", \"value\":\"a\"}, {\"key\":\"b\", \"value\":\"b\"}]}, {\"size\":2, \"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"e\", \"value\":\"e\"}]}, {\"size\":2, \"items\":[{\"key\":\"g\", \"value\":\"g\"}, {\"key\":\"h\", \"value\":\"h\"}]}]}, {\"size\":2, \"items\":[{\"key\":\"l\", \"value\":\"l\"}, {\"key\":\"o\", \"value\":\"o\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"j\", \"value\":\"j\"}, {\"key\":\"k\", \"value\":\"k\"}]}, {\"size\":2, \"items\":[{\"key\":\"m\", \"value\":\"m\"}, {\"key\":\"n\", \"value\":\"n\"}]}, {\"size\":2, \"items\":[{\"key\":\"p\", \"value\":\"p\"}, {\"key\":\"q\", \"value\":\"q\"}]}]}, {\"size\":2, \"items\":[{\"key\":\"u\", \"value\":\"u\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"s\", \"value\":\"s\"}, {\"key\":\"t\", \"value\":\"t\"}]}, {\"size\":2, \"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"w\", \"value\":\"w\"}]}, {\"size\":2, \"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void testPut3() {
        BTree<String, String> tree = createTree("abcdefghijklmnopqrstuvwxyz", 3);
        String expected = "{\"size\":2, \"items\":[{\"key\":\"h\", \"value\":\"h\"}, {\"key\":\"p\", \"value\":\"p\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"d\", \"value\":\"d\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"b\", \"value\":\"b\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"a\", \"value\":\"a\"}]}, {\"size\":1, \"items\":[{\"key\":\"c\", \"value\":\"c\"}]}]}, {\"size\":1, \"items\":[{\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"e\", \"value\":\"e\"}]}, {\"size\":1, \"items\":[{\"key\":\"g\", \"value\":\"g\"}]}]}]}, {\"size\":1, \"items\":[{\"key\":\"l\", \"value\":\"l\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"j\", \"value\":\"j\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"i\", \"value\":\"i\"}]}, {\"size\":1, \"items\":[{\"key\":\"k\", \"value\":\"k\"}]}]}, {\"size\":1, \"items\":[{\"key\":\"n\", \"value\":\"n\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"m\", \"value\":\"m\"}]}, {\"size\":1, \"items\":[{\"key\":\"o\", \"value\":\"o\"}]}]}]}, {\"size\":1, \"items\":[{\"key\":\"t\", \"value\":\"t\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"q\", \"value\":\"q\"}]}, {\"size\":1, \"items\":[{\"key\":\"s\", \"value\":\"s\"}]}]}, {\"size\":2, \"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"u\", \"value\":\"u\"}]}, {\"size\":1, \"items\":[{\"key\":\"w\", \"value\":\"w\"}]}, {\"size\":2, \"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void get() {
        BTree<String, String> tree = createTree("wabzlocudefhijkmngqstpvxry", 6);
        Assert.assertEquals("a", tree.get("a"));
        Assert.assertNull(tree.get("ab"));
    }

    @Test
    public void remove() {
        BTree<String, String> tree = createTree("abcdefghijklmnopqrstuvwxyz", 3);
        // test merge
        Assert.assertEquals("m", tree.remove("m"));
        String expected = "{\"size\":1, \"items\":[{\"key\":\"p\", \"value\":\"p\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"h\", \"value\":\"h\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"b\", \"value\":\"b\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"a\", \"value\":\"a\"}]}, {\"size\":1, \"items\":[{\"key\":\"c\", \"value\":\"c\"}]}]}, {\"size\":1, \"items\":[{\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"e\", \"value\":\"e\"}]}, {\"size\":1, \"items\":[{\"key\":\"g\", \"value\":\"g\"}]}]}, {\"size\":2, \"items\":[{\"key\":\"j\", \"value\":\"j\"}, {\"key\":\"l\", \"value\":\"l\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"i\", \"value\":\"i\"}]}, {\"size\":1, \"items\":[{\"key\":\"k\", \"value\":\"k\"}]}, {\"size\":2, \"items\":[{\"key\":\"n\", \"value\":\"n\"}, {\"key\":\"o\", \"value\":\"o\"}]}]}]}, {\"size\":1, \"items\":[{\"key\":\"t\", \"value\":\"t\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"q\", \"value\":\"q\"}]}, {\"size\":1, \"items\":[{\"key\":\"s\", \"value\":\"s\"}]}]}, {\"size\":2, \"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"u\", \"value\":\"u\"}]}, {\"size\":1, \"items\":[{\"key\":\"w\", \"value\":\"w\"}]}, {\"size\":2, \"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
        // test rotateLeft
        Assert.assertEquals("h", tree.remove("h"));
        expected = "{\"size\":1, \"items\":[{\"key\":\"p\", \"value\":\"p\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"j\", \"value\":\"j\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"b\", \"value\":\"b\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"a\", \"value\":\"a\"}]}, {\"size\":1, \"items\":[{\"key\":\"c\", \"value\":\"c\"}]}]}, {\"size\":1, \"items\":[{\"key\":\"g\", \"value\":\"g\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"e\", \"value\":\"e\"}, {\"key\":\"f\", \"value\":\"f\"}]}, {\"size\":1, \"items\":[{\"key\":\"i\", \"value\":\"i\"}]}]}, {\"size\":1, \"items\":[{\"key\":\"l\", \"value\":\"l\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"k\", \"value\":\"k\"}]}, {\"size\":2, \"items\":[{\"key\":\"n\", \"value\":\"n\"}, {\"key\":\"o\", \"value\":\"o\"}]}]}]}, {\"size\":1, \"items\":[{\"key\":\"t\", \"value\":\"t\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"q\", \"value\":\"q\"}]}, {\"size\":1, \"items\":[{\"key\":\"s\", \"value\":\"s\"}]}]}, {\"size\":2, \"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"size\":1, \"items\":[{\"key\":\"u\", \"value\":\"u\"}]}, {\"size\":1, \"items\":[{\"key\":\"w\", \"value\":\"w\"}]}, {\"size\":2, \"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void testRemove() {
        BTree<String, String> tree = createTree("abcdefghijklmnopqrstuvwxyz", 5);
        Assert.assertEquals("m", tree.remove("m"));
        String expected = "{\"size\":1, \"items\":[{\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"size\":4, \"items\":[{\"key\":\"c\", \"value\":\"c\"}, {\"key\":\"f\", \"value\":\"f\"}, {\"key\":\"i\", \"value\":\"i\"}, {\"key\":\"o\", \"value\":\"o\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"a\", \"value\":\"a\"}, {\"key\":\"b\", \"value\":\"b\"}]}, {\"size\":2, \"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"e\", \"value\":\"e\"}]}, {\"size\":2, \"items\":[{\"key\":\"g\", \"value\":\"g\"}, {\"key\":\"h\", \"value\":\"h\"}]}, {\"size\":4, \"items\":[{\"key\":\"j\", \"value\":\"j\"}, {\"key\":\"k\", \"value\":\"k\"}, {\"key\":\"l\", \"value\":\"l\"}, {\"key\":\"n\", \"value\":\"n\"}]}, {\"size\":2, \"items\":[{\"key\":\"p\", \"value\":\"p\"}, {\"key\":\"q\", \"value\":\"q\"}]}]}, {\"size\":2, \"items\":[{\"key\":\"u\", \"value\":\"u\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"s\", \"value\":\"s\"}, {\"key\":\"t\", \"value\":\"t\"}]}, {\"size\":2, \"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"w\", \"value\":\"w\"}]}, {\"size\":2, \"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals("h", tree.remove("h"));
        expected = "{\"size\":1, \"items\":[{\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"size\":4, \"items\":[{\"key\":\"c\", \"value\":\"c\"}, {\"key\":\"f\", \"value\":\"f\"}, {\"key\":\"j\", \"value\":\"j\"}, {\"key\":\"o\", \"value\":\"o\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"a\", \"value\":\"a\"}, {\"key\":\"b\", \"value\":\"b\"}]}, {\"size\":2, \"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"e\", \"value\":\"e\"}]}, {\"size\":2, \"items\":[{\"key\":\"g\", \"value\":\"g\"}, {\"key\":\"i\", \"value\":\"i\"}]}, {\"size\":3, \"items\":[{\"key\":\"k\", \"value\":\"k\"}, {\"key\":\"l\", \"value\":\"l\"}, {\"key\":\"n\", \"value\":\"n\"}]}, {\"size\":2, \"items\":[{\"key\":\"p\", \"value\":\"p\"}, {\"key\":\"q\", \"value\":\"q\"}]}]}, {\"size\":2, \"items\":[{\"key\":\"u\", \"value\":\"u\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"s\", \"value\":\"s\"}, {\"key\":\"t\", \"value\":\"t\"}]}, {\"size\":2, \"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"w\", \"value\":\"w\"}]}, {\"size\":2, \"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void size() {
        BTree<String, String> tree = createTree("wabzlocudefhijkmngqstpvxry", 6);
        Assert.assertEquals(26, tree.size());
    }

    @Test
    public void height() {
        BTree<String, String> tree = createTree("wabzlocudefhijkmngqstpvxry", 5);
        Assert.assertEquals(3, tree.height());
    }

    @Test
    public void testHeight() {
        BTree<String, String> tree = createTree("abcdef", 5);

        String expected = "{\"size\":1, \"items\":[{\"key\":\"c\", \"value\":\"c\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"a\", \"value\":\"a\"}, {\"key\":\"b\", \"value\":\"b\"}]}, {\"size\":3, \"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"e\", \"value\":\"e\"}, {\"key\":\"f\", \"value\":\"f\"}]}]}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(2, tree.height());

        tree.remove("f");
        expected = "{\"size\":1, \"items\":[{\"key\":\"c\", \"value\":\"c\"}], \"children\":[{\"size\":2, \"items\":[{\"key\":\"a\", \"value\":\"a\"}, {\"key\":\"b\", \"value\":\"b\"}]}, {\"size\":2, \"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"e\", \"value\":\"e\"}]}]}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(2, tree.height());

        tree.remove("e");
        expected = "{\"size\":4, \"items\":[{\"key\":\"a\", \"value\":\"a\"}, {\"key\":\"b\", \"value\":\"b\"}, {\"key\":\"c\", \"value\":\"c\"}, {\"key\":\"d\", \"value\":\"d\"}]}";
        Assert.assertEquals(expected, tree.toString());
        Assert.assertEquals(1, tree.height());
    }

    @Test
    public void isEmpty() {
        BTree<String, String> tree = createTree("wabzlocudefhijkmngqstpvxry", 6);
        Assert.assertFalse(tree.isEmpty());
        tree.clear();
        Assert.assertTrue(tree.isEmpty());
    }

    @Test
    @Ignore
    public void performance() {
        Set<String> keys = createKeys(10000000);

        long t1 = System.currentTimeMillis();
        BTree<String, String> tree = new BTree<>(32);
        for (String key : keys) {
            tree.put(key, key);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("tree-put:\t" + (t2 - t1));

        TreeMap<String, String> map = new TreeMap<>();
        for (String key : keys) {
            map.put(key, key);
        }
        long t3 = System.currentTimeMillis();
        System.out.println("map-put:\t" + (t3 - t2));

        for (String k : keys) {
            tree.get(k);
        }
        long t4 = System.currentTimeMillis();
        System.out.println("tree-get:\t" + (t4 - t3));

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

    private BTree<String, String> createTree(String text, int order) {
        char[] s = text.toCharArray();
        BTree<String, String> tree = new BTree<>(order);
        for (char c : s) {
            String key = String.valueOf(c);
            tree.put(key, key);
        }
        return tree;
    }
}