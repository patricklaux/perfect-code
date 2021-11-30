package com.igeeksky.perfect.api;

/**
 * 集合接口
 *
 * @author Patrick.Lau
 * @since 1.0.0 2021-12-01
 */
public interface BaseSet<E> extends BaseCollection<E> {

    void add(E e);

    void remove(E e);
}
