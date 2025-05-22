package com.CUTECAT;

import com.CUTECAT.modes.ManualMode;

import static com.CUTECAT.diegoutil.DiegoMathUtils.*;
import static com.CUTECAT.diegoutil.DiegoPhysicsUtils.*;
import static com.CUTECAT.diegoutil.DiegoStringUtils.*;
import static com.CUTECAT.diegoutil.DiegoArdUtils.*;

import java.util.Scanner;

public class Main {

    static ManualMode manualMode = new ManualMode();

    public static void main(String[] args) throws Exception {


        final Scanner scanner = new Scanner(System.in);

        manualMode.startControl();

        while (true) {
            testfunction(scanner.nextInt());
        }
    }

    private static void testfunction(int x) throws Exception {
        switch (x) {
            case 1:
                manualMode.driveWOSteer(true, 100);
                break;
            case 2:
                manualMode.driveWOSteer(true, 200);
                break;
            case 3:
                manualMode.driveWOSteer(false, 100);
                break;
            case 4:
                manualMode.stopControl();
        }
    }
}