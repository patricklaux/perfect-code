package com.igeeksky.perfect.tree;

import com.igeeksky.perfect.api.BaseMap;
import com.igeeksky.xtool.core.function.tuple.Tuple2;
import com.igeeksky.xtool.core.function.tuple.Tuples;
import com.igeeksky.xtool.core.lang.Assert;
import com.igeeksky.xtool.core.math.IntegerValue;

import java.util.*;

/**
 * @author Patrick.Lau
 * @since 1.0.0 2021-12-27
 */
@SuppressWarnings("unchecked")
public abstract class BinarySearchTree<K extends Comparable<K>, V> implements BaseMap<K, V> {

    protected final Comparator<K> comparator;
    protected final IntegerValue size = new IntegerValue();
    protected Node<K, V> root = null;

    public BinarySearchTree(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public V get(K key) {
        Assert.notNull(key);
        Node<K, V> p = root;
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

    protected int compare(K k1, K k2) {
        return comparator == null ? k1.compareTo(k2)
                : comparator.compare(k1, k2);
    }

    @Override
    public V remove(K key) {
        return null;
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

    public Node<K, V> getRoot() {
        return root;
    }

    static class Node<K, V> implements Map.Entry<K, V> {
        K key;
        V val;
        byte height;
        AvlTree.Node<K, V> left;
        AvlTree.Node<K, V> right;

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

        Node(K key, V value) {
            this.key = key;
            this.val = value;
        }

        @Override
        public String toString() {
            return "{\"height\":" + height +
                    ", \"key\":\"" + key + "\"" +
                    (null != val ? (", \"value\":\"" + val + "\"") : "") +
                    ((null != left) ? (", \"left\":" + left) : "") +
                    ((null != right) ? (", \"right\":" + right) : "") +
                    "}";
        }
    }

    /**
     * 先序遍历(递归实现)
     *
     * @param kvs 用于保存所有的键值对
     */
    public void preorderTraversalR(List<Tuple2<K, V>> kvs) {
        if (root == null) {
            return;
        }
        preorderTraversalR(kvs, root);
    }

    /**
     * 先序遍历(递归实现)
     *
     * @param kvs 用于保存所有的键值对
     * @param p   父节点
     */
    private void preorderTraversalR(List<Tuple2<K, V>> kvs, Node<K, V> p) {
        kvs.add(Tuples.of(p.key, p.val));
        if (p.left != null) {
            preorderTraversalR(kvs, p.left);
        }
        if (p.right != null) {
            preorderTraversalR(kvs, p.right);
        }
    }

    /**
     * 中序遍历(递归实现)
     *
     * @param kvs 用于保存所有的键值对
     */
    public void inorderTraversalR(List<Tuple2<K, V>> kvs) {
        if (root == null) {
            return;
        }
        inorderTraversalR(kvs, root);
    }

    /**
     * 中序遍历(递归实现)
     *
     * @param kvs 用于保存所有的键值对
     * @param p   父节点
     */
    private void inorderTraversalR(List<Tuple2<K, V>> kvs, Node<K, V> p) {
        if (p.left != null) {
            inorderTraversalR(kvs, p.left);
        }
        kvs.add(Tuples.of(p.key, p.val));
        if (p.right != null) {
            inorderTraversalR(kvs, p.right);
        }
    }

    /**
     * 后序遍历(递归实现)
     *
     * @param kvs 用于保存所有的键值对
     */
    public void postorderTraversalR(List<Tuple2<K, V>> kvs) {
        if (root == null) {
            return;
        }
        postorderTraversalR(kvs, root);
    }

    /**
     * 后序遍历(递归实现)
     *
     * @param kvs 用于保存所有的键值对
     * @param p   父节点
     */
    private void postorderTraversalR(List<Tuple2<K, V>> kvs, Node<K, V> p) {
        if (p.left != null) {
            postorderTraversalR(kvs, p.left);
        }
        if (p.right != null) {
            postorderTraversalR(kvs, p.right);
        }
        kvs.add(Tuples.of(p.key, p.val));
    }

    /**
     * 先序遍历（栈实现）
     *
     * @param kvs 用于保存所有的键值对
     */
    public void preorderTraversal(List<Tuple2<K, V>> kvs) {
        if (root == null) {
            return;
        }
        Stack<Node<K, V>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node<K, V> p = stack.pop();
            kvs.add(Tuples.of(p.key, p.val));
            if (p.right != null) {
                stack.push(p.right);
            }
            if (p.left != null) {
                stack.push(p.left);
            }
        }
    }

    /**
     * 中序遍历（栈实现）
     *
     * @param kvs 用于保存所有的键值对
     */
    public void inorderTraversal(List<Tuple2<K, V>> kvs) {
        if (root == null) {
            return;
        }
        Stack<Node<K, V>> stack = new Stack<>();
        Node<K, V> p = root;
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                kvs.add(Tuples.of(p.key, p.val));
                p = p.right;
            }
        }
    }

