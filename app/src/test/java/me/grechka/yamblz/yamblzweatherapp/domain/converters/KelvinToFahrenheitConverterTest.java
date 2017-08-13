package me.grechka.yamblz.yamblzweatherapp.domain.converters;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by alexander on 10/08/2017.
 */

@RunWith(Parameterized.class)
public class KelvinToFahrenheitConverterTest extends ConverterTest {

    public KelvinToFahrenheitConverterTest(float inputValue, float expectedValue) {
        super(inputValue, expectedValue);
    }

    @Override
    public Converter<Integer, Float> obtainConverter() {
        return new KelvinToFahrenheitConverter();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {  0.0f, -459.67f }, { 273.0f, 31.73f }
        });
    }
}