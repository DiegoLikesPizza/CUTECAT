package com.CUTECAT.modes;

import com.CUTECAT.diegoutil.DiegoArdUtils;

public abstract class modebase extends DiegoArdUtils {

    private final double WEIGHT = 0.0002;

    private int ammoLeft;
    private int preferredKeySpeed;


    // Methoden zum Eingeben des Ziels
    public abstract void setTargetDistance(int newDistance);
    public abstract void setTargetHeight(int newHeight);
    public abstract void setTargetRelativeDirection(boolean right, int newDirection);

    // Methode zum Eingeben der verbleibenden Munition
    public abstract void setRemainingAmmo(int RemainingAmmo);


    // manual / semi / auto

    public void driveWOSteer(boolean forward, int speed) throws Exception {
        setFront(forward);
        setSpeed(speed);
        setDriveYaw(0);
    }

    public void driveWSteer(boolean forward, int speed, int yaw) throws Exception {
        setFront(forward);
        setSpeed(speed);
        setDriveYaw(yaw);
    }

    public void shootOnce(){
        shoot();
    }

    // semi / auto


}