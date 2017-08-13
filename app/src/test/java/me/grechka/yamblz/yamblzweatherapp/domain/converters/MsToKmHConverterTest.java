package me.grechka.yamblz.yamblzweatherapp.domain.converters;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by alexander on 10/08/2017.
 */

@RunWith(Parameterized.class)
public class MsToKmHConverterTest extends ConverterTest {

    public MsToKmHConverterTest(float inputValue, float expectedValue) {
        super(inputValue, expectedValue);
    }

    @Override
    public Converter<Integer, Float> obtainConverter() {
        return new MsToKmHConverter();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {  1.0f, 3.6f }, { 2.3f, 8.28f }
        });
    }
}