    public void postorderTraversal(List<Tuple2<K, V>> kvs) {
        if (root == null) {
            return;
        }
        Stack<Node<K, V>> stack = new Stack<>();
        Node<K, V> p = root, prev = null;
        while (p != null || !stack.empty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                if (p.right != null && p.right != prev) {
                    stack.push(p);
                    p = p.right;
                } else {
                    kvs.add(Tuples.of(p.key, p.val));
                    prev = p;
                    p = null;
                }
            }
        }
    }

    public void postorderTraversal1(List<Tuple2<K, V>> kvs) {
        if (root == null) {
            return;
        }
        Stack<Tuple2<Node<K, V>, Integer>> stack = new Stack<>();
        // 1.压入根节点，初始状态为 0
        stack.push(Tuples.of(root, 0));
        while (!stack.isEmpty()) {
            // 2.弹出栈顶元素
            Tuple2<Node<K, V>, Integer> tuple = stack.pop();
            // 3.判断状态
            Node<K, V> p = tuple.getT1();
            if (tuple.getT2() == 1) {
                // 3.1. 如果状态为 1，表示已经遍历右子树，添加键值到结果集（访问节点）
                kvs.add(Tuples.of(p.key, p.val));
            } else {
                // 3.1. 如果状态为 0，表示还未遍历右子树，改变状态为 1 并重新入栈
                stack.push(tuple.mapT2(t2 -> 1));
                if (p.right != null) {
                    // 3.2.右孩子先入栈，初始状态为 0
                    stack.push(Tuples.of(p.right, 0));
                }
                if (p.left != null) {
                    // 3.3.左孩子后入栈，初始状态为 0
                    stack.push(Tuples.of(p.left, 0));
                }
            }
        }
    }

    /**
     * 层序遍历（队列实现）（广度优先遍历）
     *
     * @param kvs 用于保存所有的键值对
     */
    public void levelOrderTraversal(List<Tuple2<K, V>> kvs) {
        if (root == null) {
            return;
        }
        ArrayDeque<Node<K, V>> queue = new ArrayDeque<>(size.get());
        queue.push(root);
        while (!queue.isEmpty()) {
            Node<K, V> p = queue.poll();
            kvs.add(Tuples.of(p.key, p.val));
            if (p.left != null) {
                queue.offer(p.left);
            }
            if (p.right != null) {
                queue.offer(p.right);
            }
        }
    }

    public void morrisPreorderTraversal(List<Tuple2<K, V>> kvs) {
        Node<K, V> p = root, pred;
        while (p != null) {
            if (p.left != null) {
                pred = p.left;
                while (pred.right != null && pred.right != p) {
                    pred = pred.right;
                }
                if (pred.right == null) {
                    kvs.add(Tuples.of(p.key, p.val));
                    pred.right = p;
                    p = p.left;
                } else {
                    pred.right = null;
                    p = p.right;
                }
            } else {
                kvs.add(Tuples.of(p.key, p.val));
                p = p.right;
            }
        }
    }

    public void morrisInorderTraversal(List<Tuple2<K, V>> kvs) {
        Node<K, V> p = root, pred;
        while (p != null) {
            if (p.left != null) {
                pred = p.left;
                while (pred.right != null && pred.right != p) {
                    pred = pred.right;
                }
                if (pred.right == null) {
                    pred.right = p;
                    p = p.left;
                    continue;
                }
                pred.right = null;
            }
            kvs.add(Tuples.of(p.key, p.val));
            p = p.right;
        }
    }

    public void morrisPostorderTraversal(List<Tuple2<K, V>> kvs) {
        Node<K, V> p = root, pred;
        while (p != null) {
            if (p.left != null) {
                pred = p.left;
                while (pred.right != null && pred.right != p) {
                    pred = pred.right;
                }
                if (pred.right == null) {
                    pred.right = p;
                    p = p.left;
                } else {
                    pred.right = null;
                    reverseAdd(kvs, p.left);
                    p = p.right;
                }
            } else {
                p = p.right;
            }
        }
        reverseAdd(kvs, root);
    }

    private void reverseAdd2(List<Tuple2<K, V>> kvs, Node<K, V> head) {
        Stack<Node<K, V>> stack = new Stack<>();
        Node<K, V> next = head;
        while (next != null) {
            stack.push(next);
            next = next.right;
        }
        while (!stack.isEmpty()) {
            next = stack.pop();
            kvs.add(Tuples.of(next.key, next.val));
        }
    }

    private void reverseAdd(List<Tuple2<K, V>> kvs, Node<K, V> node) {
        Node<K, V> tail = reverse(node);
        Node<K, V> next = tail;
        while (next != null) {
            kvs.add(Tuples.of(next.key, next.val));
            next = next.right;
        }
        reverse(tail);
    }

    private Node<K, V> reverse(Node<K, V> node) {
        Node<K, V> prev = null, next;
        while (node != null) {
            next = node.right;
            node.right = prev;
            prev = node;
            node = next;
        }
        return prev;
    }

    /**
     * @param kvs 用于保存所有的键值对
     * @param dfs 是否深度优先遍历：是，深度优先遍历（先序遍历）；否：广度优先遍历（层序遍历）
     */
    public void iddfsPlus(List<Tuple2<K, V>> kvs, boolean dfs) {
        if (root == null) {
            return;
        }
        kvs.add(Tuples.of(root.key, root.val));
        if (dfs) {
            iddfsPlus(kvs, root.height, true);
            return;
        }
        for (int depth = 1; depth <= root.height; depth++) {
            if (!iddfsPlus(kvs, depth, false)) {
                return;
            }
        }
    }

    /**
     * 游标
     *
     * @param kvs 用于保存所有的键值对
     */
    private boolean iddfsPlus(List<Tuple2<K, V>> kvs, int depth, boolean dfs) {
        int threshold = depth - 1, curDep = 0;
        boolean remaining = false;
        Node<K, V>[] path = new Node[depth];
        path[0] = root;
        while (curDep >= 0) {
            Node<K, V> p = path[curDep];
            if (curDep < threshold) {
                Node<K, V> ch = path[curDep + 1];
                if (p.left != null && p.left != ch) {
                    if (p.right != ch) {
                        path[++curDep] = p.left;
                        if (dfs) {
                            kvs.add(Tuples.of(p.left.key, p.left.val));
                        }
                        continue;
                    }
                }
                if (p.right != null && p.right != ch) {
                    path[++curDep] = p.right;
                    if (dfs) {
                        kvs.add(Tuples.of(p.right.key, p.right.val));
                    }
                    continue;
                }
            } else {
                remaining = true;
                if (p.left != null) {
                    kvs.add(Tuples.of(p.left.key, p.left.val));
                }
                if (p.right != null) {
                    kvs.add(Tuples.of(p.right.key, p.right.val));
                }
            }
            --curDep;
        }
        return remaining;
    }

    /**
     * 深度优先
     *
     * @param kvs 用于保存所有的键值对
     */
    public void iddfsPlus(List<Tuple2<K, V>> kvs, int maxDep) {
        Assert.isTrue(maxDep >= 0);
        Node<K, V>[] path = new Node[maxDep + 1];
        int curDep = 0;
        path[0] = root;
        kvs.add(Tuples.of(root.key, root.val));
        while (curDep >= 0) {
            if (curDep < maxDep) {
                Node<K, V> p = path[curDep];
                Node<K, V> ch = path[curDep + 1];
                if (p.left != null && p.left != ch) {
                    if (p.right != ch) {
                        path[++curDep] = p.left;
                        kvs.add(Tuples.of(p.left.key, p.left.val));
                        continue;
                    }
                }
                if (p.right != null && p.right != ch) {
                    path[++curDep] = p.right;
                    kvs.add(Tuples.of(p.right.key, p.right.val));
                    continue;
                }
            }
            --curDep;
        }
    }

    /**
     * 递归 IDDFS（层序遍历）
     *
     * @param kvs 用于保存所有的键值对
     */
    public void iddfsR(List<Tuple2<K, V>> kvs, int maxDep) {
        Assert.isTrue(maxDep >= 0);
        if (root == null) {
            return;
        }
        for (int i = 0; i <= maxDep; i++) {
            if (!dls2(kvs, root, 0, i)) {
                break;
            }
        }
    }

    private boolean dls(List<Tuple2<K, V>> kvs, Node<K, V> node, int depth) {
        if (depth == 0) {
            kvs.add(Tuples.of(node.key, node.val));
            return true;
        } else {
            boolean remaining = false;
            if (null != node.left && dls(kvs, node.left, depth - 1)) {
                remaining = true;
            }
            if (null != node.right && dls(kvs, node.right, depth - 1)) {
                remaining = true;
            }
            return remaining;
        }
    }

    private boolean dls2(List<Tuple2<K, V>> kvs, Node<K, V> node, int curDep, int limitDep) {
        if (curDep < limitDep) {
            boolean remaining = false;
            if (null != node.left && dls2(kvs, node.left, curDep + 1, limitDep)) {
                remaining = true;
            }
            if (null != node.right && dls2(kvs, node.right, curDep + 1, limitDep)) {
                remaining = true;
            }
            return remaining;
        } else {
            kvs.add(Tuples.of(node.key, node.val));
            return true;
        }
    }
}