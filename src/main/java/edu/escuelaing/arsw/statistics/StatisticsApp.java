package edu.escuelaing.arsw.statistics;

import java.io.IOException;
import java.util.List;

/**
 * Main application class for the Statistics Calculator program.
 * This program calculates the mean and standard deviation of a set of real numbers
 * read from a file, using a custom linked list implementation.
 * 
 * @author Diego Cardenas
 * @version 1.0
 */
public class StatisticsApp {
    
    /**
     * Main method to run the statistics calculator application.
     * 
     * @param args command line arguments - expects the file path as first argument
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java StatisticsApp <file_path>");
            System.err.println("Example: java StatisticsApp data/column1.txt");
            System.exit(1);
        }
        
        String filePath = args[0];
        
        try {
            // Read numbers from file into custom linked list
            CustomLinkedList<Double> numbers = FileDataReader.readNumbersFromFile(filePath);
            
            // Calculate statistics
            StatisticsCalculator.StatisticsResult result = StatisticsCalculator.calculateStatistics(numbers);
            
            // Display results
            System.out.println("Statistics Calculator Results");
            System.out.println("=============================");
            System.out.println("File: " + filePath);
            System.out.println("Number of values: " + numbers.size());
            System.out.printf("Mean: %.2f%n", result.getMean());
            System.out.printf("Standard Deviation: %.2f%n", result.getStandardDeviation());
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numbers: " + e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Processes a file and returns the statistics result.
     * This method is useful for programmatic access and testing.
     * 
     * @param filePath the path to the file containing numbers
     * @return StatisticsResult containing mean and standard deviation
     * @throws IOException if there's an error reading the file
     * @throws NumberFormatException if the file contains invalid numbers
     * @throws IllegalArgumentException if the input is invalid
     */
    public static StatisticsCalculator.StatisticsResult processFile(String filePath) 
            throws IOException, NumberFormatException, IllegalArgumentException {
        
        // Validate file first
        FileDataReader.validateFile(filePath);
        
        // Read numbers from file
        CustomLinkedList<Double> numbers = FileDataReader.readNumbersFromFile(filePath);
        
        // Calculate and return statistics
        return StatisticsCalculator.calculateStatistics(numbers);
    }
    
    /**
     * Processes a list of numbers and returns the statistics result.
     * This method is useful for programmatic access with pre-loaded data.
     * 
     * @param numbers the list of numbers to process
     * @return StatisticsResult containing mean and standard deviation
     * @throws IllegalArgumentException if the input is invalid
     */
    public static StatisticsCalculator.StatisticsResult processNumbers(List<Double> numbers) 
            throws IllegalArgumentException {
        return StatisticsCalculator.calculateStatistics(numbers);
    }
    
    /**
     * Displays help information for using the application.
     */
    public static void displayHelp() {
        System.out.println("Statistics Calculator - Help");
        System.out.println("============================");
        System.out.println();
        System.out.println("This program calculates the mean and standard deviation of numbers read from a file.");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  java -jar statistics-calculator.jar <file_path>");
        System.out.println("  mvn exec:java -Dexec.args=\"<file_path>\"");
        System.out.println();
        System.out.println("File Format:");
        System.out.println("  - Each line should contain one number");
        System.out.println("  - Empty lines are ignored");
        System.out.println("  - Numbers can be integers or decimals");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  java -jar statistics-calculator.jar data/column1.txt");
        System.out.println();
        System.out.println("Output:");
        System.out.println("  The program displays the mean and standard deviation");
        System.out.println("  rounded to 2 decimal places.");
    }
}