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
public class StandTrie<V> implements Trie<V> {

    private int size;
    private static final int R = 128;
    private final TrieNode<V> root = new TrieNode<>(R);

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
        TrieNode<V> node = root;
        for (int i = 0; i < len; i++) {
            if (node.next == null) {
                // 小优化：只有添加子节点时才创建数组，即叶子节点无数组
                node.next = new TrieNode[R];
            }
            char c = key.charAt(i);
            TrieNode<V> child = node.next[c];
            if (child == null) {
                node.next[c] = (child = new TrieNode<>());
            }
            node = child;
        }
        node.val = value;
        ++size;
    }

    /**
     * 通过key获取值
     *
     * @param key 键
     * @return 值
     */
    @Override
    public V get(String key) {
        TrieNode<V> node = find(key);
        return (node == null) ? null : node.val;
    }

    /**
     * 移除键值对
     */
    @Override
    public V remove(String key) {
        TrieNode<V> node = find(key);
        if (node == null) {
            return null;
        }
        V oldVal = node.val;
        if (oldVal != null) {
            // 为了让代码更简单清晰，这里使用惰性删除，仅把对应的值置为空，并没有真正删除节点
            node.val = null;
            --size;
        }
        return oldVal;
    }

    /**
     * 通过key查找节点
     *
     * @param key 键
     * @return key对应的节点
     */
    private TrieNode<V> find(String key) {
        int len = key.length();
        if (len == 0) {
            return null;
        }
        TrieNode<V> node = root;
        for (int i = 0; i < len; i++) {
            if (node.next == null) {
                return null;
            }
            char c = key.charAt(i);
            node = node.next[c];
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
        TrieNode<V> node = root;
        Tuple2<String, V> tuple2 = null;
        for (int i = 0; i < len; i++) {
            if (node.next == null) {
                return tuple2;
            }
            char c = word.charAt(i);
            node = node.next[c];
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
        List<Tuple2<String, V>> list = new LinkedList<>();
        TrieNode<V> parent = find(prefix);
        traversal(parent, prefix, list);
        return list;
    }

    private void traversal(TrieNode<V> parent, String prefix, List<Tuple2<String, V>> list) {
        // 深度优先遍历
        if (parent != null) {
            if (parent.val != null) {
                list.add(Tuples.of(prefix, parent.val));
            }
            if (parent.next != null) {
                for (int c = 0; c < R; c++) {
                    // 由于数组中可能存在大量空链接，因此遍历时可能会有很多无意义操作
                    String key = prefix + (char) c;
                    TrieNode<V> node = parent.next[c];
                    traversal(node, key, list);
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
        TrieNode<V> node = root;
        for (int i = start; i < end; i++) {
            if (node.next == null) {
                return;
            }
            char c = text.charAt(i);
            node = node.next[c];
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
        Arrays.fill(root.next, null);
        size = 0;
    }

    /**
     * 朴素Trie节点
     *
     * @param <V> 泛型，值类型
     */
    @SuppressWarnings("unchecked")
    private static class TrieNode<V> {

        // 值（支持泛型，并不一定要是字符串）
        V val;

        // 子节点
        TrieNode<V>[] next;

        public TrieNode() {
        }

        public TrieNode(int r) {
            this.next = new TrieNode[r];
        }
    }
}