package com.igeeksky.perfect.tree;


import com.igeeksky.perfect.api.BaseMap;
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
public class AvLTree<K extends Comparable<K>, V> implements BaseMap<K, V> {

    private static final int AVL_RIGHT_ROTATE_THRESHOLD = 2;
    private static final int AVL_LEFT_ROTATE_THRESHOLD = -2;
    private static final int AVL_RIGHT_SLANT = -1;
    private static final int AVL_LEFT_SLANT = 1;

    private final IntegerValue size = new IntegerValue();
    private final Comparator<K> comparator;
    private AvlNode<K, V> root = null;

    public AvLTree() {
        this.comparator = null;
    }

    public AvLTree(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new AvlNode<>(key, value);
            size.increment();
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
                    break;
                } else {
                    p = p.left;
                    depth++;
                }
            } else if (cmp < 0) {
                if (p.right == null) {
                    p.right = new AvlNode<>(key, value);
                    size.increment();
                    break;
                } else {
                    p = p.right;
                    depth++;
                }
            } else {
                p.val = value;
                // 树结构未改变，无需回溯调整
                return;
            }
        }
        retracing(path, depth);
    }

    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        AvlNode<K, V> p = root;
        while (true) {
            int cmp = compare(p.key, key);
            if (cmp > 0) {
                if (p.left != null) {
                    p = p.left;
                } else {
                    return null;
                }
            } else if (cmp < 0) {
                if (p.right != null) {
                    p = p.right;
                } else {
                    return null;
                }
            } else {
                return p.val;
            }
        }
    }

    @Override
    public V remove(K key) {
        if (root == null) {
            return null;
        }
        int depth = 0, maxDepth = root.height + 1;
        // 建立 根节点 至 删除节点之间的 回溯路径；因为父节点缺失，所以需要额外的数组来保存回溯路径
        AvlNode<K, V>[] path = new AvlNode[maxDepth];
        AvlNode<K, V> p = root, del = null;
        int cmp = compare(p.key, key);
        if (cmp == 0) {
            V oldVal = root.val;
            root = swap(root);
            size.decrement();
            return oldVal;
        }

        boolean isLeft = true;
        for (; depth < maxDepth; depth++) {
            path[depth] = p;
            if (cmp > 0) {
                if (p.left == null) {
                    return null;
                }
                cmp = compare(p.left.key, key);
                if (cmp == 0) {
                    del = p.left;
                    break;
                }
                p = p.left;
            } else {
                if (p.right == null) {
                    return null;
                }
                cmp = compare(p.right.key, key);
                if (cmp == 0) {
                    del = p.right;
                    isLeft = false;
                    break;
                }
                p = p.right;
            }
        }

        // p 为删除节点的父节点
        p = path[depth];
        if (isLeft) {
            p.left = swap(del);
        } else {
            p.right = swap(del);
        }

        retracing(path, depth);
        root = balance(root);
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
     * 使用左子树的最大节点（排序后的前驱节点） 替换 删除节点
     *
     * @param del  待删除节点
     * @param pred 前驱节点（左孩子）
     * @return 与删除节点同位置的节点（可能为左子树的最大节点）
     */
    private AvlNode<K, V> swapPredecessor(AvlNode<K, V> del, AvlNode<K, V> pred) {
        AvlNode<K, V>[] path = new AvlNode[del.height];
        int depth = 0;
        while (pred.right != null) {
            depth++;
            path[depth] = pred;
            pred = pred.right;
        }

        if (depth > 0) {
            AvlNode<K, V> parent = path[depth];
            parent.right = pred.left;
            pred.left = del.left;
        }
        pred.right = del.right;

        path[0] = pred;
        retracing(path, depth);
        return balance(pred);
    }

    /**
     * 使用右子树的最小节点（排序后的后驱节点） 替换 删除节点
     *
     * @param del  待删除节点
     * @param succ 右孩子
     * @return 与删除节点同位置的节点（可能为右子树的最小节点）
     */
    private AvlNode<K, V> swapSuccessor(AvlNode<K, V> del, AvlNode<K, V> succ) {
        AvlNode<K, V>[] path = new AvlNode[del.height];
        int depth = 0;
        while (succ.left != null) {
            depth++;
            path[depth] = succ;
            succ = succ.left;
        }

        if (depth > 0) {
            AvlNode<K, V> parent = path[depth];
            parent.left = succ.right;
            succ.right = del.right;
        }
        succ.left = del.left;

        path[0] = succ;
        retracing(path, depth);
        return balance(succ);
    }

    private void retracing(AvlNode<K, V>[] path, int depth) {
        for (int j = depth; j > 0; j--) {
            AvlNode<K, V> p = path[j];
            int height = p.height;
            AvlNode<K, V> pp = path[j - 1];
            if (pp.left == p) {
                pp.left = balance(p);
            } else {
                pp.right = balance(p);
            }
            updateHeight(p);
            // 高度不变，无需继续回溯
            if (p.height == height) {
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

    private static class AvlNode<K, V> implements Map.Entry<K, V> {
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

    private AvlNode<K, V> rotateLeftRight(AvlNode<K, V> node) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    private AvlNode<K, V> rotateRightLeft(AvlNode<K, V> node) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    private byte height(AvlNode<K, V> node) {
        return (node == null) ? -1 : node.height;
    }

    private void updateHeight(AvlNode<K, V> parent) {
        parent.height = (byte) (Math.max(height(parent.left), height(parent.right)) + 1);
    }
}
