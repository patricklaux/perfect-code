package com.igeeksky.perfect.nlp.trie;

import com.igeeksky.perfect.api.Trie;
import com.igeeksky.xtool.core.function.tuple.Tuple2;
import com.igeeksky.xtool.core.nlp.Found;

import java.util.List;

/**
 * @author Patrick.Lau
 * @since 1.0.0 2021-12-06
 */
public class TernarySearchTrie<V> implements Trie<V> {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void put(String key, V value) {

    }

    @Override
    public V get(String key) {
        return null;
    }

    @Override
    public V remove(String key) {
        return null;
    }

    @Override
    public Tuple2<String, V> prefixMatch(String word) {
        return null;
    }

    @Override
    public List<Tuple2<String, V>> keysWithPrefix(String prefix) {
        return null;
    }

    @Override
    public List<Found<V>> matchAll(String text) {
        return null;
    }

    /**
     * TernarySearchTree节点
     *
     * @param <V> 泛型，值类型
     */
    private static class TstNode<V> {
        // 字符
        char c;

        // 值
        V val;

        // 兄弟节点
        TstNode<V> left;

        // 子节点
        TstNode<V> mid;

        // 兄弟节点
        TstNode<V> right;
    }

}
