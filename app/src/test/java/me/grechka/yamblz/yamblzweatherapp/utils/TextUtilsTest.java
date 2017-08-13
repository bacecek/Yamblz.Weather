package me.grechka.yamblz.yamblzweatherapp.utils;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by alexander on 09/08/2017.
 */

public class TextUtilsTest {

    @Test
    public void TextUtils_stringIsNull_returnTrue() {
        assertTrue(TextUtils.isEmpty(null));
    }

    @Test
    public void TextUtils_stringIsEmpty_returnTrue() {
        assertTrue(TextUtils.isEmpty(""));
    }

    @Test
    public void TextUtils_stringIsSpaces_returnTrue() {
        assertTrue(TextUtils.isEmpty("     "));
    }

    @Test
    public void TextUtils_stringNotEmpty_returnFalse() {
        assertFalse(TextUtils.isEmpty("good boy - cowboy"));
    }
}
