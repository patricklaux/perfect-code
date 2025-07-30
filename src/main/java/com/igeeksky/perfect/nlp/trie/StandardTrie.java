package com.igeeksky.perfect.nlp.trie;

import com.igeeksky.perfect.api.Trie;
import com.igeeksky.xtool.core.function.tuple.Tuple2;
import com.igeeksky.xtool.core.function.tuple.Tuples;
import com.igeeksky.xtool.core.nlp.Found;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Patrick.Lau
 * @since 1.0.0 2021-11-30
 */
public class StandardTrie<V> implements Trie<V> {

    /**
     * 键值对数量
     */
    private int size;

    /**
     * Radix 值（即字符集大小）
     */
    private static final int R = 65536;

    /**
     * 根节点
     */
    private final StandardNode<V> root = new StandardNode<>(R);

    /**
     * 添加键值对
     *
     * @param key   键
     * @param value 值
     */
    @Override
    @SuppressWarnings("unchecked")
    public void put(String key, V value) {
        int len = key.length();
        if (len == 0) {
            return;
        }
        StandardNode<V> node = root;
        for (int i = 0; i < len; i++) {
            if (node.table == null) {
                // 小优化：只有添加子节点时才创建数组，即叶子节点无数组
                node.table = new StandardNode[R];
            }
            char c = key.charAt(i);
            StandardNode<V> child = node.table[c];
            if (child == null) {
                node.table[c] = (child = new StandardNode<>());
            }
            node = child;
        }
        if (node.val == null) {
            // 如果节点原来无值，则键值对计数加 1
            ++size;
        }
        node.val = value;
    }

    /**
     * 获取键关联的值
     *
     * @param key 键
     * @return 值
     */
    @Override
    public V get(String key) {
        StandardNode<V> node = find(key);
        return (node == null) ? null : node.val;
    }

    /**
     * 删除键值对（惰性删除）
     *
     * @param key 键
     * @return 旧值
     */
    @Override
    public V remove(String key) {
        StandardNode<V> node = find(key);
        if (node == null) {
            return null;
        }
        V oldVal = node.val;
        if (oldVal != null) {
            // 惰性删除：仅把关联的值置空，没有真正删除节点
            node.val = null;
            --size;
        }
        return oldVal;
    }

    /**
     * 通过键查找节点
     *
     * @param key 键
     * @return 键的对应节点
     */
    private StandardNode<V> find(String key) {
        int len = key.length();
        if (len == 0) {
            return null;
        }
        StandardNode<V> node = root;
        for (int i = 0; i < len; i++) {
            if (node.table == null) {
                return null;
            }
            char c = key.charAt(i);
            node = node.table[c];
            if (node == null) {
                return null;
            }
        }
        return node;
    }

    @Override
    public Tuple2<String, V> prefixMatch(String word) {
        int len = word.length();
        if (len == 0) {
            return null;
        }
        StandardNode<V> node = root;
        Tuple2<String, V> tuple2 = null;
        for (int i = 0; i < len; i++) {
            if (node.table == null) {
                return tuple2;
            }
            char c = word.charAt(i);
            node = node.table[c];
            if (node == null) {
                return tuple2;
            }
            if (node.val != null) {
                tuple2 = Tuples.of(word.substring(0, i + 1), node.val);
            }
        }
        return tuple2;
    }

    @Override
    public List<Tuple2<String, V>> keysWithPrefix(String prefix) {
        List<Tuple2<String, V>> results = new LinkedList<>();
        StandardNode<V> parent = find(prefix);
        traversal(parent, prefix, results);
        return results;
    }

    private void traversal(StandardNode<V> parent, String prefix, List<Tuple2<String, V>> results) {
        // 深度优先遍历
        if (parent != null) {
            if (parent.val != null) {
                results.add(Tuples.of(prefix, parent.val));
            }
            if (parent.table != null) {
                for (int c = 0; c < R; c++) {
                    // 由于数组中可能存在大量空链接，因此遍历时可能会有很多无意义操作
                    String key = prefix + (char) c;
                    StandardNode<V> node = parent.table[c];
                    traversal(node, key, results);
                }
            }
        }
    }

    @Override
    public List<Found<V>> matchAll(String text) {
        List<Found<V>> list = new LinkedList<>();
        int len = text.length();
        for (int i = 0; i < len; i++) {
            matchAll(list, text, i, len);
        }
        return list;
    }

    private void matchAll(List<Found<V>> list, String text, int start, int end) {
        StandardNode<V> node = root;
        for (int i = start; i < end; i++) {
            if (node.table == null) {
                return;
            }
            char c = text.charAt(i);
            node = node.table[c];
            if (node == null) {
                return;
            }
            if (node.val != null) {
                list.add(new Found<>(start, i, text.substring(start, i + 1), node.val));
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(root.table, null);
        size = 0;
    }

    /**
     * 朴素 Trie节点
     *
     * @param <V> 值类型
     */
    private static class StandardNode<V> {

        // 值
        V val;

        // 子节点
        StandardNode<V>[] table;

        public StandardNode() {
        }

        // r 为数组容量
        @SuppressWarnings("unchecked")
        public StandardNode(int R) {
            this.table = new StandardNode[R];
        }
    }

}