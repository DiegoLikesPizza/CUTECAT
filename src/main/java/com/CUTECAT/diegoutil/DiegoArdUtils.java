package com.CUTECAT.diegoutil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import static com.CUTECAT.diegoutil.DiegoMathUtils.*;
import static com.CUTECAT.diegoutil.DiegoPhysicsUtils.*;
import static com.CUTECAT.diegoutil.DiegoStringUtils.*;

public class DiegoArdUtils extends Thread {

    private static final String[] MOTORS = {"MOTOR1", "MOTOR2", "MOTOR3", "MOTOR4"};
    
    private static final String ARDUINOIP = "172.16.10.127";
    private static final int PORT = 81;
    private static final int MIN_PITCH = 2;
    private static final int MAX_PITCH = 85;
    private static final int MIN_YAW = -90;
    private static final int MAX_YAW = 90;
    private static final int MAX_SPEED = 255;

    private HashMap<String, Integer> ArdMap = new HashMap<>();
    private ArrayList<Integer> ArdCues = new ArrayList<>();

    private boolean remoteControlling;

    private void initializeControlMap() {
        for (String motor : MOTORS) {
            ArdMap.put(motor + "_DIR", 1);    // jeder Motor mit eigener dir
            ArdMap.put(motor + "_SPEED", 0);  // jeder Motor mit eigenem speed
        }
        ArdMap.put("steer", 0);
        ArdMap.put("HYaw", 0);
        ArdMap.put("HPitch", 0);
        ArdMap.put("SYaw", 0);
        ArdMap.put("SPitch", 0);
        ArdMap.put("Abschuss", 0);
    }

    private void updateArdCues() {
        ArdCues.clear();

        for (String motor : MOTORS) {
            Integer dir = ArdMap.getOrDefault(motor + "_DIR", 1);    // Default to forward
            Integer speed = ArdMap.getOrDefault(motor + "_SPEED", 0); // Default to stop
            ArdCues.add(dir);
            ArdCues.add(speed);
        }

        ArdCues.add(ArdMap.getOrDefault("steer", 0));
        ArdCues.add(ArdMap.getOrDefault("HYaw", 0));
        ArdCues.add(ArdMap.getOrDefault("HPitch", 0));
        ArdCues.add(ArdMap.getOrDefault("SYaw", 0));
        ArdCues.add(ArdMap.getOrDefault("SPitch", 0));
        ArdCues.add(ArdMap.getOrDefault("Abschuss", 0));
    }

    public void run() {
        try (Socket socket = new Socket(ARDUINOIP, PORT);
             PrintWriter outprintwriter = new PrintWriter(socket.getOutputStream(), true)) {
            
            initializeControlMap();  // Initialize with default values
            
            while (remoteControlling) {
                updateArdCues();     // Safely transfer values
                
                if(ArdMap.getOrDefault("Abschuss", 0) == 1) {
                    try {
                        sleep(125);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                    ArdMap.put("Abschuss", 0);
                    ArdCues.set(ArdCues.size() - 1, 0);  // Safer than removeLast/add
                }

                String csv = toCsv(ArdCues);
                outprintwriter.println(csv);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to establish or maintain connection to Arduino", e);
        }
    }

    // Updated motor control methods
    public void setMotorDirection(int motorNumber, boolean forward) {
        if (motorNumber < 1 || motorNumber > 4) {
            throw new IllegalArgumentException("Motor number must be between 1 and 4");
        }
        ArdMap.put(MOTORS[motorNumber-1] + "_DIR", forward ? 1 : 2);
    }

    public void setMotorSpeed(int motorNumber, int speed) {
        if (motorNumber < 1 || motorNumber > 4) {
            throw new IllegalArgumentException("Motor number must be between 1 and 4");
        }
        if (speed < 0 || speed > 255) {
            throw new IllegalArgumentException("Speed must be between 0 and 255");
        }
        ArdMap.put(MOTORS[motorNumber-1] + "_SPEED", speed);
    }

    public void setFront(boolean front){
        if(front){
            ArdMap.put("Motor dir", 1);
        } else {
            ArdMap.put("Motor dir", 2);
        }
    }

    public void setSpeed(int newSpeed) throws IllegalArgumentException {
        if (!isBetween(0, MAX_SPEED, newSpeed)) {
            throw new IllegalArgumentException("Speed must be between 0 and " + MAX_SPEED);
        }
        ArdMap.put("Motor speed", newSpeed);
    }

    public void setDriveYaw(int newYaw) throws Exception {
        if(!isBetween(MIN_YAW, MAX_YAW, newYaw)){
            throw new Exception("invalid yaw input");
        }
        ArdMap.put("steer", newYaw);
    }

    public void setHeadView(int yaw, int pitch) throws Exception {
        if(!isBetween(MIN_YAW, MAX_YAW, yaw)) {
            throw new Exception("invalid yaw input");
        }
        if(!isBetween(2, 70, pitch)) {
            throw new Exception("invalid pitch input");
        }
        ArdMap.put("HYaw", yaw);
        ArdMap.put("HPitch", pitch);
    }

    public void setSensorsView(int yaw, int pitch) throws Exception {
        if(!isBetween(MIN_YAW, MAX_YAW, yaw)) {
            throw new Exception("invalid yaw input");
        }
        if(!isBetween(MIN_PITCH, MAX_PITCH, pitch)) {
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
}

/*
1---2
  |
  |
3---4



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