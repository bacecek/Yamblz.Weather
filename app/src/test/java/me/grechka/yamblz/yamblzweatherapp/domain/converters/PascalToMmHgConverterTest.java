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
public class PascalToMmHgConverterTest extends ConverterTest {

    public PascalToMmHgConverterTest(float inputValue, float expectedValue) {
        super(inputValue, expectedValue);
    }

    @Override
    public Converter<Integer, Float> obtainConverter() {
        return new PascalToMmHgConverter();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {  1010.0f, 757.5f }, { 900.0f, 675.0f }
        });
    }
}