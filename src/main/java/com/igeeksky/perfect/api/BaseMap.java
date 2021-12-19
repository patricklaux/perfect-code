package com.igeeksky.perfect.api;

/**
 * Map 接口
 *
 * @author Patrick.Lau
 * @since 1.0.0 2021-11-30
 */
public interface BaseMap<K, V> extends BaseCollection<V> {

    /**
     * 添加键值对
     *
     * @param key   键
     * @param value 值
     */
    void put(K key, V value) throws InterruptedException;

    /**
     * 获取值
     *
     * @param key 键
     * @return 值
     */
    V get(K key);

    /**
     * 删除键值对
     *
     * @param key 键
     * @return 旧值
     */
    V remove(K key);

}
