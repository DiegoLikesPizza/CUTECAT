package com.CUTECAT.modes;

import com.CUTECAT.modes.capabilities.*;
import com.CUTECAT.diegoutil.DiegoArdUtils;
import static com.CUTECAT.diegoutil.DiegoPhysicsUtils.calculateThrowFunction;

/**
 * Base class for all tank operation modes.
 * Provides common functionality for targeting, movement, and shooting.
 */
public abstract class modebase extends DiegoArdUtils {

    // Target information
    private int targetDistance;
    private int targetAngle;

    // Ammunition
    private int ammoLeft;

    // Movement settings
    private int defaultSpeed = 150;

    // Physics constants
    private static final int BALLSPEED = 35;  // Ball speed in m/s

    /**
     * Initialize the mode
     */
    public modebase() {
        ammoLeft = 10;  // Default ammo count
    }

    /**
     * Handle targeting logic - to be implemented by specific modes
     */
    protected abstract void handleTargeting();

    /**
     * Handle movement logic - to be implemented by specific modes
     */
    protected abstract void handleMovement();

    /**
     * Set the distance to the target
     * @param newDistance Distance in centimeters
     */
    public void setTargetDistance(int newDistance) {
        targetDistance = newDistance;
    }

    /**
     * Get the current target distance
     * @return Distance in centimeters
     */
    public int getTargetDistance() {
        return targetDistance;
    }

    /**
     * Set the relative direction to the target
     * @param newDirection Direction in degrees (-180 to 180)
     */
    public void setTargetRelativeDirection(int newDirection) {
        targetAngle = newDirection;
    }

    /**
     * Get the current target direction
     * @return Direction in degrees
     */
    public int getTargetRelativeDirection() {
        return targetAngle;
    }

    /**
     * Get the current ammo count
     * @return Number of shots remaining
     */
    public int getAmmoCount() {
        return ammoLeft;
    }

    /**
     * Set the remaining ammunition
     * @param remainingAmmo Number of shots
     */
    public void setRemainingAmmo(int remainingAmmo) {
        ammoLeft = remainingAmmo;
    }

    /**
     * Decrease ammo count by one
     * @return true if ammo was available and decreased, false if no ammo left
     */
    protected boolean decreaseAmmo() {
        if (ammoLeft > 0) {
            ammoLeft--;
            return true;
        }
        return false;
    }

    /**
     * Drive straight without steering
     * @param forward Direction (true = forward, false = backward)
     * @param speed Speed (0-255)
     */
    public void driveWOSteer(boolean forward, int speed) {
        try {
            setAllMotorsDirection(forward);
            setAllMotorsSpeed(speed);
            setSteeringAngle(0);
        } catch (Exception e) {
            System.err.println("Error driving: " + e.getMessage());
        }
    }

    /**
     * Drive with steering
     * @param forward Direction (true = forward, false = backward)
     * @param speed Speed (0-255)
     * @param yaw Steering angle (-90 to 90)
     */
    public void driveWSteer(boolean forward, int speed, int yaw) {
        try {
            setAllMotorsDirection(forward);
            setAllMotorsSpeed(speed);
            setSteeringAngle(yaw);
        } catch (Exception e) {
            System.err.println("Error driving with steering: " + e.getMessage());
        }
    }

    /**
     * Fire a single shot if ammo is available
     * @return true if shot was fired, false if no ammo
     */
    public boolean shootOnce() {
        if (decreaseAmmo()) {
            shoot();
            return true;
        }
        return false;
    }

    /**
     * Calculate the pitch angle needed to hit a target at the given distance
     * @param distance Distance to target in centimeters
     * @return Pitch angle in degrees
     * @throws Exception if target cannot be reached
     */
    public static int calculatePitch(int distance) throws Exception {
        double[] pitches = calculateThrowFunction(distance, BALLSPEED);

        if (pitches == null) {
            throw new Exception("Cannot reach target at distance " + distance + " with ball speed " + BALLSPEED + " m/s");
        }

        // Choose the smaller angle for a more direct shot
        if (pitches[0] < pitches[1]) {
            return (int) pitches[0];
        } else {
            return (int) pitches[1];
        }
    }
}
