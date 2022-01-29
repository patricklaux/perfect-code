package com.igeeksky.perfect.tree;

import com.igeeksky.perfect.api.BaseMap;
import com.igeeksky.xtool.core.function.tuple.Pair;
import com.igeeksky.xtool.core.function.tuple.Pairs;
import com.igeeksky.xtool.core.function.tuple.Tuple2;
import com.igeeksky.xtool.core.function.tuple.Tuples;
import com.igeeksky.xtool.core.lang.Assert;
import com.igeeksky.xtool.core.math.IntegerValue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author Patrick.Lau
 * @since 1.0.0 2022-01-13
 */
@SuppressWarnings("unchecked")
public class BTree<K extends Comparable<K>, V> implements BaseMap<K, V> {

    private final int maxOrder;
    private final int median;
    private final int maxElement;
    private final Comparator<K> comparator;
    private final IntegerValue size = new IntegerValue();
    private Node<K, V> root = null;

    public BTree(int order) {
        this(order, null);
    }

    public BTree(int order, Comparator<K> comparator) {
        // 预留 1个空位用于处理上溢
        this.maxOrder = order + 1;
        this.median = Math.round((float) order / 2) - 1;
        this.maxElement = order - 1;
        this.comparator = comparator;
    }

    @Override
    public void put(K key, V value) {
        Assert.notNull(key);
        Assert.notNull(value);
        if (root == null) {
            root = new Node<>(maxOrder, Pairs.of(key, value));
            size.increment();
            return;
        }
        Tuple2<Integer, Node<K, V>> tuple2 = search(key);
        int pos = tuple2.getT1();
        Node<K, V> x = tuple2.getT2();
        if (pos >= 0) {
            // 1. 当前节点已存在该Key
            x.setItem(pos, Pairs.of(key, value));
            return;
        }
        size.increment();
        // 解决上溢问题
        solveOverflow(x, Pairs.of(key, value));
    }

    private void solveOverflow(Node<K, V> x, Pair<K, V> item) {
        x.addItem(findPosition(x, item.getKey()), item);
        while (x.size > maxElement) {
            Node<K, V> p = x.parent;
            // 2. 当前节点为 root
            item = x.items[median];
            int pos = 0;
            if (p == null) {
                root = p = new Node<>(maxOrder);
            } else {
                pos = findPosition(p, item.getKey());
            }
            p.addItem(pos, item);
            split(p, x, pos);
            x = p;
        }
    }

    /**
     * 上溢节点分裂为两个节点
     *
     * @param p   父节点
     * @param x   上溢生成的临时节点
     * @param pos 索引位置
     */
    private void split(Node<K, V> p, Node<K, V> x, int pos) {
        Node<K, V> lc = new Node<K, V>(maxOrder).setItems(x, 0, median);
        Node<K, V> rc = new Node<K, V>(maxOrder).setItems(x, median + 1, maxElement - median);
        p.setChild(pos, lc).addChild(pos + 1, rc);
    }

    private int findPosition(Node<K, V> x, K key) {
        int m = x.size / 2, upper = x.size, lower = 0;
        while (m >= lower && m < upper) {
            int cmp = compare(x.items[m].getKey(), key);
            if (cmp > 0) {
                upper = m;
                m = m - Math.round((float) (m - lower) / 2);
            } else if (cmp < 0) {
                lower = m;
                m = m + Math.round((float) (upper - m) / 2);
            } else {
                return m;
            }
        }
        return m;
    }

    @Override
    public V get(K key) {
        Assert.notNull(key);
        Tuple2<Integer, Node<K, V>> tuple2 = search(key);
        int pos = tuple2.getT1();
        Node<K, V> x = tuple2.getT2();
        return (pos >= 0) ? x.items[pos].getValue() : null;
    }

    /**
     * 查找键所在的节点 及 键在该节点的索引位置（二分查找）
     *
     * @param key 键
     * @return 返回二元组：1.索引位置(-1表示该节点不包含该键)；2.节点
     */
    private Tuple2<Integer, Node<K, V>> search(K key) {
        Node<K, V> p = root;
        while (true) {
            int m = p.size / 2, upper = p.size, lower = 0;
            while (m >= lower && m < upper) {
                int cmp = compare(p.items[m].getKey(), key);
                if (cmp > 0) {
                    upper = m;
                    m = m - Math.round((float) (m - lower) / 2);
                } else if (cmp < 0) {
                    lower = m;
                    m = m + Math.round((float) (upper - m) / 2);
                } else {
                    return Tuples.of(m, p);
                }
            }
            if (p.isLowestLevel()) {
                return Tuples.of(-1, p);
            }
            p = p.children[m];
        }
    }

    @Override
    public V remove(K key) {
        Assert.notNull(key);
        Tuple2<Integer, Node<K, V>> tuple2 = search(key);
        int pos = tuple2.getT1();
        if (pos < 0) {
            return null;
        }
        size.decrement();
        Node<K, V> x = tuple2.getT2();
        V oldVal = x.items[pos].getValue();
        if (x.isLowestLevel()) {
            x.deleteItem(pos);
        } else {
            x = swap(x, pos);
        }
        solveUnderflow(x);
        return oldVal;
    }

    /**
     * 与前驱交换键
     *
     * @param x   待删除键的节点
     * @param pos 键的索引位置
     * @return 前驱节点
     */
    private Node<K, V> swap(Node<K, V> x, int pos) {
        Node<K, V> pred = x.children[pos];
        while (!pred.isLowestLevel()) {
            pred = pred.children[pred.size];
        }
        int swapIndex = pred.size - 1;
        x.items[pos] = pred.items[swapIndex];
        pred.deleteItem(swapIndex);
        return pred;
    }

