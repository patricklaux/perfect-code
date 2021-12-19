package com.igeeksky.perfect.nlp.string;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Patrick.Lau
 * @since 0.0.4 2021-12-18
 */
public class KeyBitsTest {

    @Test
    public void bitAt() {
        Assert.assertEquals(0, KeyBits.bitAt("ab", 31));
    }

    @Test
    public void diffAt() {
        String aa = "aa";
        String ab = "ab";
        Assert.assertEquals(30, KeyBits.diffAt(aa, ab));
    }
}