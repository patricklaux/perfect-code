package com.igeeksky.perfect.tree;

import com.igeeksky.perfect.api.BaseMap;
import com.igeeksky.xtool.core.lang.Assert;
import com.igeeksky.xtool.core.math.IntegerValue;

import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

/**
 * @author Patrick.Lau
 * @since 0.0.4 2022-02-21
 */
@SuppressWarnings("unchecked")
public class SkipList<K extends Comparable<K>, V> implements BaseMap<K, V> {

    private static final int MAX_LEVEL = 32;
    private static final float P = 1F / 4;

    private final IntegerValue size = new IntegerValue();
    private final Random RANDOM = new Random();
    private int height = 1;

    private final Node<K, V> header;

    public SkipList() {
        header = new Node<>(null, null, MAX_LEVEL);
    }

    @Override
    public void put(K key, V value) {
        Assert.notNull(key);
        Assert.notNull(value);
        Node<K, V>[] update = new Node[height];
        int h = height - 1;
        Node<K, V> x = header;
        while (h >= 0) {
            update[h] = x;
            Node<K, V> f = x.forward[h];
            if (f == null) {
                h--;
                continue;
            }
            int cmp = compare(f.key, key);
            if (cmp > 0) {
                h--;
            } else if (cmp < 0) {
                x = f;
            } else {
                f.val = value;
                return;
            }
        }
        size.increment();
        insert(update, key, value);
    }

    private void insert(Node<K, V>[] update, K key, V value) {
        int level = randomLevel();
        Node<K, V> n = new Node<>(key, value, level);
        if (level > height) {
            height = level;
            header.forward[--level] = n;
        }
        for (int l = level - 1; l >= 0; l--) {
            Node<K, V> prev = update[l];
            n.forward[l] = prev.forward[l];
            prev.forward[l] = n;
        }
    }

    @Override
    public V get(K key) {
        Assert.notNull(key);
        int h = height - 1;
        Node<K, V> x = header;
        while (h >= 0) {
            Node<K, V> f = x.forward[h];
            if (f == null) {
                h--;
                continue;
            }
            int cmp = compare(f.key, key);
            if (cmp > 0) {
                h--;
            } else if (cmp < 0) {
                x = f;
            } else {
                return f.val;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        Assert.notNull(key);
        int h = height - 1;
        Node<K, V> x = header;
        V oldVal = null;
        while (h >= 0) {
            Node<K, V> f = x.forward[h];
            if (f == null) {
                h--;
                continue;
            }
            int cmp = compare(f.key, key);
            if (cmp > 0) {
                h--;
            } else if (cmp < 0) {
                x = f;
            } else {
                oldVal = f.val;
                x.forward[h] = f.forward[h];
                if (header == x && x.forward[h] == null) {
                    height--;
                }
                h--;
            }
        }
        if (oldVal != null) {
            size.decrement();
        }
        return oldVal;
    }

    @Override
    public int size() {
        return size.get();
    }

    @Override
    public boolean isEmpty() {
        return size.get() <= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(header.forward, null);
        size.set(0);
        height = 1;
    }

    @Override
    public String toString() {
        System.out.println(height);
        StringJoiner joiner = new StringJoiner("\n\n");
        for (int h = height - 1; h >= 0; h--) {
            Node<K, V> node = header.forward[h];
            StringJoiner subJoiner = new StringJoiner("\t-->\t");
            subJoiner.add("header");
            while (node != null) {
                subJoiner.add(node.key.toString());
                node = node.forward[h];
            }
            subJoiner.add("null");
            joiner.add(subJoiner.toString());
        }
        return joiner.toString();
    }

    private int randomLevel() {
        int level = 1;
        int max = Math.min(height + 1, MAX_LEVEL);
        while (level < max && RANDOM.nextFloat() < P) {
            level++;
        }
        return level;
    }

    private int compare(K k1, K k2) {
        return k1 != null ? k1.compareTo(k2) : -1;
    }

    static class Node<K, V> {
        K key;
        V val;
        Node<K, V>[] forward;

        public Node(K key, V val, int level) {
            this.key = key;
            this.val = val;
            this.forward = new Node[level];
        }
    }
}