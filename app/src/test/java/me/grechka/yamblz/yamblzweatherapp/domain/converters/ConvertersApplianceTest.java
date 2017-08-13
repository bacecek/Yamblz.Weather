package me.grechka.yamblz.yamblzweatherapp.domain.converters;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by alexander on 08/08/2017.
 */

public class ConvertersApplianceTest {

    private Converter<Integer, Float> kelvinToCelsius;
    private Converter<Integer, Float> kelvinToFahrenheit;
    private Converter<Integer, Float> msToKmH;
    private Converter<Integer, Float> pascalToMmHg;

    @Before
    public void onInit() {
        kelvinToCelsius = new KelvinToCelsiusConverter();
        kelvinToFahrenheit = new KelvinToFahrenheitConverter();
        msToKmH = new MsToKmHConverter();
        pascalToMmHg = new PascalToMmHgConverter();
    }

    @Test
    public void KelvinToFahrenheitConverter_applied_whenModeIsFahrenheit() {
        assertTrue(kelvinToFahrenheit.isApplicable(ConvertersConfig.TEMPERATURE_FAHRENHEIT));
    }

    @Test
    public void KelvinToFahrenheitConverter_notApplied_whenModeIsCelsius() {
        assertFalse(kelvinToFahrenheit.isApplicable(ConvertersConfig.TEMPERATURE_CELSIUS));
    }

    @Test
    public void KelvinToCelsiusConverter_applied_whenModeIsCelsius() {
        assertTrue(kelvinToCelsius.isApplicable(ConvertersConfig.TEMPERATURE_CELSIUS));
    }

    @Test
    public void KelvinToCelsiusConverter_notApplied_whenModeIsFahrenheit() {
        assertFalse(kelvinToCelsius.isApplicable(ConvertersConfig.TEMPERATURE_FAHRENHEIT));
    }

    @Test
    public void MsToKmHConverter_applied_whenModeIsKmH() {
        assertTrue(msToKmH.isApplicable(ConvertersConfig.SPEED_KMH));
    }

    @Test
    public void MsToKmHConverter_notApplied_whenModeIsMs() {
        assertFalse(msToKmH.isApplicable(ConvertersConfig.SPEED_MS));
    }

    @Test
    public void PascalToMmHgConverter_applied_whenModeIsMmHg() {
        assertTrue(pascalToMmHg.isApplicable(ConvertersConfig.PRESSURE_MMHG));
    }

    @Test
    public void PascalToMmHgConverter_notApplied_whenModeIsPascal() {
        assertFalse(pascalToMmHg.isApplicable(ConvertersConfig.PRESSURE_PASCAL));
    }
}
