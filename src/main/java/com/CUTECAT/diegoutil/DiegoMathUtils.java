package com.CUTECAT.diegoutil;

import java.util.Random;

public class DiegoMathUtils {

    private static final Random rand  = new Random();

    public static boolean coinflip() {
        return rand.nextBoolean();
    }

    public static int getRandom(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static boolean isBetween(int min, int max, int value) {
        return value >= min && value <= max;
    }

    public static int getDifference(int a, int b) {
        return Math.abs(a - b);
    }
}