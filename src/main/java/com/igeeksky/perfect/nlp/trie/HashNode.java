package com.igeeksky.perfect.nlp.trie;

import java.util.Map;

/**
 * @author Patrick.Lau
 * @since 0.0.4 2021-12-06
 */
public class HashNode<V> {
    // 字符
    char c;

    // 值
    V val;

    // 兄弟节点
    Map<Character, HashNode<V>> kids;
}
