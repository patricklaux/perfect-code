package com.igeeksky.perfect.tree;

import com.igeeksky.perfect.api.BaseMap;
import com.igeeksky.xtool.core.lang.Assert;
import com.igeeksky.xtool.core.math.IntegerValue;

import java.util.Comparator;

/**
 * @author Patrick.Lau
 * @since 1.0.0 2022-01-05
 */
public class RedBlackTree<K extends Comparable<K>, V> implements BaseMap<K, V> {

    private final IntegerValue size = new IntegerValue();
    private final Comparator<K> comparator;
    private Node<K, V> root;

    public RedBlackTree() {
        this.comparator = null;
    }

    public RedBlackTree(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public V get(K key) {
        Node<K, V> p = search(key);
        return (p == null) ? null : p.val;
    }

    @Override
    public void put(K key, V value) {
        Assert.notNull(key);
        Assert.notNull(value);
        if (root == null) { // 0. 情形1：新插入节点为根节点
            size.increment();
            root = new Node<>(key, value, BLACK);
            size.set(1);
            return;
        }
        Node<K, V> p = root;
        while (true) {
            int cmp = compare(p.key, key);
            if (cmp > 0) {
                if (p.left == null) {
                    size.increment();
                    p.left = new Node<>(key, value, p);
                    fixAfterInsertion(p.left);
                    return;
                }
                p = p.left;
            } else if (cmp < 0) {
                if (p.right == null) {
                    size.increment();
                    p.right = new Node<>(key, value, p);
                    fixAfterInsertion(p.right);
                    return;
                }
                p = p.right;
            } else {
                p.val = value;
                return;
            }
        }
    }

    private Node<K, V> search(K key) {
        Assert.notNull(key);
        Node<K, V> p = root;
        while (p != null) {
            int cmp = compare(p.key, key);
            if (cmp > 0) {
                p = p.left;
            } else if (cmp < 0) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    /**
     * 插入节点后变色和旋转，使得其符合红黑树的性质
     *
     * @param x 新插入节点
     */
    private void fixAfterInsertion(Node<K, V> x) {
        // 1. 情形2：父节点是黑色，无需任何调整
        // 2. 父节点是红色
        while (x.parent.red == RED) {
            Node<K, V> p = x.parent;
            Node<K, V> g = p.parent;
            Node<K, V> u = (p == g.left) ? g.right : g.left;
            if (isBlack(u)) {
                // 2.1. 情形3：父节点是红色，叔节点是黑色（或叔节点不存在）
                g.red = RED;
                if (x == p.left) {
                    if (p == g.left) {  // LL
                        p.red = BLACK;
                        rotateRight(g);
                    } else {    // RL
                        x.red = BLACK;
                        rotateRight(p);
                        rotateLeft(g);
                    }
                } else {
                    if (p == g.right) { // RR
                        p.red = BLACK;
                        rotateLeft(g);
                    } else {    // LR
                        x.red = BLACK;
                        rotateLeft(p);
                        rotateRight(g);
                    }
                }
                return;
            } else {
                // 2.2. 情形4：父节点是红色，叔节点是红色
                p.red = u.red = BLACK;
                if (g == root) {
                    return;
                }
                g.red = RED;
                x = g;
            }
        }
    }

    @Override
    public V remove(K key) {
        Node<K, V> x = search(key);
        if (x == null) {
            return null;
        }
        V oldVal = x.val;
        delete(x);
        return oldVal;
    }

    private void delete(Node<K, V> x) {
        size.decrement();
        // 是否有两个孩子
        if (x.left != null && x.right != null) {
            // 交换节点
            x = swap(x);
        }
        Node<K, V> r = x.left != null ? x.left : x.right;
        if (x.parent == null) {
            root = r;
            if (r != null) {
                r.red = BLACK;
            }
            return;
        }
        if (r == null) {
            if (x.red == BLACK) {
                fixAfterDeletion(x);
            }
            transplant(x, null);
        } else {
            transplant(x, r);
            if (x.red == BLACK) {
                fixAfterDeletion(r);
            }
        }
    }

    private void transplant(Node<K, V> x, Node<K, V> r) {
        Node<K, V> p = x.parent;
        x.parent = x.left = x.right = null;
        if (r != null) r.parent = p;
        if (x == p.left) {
            p.left = r;
        } else {
            p.right = r;
        }
    }

    private void fixAfterDeletion(Node<K, V> x) {
        while (x != root && isBlack(x)) {
            if (x == x.parent.left) {
                Node<K, V> s = x.parent.right;
                if (s.red == RED) {
                    setColor(s, BLACK);
                    setColor(x.parent, RED);
                    rotateLeft(x.parent);
                    s = x.parent.right;
                }
                if (isBlack(s.left) && isBlack(s.right)) {
                    setColor(s, RED);
                    x = x.parent;
                } else {
                    if (isBlack(s.right)) {
                        setColor(s.left, BLACK);
                        setColor(s, RED);
                        rotateRight(s);
                        s = x.parent.right;
                    }
                    setColor(s, x.parent.red);
                    setColor(x.parent, BLACK);
                    setColor(s.right, BLACK);
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                Node<K, V> s = x.parent.left;
                if (s.red == RED) {
                    setColor(s, BLACK);
                    setColor(x.parent, RED);
                    rotateRight(x.parent);
                    s = x.parent.left;
                }
                if (isBlack(s.left) && isBlack(s.right)) {
                    setColor(s, RED);
                    x = x.parent;
                } else {
                    if (isBlack(s.left)) {
                        setColor(s.right, BLACK);
                        setColor(s, RED);
                        rotateLeft(s);
                        s = x.parent.left;
                    }
                    setColor(s, x.parent.red);
                    setColor(x.parent, BLACK);
                    setColor(s.left, BLACK);
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        setColor(x, BLACK);
    }

    private boolean isRed(Node<K, V> n) {
        return n != null && n.red == RED;
    }

    private boolean isBlack(Node<K, V> n) {
        return n == null || n.red == BLACK;
    }

    private void setColor(Node<K, V> n, boolean red) {
        if (n != null) n.red = red;
    }

    private Node<K, V> swap(Node<K, V> x) {
        Node<K, V> s = swapPredecessor(x);
        x.key = s.key;
        x.val = s.val;
        return s;
    }

    private Node<K, V> swapPredecessor(Node<K, V> x) {
        Node<K, V> s = x.left;
        while (s.right != null) {
            s = s.right;
        }
        return s;
    }

    private Node<K, V> swapSuccessor(Node<K, V> x) {
        Node<K, V> s = x.right;
        while (s.left != null) {
            s = s.left;
        }
        return s;
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
        root = null;
    }

    private int compare(K k1, K k2) {
        return comparator == null ? k1.compareTo(k2)
                : comparator.compare(k1, k2);
    }

    @Override
    public String toString() {
        return root.toString();
    }

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    static class Node<K, V> {
        K key;
        V val;

        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        boolean red = RED;

        Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.val = value;
            this.parent = parent;
        }

        Node(K key, V value, boolean red) {
            this.key = key;
            this.val = value;
            this.red = red;
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

    private void rotateLeft(Node<K, V> p) {
        Node<K, V> r = p.right;
        p.right = r.left;
        if (r.left != null) {
            r.left.parent = p;
        }
        r.parent = p.parent;
        if (p.parent == null) {
            root = r;
        } else if (p.parent.left == p) {
            p.parent.left = r;
        } else {
            p.parent.right = r;
        }
        r.left = p;
        p.parent = r;
    }

    private void rotateRight(Node<K, V> p) {
        Node<K, V> l = p.left;
        p.left = l.right;
        if (l.right != null) {
            l.right.parent = p;
        }
        l.parent = p.parent;
        if (p.parent == null) {
            root = l;
        } else if (p.parent.left == p) {
            p.parent.left = l;
        } else {
            p.parent.right = l;
        }
        l.right = p;
        p.parent = l;
    }
}
