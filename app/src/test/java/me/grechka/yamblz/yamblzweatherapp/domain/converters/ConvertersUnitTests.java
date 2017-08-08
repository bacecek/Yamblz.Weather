package me.grechka.yamblz.yamblzweatherapp.domain.converters;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by alexander on 08/08/2017.
 */

public class ConvertersUnitTests {

    private static final float EPS = 0.001f;

    @Test
    public void KelvinToCelsiusConverter_convertSuccess_whenInputIs273() {
        Converter<Integer, Float> converter = new KelvinToCelsiusConverter();

        float kelvinTemperature = 273.0f;
        float expectedCelsiusTemperature = -0.15f;

        assertEquals(expectedCelsiusTemperature, converter.convert(kelvinTemperature), EPS);
    }

    @Test
    public void KelvinToCelsiusConverter_convertSuccess_whenInputIs305() {
        Converter<Integer, Float> converter = new KelvinToCelsiusConverter();

        float kelvinTemperature = 305.0f;
        float expectedCelsiusTemperature = 31.85f;

        assertEquals(expectedCelsiusTemperature, converter.convert(kelvinTemperature), EPS);
    }

    @Test
    public void KelvinToFahrenheitConverter_convertSuccess_whenInputIs273() {
        Converter<Integer, Float> converter = new KelvinToFahrenheitConverter();

        float kelvinTemperature = 273.0f;
        float expectedFahrenheitTemperature = 31.73f;

        assertEquals(expectedFahrenheitTemperature, converter.convert(kelvinTemperature), EPS);
    }

    @Test
    public void KelvinToFahrenheitConverter_convertSuccess_whenInputIs305() {
        Converter<Integer, Float> converter = new KelvinToFahrenheitConverter();

        float kelvinTemperature = 305.0f;
        float expectedFahrenheitTemperature = 89.33f;

        assertEquals(expectedFahrenheitTemperature, converter.convert(kelvinTemperature), EPS);
    }

    @Test
    public void MsToKmHConverter_convertSuccess_whenInputIs505() {
        Converter<Integer, Float> converter = new MsToKmHConverter();

        float msSpeed = 5.05f;
        float expectedKmHSpeed = 18.18f;

        assertEquals(expectedKmHSpeed, converter.convert(msSpeed), EPS);
    }

    @Test
    public void MsToKmHConverter_convertSuccess_whenInputIs10879() {
        Converter<Integer, Float> converter = new MsToKmHConverter();

        float msSpeed = 1.0879f;
        float expectedKmHSpeed = 3.91644f;

        assertEquals(expectedKmHSpeed, converter.convert(msSpeed), EPS);
    }

    @Test
    public void PascalToMmHgConverter_convertSuccess_whenInputIs1015() {
        Converter<Integer, Float> converter = new PascalToMmHgConverter();

        float pascalPressure = 1015.0f;
        float expectedMmHgPressure = 761.25f;

        assertEquals(expectedMmHgPressure, converter.convert(pascalPressure), EPS);
    }

    @Test
    public void PascalToMmHgConverter_convertSuccess_whenInputIs1100() {
        Converter<Integer, Float> converter = new PascalToMmHgConverter();

        float pascalPressure = 1100.0f;
        float expectedMmHgPressure = 825.0f;

        assertEquals(expectedMmHgPressure, converter.convert(pascalPressure), EPS);
    }
}
