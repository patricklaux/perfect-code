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

    public void put(K key, V value) {
        Assert.notNull(key);
        Assert.notNull(value);
        // 情形1：新插入节点为根节点
        if (root == null) {
            size.increment();
            root = new Node<>(key, value, BLACK);
            size.set(1);
            return;
        }
        Node<K, V> p = root;
        // 先查找，然后将新节点添加为叶子节点
        while (true) {
            int cmp = compare(p.key, key);
            if (cmp > 0) {
                if (p.left == null) {
                    size.increment();
                    p.left = new Node<>(key, value, p);
                    // 修复红黑树性质
                    fixAfterInsertion(p.left);
                    return;
                }
                p = p.left;
            } else if (cmp < 0) {
                if (p.right == null) {
                    size.increment();
                    p.right = new Node<>(key, value, p);
                    // 修复红黑树性质
                    fixAfterInsertion(p.right);
                    return;
                }
                p = p.right;
            } else {
                // 键已在树中，覆盖原值后退出
                p.val = value;
                return;
            }
        }
    }

    /**
     * 插入节点后修复红黑树性质
     *
     * @param x 当前节点
     */
    private void fixAfterInsertion(Node<K, V> x) {
        // 1. 情形2：父节点为黑色，无需调整，不进入循环
        // 2. 父节点是红色，需要修复，进入循环
        while (isRed(x.parent)) {
            Node<K, V> p = x.parent;
            Node<K, V> g = p.parent;
            Node<K, V> u = (p == g.left) ? g.right : g.left;
            if (isBlack(u)) {
                // 情形4：父节点为红色，叔叔节点为黑色（或叔叔节点不存在）
                setColor(g, RED);
                if (p == g.left) {
                    if (x == p.right) {
                        rotateLeft(p);  // 4-3：LR
                        p = x;          // 4-3：LR
                    }
                    setColor(p, BLACK); // 4-1：LL
                    rotateRight(g);     // 4-1：LL
                } else {
                    if (x == p.left) {
                        rotateRight(p); // 4-4：RL
                        p = x;          // 4-4：RL
                    }
                    setColor(p, BLACK); // 4-2：RR
                    rotateLeft(g);      // 4-2：RR
                }
                return;
            }
            // 情形3：父节点是红色，叔叔节点是红色
            setColor(p, BLACK);
            setColor(u, BLACK);
            if (g == root) return;  // 情形1：已经递归到根节点(这里省略了变色过程)
            setColor(g, RED);
            x = g;  // 再次迭代
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
            // 情形A1：与前驱交换
            x = predecessor(x);
        }
        // 获取待删除节点的子节点，用以接替待删除节点的位置
        Node<K, V> replacement = x.left != null ? x.left : x.right;
        if (replacement != null) {
            // 情形A2：待删除节点 X 有一个孩子（X 必为黑色，r 必为红色）
            // 子节点设为黑色
            setColor(replacement, BLACK);
        } else {
            if (isBlack(x)) {
                // 情形A4
                fixAfterDeletion(x);
            }
        }
        // 移除待删除节点
        transplant(x, replacement);
    }

    /**
     * 与前驱交换
     *
     * @param x 待删除节点
     * @return 前驱
     */
    private Node<K, V> predecessor(Node<K, V> x) {
        Node<K, V> pred = x.left;
        while (pred.right != null) {
            pred = pred.right;
        }
        x.key = pred.key;
        x.val = pred.val;
        return pred;
    }

    /**
     * 迁移待删除节点的父链接，移除节点
     *
     * @param x 待删除节点
     * @param r 待删除节点的子节点
     */
    private void transplant(Node<K, V> x, Node<K, V> r) {
        Node<K, V> p = x.parent;
        if (p == null) {
            root = r;
            return;
        }
        if (r != null) {
            r.parent = p;
        }
        if (x == p.left) {
            p.left = r;
        } else {
            p.right = r;
        }
    }

    /**
     * 删除节点后修复红黑树的性质
     *
     * @param x 当前节点（待删除节点或其唯一子节点）
     */
    private void fixAfterDeletion(Node<K, V> x) {
        // 情形 B1：如果x 为根，退出
        while (x != root) {
            if (x == x.parent.left) {
                Node<K, V> s = x.parent.right;
                if (s.red == RED) {
                    // 情形 B2：兄弟节点S 染黑色，父节点P 染红色，父节点P 左旋，未结束
                    setColor(s, BLACK);         // B2
                    setColor(x.parent, RED);    // B2
                    rotateLeft(x.parent);       // B2
                    s = x.parent.right;         // B2
                }
                if (isBlack(s.left) && isBlack(s.right)) {
                    // 情形 B3 或 B6：兄弟节点S 染红色，未结束
                    setColor(s, RED);               // B3 或 B6
                    if (isRed(x.parent)) {
                        setColor(x.parent, BLACK);  // B3
                        return;                     // B3
                    }
                    x = x.parent;                   // B6 Δh+1
                } else {
                    if (isBlack(s.right)) {
                        // 情形 B5：近侄节点C 染黑色，兄弟节点S 染红色，兄弟节点S 右旋，S:=P.r，未结束
                        setColor(s.left, BLACK);    // B5
                        setColor(s, RED);           // B5
                        rotateRight(s);             // B5
                        s = x.parent.right;         // B5
                    }
                    // 情形 B4：兄弟节点S 染父节点P 的颜色，父节点P 染黑色，远侄节点D 染黑色，父节点P 左旋，结束
                    setColor(s, x.parent.red);      // B4
                    setColor(x.parent, BLACK);      // B4
                    setColor(s.right, BLACK);       // B4
                    rotateLeft(x.parent);           // B4
                    return;                         // B4
                }
            } else {
                Node<K, V> s = x.parent.left;
                if (s.red == RED) {
                    // 情形 B2：兄弟节点S 染黑色，父节点P 染红色，父节点P 右旋，未结束
                    setColor(s, BLACK);             // B2
                    setColor(x.parent, RED);        // B2
                    rotateRight(x.parent);          // B2
                    s = x.parent.left;              // B2
                }
                if (isBlack(s.left) && isBlack(s.right)) {
                    // 情形 B3 或 B6：兄弟节点S 染红色，未结束
                    setColor(s, RED);               // B3 或 B6
                    if (isRed(x.parent)) {
                        setColor(x.parent, BLACK);  // B3
                        return;                     // B3
                    }
                    x = x.parent;                   // B6 Δh+1
                } else {
                    if (isBlack(s.left)) {
                        // 情形 B5：近侄节点C 染黑色，兄弟节点S 染红色，兄弟节点S 左旋，S:=P.l，未结束
                        setColor(s.right, BLACK);   //B5
                        setColor(s, RED);           //B5
                        rotateLeft(s);              //B5
                        s = x.parent.left;          //B5
                    }
                    // 情形 B4：兄弟节点S 染父节点P 的颜色，父节点P 染黑色，远侄节点D 染黑色，父节点P 右旋，结束
                    setColor(s, x.parent.red);      // B4
                    setColor(x.parent, BLACK);      // B4
                    setColor(s.left, BLACK);        // B4
                    rotateRight(x.parent);          // B4
                    return;                         // B4
                }
            }
        }
    }

    private boolean isRed(Node<K, V> n) {
        return n != null && n.red == RED;
    }

    private boolean isBlack(Node<K, V> n) {
        return n == null || n.red == BLACK;
    }

    private void setColor(Node<K, V> n, boolean color) {
        if (n != null) n.red = color;
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
        K key;  // 键
        V val;  // 值

        Node<K, V> left;    // 左孩子
        Node<K, V> right;   // 右孩子
        Node<K, V> parent;  // 父节点
        boolean red = RED;  // 颜色，默认红色

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

    /**
     * 左旋
     *
     * @param p 节点
     */
    private void rotateLeft(Node<K, V> p) {
        Node<K, V> r = p.right;
        p.right = r.left;
        if (r.left != null) {
            r.left.parent = p;
        }
        Node<K, V> g = r.parent = p.parent;
        if (g == null) {
            // 祖父节点为空，r设为根节点
            root = r;
        } else if (g.left == p) {
            g.left = r;
        } else {
            g.right = r;
        }
        r.left = p;
        p.parent = r;
    }

    /**
     * 右旋
     *
     * <pre>
     *     以 p 节点的左孩子 l 为轴进行旋转：
     *     p 变成 l 的右孩子；
     *     lr 变成 p 的左孩子；
     *     p 的 父节点变成 l；
     *     l 的父节点变成 p 的父节点 g；
     *           g                   g
     *          /                   /
     *         p                   l
     *       /   \               /   \
     *      l     r             ll    p
     *    /  \                      /  \
     *   ll   lr                   lr   r
     * </pre>
     *
     * @param p 节点
     */
    private void rotateRight(Node<K, V> p) {
        Node<K, V> l = p.left;
        p.left = l.right;
        if (l.right != null) {
            l.right.parent = p;
        }
        Node<K, V> g = l.parent = p.parent;
        if (g == null) {
            // 祖父节点为空，l设为根节点
            root = l;
        } else if (g.left == p) {
            g.left = l;
        } else {
            g.right = l;
        }
        l.right = p;
        p.parent = l;
    }
}
