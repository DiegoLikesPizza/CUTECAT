package com.CUTECAT.modes;

import com.CUTECAT.modes.capabilities.*;

/**
 * Semi-automatic mode implementation where the tank automatically calculates
 * shooting angles and performs automatic aiming, but movement is manual.
 */
public class SemiAutoMode extends modebase 
    implements MovementCapable, ShootingCapable, TargetingCapable {

    // Target lock status
    private boolean targetLocked = false;

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
     * Fire a shot if ammo is available and target is locked
     */
    @Override
    public void shoot() {
        if (canShoot()) {
            if (targetLocked) {
                shootOnce();
                System.out.println("Shot fired at target! Remaining ammo: " + getAmmoCount());
            } else {
                System.out.println("Cannot shoot - target not locked!");
            }
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
     * Automatically aim at a target at the given distance and angle
     * @param distance Distance to target in centimeters
     * @param angle Horizontal angle to target (-180 to 180)
     * @throws Exception if target cannot be reached
     */
    @Override
    public void aim(int distance, int angle) throws Exception {
        // Store target information
        setTargetDistance(distance);
        setTargetRelativeDirection(angle);

        // Calculate pitch based on distance
        int pitch = calculatePitch(distance);

        // Set head position
        setHeadPosition(angle, pitch);

        // Point camera and ultrasonic sensor in the same direction
        setCameraAngle(angle);
        setUltrasonicAngle(angle);

        // Mark target as locked
        targetLocked = true;

        System.out.println("Target locked at distance " + distance + 
                           "cm, angle " + angle + "°, pitch " + pitch + "°");
    }

    /**
     * Check if target is locked
     * @return true if target is locked, false otherwise
     */
    @Override
    public boolean isTargetLocked() {
        return targetLocked;
    }

    /**
     * Handle targeting logic - automatically aim at the target
     * if target distance and direction are set
     */
    @Override
    protected void handleTargeting() {
        int distance = getTargetDistance();
        int angle = getTargetRelativeDirection();

        if (distance > 0) {
            try {
                aim(distance, angle);
            } catch (Exception e) {
                System.err.println("Error aiming: " + e.getMessage());
                targetLocked = false;
            }
        }
    }

    /**
     * Handle movement logic - in semi-auto mode, movement is manual
     */
    @Override
    protected void handleMovement() {
        // In semi-auto mode, movement is handled manually
    }
}
