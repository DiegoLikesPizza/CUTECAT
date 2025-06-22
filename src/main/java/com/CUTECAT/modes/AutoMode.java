package com.CUTECAT.modes;

import com.CUTECAT.modes.capabilities.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fully automatic mode implementation where the tank automatically handles
 * pathfinding, driving, targeting, and shooting.
 */
public class AutoMode extends modebase 
    implements MovementCapable, ShootingCapable, TargetingCapable {

    // Target lock status
    private boolean targetLocked = false;

    // Pathfinding
    private List<PathPoint> path = new ArrayList<>();
    private int currentPathIndex = 0;
    private boolean pathCompleted = false;

    // Auto mode status
    private boolean autoModeActive = false;

    /**
     * Represents a point in the tank's path
     */
    private static class PathPoint {
        int x, y;
        int direction;

        public PathPoint(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }

    /**
     * Start automatic operation
     */
    public void startAutoMode() {
        autoModeActive = true;
        System.out.println("Auto mode activated");

        // Start a separate thread for automatic operation
        new Thread(() -> {
            while (autoModeActive) {
                handleMovement();
                handleTargeting();

                // If target is locked and we have ammo, shoot
                if (isTargetLocked() && canShoot()) {
                    shoot();
                }

                // Small delay to avoid CPU overuse
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    /**
     * Stop automatic operation
     */
    public void stopAutoMode() {
        autoModeActive = false;
        stopMovement();
        System.out.println("Auto mode deactivated");
    }

    /**
     * Set a path for the tank to follow
     * @param newPath List of path points
     */
    public void setPath(List<PathPoint> newPath) {
        path = newPath;
        currentPathIndex = 0;
        pathCompleted = false;
        System.out.println("New path set with " + newPath.size() + " points");
    }

    /**
     * Add a point to the current path
     * @param x X coordinate
     * @param y Y coordinate
     * @param direction Direction to face at this point
     */
    public void addPathPoint(int x, int y, int direction) {
        path.add(new PathPoint(x, y, direction));
        System.out.println("Added path point: (" + x + ", " + y + "), direction: " + direction);
    }

    /**
     * Clear the current path
     */
    public void clearPath() {
        path.clear();
        currentPathIndex = 0;
        pathCompleted = false;
        System.out.println("Path cleared");
    }

    /**
     * Check if the path has been completed
     * @return true if path is completed, false otherwise
     */
    public boolean isPathCompleted() {
        return pathCompleted;
    }

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
                System.out.println("Auto shot fired at target! Remaining ammo: " + getAmmoCount());
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

        System.out.println("Auto mode: Target locked at distance " + distance + 
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
     * Handle targeting logic - automatically scan for and aim at targets
     */
    @Override
    protected void handleTargeting() {
        // In a real implementation, this would use sensors to detect targets
        // For this simulation, we'll use the manually set target distance and direction

        int distance = getTargetDistance();
        int angle = getTargetRelativeDirection();

        if (distance > 0) {
            try {
                aim(distance, angle);
            } catch (Exception e) {
                System.err.println("Error aiming: " + e.getMessage());
                targetLocked = false;
            }
        } else {
            // Simulate scanning for targets by rotating the head
            int currentYaw = getValue("HYaw");
            int newYaw = (currentYaw + 5) % 180;
            try {
                setHeadPosition(newYaw, 45);  // Scan at a 45-degree elevation
            } catch (Exception e) {
                System.err.println("Error scanning: " + e.getMessage());
            }
        }
    }

    /**
     * Handle movement logic - automatically follow the path
     */
    @Override
    protected void handleMovement() {
        if (path.isEmpty()) {
            // No path to follow
            stopMovement();
            return;
        }

        if (currentPathIndex >= path.size()) {
            // Path completed
            stopMovement();
            pathCompleted = true;
            return;
        }

        // Simulate moving to the next point in the path
        PathPoint currentPoint = path.get(currentPathIndex);

        // In a real implementation, this would use sensors and encoders to navigate
        // For this simulation, we'll just move in the specified direction

        move(150, currentPoint.direction);

        // Simulate reaching the point after some time
        currentPathIndex++;

        if (currentPathIndex >= path.size()) {
            System.out.println("Path completed");
            pathCompleted = true;
        }
    }

    /**
     * For testing: Simulate finding a target
     * @param distance Distance to target
     * @param angle Angle to target
     */
    public void simulateTargetFound(int distance, int angle) {
        setTargetDistance(distance);
        setTargetRelativeDirection(angle);
        System.out.println("Target simulated at distance " + distance + "cm, angle " + angle + "°");
    }
}
