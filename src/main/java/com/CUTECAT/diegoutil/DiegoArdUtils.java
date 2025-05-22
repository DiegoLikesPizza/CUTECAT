package com.CUTECAT.diegoutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import static com.CUTECAT.diegoutil.DiegoMathUtils.*;
import static com.CUTECAT.diegoutil.DiegoPhysicsUtils.*;
import static com.CUTECAT.diegoutil.DiegoStringUtils.*;

public abstract class DiegoArdUtils extends Thread{


    private static final String ARDUINOIP = "172.16.10.127";

    private HashMap<String, Integer> ArdMap = new HashMap<>();
    private ArrayList<Integer> ArdCues = new ArrayList<>();

    private boolean remoteControlling;

    private static final int BALLSPEED = 35;        // Startgeschwindigkeit der Kugel in m/s

    public void run() {
        try (Socket socket = new Socket(ARDUINOIP, 81);
             PrintWriter outprintwriter = new PrintWriter(socket.getOutputStream(), true)) {
            
        while (remoteControlling) {

            ArdMap.put("Motor dir", 1);             // Motoren Richtung                 1 / 2
            ArdMap.put("Motor speed", 0);           // Motoren speed                    0 - 255
            ArdMap.put("steer", 0);                 // Lenkservo                       -90 - 90
            ArdMap.put("HYaw", 0);                  // Turmservo horizontal            -90 - 90
            ArdMap.put("HPitch", 0);                // Laufservo vertikal               2 - 85
            ArdMap.put("SYaw", 0);                  // Kamera und Ultraschall Yaw      -90 - 90
            ArdMap.put("SPitch", 0);                // Kamera und Ultraschall Pitch     2 - 85
            ArdMap.put("Abschuss", 0);              // Abschuss halt idfk               0 / 1

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
                ArdMap.put("shoot", 0);
                ArdCues.removeLast();
                ArdCues.add(0);
            }

            String csv = toCsv(ArdCues);
            outprintwriter.println(csv);
        }
    } catch (IOException e) {
        throw new RuntimeException("Failed to establish or maintain connection to Arduino", e);
    }
}

    public void setFront(boolean front){
        if(front){
            ArdMap.put("Motor dir", 1);
        } else {
            ArdMap.put("Motor dir", 2);
        }
    }

    public void setSpeed(int newSpeed) throws Exception {
        if(isBetween(0, 255, newSpeed)){
            ArdMap.put("Motor speed", newSpeed);
        } else {
            throw new Exception("invalid speed input");
        }
    }

    public void setDriveYaw(int newYaw) throws Exception {
        if(!isBetween(-90, 90, newYaw)){
            throw new Exception("invalid yaw input");
        }
        ArdMap.put("steer", newYaw);
    }

    public void setHeadView(int yaw, int pitch) throws Exception {
        if(!isBetween(-90, 90, yaw)) {
            throw new Exception("invalid yaw input");
        }
        if(!isBetween(2, 70, pitch)) {
            throw new Exception("invalid pitch input");
        }
        ArdMap.put("HYaw", yaw);
        ArdMap.put("HPitch", pitch);
    }

    public void setSensorsView(int yaw, int pitch) throws Exception {
        if(!isBetween(-90, 90, yaw)) {
            throw new Exception("invalid yaw input");
        }
        if(!isBetween(2, 85, pitch)) {
            throw new Exception("invalid pitch input");
        }
        ArdMap.put("SYaw", yaw);
        ArdMap.put("SPitch", pitch);
    }

    public void startControl() {
        remoteControlling = true;
        this.start();
    }

    public void stopControl() {
        remoteControlling = false;
    }

    public void shoot() {
        ArdMap.put("shoot", 1);
    }


    public int getValue(String key) {
        return ArdMap.get(key);
    }

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