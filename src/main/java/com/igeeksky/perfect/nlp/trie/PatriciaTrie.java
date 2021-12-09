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
 * @since 1.0.0 2021-12-08
 */
@SuppressWarnings("unchecked")
public class PatriciaTrie<V> implements Trie<V> {

    private int size;
    private static final int R = 65536;
    private final PatriciaNode<V> root = new PatriciaNode<>(R);

    @Override
    public void put(String key, V value) {
        int keyLast = key.length() - 1;
        if (keyLast < 0) {
            return;
        }
        PatriciaNode<V> node = root;
        for (int i = 0; i <= keyLast; i++) {
            char c = key.charAt(i);
            if (node.table == null) {
                node.table = new PatriciaNode[R];
                node.table[c] = new PatriciaNode<>(key.substring(i), value);
                ++size;
                return;
            }
            PatriciaNode<V> child = node.table[c];
            if (child == null) {
                node.table[c] = new PatriciaNode<>(key.substring(i), value);
                ++size;
                return;
            }
            String segment = child.segment;
            int childLast = segment.length() - 1;
            // 查找分裂点
            for (int j = 0; j <= childLast; j++, i++) {
                if (segment.charAt(j) == key.charAt(i)) {
                    if (j == childLast) {
                        if (i == keyLast) {
                            if (child.val == null) {
                                ++size;
                            }
                            child.val = value;
                            return;
                        } else {
                            break;
                        }
                    }
                    if (i == keyLast) {
                        PatriciaNode<V> child0 = new PatriciaNode<>(R, segment.substring(0, j + 1), value);
                        node.table[c] = child0;
                        ++size;
                        child.segment = segment.substring(j + 1);
                        child0.table[child.segment.charAt(0)] = child;
                        return;
                    }
                } else {
                    PatriciaNode<V> child0 = new PatriciaNode<>(R, segment.substring(0, j));
                    node.table[c] = child0;

                    child.segment = segment.substring(j);
                    child0.table[child.segment.charAt(0)] = child;

                    PatriciaNode<V> child1 = new PatriciaNode<>(key.substring(i), value);
                    child0.table[child1.segment.charAt(0)] = child1;
                    ++size;
                    return;
                }
            }
            node = child;
        }
    }

    @Override
    public V get(String key) {
        PatriciaNode<V> node = find(key);
        return (node == null) ? null : node.val;
    }

    /**
     * 通过key查找节点
     *
     * @param key 键
     * @return key对应的节点
     */
    private PatriciaNode<V> find(String key) {
        int keyLast = key.length() - 1;
        if (keyLast < 0) {
            return null;
        }
        PatriciaNode<V> node = root;
        for (int i = 0; i <= keyLast; i++) {
            if (node.table == null) {
                return null;
            }
            char c = key.charAt(i);
            node = node.table[c];
            if (node == null) {
                return null;
            }
            String chars = node.segment;
            int charsLast = chars.length() - 1;
            for (int j = 0; j <= charsLast; j++, i++) {
                if (chars.charAt(j) == key.charAt(i)) {
                    if (j == charsLast) {
                        if (i == keyLast) {
                            return node;
                        }
                        break;
                    }
                    if (i == keyLast) {
                        return null;
                    } else {
                        continue;
                    }
                }
                return null;
            }
        }
        return node;
    }

    @Override
    public V remove(String key) {
        PatriciaNode<V> node = find(key);
        if (node == null) {
            return null;
        }
        V oldVal = node.val;
        if (oldVal != null) {
            // 使用惰性删除：仅把关联的值置空，没有真正删除节点
            node.val = null;
            --size;
        }
        return oldVal;
    }

    @Override
    public Tuple2<String, V> prefixMatch(String word) {
        int last = word.length() - 1;
        if (last < 0) {
            return null;
        }
        PatriciaNode<V> node = root;
        Tuple2<String, V> tuple2 = null;
        for (int i = 0; i <= last; i++) {
            if (node.table == null) {
                return tuple2;
            }
            char c = word.charAt(i);
            node = node.table[c];
            if (node == null) {
                return tuple2;
            }
            String chars = node.segment;
            int charsLast = chars.length() - 1;
            for (int j = 0; j <= charsLast; j++, i++) {
                if (chars.charAt(j) == word.charAt(i)) {
                    if (j == charsLast) {
                        if (node.val != null) {
                            tuple2 = Tuples.of(word.substring(0, i + 1), node.val);
                        }
                        break;
                    }
                    if (i == last) {
                        return tuple2;
                    }
                    continue;
                }
                return tuple2;
            }
        }
        return tuple2;
    }

    @Override
    public List<Tuple2<String, V>> keysWithPrefix(String prefix) {
        List<Tuple2<String, V>> list = new LinkedList<>();
        PatriciaNode<V> parent = find(prefix);
        traversal(parent, prefix, list);
        return list;
    }

    private void traversal(PatriciaNode<V> parent, String prefix, List<Tuple2<String, V>> list) {
        // 深度优先遍历
        if (parent != null) {
            if (parent.val != null) {
                list.add(Tuples.of(prefix, parent.val));
            }
            if (parent.table != null) {
                for (int c = 0; c < R; c++) {
                    // 由于数组中可能存在大量空链接，因此遍历时可能会有很多无意义操作
                    String key = prefix + (char) c;
                    PatriciaNode<V> node = parent.table[c];
                    traversal(node, key, list);
                }
            }
        }
    }

    @Override
    public List<Found<V>> matchAll(String text) {
        List<Found<V>> list = new LinkedList<>();
        int last = text.length() - 1;
        for (int i = 0; i <= last; i++) {
            matchAll(list, text, i, last);
        }
        return list;
    }

    private void matchAll(List<Found<V>> list, String text, int start, int last) {
        PatriciaNode<V> node = root;
        for (int i = start; i <= last; i++) {
            if (node.table == null) {
                return;
            }
            char c = text.charAt(i);
            node = node.table[c];
            if (node == null) {
                return;
            }
            String chars = node.segment;
            int charsLast = chars.length() - 1;
            for (int j = 0; j <= charsLast; j++, i++) {
                if (chars.charAt(j) == text.charAt(i)) {
                    if (j == charsLast) {
                        if (node.val != null) {
                            list.add(new Found<>(start, i, text.substring(start, i + 1), node.val));
                        }
                        break;
                    }
                    if (i == last) {
                        return;
                    }
                    continue;
                }
                return;
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

    private static class PatriciaNode<V> {

        // key的其中一部分
        String segment;

        // 值（支持泛型，可以是非字符串）
        V val;

        // 子节点
        PatriciaNode<V>[] table;

        public PatriciaNode(int R) {
            this.table = new PatriciaNode[R];
        }

        public PatriciaNode(int R, String segment) {
            this.segment = segment;
            this.table = new PatriciaNode[R];
        }

        public PatriciaNode(String segment, V value) {
            this.segment = segment;
            this.val = value;
        }

        public PatriciaNode(int R, String segment, V value) {
            this.segment = segment;
            this.val = value;
            this.table = new PatriciaNode[R];
        }
    }
}
