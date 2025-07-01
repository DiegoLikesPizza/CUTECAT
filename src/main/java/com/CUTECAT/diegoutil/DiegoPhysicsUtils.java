package com.CUTECAT.diegoutil;

/**
 * Utility class for physics calculations.
 */
public class DiegoPhysicsUtils {
    
    // Gravitational acceleration (m/s²)
    private static final double GRAVITY = 9.81;
    
    /**
     * Calculates the angle needed to hit a target at a given distance with a projectile.
     * 
     * @param distance The horizontal distance to the target (in meters)
     * @param height The height difference between the launcher and the target (in meters)
     * @param velocity The initial velocity of the projectile (in m/s)
     * @return The angle in degrees, or -1 if the target cannot be hit
     */
    public static double calculateLaunchAngle(double distance, double height, double velocity) {
        // Check if the target is reachable
        double v2 = velocity * velocity;
        double discriminant = v2 * v2 - GRAVITY * (GRAVITY * distance * distance + 2 * height * v2);
        
        if (discriminant < 0) {
            // Target cannot be hit with the given velocity
            return -1;
        }
        
        // Calculate the two possible angles
        double term1 = Math.atan((v2 + Math.sqrt(discriminant)) / (GRAVITY * distance));
        double term2 = Math.atan((v2 - Math.sqrt(discriminant)) / (GRAVITY * distance));
        
        // Convert to degrees
        double angle1 = DiegoMathUtils.degrees(term1);
        double angle2 = DiegoMathUtils.degrees(term2);
        
        // Return the smaller angle (more direct shot)
        return Math.min(angle1, angle2);
    }
    
    /**
     * Calculates the time it takes for a projectile to hit a target.
     * 
     * @param distance The horizontal distance to the target (in meters)
     * @param angle The launch angle (in degrees)
     * @param velocity The initial velocity of the projectile (in m/s)
     * @return The time in seconds
     */
    public static double calculateTimeToTarget(double distance, double angle, double velocity) {
        // Convert angle to radians
        double angleRad = DiegoMathUtils.radians(angle);
        
        // Calculate time
        return distance / (velocity * Math.cos(angleRad));
    }
    
    /**
     * Calculates the maximum height a projectile will reach.
     * 
     * @param angle The launch angle (in degrees)
     * @param velocity The initial velocity of the projectile (in m/s)
     * @return The maximum height in meters
     */
    public static double calculateMaxHeight(double angle, double velocity) {
        // Convert angle to radians
        double angleRad = DiegoMathUtils.radians(angle);
        
        // Calculate max height
        double vSin = velocity * Math.sin(angleRad);
        return (vSin * vSin) / (2 * GRAVITY);
    }
    
    /**
     * Calculates the maximum distance a projectile can travel.
     * 
     * @param angle The launch angle (in degrees)
     * @param velocity The initial velocity of the projectile (in m/s)
     * @return The maximum distance in meters
     */
    public static double calculateMaxDistance(double angle, double velocity) {
        // Convert angle to radians
        double angleRad = DiegoMathUtils.radians(angle);
        
        // Calculate max distance
        double sin2Angle = Math.sin(2 * angleRad);
        return (velocity * velocity * sin2Angle) / GRAVITY;
    }
    
    /**
     * Calculates the optimal angle for maximum distance.
     * 
     * @return The optimal angle in degrees
     */
    public static double calculateOptimalAngle() {
        // The optimal angle for maximum distance is 45 degrees
        return 45.0;
    }
}