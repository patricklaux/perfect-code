package com.igeeksky.perfect.nlp.trie;

import com.igeeksky.perfect.api.Trie;
import com.igeeksky.perfect.nlp.string.KeyBits;
import com.igeeksky.xtool.core.function.tuple.Tuple2;
import com.igeeksky.xtool.core.lang.Assert;
import com.igeeksky.xtool.core.math.IntegerValue;
import com.igeeksky.xtool.core.nlp.Found;

import java.util.List;
import java.util.Objects;

/**
 * Patricia trie 二进制实现
 *
 * @author Patrick.Lau
 * @since 1.0.0 2021-12-08
 */
public class PatriciaBinaryTrie<V> implements Trie<V> {

    private final IntegerValue size = new IntegerValue();
    private final PatriciaBinaryNode<V> root = new PatriciaBinaryNode<>();

    /**
     * 添加键值对
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void put(String key, V value) {
        Assert.notNull(value);
        PatriciaBinaryNode<V> near = find(key);
        String nearKey = near.key;
        if (Objects.equals(key, nearKey)) {
            if (near.val == null) {
                size.increment();
            }
            near.val = value;
            return;
        }
        int diffAt = KeyBits.diffAt(key, nearKey);
        insert(key, value, diffAt);
    }

    /**
     * 新增节点
     *
     * @param key    键
     * @param value  值
     * @param diffAt 差异位下标
     */
    private void insert(String key, V value, int diffAt) {
        PatriciaBinaryNode<V> path = root, current = (KeyBits.bitAt(key, path.diffIndex) > 0) ? path.right : path.left;
        while (true) {
            if (current.diffIndex >= diffAt || current.diffIndex <= path.diffIndex) {
                PatriciaBinaryNode<V> node = new PatriciaBinaryNode<>(diffAt, key, value);
                int bit = KeyBits.bitAt(key, node.diffIndex);
                node.left = bit > 0 ? current : node;
                node.right = bit > 0 ? node : current;
                bit = KeyBits.bitAt(key, path.diffIndex);
                if (bit > 0) {
                    path.right = node;
                } else {
                    path.left = node;
                }
                size.increment();
                return;
            }
            path = current;
            current = (KeyBits.bitAt(key, current.diffIndex) > 0) ? current.right : current.left;
        }
    }

    /**
     * 查找键关联的值
     *
     * @param key 键
     * @return 值
     */
    @Override
    public V get(String key) {
        PatriciaBinaryNode<V> near = find(key);
        return (Objects.equals(key, near.key)) ? near.val : null;
    }

    /**
     * 找到最接近的节点
     *
     * @param key 键
     * @return 与输入的 key 最接近的节点
     */
    private PatriciaBinaryNode<V> find(String key) {
        Assert.hasLength(key);
        PatriciaBinaryNode<V> path = root;
        while (true) {
            PatriciaBinaryNode<V> current = (KeyBits.bitAt(key, path.diffIndex) > 0) ? path.right : path.left;
            if (current.diffIndex <= path.diffIndex) {
                return current;
            }
            path = current;
        }
    }

    /**
     * 删除键值对（惰性删除）
     *
     * @param key 键
     * @return 旧值
     */
    @Override
    public V remove(String key) {
        PatriciaBinaryNode<V> nearNode = find(key);
        if (!Objects.equals(key, nearNode.key)) {
            return null;
        }
        V oldVal = nearNode.val;
        if (oldVal != null) {
            // 惰性删除：仅把关联的值置空，没有真正删除节点
            nearNode.val = null;
            size.decrement();
        }
        return oldVal;
    }

    @Override
    public Tuple2<String, V> prefixMatch(String word) {
        throw new UnsupportedOperationException("prefixMatch is not support");
    }

    @Override
    public List<Tuple2<String, V>> keysWithPrefix(String prefix) {
        throw new UnsupportedOperationException("keysWithPrefix is not support");
    }

    @Override
    public List<Found<V>> matchAll(String text) {
        throw new UnsupportedOperationException("matchAll is not support");
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
        size.set(0);
        root.left = root.right = root;
    }

    private static class PatriciaBinaryNode<V> {
        // 差异位的下标位置
        final int diffIndex;
        // 键
        final String key;
        // 值
        V val;
        // 左孩子
        PatriciaBinaryNode<V> left;
        // 右孩子
        PatriciaBinaryNode<V> right;

        // root 特有构造方法
        PatriciaBinaryNode() {
            this.diffIndex = 0;
            this.key = String.valueOf('\0');
            this.left = this.right = this;
        }

        PatriciaBinaryNode(int diffIndex, String key, V value) {
            this.diffIndex = diffIndex;
            this.key = key;
            this.val = value;
        }

        @Override
        public String toString() {
            return "{\"diffIndex\":" + diffIndex +
                    ", \"key\":\"" + key + "\"" +
                    ", \"val\":\"" + (null != val ? val + "\"" : "\"") +
                    ", \"left\":\"" + (null != left ? left.key + "\"" : "\"") +
                    ", \"right\":\"" + (null != right ? right.key + "\"" : "\"") +
                    '}';
        }
    }
}
