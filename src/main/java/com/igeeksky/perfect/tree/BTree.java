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
    private final IntegerValue height = new IntegerValue();
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
        // 1.如果为空树，创建根节点并添加键值对
        if (root == null) {
            root = new Node<>(maxOrder, Pairs.of(key, value));
            size.increment();
            height.increment();
            return;
        }
        // 2.查找键，并判断键是否已存在
        Tuple2<Integer, Node<K, V>> tuple2 = search(key);
        int pos = tuple2.getT1();
        Node<K, V> x = tuple2.getT2();
        Pair<K, V> item = x.items[pos];
        if (item != null && compare(item.getKey(), key) == 0) {
            // 2.1. 当前节点已存在该 Key
            x.setItem(pos, Pairs.of(key, value));
            return;
        }
        // 2.2. 键不在树中，增加树的size
        size.increment();
        // 3.添加键值对到最底层的内部节点
        x.addItem(pos, Pairs.of(key, value));
        // 4. 上溢处理
        solveOverflow(x);
    }

    private void solveOverflow(Node<K, V> x) {
        // 判断当前节点是否上溢（键数量超过允许的最大值）
        while (x.size > maxElement) {
            Node<K, V> p = x.parent;
            // 1.如果出现上溢，提取当前节点最中间的数据项
            Pair<K, V> item = x.items[median];
            int pos = 0;
            if (p == null) {
                // 2.父节点为空，说明当前节点为 root，创建新的根节点
                root = p = new Node<>(maxOrder);
                // 2.1.树高 +1
                height.increment();
            } else {
                // 3.获取中间数据项在父节点中的插入位置
                pos = position(p, x);
            }
            // 4.当前节点最中间的数据项并添加到父节点
            p.addItem(pos, item);
            // 5.当前节点分裂成两个子节点并添加到父节点
            split(p, x, pos);
            // 6.父节点设为当前节点
            x = p;
        }
    }

    /**
     * 上溢节点分裂为两个节点
     *
     * @param p   父节点
     * @param x   上溢节点
     * @param pos 索引位置
     */
    private void split(Node<K, V> p, Node<K, V> x, int pos) {
        Node<K, V> lc = new Node<K, V>(maxOrder).setItems(x, 0, median);
        Node<K, V> rc = new Node<K, V>(maxOrder).setItems(x, median + 1, maxElement - median);
        p.setChild(pos, lc);
        p.addChild(pos + 1, rc);
    }

    @Override
    public V get(K key) {
        Assert.notNull(key);
        if (root == null) {
            return null;
        }
        // 1.先根据 Key 找到包含该 Key 的节点
        Tuple2<Integer, Node<K, V>> tuple2 = search(key);
        int pos = tuple2.getT1();
        Node<K, V> x = tuple2.getT2();
        Pair<K, V> item = x.items[pos];
        // 2.如果 pos 对应的数据项不为空且两个键相同，返回值；否则返回空
        if (item != null && compare(item.getKey(), key) == 0) {
            return item.getValue();
        }
        return null;
    }

    /**
     * 查找键所在的节点 及 键在该节点的索引位置
     *
     * @param key 键
     * @return 返回二元组：(索引位置, 节点)
     */
    private Tuple2<Integer, Node<K, V>> search(K key) {
        Node<K, V> p = root;
        while (true) {
            // 1. 二分查找（m 为中值，upper 为上界，lower 为下界）
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
                    // 1.1.节点包含该键
                    return Tuples.of(m, p);
                }
            }
            // 1.2.节点不包含该键
            // 1.2.1.已到达叶子节点，结束查找
            if (p.isLeaf()) {
                return Tuples.of(m, p);
            }
            // 1.2.2.未到达叶子节点，查找子节点
            p = p.children[m];
        }
    }

    @Override
    public V remove(K key) {
        Assert.notNull(key);
        Tuple2<Integer, Node<K, V>> tuple2 = search(key);
        int pos = tuple2.getT1();
        Node<K, V> x = tuple2.getT2();
        Pair<K, V> item = x.items[pos];
        if (item == null || compare(item.getKey(), key) != 0) {
            return null;
        }
        size.decrement();
        V oldVal = item.getValue();
        if (x.isLeaf()) {
            x.deleteItem(pos);
        } else {
            x = predecessor(x, pos);
        }
        solveUnderflow(x);
        return oldVal;
    }

    /**
     * 与前驱交换数据项
     *
     * @param x   待删除键的节点
     * @param pos 键的索引位置
     * @return 前驱节点
     */
    private Node<K, V> predecessor(Node<K, V> x, int pos) {
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
     * 下溢处理
     *
     * @param x 当前节点
     */
    private void solveUnderflow(Node<K, V> x) {
        while (x.size < median) {
            Node<K, V> p = x.parent;
            if (p == null) {
                // 1.当前节点为根节点，且已无数据项，其唯一子节点设为根节点
                if (x.size == 0) {
                    root = x.children[0];
                    height.decrement();
                }
                return;
            }
            int pos = position(p, x);
            // 2. 旋转
            if (pos > 0) {
                Node<K, V> sl = p.children[pos - 1];
                // 2.1.左兄弟有富余数据项，右旋
                if (sl.size > median) {
                    rotateRight(p, x, sl, pos);
                    return;
                }
            }
            if (pos < p.size) {
                Node<K, V> sr = p.children[pos + 1];
                // 2.2.右兄弟有富余数据项，左旋
                if (sr.size > median) {
                    rotateLeft(p, x, sr, pos);
                    return;
                }
            }
            // 3. 合并
            if (pos > 0) {
                merge(p, p.children[pos - 1], x, pos - 1);
            } else {
                merge(p, x, p.children[pos + 1], pos);
            }
            x = p;
        }
    }

    /**
     * 获取子节点在父节点中的索引位置
     *
     * @param p 父节点
     * @param x 子节点
     * @return 索引位置
     */
    private int position(Node<K, V> p, Node<K, V> x) {
        for (int i = 0; i <= p.size; i++) {
            if (x == p.children[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 右旋
     *
     * @param p   父节点
     * @param x   当前节点
     * @param sl  当前节点的左兄弟
     * @param pos 父节点中用于旋转的数据项索引位置
     */
    void rotateRight(Node<K, V> p, Node<K, V> x, Node<K, V> sl, int pos) {
        x.addItem(0, p.items[pos]);
        p.setItem(pos, sl.items[sl.size - 1]);
        sl.deleteItem(sl.size - 1);
        x.addChild(0, sl.children[sl.size]);
        sl.deleteChild(sl.size);
    }

    /**
     * 左旋
     *
     * @param p   父节点
     * @param x   当前节点
     * @param sr  当前节点的右兄弟
     * @param pos 父节点中用于旋转的数据项索引位置
     */
    void rotateLeft(Node<K, V> p, Node<K, V> x, Node<K, V> sr, int pos) {
        x.addItem(x.size, p.items[pos]);
        p.setItem(pos, sr.items[0]);
        sr.deleteItem(0);
        x.addChild(x.size, sr.children[0]);
        sr.deleteChild(0);
    }

    /**
     * 合并兄弟节点
     *
     * @param p   父节点
     * @param l   左孩子节点
     * @param r   右孩子节点
     * @param pos 父节点中用于合并的数据项索引位置
     */
    void merge(Node<K, V> p, Node<K, V> l, Node<K, V> r, int pos) {
        l.addItem(l.size, p.items[pos]);
        p.deleteItem(pos);
        p.deleteChild(pos + 1);
        l.merge(r);
    }

    @Override
    public int size() {
        return size.get();
    }

    public int height() {
        return height.get();
    }

    @Override
    public boolean isEmpty() {
        return size.get() <= 0;
    }

    @Override
    public void clear() {
        size.set(0);
        height.set(0);
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
        //节点当前包含的键数量
        int size;

        // 父节点
        Node<K, V> parent;

        // 数据项数组（每一个数据项是一个键值对）
        Pair<K, V>[] items;

        // 子节点数组
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

        void setChild(int index, Node<K, V> ch) {
            this.children[index] = ch;
            if (ch != null) {
                ch.setParent(this);
            }
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

        boolean isLeaf() {
            return children[0] == null;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"size\":" + size +
                    ", \"items\":" + arrayToString(items) +
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