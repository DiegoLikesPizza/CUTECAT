package com.CUTECAT.modes.capabilities;

/**
 * Interface for classes that can perform targeting operations.
 */
public interface TargetingCapable {
    
    /**
     * Activates the camera for targeting.
     */
    void activateCamera();
    
    /**
     * Adjusts the camera position.
     * 
     * @param angle The angle of the camera (0-180)
     */
    void adjustCamera(int angle);
    
    /**
     * Activates the ultrasonic sensor for distance measurement.
     */
    void activateUltrasonicSensor();
    
    /**
     * Adjusts the ultrasonic sensor position.
     * 
     * @param angle The angle of the ultrasonic sensor (0-180)
     */
    void adjustUltrasonicSensor(int angle);
    
    /**
     * Gets the distance to the target using the ultrasonic sensor.
     * 
     * @return The distance in centimeters
     */
    double getTargetDistance();
    
    /**
     * Automatically aims at a detected target.
     * 
     * @return true if targeting was successful, false otherwise
     */
    boolean autoTarget();
}