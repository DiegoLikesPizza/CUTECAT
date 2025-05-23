package com.CUTECAT.modes;

import com.CUTECAT.modes.capabilities.*;
import com.CUTECAT.diegoutil.DiegoArdUtils;
import static com.CUTECAT.diegoutil.DiegoPhysicsUtils.calculateThrowFunction;

public abstract class modebase extends DiegoArdUtils {

    private int targetDistance;
    private int targetAngle;
    private int ammoLeft;
    private int preferredKeySpeed;
    
    private static final int BALLSPEED = 35;        // Startgeschwindigkeit der Kugel in m/s

    protected abstract void handleTargeting();
    protected abstract void handleMovement();

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

    public int getAmmoCount() {
        return ammoLeft;
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

    public static int calculatePitch(int distance) throws Exception {
        double[] pitches = calculateThrowFunction(distance, BALLSPEED);

        if (pitches == null) {
            throw new Exception("cannot reach target");
        }

        if (pitches[0] < pitches[1]) {
            return (int) pitches[0];
        } else {
            return (int) pitches[1];
        }
    }

}