package com.igeeksky.perfect.api;

import com.igeeksky.xtool.core.function.tuple.Tuple2;
import com.igeeksky.xtool.core.nlp.Found;

import java.util.List;

/**
 * 字典树接口
 *
 * @author Patrick.Lau
 * @since 1.0.0 2021-11-30
 */
public interface Trie<V> extends BaseMap<String, V> {

    /**
     * 前缀匹配：使用输入字符串的前缀去匹配树中已有的 key（默认最长匹配）
     *
     * <pre>
     *     Trie中已有：ab, abc, abcd, abd, bcd
     *     trie.prefixMatch("abcdef") == [abcd, abcd]
     *     依次会匹配到 ab, abc, abcd，默认返回最长匹配结果：abcd
     * </pre>
     *
     * @param word 待匹配词（不为空且长度大于0）
     * @return 是否匹配到 key。是：返回 key 和 value；否：返回空
     */
    Tuple2<String, V> prefixMatch(String word);

    /**
     * 前缀搜索：输入前缀，返回以此前缀开头的所有 key 及关联的 value（默认最长匹配）
     *
     * <pre>
     *     Trie中已有：ab, abc, abcd, abd, bcd
     *     trie.keysWithPrefix("ab") == [[ab, ab], [abc, abc], [abcd, abcd]]
     * </pre>
     *
     * @param prefix 前缀（不为空且长度大于0）
     * @return 所有包含给定前缀的 key 及关联的 value
     */
    List<Tuple2<String, V>> keysWithPrefix(String prefix);

    /**
     * 包含匹配：输入一段文本，返回文本中包含的 key 及关联的 value 和 key 的起止位置
     * <p>
     * 如果文本中包含有多个 key，那么将这些 keys 和 关联的 values 和 起止位置都返回
     *
     * <pre>
     *     例：
     *     Trie中已有：ab, abc, abcd, abd, bcd
     *     trie.matchAll("xxabcdexx") ==
     *     [{"start":2, "end":3, "key":"ab", "value":"ab"}, {"start":2, "end":4, "key":"abc", "value":"abc"},
     *     {"start":2, "end":5, "key":"abcd", "value":"abcd"}, {"start":3, "end":5, "key":"bcd", "value":"bcd"}]
     * </pre>
     *
     * @param text 文本段（不为空且长度大于0）
     * @return 返回该文本中包含的所有 key 及关联的 value、与及 key 的起止位置
     */
    List<Found<V>> matchAll(String text);

}