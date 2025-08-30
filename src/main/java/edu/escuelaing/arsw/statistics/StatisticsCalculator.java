package edu.escuelaing.arsw.statistics;

import java.util.List;

/**
 * Utility class for calculating statistical measures like mean and standard deviation.
 * Uses the formulas specified in the assignment requirements.
 * 
 * @author Diego Cardenas
 * @version 1.0
 */
public class StatisticsCalculator {
    
    /**
     * Calculates the mean (average) of a list of numbers.
     * Formula: mean = (Σ xi) / n
     * where xi are the individual values and n is the number of values.
     * 
     * @param numbers the list of numbers to calculate mean for
     * @return the mean of the numbers
     * @throws IllegalArgumentException if the list is null or empty
     */
    public static double calculateMean(List<Double> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }
        
        double sum = 0.0;
        for (Double number : numbers) {
            if (number == null) {
                throw new IllegalArgumentException("List cannot contain null values");
            }
            sum += number;
        }
        
        return sum / numbers.size();
    }
    
    /**
     * Calculates the standard deviation of a list of numbers.
     * Formula: s = √[(Σ (xi - x̄)²) / (n - 1)]
     * where xi are the individual values, x̄ is the mean, and n is the number of values.
     * 
     * @param numbers the list of numbers to calculate standard deviation for
     * @return the standard deviation of the numbers
     * @throws IllegalArgumentException if the list is null, empty, or has less than 2 elements
     */
    public static double calculateStandardDeviation(List<Double> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }
        
        if (numbers.size() < 2) {
            throw new IllegalArgumentException("Standard deviation requires at least 2 values");
        }
        
        double mean = calculateMean(numbers);
        double sumOfSquaredDifferences = 0.0;
        
        for (Double number : numbers) {
            if (number == null) {
                throw new IllegalArgumentException("List cannot contain null values");
            }
            double difference = number - mean;
            sumOfSquaredDifferences += difference * difference;
        }
        
        double variance = sumOfSquaredDifferences / (numbers.size() - 1);
        return Math.sqrt(variance);
    }
    
    /**
     * Data class to hold statistical results.
     */
    public static class StatisticsResult {
        private final double mean;
        private final double standardDeviation;
        
        public StatisticsResult(double mean, double standardDeviation) {
            this.mean = mean;
            this.standardDeviation = standardDeviation;
        }
        
        /**
         * Gets the mean value.
         * @return the mean
         */
        public double getMean() {
            return mean;
        }
        
        /**
         * Gets the standard deviation value.
         * @return the standard deviation
         */
        public double getStandardDeviation() {
            return standardDeviation;
        }
        
        @Override
        public String toString() {
            return String.format("Mean: %.2f, Standard Deviation: %.2f", mean, standardDeviation);
        }
    }
    
    /**
     * Calculates both mean and standard deviation for a list of numbers.
     * 
     * @param numbers the list of numbers to calculate statistics for
     * @return a StatisticsResult containing both mean and standard deviation
     * @throws IllegalArgumentException if the list is null, empty, or invalid
     */
    public static StatisticsResult calculateStatistics(List<Double> numbers) {
        double mean = calculateMean(numbers);
        double standardDeviation = calculateStandardDeviation(numbers);
        return new StatisticsResult(mean, standardDeviation);
    }
}