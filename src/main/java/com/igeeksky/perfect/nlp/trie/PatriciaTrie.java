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

    /**
     * 添加键值对
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void put(String key, V value) {
        int last = key.length() - 1;
        if (last < 0) {
            return;
        }
        PatriciaNode<V> node = root;
        for (int i = 0; i <= last; i++) {
            char c = key.charAt(i);
            // 判断数组是否为空，如果为空则创建数组
            if (node.table == null) {
                node.table = new PatriciaNode[R];
            }
            // 通过字符查找子节点，如果子节点不存在则创建子节点并返回，否则与子节点进行字符串比较
            PatriciaNode<V> child = node.table[c];
            if (child == null) {
                node.table[c] = new PatriciaNode<>(key.substring(i), value);
                ++size;
                return;
            }
            String subKey = child.subKey;
            int subLast = subKey.length() - 1;
            // 与子节点进行字符串比较
            for (int j = 0; j <= subLast; j++, i++) {
                // 字符相同
                if (subKey.charAt(j) == key.charAt(i)) {
                    if (i == last) {
                        if (j == subLast) {
                            if (child.val == null) {
                                ++size;
                            }
                            child.val = value;
                        } else {
                            // 子节点分裂成两个节点，无新的分支节点，例：原有 bcd，新增键bc ——> bc, d
                            PatriciaNode<V> child0 = new PatriciaNode<>(R, subKey.substring(0, j + 1), value);
                            node.table[c] = child0;
                            child.subKey = subKey.substring(j + 1);
                            child0.table[child.subKey.charAt(0)] = child;
                            ++size;
                        }
                        return;
                    }
                    if (j == subLast) {
                        break;
                    }
                } else {
                    // 字符不同
                    // 子节点分裂成两个节点，再加上新的分支节点，例：原有 bcd，新增键 bca ——> bc, d, 新分支a
                    PatriciaNode<V> child0 = new PatriciaNode<>(R, subKey.substring(0, j));
                    node.table[c] = child0;

                    child.subKey = subKey.substring(j);
                    child0.table[child.subKey.charAt(0)] = child;
                    // 新分支
                    PatriciaNode<V> child1 = new PatriciaNode<>(key.substring(i), value);
                    child0.table[child1.subKey.charAt(0)] = child1;
                    ++size;
                    return;
                }
            }
            node = child;
        }
    }

    /**
     * 获取键关联的值
     *
     * @param key 键
     * @return 值
     */
    @Override
    public V get(String key) {
        PatriciaNode<V> node = find(key);
        return (node == null) ? null : node.val;
    }

    /**
     * 通过键查找节点
     *
     * @param key 键
     * @return 键的对应节点
     */
    private PatriciaNode<V> find(String key) {
        int last = key.length() - 1;
        if (last < 0) {
            return null;
        }
        PatriciaNode<V> node = root;
        for (int i = 0; i <= last; i++) {
            if (node.table == null || (node = node.table[key.charAt(i)]) == null) {
                return null;
            }
            String subKey = node.subKey;
            int subLast = subKey.length() - 1;
            for (int j = 0; j <= subLast; j++, i++) {
                if (subKey.charAt(j) == key.charAt(i)) {
                    if (i == last) {
                        if (j == subLast) {
                            return node;
                        }
                        return null;
                    }
                    if (j == subLast) {
                        break;
                    }
                    continue;
                }
                return null;
            }
        }
        return node;
    }

    /**
     * 删除键值对（惰性删除）
     *
     * @param key 键
     * @return 旧值
     */
    @Override
    public V remove(String key) {
        PatriciaNode<V> node = find(key);
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
     * 前缀匹配：查找 word 的最长前缀
     *
     * @param word 待匹配词（不为空且长度大于0）
     * @return 键值对
     */
    @Override
    public Tuple2<String, V> prefixMatch(String word) {
        int last = word.length() - 1;
        if (last < 0) {
            return null;
        }
        PatriciaNode<V> node = root;
        Tuple2<String, V> tuple2 = null;
        for (int i = 0; i <= last; i++) {
            if (node.table == null || (node = node.table[word.charAt(i)]) == null) {
                return tuple2;
            }
            String subKey = node.subKey;
            int subLast = subKey.length() - 1;
            for (int j = 0; j <= subLast; j++, i++) {
                if (subKey.charAt(j) == word.charAt(i)) {
                    if (j == subLast) {
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

    /**
     * 查找并返回以 prefix 开头的所有键（及关联的值）
     *
     * @param prefix 前缀（不为空且长度大于0）
     * @return 所有以 prefix 开头的键值对
     */
    @Override
    public List<Tuple2<String, V>> keysWithPrefix(String prefix) {
        List<Tuple2<String, V>> results = new LinkedList<>();
        PatriciaNode<V> parent = find(prefix);
        if (parent == null) {
            return results;
        }
        traversal(parent, prefix, results);
        return results;
    }

    /**
     * 深度优先遍历，将所有以 prefix 开头的键值对添加到结果集
     *
     * @param parent  父节点
     * @param prefix  前缀
     * @param results 结果集
     */
    private void traversal(PatriciaNode<V> parent, String prefix, List<Tuple2<String, V>> results) {
        if (parent.val != null) {
            results.add(Tuples.of(prefix, parent.val));
        }
        if (parent.table != null) {
            for (int c = 0; c < R; c++) {
                // 由于数组中可能存在大量空链接，因此遍历时可能会有很多无意义操作
                PatriciaNode<V> node = parent.table[c];
                if (node != null) {
                    String key = prefix + node.subKey;
                    traversal(node, key, results);
                }
            }
        }
    }

    /**
     * 查找给定文本段中包含的键
     *
     * @param text 文本段（不为空且长度大于0）
     * @return 结果集（键、值、键在文本中的起止位置）
     */
    @Override
    public List<Found<V>> matchAll(String text) {
        List<Found<V>> results = new LinkedList<>();
        int last = text.length() - 1;
        for (int i = 0; i <= last; i++) {
            matchAll(results, text, i, last);
        }
        return results;
    }

    /**
     * 找到前缀节点后，遍历所有后缀节点，并将所有后缀节点包含的键值对添加到结果集
     *
     * @param results 结果集
     * @param text    文本段
     * @param start   文本段的开始位置
     * @param last    文本段的结束位置（末尾）
     */
    private void matchAll(List<Found<V>> results, String text, int start, int last) {
        PatriciaNode<V> node = root;
        for (int i = start; i <= last; i++) {
            if (node.table == null || (node = node.table[text.charAt(i)]) == null) {
                return;
            }
            String subKey = node.subKey;
            int subLast = subKey.length() - 1;
            for (int j = 0; j <= subLast; j++, i++) {
                if (subKey.charAt(j) == text.charAt(i)) {
                    if (j == subLast) {
                        if (node.val != null) {
                            results.add(new Found<>(start, i, text.substring(start, i + 1), node.val));
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

        // key的一部分
        String subKey;

        // 值（支持泛型，可以是非字符串）
        V val;

        // 子节点
        PatriciaNode<V>[] table;

        public PatriciaNode(int R) {
            this.table = new PatriciaNode[R];
        }

        public PatriciaNode(int R, String subKey) {
            this.subKey = subKey;
            this.table = new PatriciaNode[R];
        }

        public PatriciaNode(String subKey, V value) {
            this.subKey = subKey;
            this.val = value;
        }

        public PatriciaNode(int R, String subKey, V value) {
            this.subKey = subKey;
            this.val = value;
            this.table = new PatriciaNode[R];
        }
    }
}
