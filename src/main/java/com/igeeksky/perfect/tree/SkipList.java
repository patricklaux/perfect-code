package com.igeeksky.perfect.tree;

import com.igeeksky.perfect.api.BaseMap;
import com.igeeksky.xtool.core.lang.Assert;
import com.igeeksky.xtool.core.math.IntegerValue;

import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

/**
 * 跳表
 *
 * @author Patrick.Lau
 * @since 0.0.4 2022-02-21
 */
public class SkipList<K extends Comparable<K>, V> implements BaseMap<K, V> {

    // 最大层数
    private static final int MAX_LEVEL = 32;
    // 随机数阈值：使用整数，避免使用浮点数比较
    private static final int THRESHOLD = Integer.MAX_VALUE / 4;

    // 头结点
    private final Node<K, V> header;
    // 随机数生成器
    private final Random RANDOM = new Random();
    // 元素数量
    private final IntegerValue size = new IntegerValue();
    // 跳表高度
    private final IntegerValue height = new IntegerValue(1);

    public SkipList() {
        // 初始化头结点：头节点不保存键值，默认高度为 32
        header = new Node<>(null, null, MAX_LEVEL);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void put(K key, V value) {
        Assert.notNull(key);
        Assert.notNull(value);
        // 用以记录可能需要更新索引链接的节点
        Node<K, V>[] update = new Node[height.get()];
        Node<K, V> x = header;
        int h = height.get() - 1;
        while (h >= 0) {
            update[h] = x;
            // 获取当前索引层的右侧节点
            Node<K, V> right = x.forward[h];
            if (right == null) {
                // 情形A1：当前索引层右侧节点为空，跳转到下一索引层
                h--;
                continue;
            }
            int cmp = compare(key, right.key);
            if (cmp > 0) {
                // 情形A2：key 的键大于当前索引层右侧节点的键，向右侧节点前进
                x = right;
            } else if (cmp < 0) {
                // 情形A3：key 的键小于当前索引层右侧节点的键，跳转到下一索引层
                h--;
            } else {
                // 情形A4：key 的键等于当前索引层右侧节点的键，更新当前索引层右侧节点的值，并返回
                right.val = value;
                return;
            }
        }
        // 情形B1：已到达最底层且无匹配的键，插入新节点，并更新索引
        insert(update, key, value);
        size.increment();
    }

    /**
     * 插入新节点
     *
     * @param update 可能需要更新索引的节点
     * @param key    键
     * @param value  值
     */
    private void insert(Node<K, V>[] update, K key, V value) {
        int level = randomLevel();
        Node<K, V> node = new Node<>(key, value, level);
        if (level > height.get()) {
            // 创建了新索引层：高度加 1，头节点与新节点建立连接
            height.set(level);
            header.forward[--level] = node;
        }
        // 更新索引
        for (int i = 0; i < level; i++) {
            // left --> right  ===>  left --> new_node --> right
            Node<K, V> left = update[i];
            node.forward[i] = left.forward[i];
            left.forward[i] = node;
        }
    }

    @Override
    public V get(K key) {
        Assert.notNull(key);
        int h = height.get() - 1;
        Node<K, V> x = header;
        while (h >= 0) {
            Node<K, V> right = x.forward[h];
            if (right == null) {
                h--;
                continue;
            }
            int cmp = compare(key, right.key);
            if (cmp > 0) {
                x = right;
            } else if (cmp < 0) {
                h--;
            } else {
                return right.val;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        Assert.notNull(key);
        int h = height.get() - 1;
        Node<K, V> x = header;
        V oldVal = null;
        while (h >= 0) {
            Node<K, V> right = x.forward[h];
            if (right == null) {
                h--;
                continue;
            }
            int cmp = compare(key, right.key);
            if (cmp > 0) {
                x = right;
            } else if (cmp < 0) {
                h--;
            } else {
                oldVal = right.val;
                x.forward[h] = right.forward[h];
                if (header == x && x.forward[h] == null) {
                    height.decrement();
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

    public int height() {
        return height.get();
    }

    @Override
    public boolean isEmpty() {
        return size.get() <= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(header.forward, null);
        size.set(0);
        height.set(1);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n\n");
        for (int h = height.get() - 1; h >= 0; h--) {
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

    /**
     * 随机函数：生成索引层数
     *
     * @return 索引层数
     */
    private int randomLevel() {
        int level = 1;
        int max = Math.min(height.get() + 1, MAX_LEVEL);
        while (level < max && RANDOM.nextInt(0, Integer.MAX_VALUE) < THRESHOLD) {
            level++;
        }
        return level;
    }

    private int compare(K k1, K k2) {
        return k1.compareTo(k2);
    }

    static class Node<K, V> {
        K key;
        V val;

        // 用以存储索引，索引层数由随机函数决定
        Node<K, V>[] forward;

        @SuppressWarnings("unchecked")
        public Node(K key, V val, int level) {
            this.key = key;
            this.val = val;
            this.forward = new Node[level];
        }
    }

}