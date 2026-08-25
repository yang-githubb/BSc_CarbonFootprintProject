package com.example.carbonfootprint;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CarbonCalculatorTest {

    @Test
    public void electricityLowUsage() {
        assertEquals(454.8, CarbonCalculator.amountFor(3, 1), 0.01);
    }

    @Test
    public void electricityHighUsage() {
        assertEquals(9096.0, CarbonCalculator.amountFor(3, 5), 0.01);
    }

    @Test
    public void wasteMediumUsage() {
        assertEquals(4970.0, CarbonCalculator.amountFor(6, 2), 0.01);
    }

    @Test
    public void fuelAverageUsage() {
        assertEquals(2009.68214, CarbonCalculator.amountFor(15, 3), 0.01);
    }

    @Test
    public void unrelatedQuestionContributesNothing() {
        assertEquals(0.0, CarbonCalculator.amountFor(1, 4), 0.0);
    }

    @Test
    public void outOfRangeOptionContributesNothing() {
        assertEquals(0.0, CarbonCalculator.amountFor(3, 9), 0.0);
        assertEquals(0.0, CarbonCalculator.amountFor(6, 0), 0.0);
    }

    @Test
    public void breakdownAccumulatesPerCategoryInTonnes() {
        CarbonCalculator.Breakdown breakdown = new CarbonCalculator.Breakdown();
        breakdown.add(3, 1);   // 454.8 kg electricity
        breakdown.add(6, 1);   // 1988 kg waste
        breakdown.add(15, 1);  // 401.0 kg fuel
        breakdown.add(2, 2);   // no contribution

        assertEquals(0.4548, breakdown.getElectricityTonnes(), 0.0001);
        assertEquals(1.988, breakdown.getWasteTonnes(), 0.0001);
        assertEquals(0.40100, breakdown.getFuelTonnes(), 0.0001);
        assertEquals(2.8438, breakdown.getTotalTonnes(), 0.0001);
    }
}
