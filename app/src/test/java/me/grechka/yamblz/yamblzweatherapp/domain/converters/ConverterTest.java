package me.grechka.yamblz.yamblzweatherapp.domain.converters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexander on 08/08/2017.
 */

public abstract class ConverterTest {

    private static final float EPS = 0.001f;

    private Converter<Integer, Float> converter;

    private float inputValue;
    private float expectedValue;

    public abstract Converter<Integer, Float> obtainConverter();

    public ConverterTest(float inputValue, float expectedValue) {
        this.inputValue = inputValue;
        this.expectedValue = expectedValue;
    }

    @Before
    public void onInit() {
        converter = obtainConverter();
    }

    @Test
    public void converterTest_successfulConvert() {
        assertEquals(expectedValue, converter.convert(inputValue), EPS);
    }
}
