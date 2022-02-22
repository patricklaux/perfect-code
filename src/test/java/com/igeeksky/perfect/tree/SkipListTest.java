package com.igeeksky.perfect.tree;

import com.igeeksky.perfect.KeyGenerator;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

/**
 * @author Patrick.Lau
 * @since 0.0.4 2022-02-21
 */
public class SkipListTest {

    @Test
    public void testGetWithMap() {
        int size = 20;
        List<String> keys = new ArrayList<>(size);
        keys.addAll(KeyGenerator.createKeys(size));

        SkipList<String, String> tree = new SkipList<>();
        Assert.assertTrue(tree.isEmpty());

        Map<String, String> map = new HashMap<>();

        for (String key : keys) {
            tree.put(key, key);
            map.put(key, key);
        }

        System.out.println(tree+"\n\n");
        Assert.assertEquals(map.size(), tree.size());
        Assert.assertFalse(tree.isEmpty());

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

        System.out.println(tree+"\n\n");
        Assert.assertEquals(map.size(), tree.size());
        for (String k : keys) {
            Assert.assertEquals(map.get(k), tree.get(k));
        }

        tree.clear();
        System.out.println(tree+"\n\n");
        Assert.assertTrue(tree.isEmpty());
    }

    @Test
    @Ignore
    public void performance() {
        Set<String> keys = KeyGenerator.createKeys(10000000);
        long t1 = System.currentTimeMillis();

        SkipList<String, String> skipList = new SkipList<>();
        for (String key : keys) {
            skipList.put(key, key);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("skip-put:\t" + (t2 - t1));

        TreeMap<String, String> map = new TreeMap<>();
        for (String key : keys) {
            map.put(key, key);
        }
        long t3 = System.currentTimeMillis();
        System.out.println("map-put:\t" + (t3 - t2));

        for (String k : keys) {
            skipList.get(k);
        }
        long t4 = System.currentTimeMillis();
        System.out.println("skip-get:\t" + (t4 - t3));

        for (String k : keys) {
            map.get(k);
        }
        long t5 = System.currentTimeMillis();
        System.out.println("map-get:\t" + (t5 - t4));
    }
}