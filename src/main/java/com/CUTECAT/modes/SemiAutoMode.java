package com.CUTECAT.modes;

import com.CUTECAT.modes.capabilities.*;

public class SemiAutoMode extends modebase 
    implements MovementCapable, ShootingCapable, TargetingCapable {

    @Override
    public void move(int speed, int direction) {

    }

    @Override
    public boolean canShoot() {
        return false;
    }

    @Override
    public void aim(int distance, int angle) {

    }

    @Override
    public boolean isTargetLocked() {
        return false;
    }

    @Override
    protected void handleTargeting() {

    }

    @Override
    protected void handleMovement() {

    }
    // Implement all interface methods
}