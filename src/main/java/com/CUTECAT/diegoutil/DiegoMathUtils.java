package com.CUTECAT.diegoutil;

/**
 * Utility class for mathematical operations.
 */
public class DiegoMathUtils {
    
    /**
     * Maps a value from one range to another.
     * 
     * @param value The value to map
     * @param fromLow The lower bound of the input range
     * @param fromHigh The upper bound of the input range
     * @param toLow The lower bound of the output range
     * @param toHigh The upper bound of the output range
     * @return The mapped value
     */
    public static double map(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return (value - fromLow) * (toHigh - toLow) / (fromHigh - fromLow) + toLow;
    }
    
    /**
     * Constrains a value to be within a specified range.
     * 
     * @param value The value to constrain
     * @param min The lower bound of the range
     * @param max The upper bound of the range
     * @return The constrained value
     */
    public static double constrain(double value, double min, double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
    
    /**
     * Constrains a value to be within a specified range.
     * 
     * @param value The value to constrain
     * @param min The lower bound of the range
     * @param max The upper bound of the range
     * @return The constrained value
     */
    public static int constrain(int value, int min, int max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
    
    /**
     * Calculates the distance between two points.
     * 
     * @param x1 The x-coordinate of the first point
     * @param y1 The y-coordinate of the first point
     * @param x2 The x-coordinate of the second point
     * @param y2 The y-coordinate of the second point
     * @return The distance between the two points
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Converts an angle from degrees to radians.
     * 
     * @param degrees The angle in degrees
     * @return The angle in radians
     */
    public static double radians(double degrees) {
        return degrees * Math.PI / 180.0;
    }
    
    /**
     * Converts an angle from radians to degrees.
     * 
     * @param radians The angle in radians
     * @return The angle in degrees
     */
    public static double degrees(double radians) {
        return radians * 180.0 / Math.PI;
    }
}