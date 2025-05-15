package com.CUTECAT.diegoutil;

public class DiegoPhysicsUtils {

    public static final double GRAVITATION = 9.81;

    public static double getGravitationalPull(double weight) {
        return  weight * GRAVITATION;
    }

    public static double[] calculateThrowFunction(double distance, double v0) {
        double sin2Theta = (distance * GRAVITATION) / (v0 * v0);

        if (sin2Theta < -1 || sin2Theta > 1) {
            return null;
        }

        double twoTheta = Math.asin(sin2Theta);
        double theta1 = Math.toDegrees(twoTheta) / 2;

        double theta2 = 90.0 - theta1;

        return new double[]{theta1, theta2};
    }
}