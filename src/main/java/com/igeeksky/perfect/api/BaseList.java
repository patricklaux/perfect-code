package com.igeeksky.perfect.api;

/**
 * 列表接口
 *
 * @author Patrick.Lau
 * @since 1.0.0 2021-12-01
 */
public interface BaseList<E> extends BaseCollection<E> {

    void add(E e);

    E get(int index);

    void remove(E e);

}