    /**
     * 解决下溢问题
     *
     * @param x 叶子节点
     */
    private void solveUnderflow(Node<K, V> x) {
        while (x.size < median) {
            // 1. 旋转
            Node<K, V> p = x.parent;
            if (p == null) {
                return;
            }
            int pos = position(p, x);
            if (pos > 0) {
                Node<K, V> sl = p.children[pos - 1];
                if (sl.size > median) {
                    rotateRight(p, x, sl, pos);
                    return;
                }
            }
            if (pos < p.size) {
                Node<K, V> sr = p.children[pos + 1];
                if (sr.size > median) {
                    rotateLeft(p, x, sr, pos);
                    return;
                }
            }
            // 2. 合并
            if (pos > 0) {
                mergeLeft(p, x, p.children[pos - 1], pos);
            } else {
                mergeRight(p, x, p.children[pos + 1], pos);
            }
            x = p;
        }
    }

    private int position(Node<K, V> p, Node<K, V> x) {
        for (int i = 0; i <= p.size; i++) {
            if (x == p.children[i]) {
                return i;
            }
        }
        return -1;
    }

    void rotateRight(Node<K, V> p, Node<K, V> x, Node<K, V> sl, int pos) {
        x.addItem(0, p.items[pos]);
        p.setItem(pos, sl.items[sl.size - 1]);
        sl.deleteItem(sl.size - 1);
        x.addChild(0, sl.children[sl.size]);
        sl.deleteChild(sl.size);
    }

    void rotateLeft(Node<K, V> p, Node<K, V> x, Node<K, V> sr, int pos) {
        x.addItem(x.size, p.items[pos]);
        p.setItem(pos, sr.items[0]);
        sr.deleteItem(0);
        x.addChild(x.size, sr.children[0]);
        sr.deleteChild(0);
    }

    void mergeRight(Node<K, V> p, Node<K, V> x, Node<K, V> sr, int pos) {
        x.addItem(x.size, p.items[pos]);
        p.deleteItem(pos);
        p.deleteChild(pos + 1);
        x.merge(sr);
    }

    void mergeLeft(Node<K, V> p, Node<K, V> x, Node<K, V> sl, int pos) {
        sl.addItem(sl.size, p.items[pos - 1]);
        p.deleteItem(pos - 1);
        p.deleteChild(pos);
        sl.merge(x);
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
        return comparator == null ? k1.compareTo(k2) : comparator.compare(k1, k2);
    }

    @Override
    public String toString() {
        if (root != null) {
            return root.toString();
        }
        return "";
    }

    static class Node<K, V> {
        int size;               // 节点当前包含的数据项数量
        Node<K, V> parent;      // 父节点
        Pair<K, V>[] items;     // 数据项数组（每一个数据项是一个键值对）
        Node<K, V>[] children;  // 子节点数组

        Node(int order) {
            this.items = new Pair[order - 1];
            this.children = new Node[order];
        }

        Node(int order, Pair<K, V> item) {
            this.items = new Pair[order - 1];
            this.children = new Node[order];
            this.items[size++] = item;
        }

        void addItem(int pos, Pair<K, V> item) {
            // 元素右移腾出空位
            if (pos < items.length - 1) {
                System.arraycopy(items, pos, items, pos + 1, size - pos);
            }
            setItem(pos, item);
            size++;
        }

        void setItem(int pos, Pair<K, V> item) {
            items[pos] = item;
        }

        Node<K, V> setItems(Node<K, V> t, int pos, int length) {
            System.arraycopy(t.items, pos, this.items, 0, size = length);
            System.arraycopy(t.children, pos, this.children, 0, length + 1);
            Arrays.stream(children).filter(Objects::nonNull).forEach(ch -> ch.setParent(this));
            return this;
        }

        void deleteItem(int pos) {
            if (pos < items.length - 1) {
                System.arraycopy(items, pos + 1, items, pos, size - 1 - pos);
                items[size - 1] = null;
            } else {
                items[pos] = null;
            }
            size--;
        }

        void addChild(int pos, Node<K, V> ch) {
            // 元素右移腾出空位
            int last = children.length - 1;
            if (pos < last) {
                System.arraycopy(children, pos, children, pos + 1, last - pos);
            }
            setChild(pos, ch);
        }

        Node<K, V> setChild(int index, Node<K, V> ch) {
            this.children[index] = ch;
            if (ch != null) {
                ch.setParent(this);
            }
            return this;
        }

        void deleteChild(int pos) {
            if (pos < children.length - 1) {
                System.arraycopy(children, pos + 1, children, pos, children.length - 1 - pos);
                children[children.length - 1] = null;
            } else {
                children[pos] = null;
            }
        }

        void setParent(Node<K, V> parent) {
            this.parent = parent;
        }

        void merge(Node<K, V> s) {
            System.arraycopy(s.items, 0, this.items, size, s.size);
            System.arraycopy(s.children, 0, this.children, size, s.size + 1);
            size += s.size;
        }

        boolean isLowestLevel() {
            return children[0] == null;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"size\":" + size +
                    ", \"items\":" + arrayToString(items) +
                    (isLowestLevel() ? "" : ", \"children\":" + arrayToString(children)) +
                    "}";
        }

        private String arrayToString(Object[] objects) {
            int last = 0;
            for (; last < objects.length; last++) {
                if (objects[last] == null) {
                    break;
                }
            }
            if (last == 0) {
                return "[]";
            }
            if (last == objects.length) {
                return Arrays.toString(objects);
            }
            Object[] temp = new Object[last];
            System.arraycopy(objects, 0, temp, 0, last);
            return Arrays.toString(temp);
        }
    }
}