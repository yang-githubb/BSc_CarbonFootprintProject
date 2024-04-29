package com.example.carbonfootprint;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CalculatorJunitTest {
    private profile profile;

    @Before
    public void setUp() {
        profile = new profile();
    }

    @Test
    public void testElectricityLowUsage() {
        double result = profile.calculate("3", "1");
        assertEquals(454.8, result, 0.01);
    }

    @Test
    public void testElectricityHighUsage() {
        double result = profile.calculate("3", "5");
        assertEquals(9096.0, result, 0.01);
    }

    @Test
    public void testWasteMediumUsage() {
        double result = profile.calculate("6", "2");
        assertEquals(14910, result, 0.01);
    }

    @Test
    public void testFuelAverageUsage() {
        double result = profile.calculate("15", "3");
        assertEquals(2009.68214, result, 0.01);
    }
}
