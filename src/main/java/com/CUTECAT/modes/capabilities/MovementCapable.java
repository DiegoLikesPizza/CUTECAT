package com.CUTECAT.modes.capabilities;

/**
 * Interface for classes that can control tank movement
 */
public interface MovementCapable {
    /**
     * Move the tank with the given speed and direction
     * @param speed Speed value (0-255)
     * @param direction Steering angle (-90 to 90)
     */
    void move(int speed, int direction);

    /**
     * Stop the tank's movement
     */
    void stopMovement();
}
