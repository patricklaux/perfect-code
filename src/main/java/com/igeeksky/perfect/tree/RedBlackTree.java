package com.igeeksky.perfect.tree;

import java.util.Comparator;

/**
 * @author Patrick.Lau
 * @since 0.0.4 2022-01-05
 */
public class RedBlackTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    public RedBlackTree(Comparator<K> comparator) {
        super(comparator);
    }

    @Override
    public void put(K key, V value) throws InterruptedException {

    }

    @Override
    public V remove(K key) {
        return null;
    }

    static class RBNode<K, V> extends BinarySearchTree.Node<K, V> {

        boolean red;

        RBNode(K key, V value) {
            super(key, value);
        }

        @Override
        public String toString() {
            return "{\"red\":" + red +
                    ", \"key\":\"" + key + "\"" +
                    (null != val ? (", \"value\":\"" + val + "\"") : "") +
                    ((null != left) ? (", \"left\":" + left) : "") +
                    ((null != right) ? (", \"right\":" + right) : "") +
                    "}";
        }
    }


}
