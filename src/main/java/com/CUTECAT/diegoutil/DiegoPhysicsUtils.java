package com.CUTECAT.diegoutil;

public class DiegoPhysicsUtils {

    public static final double GRAVITATION = 9.81;

    public static double getGravitationalPull(double weight) {
        return  weight * GRAVITATION;
    }
}