package com.CUTECAT.diegoutil;

import java.util.ArrayList;
import java.util.HashMap;
import static com.CUTECAT.diegoutil.DiegoMathUtils.*;
import static com.CUTECAT.diegoutil.DiegoPhysicsUtils.*;

public class DiegoArdUtils extends Thread{

    private static HashMap<String, Integer> ArdMap = new HashMap<>();
    private static ArrayList<Integer> ArdCues = new ArrayList<>();

    private static boolean remoteControlling;

    private static final int ballSpeed = 35;        // Startgeschwindigkeit der Kugel in m/s

    private static int drivefront = 1;
    private static int speed = 0;
    private static int driveyaw = 0;
    private static int headYaw = 0;
    private static int headPitch = 0;
    private static int SensorsYaw = 0;
    private static int SensorsPitch = 0;
    private static int shoot = 0;

    public void run() {

        while (remoteControlling) {

            ArdMap.put("Motor dir", drivefront);    // Motoren Richtung                 1 / 2
            ArdMap.put("Motor speed", speed);       // Motoren speed                    0 - 255
            ArdMap.put("steer", driveyaw);          // Lenkservo                       -90 - 90
            ArdMap.put("HYaw", headYaw);            // Turmservo horizontal            -90 - 90
            ArdMap.put("HPitch", headPitch);        // Laufservo vertikal               2 - 85
            ArdMap.put("SYaw", SensorsYaw);         // Kamera und Ultraschall Yaw      -90 - 90
            ArdMap.put("SPitch", SensorsPitch);     // Kamera und Ultraschall Pitch     2 - 85
            ArdMap.put("Abschuss", shoot);          // Abschuss halt idfk               0 / 1

            ArdCues.clear();

            ArdCues.add(ArdMap.get("Motor dir"));       // Motor 1
            ArdCues.add(ArdMap.get("Motor speed"));
            ArdCues.add(ArdMap.get("Motor dir"));       // Motor 2
            ArdCues.add(ArdMap.get("Motor speed"));
            ArdCues.add(ArdMap.get("Motor dir"));       // Motor 3
            ArdCues.add(ArdMap.get("Motor speed"));
            ArdCues.add(ArdMap.get("Motor dir"));       // Motor 4
            ArdCues.add(ArdMap.get("Motor speed"));
            ArdCues.add(ArdMap.get("steer"));           // Lenkservo
            ArdCues.add(ArdMap.get("HYaw"));            // Kopfservo Breite
            ArdCues.add(ArdMap.get("HPitch"));          // Kopfservo Höhe
            ArdCues.add(ArdMap.get("SYaw"));            // Kamera und Ultraschall Yaw
            ArdCues.add(ArdMap.get("SPitch"));          // Kamera und Ultraschall Pitch
            ArdCues.add(ArdMap.get("Abschuss"));        // Abschuss var

            if(ArdMap.get("Abschuss") == 1) {
                try {
                    sleep(125);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                shoot = 0;
                ArdCues.removeLast();
                ArdCues.add(0);
            }

            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void setFront(boolean front){
        if(front){
            drivefront = 1;
        } else {
            drivefront = 2;
        }
    }

    public void setSpeed(int newSpeed) throws Exception {
        if(isBetween(0, 255, newSpeed)){
            speed = newSpeed;
        } else {
            throw new Exception("invalid speed input");
        }
    }

    public void setDriveYaw(int newYaw) throws Exception {
        if(!isBetween(-90, 90, newYaw)){
            throw new Exception("invalid yaw input");
        }
        driveyaw = newYaw;
    }

    public void setHeadView(int yaw, int pitch) throws Exception {
        if(!isBetween(-90, 90, yaw)) {
            throw new Exception("invalid yaw input");
        }
        if(!isBetween(2, 70, pitch)) {
            throw new Exception("invalid pitch input");
        }
        headYaw = yaw;
        headPitch = pitch;
    }

    public void setSensorsView(int yaw, int pitch) throws Exception {
        if(!isBetween(-90, 90, yaw)) {
            throw new Exception("invalid yaw input");
        }
        if(!isBetween(2, 85, pitch)) {
            throw new Exception("invalid pitch input");
        }
        SensorsYaw = yaw;
        SensorsPitch = pitch;
    }

    public void startControl() {
        remoteControlling = true;
        this.start();
    }

    public void stopControl() {
        remoteControlling = false;
    }

    public void shoot() {
        shoot = 1;
    }


    public static int getValue(String key) {
        return ArdMap.get(key);
    }

    public static int calculatePitch(int distance) throws Exception {
        double[] pitches = calculateThrowFunction(distance, ballSpeed);

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

/*
1---2
  |
  |
3---4
 */

/*
ArrayList

Motor1 Richtung     ( 1 = vorwärts; 2 = rückwärts)
Motor1 Stärke       ( 0 - 255; 0 schwächste=
Motor2 Richtung     ( 1 = vorwärts; 2 = rückwärts)
Motor2 Stärke       ( 0 - 255; 0 schwächste=
Motor3 Richtung     ( 1 = vorwärts; 2 = rückwärts)
Motor3 Stärke       ( 0 - 255; 0 schwächste=
Motor4 Richtung     ( 1 = vorwärts; 2 = rückwärts)
Motor4 Stärke       ( 0 - 255; 0 schwächste=
Stellung Lenkservo  ( standartmäßig 0 )
Stellung Turmservo  ( horizontal Turm; standartmäßig 90 )
Stellung Laufservo  ( vertikal Lauf; idfk; standartmäßig 180 )
Kameraservo
Ultraschallservo
Schrittmotor

 */