package me.grechka.yamblz.yamblzweatherapp.domain.converters;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by alexander on 10/08/2017.
 */

@RunWith(Parameterized.class)
public class KelvinToCelsiusConverterTest extends ConverterTest {

    public KelvinToCelsiusConverterTest(float inputValue, float expectedValue) {
        super(inputValue, expectedValue);
    }

    @Override
    public Converter<Integer, Float> obtainConverter() {
        return new KelvinToCelsiusConverter();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {  273.0f, -0.15f }, { 305.0f, 31.85f },
                { 0.0f, -273.15f }, { 33.5f, -239.65f },
                { 1000.0f, 726.85f }, { 146.0f, -127.15f }
        });
    }
}