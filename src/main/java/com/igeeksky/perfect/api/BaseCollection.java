package com.igeeksky.perfect.api;

/**
 * @author Patrick.Lau
 * @since 1.0.0 2021-11-30
 */
public interface BaseCollection<E> {

    /**
     * 包含元素数量
     *
     * @return size
     */
    int size();

    /**
     * 容器是否为空
     *
     * @return true:empty; false:notEmpty
     */
    boolean isEmpty();

    /**
     * 清空所有元素
     */
    void clear();
}
