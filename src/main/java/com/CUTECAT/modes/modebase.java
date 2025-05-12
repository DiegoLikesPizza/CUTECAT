package com.CUTECAT.modes;

public abstract class modebase {

    // Methoden zum Eingeben des Ziels
    public abstract void setTargetDistance(int newDistance);
    public abstract void setTargetHeight(int newHeight);
    public abstract void setTargetRelativeDirection(boolean right, int newDirection);

    // Methode zum Eingeben der verbleibenden Munition
    public abstract void setRemainingAmmo(int RemainingAmmo);


}