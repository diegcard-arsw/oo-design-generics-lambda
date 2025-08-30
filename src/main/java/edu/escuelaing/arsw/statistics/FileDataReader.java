package edu.escuelaing.arsw.statistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for reading numeric data from files.
 * Supports reading numbers from text files with one number per line.
 * 
 * @author Diego Cardenas
 * @version 1.0
 */
public class FileDataReader {
    
    /**
     * Reads numeric data from a file and stores it in a CustomLinkedList.
     * Each line in the file should contain one number.
     * 
     * @param filePath the path to the file to read
     * @return a CustomLinkedList containing the numbers from the file
     * @throws IOException if there's an error reading the file
     * @throws NumberFormatException if a line cannot be parsed as a number
     * @throws IllegalArgumentException if the file path is null or empty
     */
    public static CustomLinkedList<Double> readNumbersFromFile(String filePath) throws IOException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        
        CustomLinkedList<Double> numbers = new CustomLinkedList<>();
        
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }
                
                try {
                    double number = Double.parseDouble(line);
                    numbers.add(number);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException(
                        String.format("Invalid number format at line %d: '%s'", lineNumber, line)
                    );
                }
            }
        }
        
        if (numbers.isEmpty()) {
            throw new IOException("No valid numbers found in file: " + filePath);
        }
        
        return numbers;
    }
    
    /**
     * Reads numeric data from a file using an InputStream.
     * Each line in the stream should contain one number.
     * 
     * @param inputStream the input stream to read from
     * @return a CustomLinkedList containing the numbers from the stream
     * @throws IOException if there's an error reading the stream
     * @throws NumberFormatException if a line cannot be parsed as a number
     * @throws IllegalArgumentException if the input stream is null
     */
    public static CustomLinkedList<Double> readNumbersFromStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Input stream cannot be null");
        }
        
        CustomLinkedList<Double> numbers = new CustomLinkedList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }
                
                try {
                    double number = Double.parseDouble(line);
                    numbers.add(number);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException(
                        String.format("Invalid number format at line %d: '%s'", lineNumber, line)
                    );
                }
            }
        }
        
        if (numbers.isEmpty()) {
            throw new IOException("No valid numbers found in input stream");
        }
        
        return numbers;
    }
    
    /**
     * Validates that a file exists and is readable.
     * 
     * @param filePath the path to the file to validate
     * @throws IOException if the file doesn't exist or isn't readable
     * @throws IllegalArgumentException if the file path is null or empty
     */
    public static void validateFile(String filePath) throws IOException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        
        Path path = Paths.get(filePath);
        
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        
        if (!Files.isReadable(path)) {
            throw new IOException("File is not readable: " + filePath);
        }
        
        if (Files.isDirectory(path)) {
            throw new IOException("Path is a directory, not a file: " + filePath);
        }
    }
}