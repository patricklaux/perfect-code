package com.igeeksky.perfect.collection;

import com.igeeksky.perfect.api.BaseList;
import com.igeeksky.xtool.core.lang.Assert;

import java.util.Objects;

/**
 * 单链表
 *
 * @author Patrick.Lau
 * @since 1.0.0 2021-11-16
 */
public class SinglyLinkedList<E> implements BaseList<E> {

    private int size;
    private LinkedNode<E> head, tail;

    @Override
    public void add(E e) {
        Assert.notNull(e, "element must not be null");
        LinkedNode<E> node = new LinkedNode<>(e);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        ++size;
    }

    @Override
    public E get(int index) {
        if (tail == null) {
            return null;
        }
        LinkedNode<E> f = head;
        for (int i = 0; i <= index; i++) {
            if (f == null) {
                return null;
            }
            if (i == index) {
                return f.value;
            }
            f = f.next;
        }
        return null;
    }

    @Override
    public void remove(E e) {
        Assert.notNull(e, "element must not be null");
        if (tail == null) {
            return;
        }
        if (Objects.equals(head.value, e)) {
            if (head == tail) {
                head = tail = null;
            } else {
                LinkedNode<E> next = head.next;
                head.next = null;
                head = next;
            }
            --size;
        }

        LinkedNode<E> pred = head, f = head.next;
        while (f != null) {
            if (Objects.equals(f.value, e)) {
                if (f == tail) {
                    tail = pred;
                    tail.next = null;
                } else {
                    pred.next = f.next;
                    f.next = null;
                }
                --size;
                return;
            }
            pred = f;
            f = f.next;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    private static class LinkedNode<E> {
        private final E value;
        private LinkedNode<E> next;

        LinkedNode(E value) {
            this.value = value;
        }
    }
}