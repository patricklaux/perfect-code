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

    private final int order;
    private final int middle;
    private final int maxElement;
    private final Comparator<K> comparator;
    private final IntegerValue size = new IntegerValue();
    private Node<K, V> root = null;

    public BTree(int order) {
        this(order, null);
    }

    public BTree(int order, Comparator<K> comparator) {
        this.order = order;
        this.middle = (order - 1) / 2;
        this.maxElement = order - 1;
        this.comparator = comparator;
    }

    @Override
    public void put(K key, V value) {
        Assert.notNull(key);
        Assert.notNull(value);
        if (root == null) {
            root = new Node<>(order, key, value);
            size.increment();
            return;
        }
        Tuple2<Integer, Node<K, V>> tuple2 = search(key);
        int pos = tuple2.getT1();
        Node<K, V> x = tuple2.getT2();
        if (pos >= 0) {
            x.items[pos] = Pairs.of(key, value);
            return;
        }
        size.increment();
        // 无上溢问题，直接添加键值对
        if (x.size < maxElement) {
            x.addItem(findPosition(x, key), key, value);
            return;
        }
        // 解决上溢问题
        solveOverflow(x, key, value);
    }

    private void solveOverflow(Node<K, V> x, K key, V value) {
        Node<K, V> t = createTempNode(x, key, value);
        while (true) {
            Node<K, V> p = x.parent;
            // 2. 当前节点为 root
            Pair<K, V> item = t.items[middle];
            if (p == null) {
                root = new Node<>(order, item);
                split(root, t, 0);
                return;
            }
            // 3. 父节点可以容纳上溢的键值对
            if (p.size < maxElement) {
                int pos = findPosition(p, item.getKey());
                p.addItem(pos, item.getKey(), item.getValue());
                split(p, t, pos);
                return;
            }
            // 4. 父节点无法容纳上溢的键值对
            t = createTempNode(p, t);
            x = p;
        }
    }

    /**
     * 上溢节点分裂为两个节点
     *
     * @param p   父节点
     * @param t   上溢生成的临时节点
     * @param pos 索引位置
     */
    private void split(Node<K, V> p, Node<K, V> t, int pos) {
        Node<K, V> lc = new Node<K, V>(order).setItems(t, 0, middle);
        Node<K, V> rc = new Node<K, V>(order).setItems(t, middle + 1, maxElement - middle);
        p.setChild(pos, lc).addChild(pos + 1, rc);
    }

    /**
     * 上溢的叶子节点 生成 临时节点
     *
     * @param x     叶子节点
     * @param key   键
     * @param value 值
     * @return 临时节点
     */
    private Node<K, V> createTempNode(Node<K, V> x, K key, V value) {
        int index = findPosition(x, key);
        Node<K, V> temp = new Node<>(order + 1);
        if (index > 0) {
            System.arraycopy(x.items, 0, temp.items, 0, index);
        }
        temp.items[index] = Pairs.of(key, value);
        if (index < maxElement) {
            System.arraycopy(x.items, index, temp.items, index + 1, maxElement - index);
        }
        return temp;
    }

    /**
     * 非叶节点无法容纳上溢的键，创建新的临时节点
     *
     * @param p 父节点
     * @param t 临时节点
     * @return 新的临时节点
     */
    private Node<K, V> createTempNode(Node<K, V> p, Node<K, V> t) {
        Pair<K, V> item = t.items[middle];
        int pos = findPosition(p, item.getKey());
        Node<K, V> temp = new Node<>(order + 1);
        if (pos > 0) {
            System.arraycopy(p.items, 0, temp.items, 0, pos);
            System.arraycopy(p.children, 0, temp.children, 0, pos);
        }
        temp.items[pos] = item;
        temp.children[pos] = new Node<K, V>(order).setItems(t, 0, middle).setParent(p);
        temp.children[pos + 1] = new Node<K, V>(order).setItems(t, middle + 1, maxElement - middle).setParent(p);
        if (pos < maxElement) {
            System.arraycopy(p.items, pos, temp.items, pos + 1, maxElement - pos);
            System.arraycopy(p.children, pos + 1, temp.children, pos + 2, maxElement - pos);
        }
        return temp;
    }

    private int findPosition(Node<K, V> x, K key) {
        Pair<K, V>[] items = x.items;
        for (int i = 0; i < maxElement; i++) {
            Pair<K, V> item = items[i];
            if (item == null || compare(item.getKey(), key) > 0) {
                return i;
            }
        }
        return maxElement;
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
     * 查找键所在的节点 及 键在该节点的索引位置
     *
     * @param key 键
     * @return 返回二元组：1.索引位置(-1表示该节点不包含该键)；2.节点
     */
    private Tuple2<Integer, Node<K, V>> search(K key) {
        Node<K, V> p = root;
        while (true) {
            if (p.isLeaf()) {
                for (int i = 0; i < p.size; i++) {
                    Pair<K, V> item = p.items[i];
                    if (null == item) {
                        return Tuples.of(-1, p);
                    }
                    int cmp = compare(item.getKey(), key);
                    if (cmp > 0) {
                        return Tuples.of(-1, p);
                    } else if (cmp == 0) {
                        return Tuples.of(i, p);
                    }
                }
                return Tuples.of(-1, p);
            }
            for (int i = 0; i <= p.size; i++) {
                if (i == p.size) {
                    p = p.children[i];
                    break;
                }
                int cmp = compare(p.items[i].getKey(), key);
                if (cmp > 0) {
                    p = p.children[i];
                    break;
                }
                if (cmp == 0) {
                    return Tuples.of(i, p);
                }
            }
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
        if (x.isLeaf()) {
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
        while (!pred.isLeaf()) {
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
        if (x.size >= middle) {
            return;
        }
        // 1. 旋转
        Node<K, V> p = x.parent;
        if (p == null) {
            return;
        }
        int pos = position(p, x);
        if (pos > 0) {
            Node<K, V> sl = p.children[pos - 1];
            if (sl.size > middle) {
                rotateRight(p, x, sl, pos);
                return;
            }
        }
        if (pos < p.size) {
            Node<K, V> sr = p.children[pos + 1];
            if (sr.size > middle) {
                rotateLeft(p, x, sr, pos);
                return;
            }
        }
        // 2. 合并
        if (pos >= middle) {
            mergeLeft(p, x, p.children[pos - 1], pos);
        } else {
            mergeRight(p, x, p.children[pos + 1], pos);
        }
        solveUnderflow(p);
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

    private int position(Node<K, V> p, Node<K, V> x) {
        for (int i = 0; i <= p.size; i++) {
            if (x == p.children[i]) {
                return i;
            }
        }
        return -2;
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

    static class Node<K extends Comparable<K>, V> {

        int size;
        Node<K, V> parent;
        Pair<K, V>[] items;
        Node<K, V>[] children;

        Node(int order) {
            this.items = new Pair[order - 1];
            this.children = new Node[order];
        }

        Node(int order, Pair<K, V> item) {
            this.items = new Pair[order - 1];
            this.children = new Node[order];
            this.items[size++] = item;
        }

        Node(int order, K key, V value) {
            this.items = new Pair[order - 1];
            this.children = new Node[order];
            this.items[size++] = Pairs.of(key, value);
        }

        void addItem(int pos, K key, V value) {
            addItem(pos, Pairs.of(key, value));
        }

        void addItem(int pos, Pair<K, V> item) {
            // 元素右移腾出空位
            if (pos < size) {
                System.arraycopy(items, pos, items, pos + 1, size - pos);
            }
            setItem(pos, item);
        }

        void setItem(int pos, Pair<K, V> item) {
            items[pos] = item;
            size++;
        }

        void deleteItem(int pos) {
            if (pos < size - 1) {
                System.arraycopy(items, pos + 1, items, pos, size - 1 - pos);
                items[items.length - 1] = null;
            } else {
                items[pos] = null;
            }
            size--;
        }

        void addChild(int index, Node<K, V> ch) {
            // 元素右移腾出空位
            int lastIndex = items.length;
            if (index < lastIndex) {
                System.arraycopy(children, index, children, index + 1, lastIndex - index);
            }
            setChild(index, ch);
        }

        Node<K, V> setChild(int index, Node<K, V> ch) {
            this.children[index] = ch.setParent(this);
            return this;
        }

        public void deleteChild(int pos) {
            if (pos < children.length - 1) {
                System.arraycopy(children, pos + 1, children, pos, children.length - 1 - pos);
                children[children.length - 1] = null;
            } else {
                children[pos] = null;
            }
        }

        Node<K, V> setParent(Node<K, V> parent) {
            this.parent = parent;
            return this;
        }

        void merge(Node<K, V> s) {
            System.arraycopy(s.items, 0, this.items, size, s.size);
            System.arraycopy(s.children, 0, this.children, size, s.size + 1);
        }

        boolean isLeaf() {
            return children[0] == null;
        }

        Node<K, V> setItems(Node<K, V> t, int pos, int length) {
            System.arraycopy(t.items, pos, this.items, 0, size = length);
            System.arraycopy(t.children, pos, this.children, 0, length + 1);
            Arrays.stream(children).filter(Objects::nonNull).forEach(ch -> ch.setParent(this));
            return this;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"items\":" + arrayToString(items) +
                    (isLeaf() ? "" : ", \"children\":" + arrayToString(children)) +
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
