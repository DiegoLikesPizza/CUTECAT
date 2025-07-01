package com.CUTECAT.diegoutil;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Utility class for string operations.
 */
public class DiegoStringUtils {
    
    /**
     * Converts an array of integers to a CSV string.
     * 
     * @param values The array of integers
     * @return The CSV string
     */
    public static String toCsv(int[] values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i < values.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    /**
     * Converts an array of doubles to a CSV string.
     * 
     * @param values The array of doubles
     * @return The CSV string
     */
    public static String toCsv(double[] values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i < values.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    /**
     * Converts a CSV string to an array of integers.
     * 
     * @param csv The CSV string
     * @return The array of integers
     */
    public static int[] fromCsvToIntArray(String csv) {
        String[] parts = csv.split(",");
        int[] result = new int[parts.length];
        
        for (int i = 0; i < parts.length; i++) {
            try {
                result[i] = Integer.parseInt(parts[i].trim());
            } catch (NumberFormatException e) {
                result[i] = 0; // Default value if parsing fails
            }
        }
        
        return result;
    }
    
    /**
     * Converts a CSV string to an array of doubles.
     * 
     * @param csv The CSV string
     * @return The array of doubles
     */
    public static double[] fromCsvToDoubleArray(String csv) {
        String[] parts = csv.split(",");
        double[] result = new double[parts.length];
        
        for (int i = 0; i < parts.length; i++) {
            try {
                result[i] = Double.parseDouble(parts[i].trim());
            } catch (NumberFormatException e) {
                result[i] = 0.0; // Default value if parsing fails
            }
        }
        
        return result;
    }
    
    /**
     * Pads a string to a specified length with a specified character.
     * 
     * @param str The string to pad
     * @param length The desired length
     * @param padChar The character to pad with
     * @return The padded string
     */
    public static String padRight(String str, int length, char padChar) {
        if (str.length() >= length) {
            return str;
        }
        
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < length) {
            sb.append(padChar);
        }
        
        return sb.toString();
    }
    
    /**
     * Pads a string to a specified length with a specified character.
     * 
     * @param str The string to pad
     * @param length The desired length
     * @param padChar The character to pad with
     * @return The padded string
     */
    public static String padLeft(String str, int length, char padChar) {
        if (str.length() >= length) {
            return str;
        }
        
        StringBuilder sb = new StringBuilder();
        int padLength = length - str.length();
        
        for (int i = 0; i < padLength; i++) {
            sb.append(padChar);
        }
        
        sb.append(str);
        return sb.toString();
    }
    
    /**
     * Formats an integer as a fixed-width string.
     * 
     * @param value The integer value
     * @param width The desired width
     * @return The formatted string
     */
    public static String formatInt(int value, int width) {
        return padLeft(Integer.toString(value), width, '0');
    }
}