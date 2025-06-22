package com.CUTECAT.modes;

import com.CUTECAT.modes.capabilities.*;

/**
 * Manual mode implementation where the user controls all aspects of the tank.
 * This includes movement, steering, aiming, and shooting.
 */
public class ManualMode extends modebase implements MovementCapable, ShootingCapable {

    /**
     * Move the tank with the given speed and direction
     * @param speed Speed value (0-255)
     * @param direction Steering angle (-90 to 90)
     */
    @Override
    public void move(int speed, int direction) {
        driveWSteer(true, speed, direction);
    }

    /**
     * Stop the tank's movement
     */
    @Override
    public void stopMovement() {
        driveWOSteer(true, 0);
    }

    /**
     * Fire a shot if ammo is available
     */
    @Override
    public void shoot() {
        if (canShoot()) {
            shootOnce();
            System.out.println("Shot fired! Remaining ammo: " + getAmmoCount());
        } else {
            System.out.println("Cannot shoot - no ammo left!");
        }
    }

    /**
     * Check if shooting is possible (if there's ammo)
     * @return true if shooting is possible, false otherwise
     */
    @Override
    public boolean canShoot() {
        return getAmmoCount() > 0;
    }

    /**
     * Set the head/turret position manually
     * @param yaw Horizontal angle (0-180)
     * @param pitch Vertical angle (0-180)
     */
    public void aimManually(int yaw, int pitch) {
        try {
            setHeadPosition(yaw, pitch);
        } catch (IllegalArgumentException e) {
            System.err.println("Error aiming: " + e.getMessage());
        }
    }

    /**
     * Set the camera angle manually
     * @param angle Camera angle (0-180)
     */
    public void setCameraAngleManually(int angle) {
        try {
            setCameraAngle(angle);
        } catch (IllegalArgumentException e) {
            System.err.println("Error setting camera angle: " + e.getMessage());
        }
    }

    /**
     * Set the ultrasonic sensor angle manually
     * @param angle Ultrasonic sensor angle (0-180)
     */
    public void setUltrasonicAngleManually(int angle) {
        try {
            setUltrasonicAngle(angle);
        } catch (IllegalArgumentException e) {
            System.err.println("Error setting ultrasonic angle: " + e.getMessage());
        }
    }

    /**
     * In manual mode, targeting is handled by the user
     */
    @Override
    protected void handleTargeting() {
        // In manual mode, targeting is handled by the user
    }

    /**
     * In manual mode, movement is handled by the user
     */
    @Override
    protected void handleMovement() {
        // In manual mode, movement is handled by the user
    }
}
