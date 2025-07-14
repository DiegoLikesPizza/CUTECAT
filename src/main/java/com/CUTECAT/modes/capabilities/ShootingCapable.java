package com.CUTECAT.modes.capabilities;

/**
 * Interface for classes that can control the shooting mechanism.
 */
public interface ShootingCapable {
    
    /**
     * Fires a plastic ball.
     */
    void shoot();
    
    /**
     * Adjusts the barrel position.
     * 
     * @param angle The angle of the barrel (0-180)
     */
    void adjustBarrel(int angle);
    
    /**
     * Rotates the turret.
     * 
     * @param angle The angle of the turret (0-180)
     */
    void rotateTurret(int angle);
    
    /**
     * Prepares the shooting mechanism.
     */
    void prepareShooter();
    
    /**
     * Resets the shooting mechanism to its default position.
     */
    void resetShooter();
}