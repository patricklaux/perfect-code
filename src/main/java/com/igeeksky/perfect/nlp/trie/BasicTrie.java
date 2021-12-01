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
public class BasicTrie<V> implements Trie<V> {

    private int size;
    private static final int R = 65536;
    private final BasicNode<V> root = new BasicNode<>(R, null);

    @Override
    public void put(String key, V value) {
        int last = key.length() - 1;
        BasicNode<V> node = root;
        for (int i = 0; i <= last; i++) {
            char c = key.charAt(i);
            BasicNode<V> child = node.next[c];
            if (child == null) {
                node.next[c] = (child = new BasicNode<>(R));
            }
            node = child;
            if (i == last) {
                node.val = value;
            }
        }
        ++size;
    }

    @Override
    public V get(String key) {
        BasicNode<V> node = find(key);
        return (node == null) ? null : node.val;
    }

    @Override
    public V remove(String key) {
        BasicNode<V> node = find(key);
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

    private BasicNode<V> find(String key) {
        BasicNode<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
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
        BasicNode<V> node = root;
        Tuple2<String, V> tuple2 = null;
        for (int i = 0; i < word.length(); i++) {
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
        BasicNode<V> parent = find(prefix);
        traversal(parent, prefix, list);
        return list;
    }

    // 深度优先遍历
    private void traversal(BasicNode<V> parent, String prefix, List<Tuple2<String, V>> list) {
        if (parent != null) {
            if (parent.val != null) {
                list.add(Tuples.of(prefix, parent.val));
            }
            for (int c = 0; c < R; c++) {
                // 由于数组中可能存在大量空链接，因此遍历时可能会有很多无意义操作
                String key = prefix + (char) c;
                BasicNode<V> node = parent.next[c];
                traversal(node, key, list);
            }
        }
    }

    @Override
    public List<Found<V>> matchAll(String text) {
        int len = text.length();
        List<Found<V>> list = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            matchAll(list, text, i, len);
        }
        return list;
    }

    private void matchAll(List<Found<V>> list, String text, int start, int end) {
        BasicNode<V> node = root;
        for (int i = start; i < end; i++) {
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
    private static class BasicNode<V> {

        // 值（支持泛型，并不一定要是字符串）
        V val;

        // 子节点
        BasicNode<V>[] next;

        /**
         * @param r 数组容量
         */
        public BasicNode(int r) {
            this.next = new BasicNode[r];
        }

        public BasicNode(int r, V val) {
            this.val = val;
            this.next = new BasicNode[r];
        }
    }
}
