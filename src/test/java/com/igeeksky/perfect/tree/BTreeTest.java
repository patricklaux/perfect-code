package com.igeeksky.perfect.tree;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Patrick.Lau
 * @since 1.0.0 2022-01-14
 */
public class BTreeTest {

    @Test
    public void put() {
        BTree<String, String> tree = createTree("abcdefghijklmnopqrstuvwxyz", 4);
        String expected = "{\"items\":[{\"key\":\"h\", \"value\":\"h\"}], \"children\":[{\"items\":[{\"key\":\"d\", \"value\":\"d\"}], \"children\":[{\"items\":[{\"key\":\"b\", \"value\":\"b\"}], \"children\":[{\"items\":[{\"key\":\"a\", \"value\":\"a\"}]}, {\"items\":[{\"key\":\"c\", \"value\":\"c\"}]}]}, {\"items\":[{\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"items\":[{\"key\":\"e\", \"value\":\"e\"}]}, {\"items\":[{\"key\":\"g\", \"value\":\"g\"}]}]}]}, {\"items\":[{\"key\":\"l\", \"value\":\"l\"}, {\"key\":\"p\", \"value\":\"p\"}, {\"key\":\"t\", \"value\":\"t\"}], \"children\":[{\"items\":[{\"key\":\"j\", \"value\":\"j\"}], \"children\":[{\"items\":[{\"key\":\"i\", \"value\":\"i\"}]}, {\"items\":[{\"key\":\"k\", \"value\":\"k\"}]}]}, {\"items\":[{\"key\":\"n\", \"value\":\"n\"}], \"children\":[{\"items\":[{\"key\":\"m\", \"value\":\"m\"}]}, {\"items\":[{\"key\":\"o\", \"value\":\"o\"}]}]}, {\"items\":[{\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"items\":[{\"key\":\"q\", \"value\":\"q\"}]}, {\"items\":[{\"key\":\"s\", \"value\":\"s\"}]}]}, {\"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"items\":[{\"key\":\"u\", \"value\":\"u\"}]}, {\"items\":[{\"key\":\"w\", \"value\":\"w\"}]}, {\"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void testPut() {
        BTree<String, String> tree = createTree("wabzlocudefhijkmngqstpvxry", 4);
        String expected = "{\"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"i\", \"value\":\"i\"}, {\"key\":\"o\", \"value\":\"o\"}], \"children\":[{\"items\":[{\"key\":\"b\", \"value\":\"b\"}], \"children\":[{\"items\":[{\"key\":\"a\", \"value\":\"a\"}]}, {\"items\":[{\"key\":\"c\", \"value\":\"c\"}]}]}, {\"items\":[{\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"items\":[{\"key\":\"e\", \"value\":\"e\"}]}, {\"items\":[{\"key\":\"g\", \"value\":\"g\"}, {\"key\":\"h\", \"value\":\"h\"}]}]}, {\"items\":[{\"key\":\"k\", \"value\":\"k\"}], \"children\":[{\"items\":[{\"key\":\"j\", \"value\":\"j\"}]}, {\"items\":[{\"key\":\"l\", \"value\":\"l\"}, {\"key\":\"m\", \"value\":\"m\"}, {\"key\":\"n\", \"value\":\"n\"}]}]}, {\"items\":[{\"key\":\"q\", \"value\":\"q\"}, {\"key\":\"u\", \"value\":\"u\"}, {\"key\":\"w\", \"value\":\"w\"}], \"children\":[{\"items\":[{\"key\":\"p\", \"value\":\"p\"}]}, {\"items\":[{\"key\":\"r\", \"value\":\"r\"}, {\"key\":\"s\", \"value\":\"s\"}, {\"key\":\"t\", \"value\":\"t\"}]}, {\"items\":[{\"key\":\"v\", \"value\":\"v\"}]}, {\"items\":[{\"key\":\"x\", \"value\":\"x\"}, {\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void testPut1() {
        BTree<String, String> tree = createTree("wabzlocudefhijkmngqstpvxry", 6);
        String expected = "{\"items\":[{\"key\":\"l\", \"value\":\"l\"}], \"children\":[{\"items\":[{\"key\":\"c\", \"value\":\"c\"}, {\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"items\":[{\"key\":\"a\", \"value\":\"a\"}, {\"key\":\"b\", \"value\":\"b\"}]}, {\"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"e\", \"value\":\"e\"}]}, {\"items\":[{\"key\":\"g\", \"value\":\"g\"}, {\"key\":\"h\", \"value\":\"h\"}, {\"key\":\"i\", \"value\":\"i\"}, {\"key\":\"j\", \"value\":\"j\"}, {\"key\":\"k\", \"value\":\"k\"}]}]}, {\"items\":[{\"key\":\"o\", \"value\":\"o\"}, {\"key\":\"t\", \"value\":\"t\"}, {\"key\":\"w\", \"value\":\"w\"}], \"children\":[{\"items\":[{\"key\":\"m\", \"value\":\"m\"}, {\"key\":\"n\", \"value\":\"n\"}]}, {\"items\":[{\"key\":\"p\", \"value\":\"p\"}, {\"key\":\"q\", \"value\":\"q\"}, {\"key\":\"r\", \"value\":\"r\"}, {\"key\":\"s\", \"value\":\"s\"}]}, {\"items\":[{\"key\":\"u\", \"value\":\"u\"}, {\"key\":\"v\", \"value\":\"v\"}]}, {\"items\":[{\"key\":\"x\", \"value\":\"x\"}, {\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void testPut2() {
        BTree<String, String> tree = createTree("abcdefghijklmnopqrstuvwxyz", 5);
        String expected = "{\"items\":[{\"key\":\"i\", \"value\":\"i\"}, {\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"items\":[{\"key\":\"c\", \"value\":\"c\"}, {\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"items\":[{\"key\":\"a\", \"value\":\"a\"}, {\"key\":\"b\", \"value\":\"b\"}]}, {\"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"e\", \"value\":\"e\"}]}, {\"items\":[{\"key\":\"g\", \"value\":\"g\"}, {\"key\":\"h\", \"value\":\"h\"}]}]}, {\"items\":[{\"key\":\"l\", \"value\":\"l\"}, {\"key\":\"o\", \"value\":\"o\"}], \"children\":[{\"items\":[{\"key\":\"j\", \"value\":\"j\"}, {\"key\":\"k\", \"value\":\"k\"}]}, {\"items\":[{\"key\":\"m\", \"value\":\"m\"}, {\"key\":\"n\", \"value\":\"n\"}]}, {\"items\":[{\"key\":\"p\", \"value\":\"p\"}, {\"key\":\"q\", \"value\":\"q\"}]}]}, {\"items\":[{\"key\":\"u\", \"value\":\"u\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"items\":[{\"key\":\"s\", \"value\":\"s\"}, {\"key\":\"t\", \"value\":\"t\"}]}, {\"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"w\", \"value\":\"w\"}]}, {\"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void testPut3() {
        BTree<String, String> tree = createTree("abcdefghijklmnopqrstuvwxyz", 3);
        String expected = "{\"items\":[{\"key\":\"h\", \"value\":\"h\"}, {\"key\":\"p\", \"value\":\"p\"}], \"children\":[{\"items\":[{\"key\":\"d\", \"value\":\"d\"}], \"children\":[{\"items\":[{\"key\":\"b\", \"value\":\"b\"}], \"children\":[{\"items\":[{\"key\":\"a\", \"value\":\"a\"}]}, {\"items\":[{\"key\":\"c\", \"value\":\"c\"}]}]}, {\"items\":[{\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"items\":[{\"key\":\"e\", \"value\":\"e\"}]}, {\"items\":[{\"key\":\"g\", \"value\":\"g\"}]}]}]}, {\"items\":[{\"key\":\"l\", \"value\":\"l\"}], \"children\":[{\"items\":[{\"key\":\"j\", \"value\":\"j\"}], \"children\":[{\"items\":[{\"key\":\"i\", \"value\":\"i\"}]}, {\"items\":[{\"key\":\"k\", \"value\":\"k\"}]}]}, {\"items\":[{\"key\":\"n\", \"value\":\"n\"}], \"children\":[{\"items\":[{\"key\":\"m\", \"value\":\"m\"}]}, {\"items\":[{\"key\":\"o\", \"value\":\"o\"}]}]}]}, {\"items\":[{\"key\":\"t\", \"value\":\"t\"}], \"children\":[{\"items\":[{\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"items\":[{\"key\":\"q\", \"value\":\"q\"}]}, {\"items\":[{\"key\":\"s\", \"value\":\"s\"}]}]}, {\"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"items\":[{\"key\":\"u\", \"value\":\"u\"}]}, {\"items\":[{\"key\":\"w\", \"value\":\"w\"}]}, {\"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}]}";
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
        String expected = "{\"items\":[{\"key\":\"p\", \"value\":\"p\"}], \"children\":[{\"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"h\", \"value\":\"h\"}], \"children\":[{\"items\":[{\"key\":\"b\", \"value\":\"b\"}], \"children\":[{\"items\":[{\"key\":\"a\", \"value\":\"a\"}]}, {\"items\":[{\"key\":\"c\", \"value\":\"c\"}]}]}, {\"items\":[{\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"items\":[{\"key\":\"e\", \"value\":\"e\"}]}, {\"items\":[{\"key\":\"g\", \"value\":\"g\"}]}]}, {\"items\":[{\"key\":\"j\", \"value\":\"j\"}, {\"key\":\"l\", \"value\":\"l\"}], \"children\":[{\"items\":[{\"key\":\"i\", \"value\":\"i\"}]}, {\"items\":[{\"key\":\"k\", \"value\":\"k\"}]}, {\"items\":[{\"key\":\"n\", \"value\":\"n\"}, {\"key\":\"o\", \"value\":\"o\"}]}]}]}, {\"items\":[{\"key\":\"t\", \"value\":\"t\"}], \"children\":[{\"items\":[{\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"items\":[{\"key\":\"q\", \"value\":\"q\"}]}, {\"items\":[{\"key\":\"s\", \"value\":\"s\"}]}]}, {\"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"items\":[{\"key\":\"u\", \"value\":\"u\"}]}, {\"items\":[{\"key\":\"w\", \"value\":\"w\"}]}, {\"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
        // test rotateLeft
        Assert.assertEquals("h", tree.remove("h"));
        expected = "{\"items\":[{\"key\":\"p\", \"value\":\"p\"}], \"children\":[{\"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"j\", \"value\":\"j\"}], \"children\":[{\"items\":[{\"key\":\"b\", \"value\":\"b\"}], \"children\":[{\"items\":[{\"key\":\"a\", \"value\":\"a\"}]}, {\"items\":[{\"key\":\"c\", \"value\":\"c\"}]}]}, {\"items\":[{\"key\":\"g\", \"value\":\"g\"}], \"children\":[{\"items\":[{\"key\":\"e\", \"value\":\"e\"}, {\"key\":\"f\", \"value\":\"f\"}]}, {\"items\":[{\"key\":\"i\", \"value\":\"i\"}]}]}, {\"items\":[{\"key\":\"l\", \"value\":\"l\"}], \"children\":[{\"items\":[{\"key\":\"k\", \"value\":\"k\"}]}, {\"items\":[{\"key\":\"n\", \"value\":\"n\"}, {\"key\":\"o\", \"value\":\"o\"}]}]}]}, {\"items\":[{\"key\":\"t\", \"value\":\"t\"}], \"children\":[{\"items\":[{\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"items\":[{\"key\":\"q\", \"value\":\"q\"}]}, {\"items\":[{\"key\":\"s\", \"value\":\"s\"}]}]}, {\"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"items\":[{\"key\":\"u\", \"value\":\"u\"}]}, {\"items\":[{\"key\":\"w\", \"value\":\"w\"}]}, {\"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void testRemove() {
        BTree<String, String> tree = createTree("abcdefghijklmnopqrstuvwxyz", 3);
        Assert.assertEquals("m", tree.remove("m"));
        String expected = "{\"items\":[{\"key\":\"p\", \"value\":\"p\"}], \"children\":[{\"items\":[{\"key\":\"d\", \"value\":\"d\"}, {\"key\":\"h\", \"value\":\"h\"}], \"children\":[{\"items\":[{\"key\":\"b\", \"value\":\"b\"}], \"children\":[{\"items\":[{\"key\":\"a\", \"value\":\"a\"}]}, {\"items\":[{\"key\":\"c\", \"value\":\"c\"}]}]}, {\"items\":[{\"key\":\"f\", \"value\":\"f\"}], \"children\":[{\"items\":[{\"key\":\"e\", \"value\":\"e\"}]}, {\"items\":[{\"key\":\"g\", \"value\":\"g\"}]}]}, {\"items\":[{\"key\":\"j\", \"value\":\"j\"}, {\"key\":\"l\", \"value\":\"l\"}], \"children\":[{\"items\":[{\"key\":\"i\", \"value\":\"i\"}]}, {\"items\":[{\"key\":\"k\", \"value\":\"k\"}]}, {\"items\":[{\"key\":\"n\", \"value\":\"n\"}, {\"key\":\"o\", \"value\":\"o\"}]}]}]}, {\"items\":[{\"key\":\"t\", \"value\":\"t\"}], \"children\":[{\"items\":[{\"key\":\"r\", \"value\":\"r\"}], \"children\":[{\"items\":[{\"key\":\"q\", \"value\":\"q\"}]}, {\"items\":[{\"key\":\"s\", \"value\":\"s\"}]}]}, {\"items\":[{\"key\":\"v\", \"value\":\"v\"}, {\"key\":\"x\", \"value\":\"x\"}], \"children\":[{\"items\":[{\"key\":\"u\", \"value\":\"u\"}]}, {\"items\":[{\"key\":\"w\", \"value\":\"w\"}]}, {\"items\":[{\"key\":\"y\", \"value\":\"y\"}, {\"key\":\"z\", \"value\":\"z\"}]}]}]}]}";
        Assert.assertEquals(expected, tree.toString());
    }

    @Test
    public void size() {
        BTree<String, String> tree = createTree("wabzlocudefhijkmngqstpvxry", 6);
        Assert.assertEquals(26, tree.size());
    }

    @Test
    public void isEmpty() {
        BTree<String, String> tree = createTree("wabzlocudefhijkmngqstpvxry", 6);
        Assert.assertFalse(tree.isEmpty());
        tree.clear();
        Assert.assertTrue(tree.isEmpty());
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