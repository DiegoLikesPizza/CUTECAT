package com.CUTECAT.modes;

import com.CUTECAT.diegoutil.DiegoArdUtils;

public abstract class modebase extends DiegoArdUtils {

    private int targetDistance;
    private int targetAngle;

    private int ammoLeft;
    private int preferredKeySpeed;


    // Methoden zum Eingeben des Ziels
    public void setTargetDistance(int newDistance){
        targetDistance = newDistance;
    }

    public void setTargetRelativeDirection(boolean right, int newDirection) {
        if(right){
            targetAngle = newDirection;
        } else {
            targetAngle = -newDirection;
        }
    }

    // Methode zum Eingeben der verbleibenden Munition
    public void setRemainingAmmo(int RemainingAmmo) {
        ammoLeft = RemainingAmmo;
    }


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