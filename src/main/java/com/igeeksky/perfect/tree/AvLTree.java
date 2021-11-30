package com.igeeksky.perfect.tree;

import com.igeeksky.perfect.api.BaseMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * AVL 实现
 *
 * @author Patrick.Lau
 * @since 1.0.0 2021-12-01
 */
public class AvLTree<K, V> implements BaseMap<K, V> {

    @Override
    public void put(K key, V value) {
        Set<String> set = new HashSet<>();


    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

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

    private static class AvLNode<K, V> implements Map.Entry<K, V> {
        K key;
        V val;

        byte height;
        AvLNode<K, V> left;
        AvLNode<K, V> right;

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return val;
        }

        @Override
        public V setValue(V value) {
            return this.val = value;
        }
    }
}
