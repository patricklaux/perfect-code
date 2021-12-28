package com.igeeksky.perfect.tree;

import com.igeeksky.xtool.core.function.tuple.Tuple2;
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

        String expected = "{\"height\":4, \"key\":\"p\", \"value\":\"p\", \"left\":{\"height\":3, \"key\":\"h\", \"value\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"value\":\"d\", \"left\":{\"height\":1, \"key\":\"b\", \"value\":\"b\", \"left\":{\"height\":0, \"key\":\"a\", \"value\":\"a\"}, \"right\":{\"height\":0, \"key\":\"c\", \"value\":\"c\"}}, \"right\":{\"height\":1, \"key\":\"f\", \"value\":\"f\", \"left\":{\"height\":0, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"l\", \"value\":\"l\", \"left\":{\"height\":1, \"key\":\"j\", \"value\":\"j\", \"left\":{\"height\":0, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"height\":0, \"key\":\"k\", \"value\":\"k\"}}, \"right\":{\"height\":1, \"key\":\"n\", \"value\":\"n\", \"left\":{\"height\":0, \"key\":\"m\", \"value\":\"m\"}, \"right\":{\"height\":0, \"key\":\"o\", \"value\":\"o\"}}}}, \"right\":{\"height\":3, \"key\":\"t\", \"value\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"value\":\"r\", \"left\":{\"height\":0, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"value\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"value\":\"v\", \"left\":{\"height\":0, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\", \"value\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"value\":\"y\", \"right\":{\"height\":0, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(26, avl.size());

        avl.remove("a");
        expected = "{\"height\":4, \"key\":\"p\", \"value\":\"p\", \"left\":{\"height\":3, \"key\":\"h\", \"value\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"value\":\"d\", \"left\":{\"height\":1, \"key\":\"b\", \"value\":\"b\", \"right\":{\"height\":0, \"key\":\"c\", \"value\":\"c\"}}, \"right\":{\"height\":1, \"key\":\"f\", \"value\":\"f\", \"left\":{\"height\":0, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"l\", \"value\":\"l\", \"left\":{\"height\":1, \"key\":\"j\", \"value\":\"j\", \"left\":{\"height\":0, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"height\":0, \"key\":\"k\", \"value\":\"k\"}}, \"right\":{\"height\":1, \"key\":\"n\", \"value\":\"n\", \"left\":{\"height\":0, \"key\":\"m\", \"value\":\"m\"}, \"right\":{\"height\":0, \"key\":\"o\", \"value\":\"o\"}}}}, \"right\":{\"height\":3, \"key\":\"t\", \"value\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"value\":\"r\", \"left\":{\"height\":0, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"value\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"value\":\"v\", \"left\":{\"height\":0, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\", \"value\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"value\":\"y\", \"right\":{\"height\":0, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(25, avl.size());

        avl.remove("p");
        expected = "{\"height\":4, \"key\":\"o\", \"value\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"value\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"value\":\"d\", \"left\":{\"height\":1, \"key\":\"b\", \"value\":\"b\", \"right\":{\"height\":0, \"key\":\"c\", \"value\":\"c\"}}, \"right\":{\"height\":1, \"key\":\"f\", \"value\":\"f\", \"left\":{\"height\":0, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"l\", \"value\":\"l\", \"left\":{\"height\":1, \"key\":\"j\", \"value\":\"j\", \"left\":{\"height\":0, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"height\":0, \"key\":\"k\", \"value\":\"k\"}}, \"right\":{\"height\":1, \"key\":\"n\", \"value\":\"n\", \"left\":{\"height\":0, \"key\":\"m\", \"value\":\"m\"}}}}, \"right\":{\"height\":3, \"key\":\"t\", \"value\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"value\":\"r\", \"left\":{\"height\":0, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"value\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"value\":\"v\", \"left\":{\"height\":0, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\", \"value\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"value\":\"y\", \"right\":{\"height\":0, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(24, avl.size());

        avl.remove("n");
        expected = "{\"height\":4, \"key\":\"o\", \"value\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"value\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"value\":\"d\", \"left\":{\"height\":1, \"key\":\"b\", \"value\":\"b\", \"right\":{\"height\":0, \"key\":\"c\", \"value\":\"c\"}}, \"right\":{\"height\":1, \"key\":\"f\", \"value\":\"f\", \"left\":{\"height\":0, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"l\", \"value\":\"l\", \"left\":{\"height\":1, \"key\":\"j\", \"value\":\"j\", \"left\":{\"height\":0, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"height\":0, \"key\":\"k\", \"value\":\"k\"}}, \"right\":{\"height\":0, \"key\":\"m\", \"value\":\"m\"}}}, \"right\":{\"height\":3, \"key\":\"t\", \"value\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"value\":\"r\", \"left\":{\"height\":0, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"value\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"value\":\"v\", \"left\":{\"height\":0, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\", \"value\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"value\":\"y\", \"right\":{\"height\":0, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(23, avl.size());

        avl.remove("m");
        expected = "{\"height\":4, \"key\":\"o\", \"value\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"value\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"value\":\"d\", \"left\":{\"height\":1, \"key\":\"b\", \"value\":\"b\", \"right\":{\"height\":0, \"key\":\"c\", \"value\":\"c\"}}, \"right\":{\"height\":1, \"key\":\"f\", \"value\":\"f\", \"left\":{\"height\":0, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"j\", \"value\":\"j\", \"left\":{\"height\":0, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"height\":1, \"key\":\"l\", \"value\":\"l\", \"left\":{\"height\":0, \"key\":\"k\", \"value\":\"k\"}}}}, \"right\":{\"height\":3, \"key\":\"t\", \"value\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"value\":\"r\", \"left\":{\"height\":0, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"value\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"value\":\"v\", \"left\":{\"height\":0, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\", \"value\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"value\":\"y\", \"right\":{\"height\":0, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(22, avl.size());

        avl.remove("c");
        expected = "{\"height\":4, \"key\":\"o\", \"value\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"value\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"value\":\"d\", \"left\":{\"height\":0, \"key\":\"b\", \"value\":\"b\"}, \"right\":{\"height\":1, \"key\":\"f\", \"value\":\"f\", \"left\":{\"height\":0, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"j\", \"value\":\"j\", \"left\":{\"height\":0, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"height\":1, \"key\":\"l\", \"value\":\"l\", \"left\":{\"height\":0, \"key\":\"k\", \"value\":\"k\"}}}}, \"right\":{\"height\":3, \"key\":\"t\", \"value\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"value\":\"r\", \"left\":{\"height\":0, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"value\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"value\":\"v\", \"left\":{\"height\":0, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\", \"value\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"value\":\"y\", \"right\":{\"height\":0, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(21, avl.size());

        // 重复删除
        avl.remove("c");
        expected = "{\"height\":4, \"key\":\"o\", \"value\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"value\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"value\":\"d\", \"left\":{\"height\":0, \"key\":\"b\", \"value\":\"b\"}, \"right\":{\"height\":1, \"key\":\"f\", \"value\":\"f\", \"left\":{\"height\":0, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"height\":2, \"key\":\"j\", \"value\":\"j\", \"left\":{\"height\":0, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"height\":1, \"key\":\"l\", \"value\":\"l\", \"left\":{\"height\":0, \"key\":\"k\", \"value\":\"k\"}}}}, \"right\":{\"height\":3, \"key\":\"t\", \"value\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"value\":\"r\", \"left\":{\"height\":0, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"value\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"value\":\"v\", \"left\":{\"height\":0, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\", \"value\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"value\":\"y\", \"right\":{\"height\":0, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(21, avl.size());

        avl.remove("j");
        expected = "{\"height\":4, \"key\":\"o\", \"value\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"value\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"value\":\"d\", \"left\":{\"height\":0, \"key\":\"b\", \"value\":\"b\"}, \"right\":{\"height\":1, \"key\":\"f\", \"value\":\"f\", \"left\":{\"height\":0, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"height\":1, \"key\":\"k\", \"value\":\"k\", \"left\":{\"height\":0, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"height\":0, \"key\":\"l\", \"value\":\"l\"}}}, \"right\":{\"height\":3, \"key\":\"t\", \"value\":\"t\", \"left\":{\"height\":1, \"key\":\"r\", \"value\":\"r\", \"left\":{\"height\":0, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"value\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"value\":\"v\", \"left\":{\"height\":0, \"key\":\"u\", \"value\":\"u\"}, \"right\":{\"height\":0, \"key\":\"w\", \"value\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"value\":\"y\", \"right\":{\"height\":0, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(20, avl.size());

        avl.remove("t");
        expected = "{\"height\":4, \"key\":\"o\", \"value\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"value\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"value\":\"d\", \"left\":{\"height\":0, \"key\":\"b\", \"value\":\"b\"}, \"right\":{\"height\":1, \"key\":\"f\", \"value\":\"f\", \"left\":{\"height\":0, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"height\":1, \"key\":\"k\", \"value\":\"k\", \"left\":{\"height\":0, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"height\":0, \"key\":\"l\", \"value\":\"l\"}}}, \"right\":{\"height\":3, \"key\":\"u\", \"value\":\"u\", \"left\":{\"height\":1, \"key\":\"r\", \"value\":\"r\", \"left\":{\"height\":0, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"value\":\"x\", \"left\":{\"height\":1, \"key\":\"v\", \"value\":\"v\", \"right\":{\"height\":0, \"key\":\"w\", \"value\":\"w\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"value\":\"y\", \"right\":{\"height\":0, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(19, avl.size());

        avl.remove("u");
        expected = "{\"height\":4, \"key\":\"o\", \"value\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"value\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"value\":\"d\", \"left\":{\"height\":0, \"key\":\"b\", \"value\":\"b\"}, \"right\":{\"height\":1, \"key\":\"f\", \"value\":\"f\", \"left\":{\"height\":0, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"height\":1, \"key\":\"k\", \"value\":\"k\", \"left\":{\"height\":0, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"height\":0, \"key\":\"l\", \"value\":\"l\"}}}, \"right\":{\"height\":3, \"key\":\"v\", \"value\":\"v\", \"left\":{\"height\":1, \"key\":\"r\", \"value\":\"r\", \"left\":{\"height\":0, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"height\":2, \"key\":\"x\", \"value\":\"x\", \"left\":{\"height\":0, \"key\":\"w\", \"value\":\"w\"}, \"right\":{\"height\":1, \"key\":\"y\", \"value\":\"y\", \"right\":{\"height\":0, \"key\":\"z\", \"value\":\"z\"}}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(18, avl.size());

        avl.remove("v");
        expected = "{\"height\":4, \"key\":\"o\", \"value\":\"o\", \"left\":{\"height\":3, \"key\":\"h\", \"value\":\"h\", \"left\":{\"height\":2, \"key\":\"d\", \"value\":\"d\", \"left\":{\"height\":0, \"key\":\"b\", \"value\":\"b\"}, \"right\":{\"height\":1, \"key\":\"f\", \"value\":\"f\", \"left\":{\"height\":0, \"key\":\"e\", \"value\":\"e\"}, \"right\":{\"height\":0, \"key\":\"g\", \"value\":\"g\"}}}, \"right\":{\"height\":1, \"key\":\"k\", \"value\":\"k\", \"left\":{\"height\":0, \"key\":\"i\", \"value\":\"i\"}, \"right\":{\"height\":0, \"key\":\"l\", \"value\":\"l\"}}}, \"right\":{\"height\":2, \"key\":\"w\", \"value\":\"w\", \"left\":{\"height\":1, \"key\":\"r\", \"value\":\"r\", \"left\":{\"height\":0, \"key\":\"q\", \"value\":\"q\"}, \"right\":{\"height\":0, \"key\":\"s\", \"value\":\"s\"}}, \"right\":{\"height\":1, \"key\":\"y\", \"value\":\"y\", \"left\":{\"height\":0, \"key\":\"x\", \"value\":\"x\"}, \"right\":{\"height\":0, \"key\":\"z\", \"value\":\"z\"}}}}";
        Assert.assertEquals(expected, avl.getRoot().toString());
        Assert.assertEquals(17, avl.size());
    }

    @Test
    public void preorderTraversalR() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> list = new ArrayList<>(26);
        avl.preorderTraversalR(list);
        checkResults(list, "phdbacfegljiknmotrqsxvuwyz");
    }

    @Test
    public void inorderTraversalR() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> list = new ArrayList<>(26);
        avl.inorderTraversalR(list);
        checkResults(list, "abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    public void postorderTraversalR() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> list = new ArrayList<>(26);
        avl.postorderTraversalR(list);
        checkResults(list, "acbegfdikjmonlhqsruwvzyxtp");
    }

    @Test
    public void preorderTraversal() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> list = new ArrayList<>(26);
        avl.preorderTraversal(list);
        checkResults(list, "phdbacfegljiknmotrqsxvuwyz");
    }

    @Test
    public void inorderTraversal() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> list = new ArrayList<>(26);
        avl.inorderTraversal(list);
        checkResults(list, "abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    public void postOrderTraversal() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> results = new ArrayList<>(26);
        avl.postOrderTraversal(results);
        checkResults(results, "acbegfdikjmonlhqsruwvzyxtp");
    }

    @Test
    public void postOrderTraversal1() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> results = new ArrayList<>(26);
        avl.postOrderTraversal1(results);
        checkResults(results, "acbegfdikjmonlhqsruwvzyxtp");
    }

    @Test
    public void levelOrderTraversal() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> list = new ArrayList<>(26);
        avl.levelOrderTraversal(list);
        checkResults(list, "phtdlrxbfjnqsvyacegikmouwz");
    }

    @Test
    public void morrisPreorderTraversal() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> list = new ArrayList<>(26);
        avl.morrisPreorderTraversal(list);
        checkResults(list, "phdbacfegljiknmotrqsxvuwyz");
    }

    @Test
    public void morrisInorderTraversal() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> list = new ArrayList<>(26);
        avl.morrisInorderTraversal(list);
        checkResults(list, "abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    public void morrisPostorderTraversal() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> results = new ArrayList<>(26);
        avl.morrisPostorderTraversal(results);
        checkResults(results, "acbegfdikjmonlhqsruwvzyxtp");
    }

    @Test
    public void iddfsPlus() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> list = new ArrayList<>(26);
        avl.iddfsPlus(list, false);
        checkResults(list, "phtdlrxbfjnqsvyacegikmouwz");

        list.clear();
        avl.iddfsPlus(list, true);
        checkResults(list, "phdbacfegljiknmotrqsxvuwyz");
    }

    @Test
    public void iddfsPlus2() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> list = new ArrayList<>(26);
        avl.iddfsPlus(list, 4);
        checkResults(list, "phdbacfegljiknmotrqsxvuwyz");
    }

    @Test
    public void iddfsR() {
        AvlTree<String, String> avl = createTree("abcdefghijklmnopqrstuvwxyz");
        List<Tuple2<String, String>> list = new ArrayList<>(26);
        avl.iddfsR(list, 4);
        checkResults(list, "phtdlrxbfjnqsvyacegikmouwz");
    }

    private void checkResults(List<Tuple2<String, String>> list, String abcdefghijklmnopqrstuvwxyz) {
        StringBuilder builder = new StringBuilder(26);
        list.forEach(t -> builder.append(t.getT1()));
        Assert.assertEquals(abcdefghijklmnopqrstuvwxyz, builder.toString());
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