package com.igeeksky.perfect.tree;


import com.igeeksky.perfect.api.BaseMap;
import com.igeeksky.xtool.core.lang.Assert;
import com.igeeksky.xtool.core.math.IntegerValue;

import java.util.Comparator;
import java.util.Map;

/**
 * AVL 实现
 *
 * @author Patrick.Lau
 * @since 1.0.0 2021-12-01
 */
@SuppressWarnings("unchecked")
public class AvlTree<K extends Comparable<K>, V> implements BaseMap<K, V> {

    private static final int AVL_RIGHT_ROTATE_THRESHOLD = 2;
    private static final int AVL_LEFT_ROTATE_THRESHOLD = -2;
    private static final int AVL_RIGHT_SLANT = -1;
    private static final int AVL_LEFT_SLANT = 1;

    private final IntegerValue size = new IntegerValue();
    private final Comparator<K> comparator;
    private AvlNode<K, V> root = null;

    public AvlTree() {
        this.comparator = null;
    }

    public AvlTree(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public AvlNode<K, V> getRoot() {
        return root;
    }

    @Override
    public void put(K key, V value) {
        Assert.notNull(key);
        Assert.notNull(value);
        if (root == null) {
            root = new AvlNode<>(key, value);
            size.set(1);
            return;
        }

        AvlNode<K, V> p = root;
        int depth = 0, maxDepth = root.height + 1;
        // 存储回溯路径
        AvlNode<K, V>[] path = new AvlNode[maxDepth];
        while (depth < maxDepth) {
            path[depth] = p;
            int cmp = compare(p.key, key);
            if (cmp > 0) {
                if (p.left == null) {
                    p.left = new AvlNode<>(key, value);
                    size.increment();
                    if (p.right != null) {
                        return; // 树结构未改变，无需回溯调整
                    }
                    break;
                } else {
                    p = p.left;
                    depth++;
                }
            } else if (cmp < 0) {
                if (p.right == null) {
                    p.right = new AvlNode<>(key, value);
                    size.increment();
                    if (p.left != null) {
                        return; // 树结构未改变，无需回溯调整
                    }
                    break;
                } else {
                    p = p.right;
                    depth++;
                }
            } else {
                p.val = value;
                return; // 树结构未改变，无需回溯调整
            }
        }
        backtrack(path, depth);
        root = balance(root);
    }

    @Override
    public V get(K key) {
        Assert.notNull(key);
        AvlNode<K, V> p = root;
        while (p != null) {
            int cmp = compare(p.key, key);
            if (cmp > 0) {
                p = p.left;
            } else if (cmp < 0) {
                p = p.right;
            } else {
                return p.val;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        Assert.notNull(key);
        if (root == null) {
            return null;
        }
        int depth = 0;
        AvlNode<K, V> del = root;
        // 根节点至删除节点之间的回溯路径
        AvlNode<K, V>[] path = new AvlNode[root.height];
        while (del != null) {
            int cmp = compare(del.key, key);
            if (cmp == 0) {
                size.decrement();
                if (root == del) {
                    root = swap(root);
                    return del.val;
                }
                AvlNode<K, V> p = path[--depth];
                if (p.right == del) {
                    p.right = swap(del);
                } else {
                    p.left = swap(del);
                }
                backtrack(path, depth);
                root = balance(root);
                return del.val;
            }
            path[depth] = del;
            del = (cmp > 0) ? del.left : del.right;
            ++depth;
        }
        return null;
    }

    private AvlNode<K, V> swap(AvlNode<K, V> del) {
        AvlNode<K, V> pl = del.left, pr = del.right;
        if (height(pl) >= height(pr)) {
            if (pl != null) {
                // 查找左子树最大节点并交换删除节点
                return swapPredecessor(del, pl);
            }
            //没有子节点
            return null;
        }
        // 查找右子树最小节点并交换删除节点
        return swapSuccessor(del, pr);
    }

    /**
     * 使用左子树的最大节点替换删除节点
     *
     * @param del  待删除节点
     * @param swap 左孩子
     * @return 替换后的节点（左子树的最大节点）
     */
    private AvlNode<K, V> swapPredecessor(AvlNode<K, V> del, AvlNode<K, V> swap) {
        AvlNode<K, V>[] path = new AvlNode[del.height];
        int depth = 0;
        while (swap.right != null) {
            depth++;
            path[depth] = swap;
            swap = swap.right;
        }

        if (depth > 0) {
            AvlNode<K, V> p = path[depth];
            p.right = swap.left;
            swap.left = del.left;
        }
        swap.right = del.right;
        return fixAfterDelete(swap, path, depth);
    }

    /**
     * 使用右子树的最小节点替换删除节点
     *
     * @param del  待删除节点
     * @param swap 右孩子
     * @return 替换后的节点（右子树的最大节点）
     */
    private AvlNode<K, V> swapSuccessor(AvlNode<K, V> del, AvlNode<K, V> swap) {
        AvlNode<K, V>[] path = new AvlNode[del.height];
        int depth = 0;
        while (swap.left != null) {
            depth++;
            path[depth] = swap;
            swap = swap.left;
        }

        if (depth > 0) {
            AvlNode<K, V> parent = path[depth];
            parent.left = swap.right;
            swap.right = del.right;
        }
        swap.left = del.left;
        return fixAfterDelete(swap, path, depth);
    }

    private AvlNode<K, V> fixAfterDelete(AvlNode<K, V> swap, AvlNode<K, V>[] path, int depth) {
        path[0] = swap;
        backtrack(path, depth);
        return balance(swap);
    }

    /**
     * 回溯
     *
     * @param path  回溯路径
     * @param depth 深度
     */
    private void backtrack(AvlNode<K, V>[] path, int depth) {
        for (int j = depth; j > 0; j--) {
            AvlNode<K, V> p = path[j];
            AvlNode<K, V> pp = path[j - 1];
            int height = p.height;
            updateHeight(p);
            if (pp.left == p) {
                pp.left = balance(p);
            } else {
                pp.right = balance(p);
            }
            if (p.height == height) {
                // 高度不变，无需继续回溯
                break;
            }
        }
        updateHeight(path[0]);
    }

    private int compare(K k1, K k2) {
        return comparator == null ? k1.compareTo(k2)
                : comparator.compare(k1, k2);
    }

    @Override
    public int size() {
        return size.get();
    }

    @Override
    public boolean isEmpty() {
        return size.get() == 0;
    }

    @Override
    public void clear() {
        root = null;
        size.set(0);
    }

    static class AvlNode<K, V> implements Map.Entry<K, V> {
        K key;
        V val;
        byte height;
        AvlNode<K, V> left;
        AvlNode<K, V> right;

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

        AvlNode(K key, V value) {
            this.key = key;
            this.val = value;
        }

        @Override
        public String toString() {
            return "{\"height\":" + height +
                    ", \"key\":\"" + key + "\"" +
                    //(null != val ? (", \"value\":\"" + val + "\"") : "") +
                    ((null != left) ? (", \"left\":" + left) : "") +
                    ((null != right) ? (", \"right\":" + right) : "") +
                    "}";
        }
    }

    private AvlNode<K, V> balance(AvlNode<K, V> parent) {
        int factor = balanceFactor(parent);
        if (factor >= AVL_RIGHT_ROTATE_THRESHOLD) {
            if (balanceFactor(parent.left) <= AVL_RIGHT_SLANT) {
                return rotateLeftRight(parent);
            }
            return rotateRight(parent);
        } else if (factor <= AVL_LEFT_ROTATE_THRESHOLD) {
            if (balanceFactor(parent.right) >= AVL_LEFT_SLANT) {
                return rotateRightLeft(parent);
            }
            return rotateLeft(parent);
        } else {
            return parent;
        }
    }

    private int balanceFactor(AvlNode<K, V> parent) {
        return height(parent.left) - height(parent.right);
    }

    private AvlNode<K, V> rotateLeft(AvlNode<K, V> parent) {
        AvlNode<K, V> right = parent.right;
        parent.right = right.left;
        right.left = parent;
        updateHeight(parent);
        updateHeight(right);
        return right;
    }

    private AvlNode<K, V> rotateRight(AvlNode<K, V> parent) {
        AvlNode<K, V> left = parent.left;
        parent.left = left.right;
        left.right = parent;
        updateHeight(parent);
        updateHeight(left);
        return left;
    }

    private AvlNode<K, V> rotateLeftRight(AvlNode<K, V> parent) {
        parent.left = rotateLeft(parent.left);
        return rotateRight(parent);
    }

    private AvlNode<K, V> rotateRightLeft(AvlNode<K, V> parent) {
        parent.right = rotateRight(parent.right);
        return rotateLeft(parent);
    }

    private byte height(AvlNode<K, V> node) {
        return (node == null) ? -1 : node.height;
    }

    private void updateHeight(AvlNode<K, V> parent) {
        parent.height = (byte) (Math.max(height(parent.left), height(parent.right)) + 1);
    }
}
