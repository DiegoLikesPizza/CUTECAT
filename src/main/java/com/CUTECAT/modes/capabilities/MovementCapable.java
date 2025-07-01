package com.CUTECAT.modes.capabilities;

/**
 * Interface for classes that can control vehicle movement.
 */
public interface MovementCapable {
    
    /**
     * Moves the vehicle forward.
     * 
     * @param speed The speed at which to move (0-100)
     */
    void moveForward(int speed);
    
    /**
     * Moves the vehicle backward.
     * 
     * @param speed The speed at which to move (0-100)
     */
    void moveBackward(int speed);
    
    /**
     * Turns the vehicle left.
     * 
     * @param angle The steering angle (0-180)
     */
    void turnLeft(int angle);
    
    /**
     * Turns the vehicle right.
     * 
     * @param angle The steering angle (0-180)
     */
    void turnRight(int angle);
    
    /**
     * Stops the vehicle's movement.
     */
    void stopMovement();
}