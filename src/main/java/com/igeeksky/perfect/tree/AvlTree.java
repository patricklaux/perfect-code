package com.igeeksky.perfect.tree;


import com.igeeksky.xtool.core.lang.Assert;

import java.util.Comparator;

/**
 * AVL 实现
 *
 * @author Patrick.Lau
 * @since 1.0.0 2021-12-01
 */
@SuppressWarnings("unchecked")
public class AvlTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    public AvlTree() {
        super(null);
    }

    public AvlTree(Comparator<K> comparator) {
        super(comparator);
    }

    @Override
    public void put(K key, V value) {
        Assert.notNull(key);
        Assert.notNull(value);
        if (root == null) {
            root = new Node<>(key, value);
            size.set(1);
            return;
        }

        Node<K, V> p = root;
        int depth = 0, maxDepth = root.height + 1;
        // 回溯路径
        Node<K, V>[] path = new Node[maxDepth];
        while (depth < maxDepth) {
            path[depth] = p;
            int cmp = compare(p.key, key);
            if (cmp > 0) {
                if (p.left == null) {
                    p.left = new Node<>(key, value);
                    size.increment();
                    if (p.right != null) {
                        return; // 父节点高度不变，无需回溯调整
                    }
                    break;
                } else {
                    p = p.left;
                    depth++;
                }
            } else if (cmp < 0) {
                if (p.right == null) {
                    p.right = new Node<>(key, value);
                    size.increment();
                    if (p.left != null) {
                        return; // 父节点高度不变，无需回溯调整
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
        root = backtrack(path, depth);
    }

    @Override
    public V remove(K key) {
        Assert.notNull(key);
        if (root == null) {
            return null;
        }
        int depth = 0;
        Node<K, V> del = root;
        // 根节点至删除节点之间的回溯路径
        Node<K, V>[] path = new Node[root.height];
        while (del != null) {
            int cmp = compare(del.key, key);
            if (cmp == 0) {
                size.decrement();
                if (root == del) {
                    root = swap(root);
                    return del.val;
                }
                Node<K, V> p = path[--depth];
                if (p.right == del) {
                    p.right = swap(del);
                } else {
                    p.left = swap(del);
                }
                root = backtrack(path, depth);
                return del.val;
            }
            path[depth] = del;
            del = (cmp > 0) ? del.left : del.right;
            ++depth;
        }
        return null;
    }

    private Node<K, V> swap(Node<K, V> del) {
        Node<K, V> pl = del.left, pr = del.right;
        if (height(pl) >= height(pr)) {
            if (pl != null) {
                // 左子树的高度大于等于右子树：前驱节点交换删除节点
                return swapPredecessor(del, pl);
            }
            //没有子节点
            return null;
        }
        // 右子树的高度大于左子树：后继节点交换删除节点
        return swapSuccessor(del, pr);
    }

    /**
     * 被删除节点 与其 前驱节点 交换位置
     *
     * @param del  待删除节点
     * @param swap 左孩子
     * @return 前驱节点
     */
    private Node<K, V> swapPredecessor(Node<K, V> del, Node<K, V> swap) {
        // 回溯路径
        Node<K, V>[] path = new Node[del.height];
        int depth = 0;
        while (swap.right != null) {
            depth++;
            path[depth] = swap;
            swap = swap.right;
        }

        if (depth > 0) {
            Node<K, V> p = path[depth];
            p.right = swap.left;
            swap.left = del.left;
        }
        swap.right = del.right;
        path[0] = swap;
        // 回溯调整
        return backtrack(path, depth);
    }

    /**
     * 被删除节点 与其 后继节点 交换位置
     *
     * @param del  待删除节点
     * @param swap 右孩子
     * @return 后继节点
     */
    private Node<K, V> swapSuccessor(Node<K, V> del, Node<K, V> swap) {
        // 回溯路径
        Node<K, V>[] path = new Node[del.height];
        int depth = 0;
        while (swap.left != null) {
            depth++;
            path[depth] = swap;
            swap = swap.left;
        }

        if (depth > 0) {
            Node<K, V> parent = path[depth];
            parent.left = swap.right;
            swap.right = del.right;
        }
        swap.left = del.left;
        path[0] = swap;
        // 回溯
        return backtrack(path, depth);
    }

    /**
     * 回溯
     *
     * @param path  回溯路径
     * @param depth 深度
     */
    private Node<K, V> backtrack(Node<K, V>[] path, int depth) {
        for (int j = depth; j > 0; j--) {
            Node<K, V> p = path[j];
            Node<K, V> pp = path[j - 1];
            int height = p.height, newHeight;
            updateHeight(p);
            if (pp.left == p) {
                pp.left = balance(p);
                newHeight = pp.left.height;
            } else {
                pp.right = balance(p);
                newHeight = pp.right.height;
            }
            if (newHeight == height) {
                break;  // 高度不变，停止回溯
            }
        }
        updateHeight(path[0]);
        return balance(path[0]);
    }

    private Node<K, V> balance(Node<K, V> parent) {
        int factor = balanceFactor(parent);
        if (factor >= 2) {
            if (balanceFactor(parent.left) <= -1) {
                // LR(2, -1)：先左旋后右旋
                return rotateLeftRight(parent);
            }
            // LL(2, 1) 或 L(2, 0)：右旋
            return rotateRight(parent);
        } else if (factor <= -2) {
            if (balanceFactor(parent.right) >= 1) {
                // RL(-2, 1)：先右旋后左旋
                return rotateRightLeft(parent);
            }
            // RR(-2, -1) 或 R(-2, 0)：左旋
            return rotateLeft(parent);
        } else {
            return parent;
        }
    }

    private int balanceFactor(Node<K, V> parent) {
        return height(parent.left) - height(parent.right);
    }

    private Node<K, V> rotateLeft(Node<K, V> parent) {
        Node<K, V> right = parent.right;
        parent.right = right.left;
        right.left = parent;
        updateHeight(parent);
        updateHeight(right);
        return right;
    }

    private Node<K, V> rotateRight(Node<K, V> parent) {
        Node<K, V> left = parent.left;
        parent.left = left.right;
        left.right = parent;
        updateHeight(parent);
        updateHeight(left);
        return left;
    }

    private Node<K, V> rotateLeftRight(Node<K, V> parent) {
        parent.left = rotateLeft(parent.left);
        return rotateRight(parent);
    }

    private Node<K, V> rotateRightLeft(Node<K, V> parent) {
        parent.right = rotateRight(parent.right);
        return rotateLeft(parent);
    }

    private byte height(Node<K, V> node) {
        return (node == null) ? -1 : node.height;
    }

    /**
     * 左孩子和右孩子的高度取大者+1 为父节点的高度
     *
     * @param parent 父节点
     */
    private void updateHeight(Node<K, V> parent) {
        parent.height = (byte) (Math.max(height(parent.left), height(parent.right)) + 1);
    }
}