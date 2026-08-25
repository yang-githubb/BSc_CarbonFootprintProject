package com.example.carbonfootprint;

/**
 * Converts survey answers into kg of CO2 equivalent.
 *
 * Emission factors:
 *  - electricity: 0.758 kg CO2e per kWh (Malaysian grid average)
 *  - fuel: 2.34502 kg CO2e per litre of petrol
 *  - waste: 0.497 kg CO2e per kg of municipal waste
 *
 * The per-option quantities are the yearly consumption estimates each survey
 * option represents (kWh for question 3, kg of waste for question 6, litres
 * of fuel for question 15).
 */
public final class CarbonCalculator {

    public static final int QUESTION_ELECTRICITY = 3;
    public static final int QUESTION_WASTE = 6;
    public static final int QUESTION_FUEL = 15;

    private static final double ELECTRICITY_FACTOR = 0.758;
    private static final double FUEL_FACTOR = 2.34502;
    private static final double WASTE_FACTOR = 0.497;

    private static final double[] ELECTRICITY_KWH = {600, 1800, 3000, 5400, 12000};
    private static final double[] WASTE_KG = {4000, 10000, 70000};
    private static final double[] FUEL_LITRES = {171, 514, 857, 1200, 1714};

    private CarbonCalculator() {
    }

    /**
     * Returns the kg CO2e contribution of one answer, or 0 for questions and
     * options that do not feed into the footprint calculation.
     */
    public static double amountFor(int questionId, int optionIndex) {
        switch (questionId) {
            case QUESTION_ELECTRICITY:
                return quantity(ELECTRICITY_KWH, optionIndex) * ELECTRICITY_FACTOR;
            case QUESTION_WASTE:
                return quantity(WASTE_KG, optionIndex) * WASTE_FACTOR;
            case QUESTION_FUEL:
                return quantity(FUEL_LITRES, optionIndex) * FUEL_FACTOR;
            default:
                return 0;
        }
    }

    private static double quantity(double[] options, int optionIndex) {
        int i = optionIndex - 1;
        return (i >= 0 && i < options.length) ? options[i] : 0;
    }

    /** Accumulates answers into a per-category footprint, in kg CO2e. */
    public static final class Breakdown {
        private double electricityKg;
        private double fuelKg;
        private double wasteKg;

        public void add(int questionId, int optionIndex) {
            double amount = amountFor(questionId, optionIndex);
            switch (questionId) {
                case QUESTION_ELECTRICITY:
                    electricityKg += amount;
                    break;
                case QUESTION_WASTE:
                    wasteKg += amount;
                    break;
                case QUESTION_FUEL:
                    fuelKg += amount;
                    break;
            }
        }

        public double getElectricityTonnes() {
            return electricityKg / 1000;
        }

        public double getFuelTonnes() {
            return fuelKg / 1000;
        }

        public double getWasteTonnes() {
            return wasteKg / 1000;
        }

        public double getTotalTonnes() {
            return (electricityKg + fuelKg + wasteKg) / 1000;
        }
    }
}
