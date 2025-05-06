package com.CUTECAT.diegoutil;

import java.util.ArrayList;
import java.util.HashMap;

public class DiegoArdUtils extends Thread{

    private static HashMap<String, Integer> ArdMap = new HashMap<>();

    private static ArrayList<Integer> ArdCues = new ArrayList<>();

    private static boolean remoteControlling;

    private static int drivefront = 1;
    private static int speed = 100;
    private static int drivedirection = 0;
    private static int headYaw = 0;
    private static int headPitch = 0;

    public void run() {

        while (remoteControlling) {

            ArdMap.put("Motor dir", drivefront);
            ArdMap.put("Motor speed", speed);
            ArdMap.put("steer", drivedirection);  // Lenkservo
            ArdMap.put("steer2", headYaw);  // Turmservo horizontal
            ArdMap.put("steer3", headPitch); // Laufservo vertikal
            ArdMap.put("cam", null);                    // TODO
            ArdMap.put("ultraschall", null);            // TODO
            ArdMap.put("Schrittmotor", null);           // TODO

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
            ArdCues.add(ArdMap.get("steer2"));          // Kopfservo Breite
            ArdCues.add(ArdMap.get("steer3"));          // Kopfservo Höhe
            ArdCues.add(ArdMap.get("cam"));             // TODO: Servo für Kamera
            ArdCues.add(ArdMap.get("ultraschall"));     // TODO: Servo für Ultraschall Sensor
            ArdCues.add(ArdMap.get("Schrittmotor"));    // TODO: Schrittmotor

            try {
                sleep(100);
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
        if(speed < 0){
            throw new Exception("Speed too low. Must be more than 0");
        }
        if (speed > 255) {
            throw new Exception("Speed too low. Must be less than 256");
        }
        speed = newSpeed;
    }

    public void setDriveYaw(int yaw) throws Exception {
        if(yaw < -90){
            throw new Exception("Yaw too low. Must be greater than -90");
        }
        if (yaw > 90) {
            throw new Exception("Yaw too high. Must be less than 90");
        }
        drivedirection = yaw;
    }

    public void setHeadYaw(int yaw) throws Exception {
        if(yaw < -90){
            throw new Exception("Yaw too low. Must be greater than -90");
        }
        if (yaw > 90) {
            throw new Exception("Yaw too high. Must be less than 90");
        }
        headYaw = yaw;
    }

    public void setHeadPitch(int pitch) throws Exception {
        if(pitch < 3){
            throw new Exception("Pitch too low. Must be greater than 3");
        }
        if (pitch > 85) {
            throw new Exception("Pitch too high. Must be less than 85");
        }
        headPitch = pitch;
    }

    public void startControl() {
        remoteControlling = true;
        this.start();
    }

    public void stopControl() {
        remoteControlling = false;
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