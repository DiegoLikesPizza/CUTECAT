package com.CUTECAT.modes;

import com.CUTECAT.modes.capabilities.*;

public class ManualMode extends modebase implements MovementCapable, ShootingCapable {
    @Override
    public void move(int speed, int direction) {
        try {
            driveWSteer(true, speed, direction);
        } catch (Exception e) {
            // Handle exception
        }
    }

    @Override
    public void shoot() {
        shootOnce();
    }

    @Override
    public boolean canShoot() {
        return getAmmoCount() > 0;
    }


    @Override
    protected void handleTargeting() {

    }

    @Override
    protected void handleMovement() {

    }
}