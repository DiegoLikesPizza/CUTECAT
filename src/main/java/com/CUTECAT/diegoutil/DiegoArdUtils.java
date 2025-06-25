package com.CUTECAT.diegoutil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import static com.CUTECAT.diegoutil.DiegoMathUtils.*;
import static com.CUTECAT.diegoutil.DiegoStringUtils.*;

/**
 * Utility class for communicating with the Arduino-based tank.
 * Handles sending commands for wheel movement, steering, and various servo positions.
 */
public class DiegoArdUtils extends Thread {

    // Motor constants
    private static final String[] MOTORS = {"MOTOR1", "MOTOR2", "MOTOR3", "MOTOR4"};

    // Connection constants
    private static final String ARDUINOIP = "172.16.10.127";
    private static final int PORT = 81;

    // Angle limits
    private static final int MIN_PITCH = 2;
    private static final int MAX_PITCH = 85;
    private static final int MIN_YAW = -90;
    private static final int MAX_YAW = 90;

    // Speed limits
    private static final int MIN_SPEED = 0;
    private static final int MAX_SPEED = 255;

    // Default values
    private static final int DEFAULT_STEER_ANGLE = 0;
    private static final int DEFAULT_HEAD_YAW = 90;
    private static final int DEFAULT_HEAD_PITCH = 180;
    private static final int DEFAULT_CAMERA_ANGLE = 90;
    private static final int DEFAULT_ULTRASONIC_ANGLE = 90;
    private static final int DEFAULT_STEP_MOTOR = 0;

    // Control map and command queue
    private HashMap<String, Integer> ArdMap = new HashMap<>();
    private ArrayList<Integer> ArdCues = new ArrayList<>();

    // Thread control
    private boolean remoteControlling;

    /**
     * Initialize the control map with default values for all parameters
     */
    private void initializeControlMap() {
        // Initialize wheel directions and speeds
        for (String motor : MOTORS) {
            ArdMap.put(motor + "_DIR", 1);    // 1 = forward, 2 = backward
            ArdMap.put(motor + "_SPEED", 0);  // 0-255 speed range
        }

        // Initialize steering and servo positions
        ArdMap.put("steer", DEFAULT_STEER_ANGLE);       // Steering angle (-90 to 90)
        ArdMap.put("HYaw", DEFAULT_HEAD_YAW);           // Head/turret horizontal angle (0-180, default 90)
        ArdMap.put("HPitch", DEFAULT_HEAD_PITCH);       // Head/turret vertical angle (0-180, default 180)
        ArdMap.put("CameraAngle", DEFAULT_CAMERA_ANGLE); // Camera angle
        ArdMap.put("UltrasonicAngle", DEFAULT_ULTRASONIC_ANGLE); // Ultrasonic sensor angle
        ArdMap.put("StepMotor", DEFAULT_STEP_MOTOR);    // Step motor position
        ArdMap.put("Shoot", 0);                         // Shoot command (0 = don't shoot, 1 = shoot)
    }

    /**
     * Update the Arduino command queue with current values from the control map
     */
    private void updateArdCues() {
        ArdCues.clear();

        // Add wheel directions and speeds
        for (String motor : MOTORS) {
            Integer dir = ArdMap.getOrDefault(motor + "_DIR", 1);    // Default to forward
            Integer speed = ArdMap.getOrDefault(motor + "_SPEED", 0); // Default to stop
            ArdCues.add(dir);
            ArdCues.add(speed);
        }

        // Add steering and servo positions
        ArdCues.add(ArdMap.getOrDefault("steer", DEFAULT_STEER_ANGLE));
        ArdCues.add(ArdMap.getOrDefault("HYaw", DEFAULT_HEAD_YAW));
        ArdCues.add(ArdMap.getOrDefault("HPitch", DEFAULT_HEAD_PITCH));
        ArdCues.add(ArdMap.getOrDefault("CameraAngle", DEFAULT_CAMERA_ANGLE));
        ArdCues.add(ArdMap.getOrDefault("UltrasonicAngle", DEFAULT_ULTRASONIC_ANGLE));
        ArdCues.add(ArdMap.getOrDefault("StepMotor", DEFAULT_STEP_MOTOR));
        ArdCues.add(ArdMap.getOrDefault("Shoot", 0));
    }

    /**
     * Main thread method that sends commands to the Arduino
     */
    public void run() {
        try (Socket socket = new Socket(ARDUINOIP, PORT);
             PrintWriter outprintwriter = new PrintWriter(socket.getOutputStream(), true)) {

            initializeControlMap();  // Initialize with default values

            while (remoteControlling) {
                updateArdCues();     // Safely transfer values

                String csv = toCsv(ArdCues);
                outprintwriter.println(csv);
                System.out.println("Sent to Arduino: " + csv);  // Debug output

                // Reset shoot command after a short delay
                if(ArdMap.getOrDefault("Shoot", 0) == 1) {
                    try {
                        sleep(125);  // Short delay for shoot action
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                    ArdMap.put("Shoot", 0);  // Reset shoot command
                    updateArdCues();  // Update command queue
                    csv = toCsv(ArdCues);
                    outprintwriter.println(csv);  // Send updated commands
                }

                // Add a small delay to avoid flooding the Arduino
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to establish or maintain connection to Arduino", e);
        }
    }

    /**
     * Set the direction for a specific motor
     * @param motorNumber Motor number (1-4)
     * @param forward True for forward, false for backward
     */
    public void setMotorDirection(int motorNumber, boolean forward) {
        if (motorNumber < 1 || motorNumber > 4) {
            throw new IllegalArgumentException("Motor number must be between 1 and 4");
        }
        ArdMap.put(MOTORS[motorNumber-1] + "_DIR", forward ? 1 : 2);
    }

    /**
     * Set the speed for a specific motor
     * @param motorNumber Motor number (1-4)
     * @param speed Speed value (0-255)
     */
    public void setMotorSpeed(int motorNumber, int speed) {
        if (motorNumber < 1 || motorNumber > 4) {
            throw new IllegalArgumentException("Motor number must be between 1 and 4");
        }
        if (speed < MIN_SPEED || speed > MAX_SPEED) {
            throw new IllegalArgumentException("Speed must be between " + MIN_SPEED + " and " + MAX_SPEED);
        }
        ArdMap.put(MOTORS[motorNumber-1] + "_SPEED", speed);
    }

    /**
     * Set all motors to the same direction
     * @param forward True for forward, false for backward
     */
    public void setAllMotorsDirection(boolean forward) {
        for (int i = 1; i <= 4; i++) {
            setMotorDirection(i, forward);
        }
    }

    /**
     * Set all motors to the same speed
     * @param speed Speed value (0-255)
     */
    public void setAllMotorsSpeed(int speed) {
        if (speed < MIN_SPEED || speed > MAX_SPEED) {
            throw new IllegalArgumentException("Speed must be between " + MIN_SPEED + " and " + MAX_SPEED);
        }
        for (int i = 1; i <= 4; i++) {
            setMotorSpeed(i, speed);
        }
    }

    /**
     * Set the steering angle
     * @param angle Steering angle (-90 to 90)
     * @throws IllegalArgumentException if angle is out of range
     */
    public void setSteeringAngle(int angle) throws IllegalArgumentException {
        if (!isBetween(MIN_YAW, MAX_YAW, angle)) {
            throw new IllegalArgumentException("Steering angle must be between " + MIN_YAW + " and " + MAX_YAW);
        }
        ArdMap.put("steer", angle);
    }

    /**
     * Set the head/turret position
     * @param yaw Horizontal angle (0-180, default 90)
     * @param pitch Vertical angle (0-180, default 180)
     * @throws IllegalArgumentException if angles are out of range
     */
    public void setHeadPosition(int yaw, int pitch) throws IllegalArgumentException {
        if (yaw < 0 || yaw > 180) {
            throw new IllegalArgumentException("Head yaw must be between 0 and 180");
        }
        if (pitch < 0 || pitch > 180) {
            throw new IllegalArgumentException("Head pitch must be between 0 and 180");
        }
        ArdMap.put("HYaw", yaw);
        ArdMap.put("HPitch", pitch);
    }

    /**
     * Set the camera angle
     * @param angle Camera angle (0-180)
     * @throws IllegalArgumentException if angle is out of range
     */
    public void setCameraAngle(int angle) throws IllegalArgumentException {
        if (angle < 0 || angle > 180) {
            throw new IllegalArgumentException("Camera angle must be between 0 and 180");
        }
        ArdMap.put("CameraAngle", angle);
    }

    /**
     * Set the ultrasonic sensor angle
     * @param angle Ultrasonic sensor angle (0-180)
     * @throws IllegalArgumentException if angle is out of range
     */
    public void setUltrasonicAngle(int angle) throws IllegalArgumentException {
        if (angle < 0 || angle > 180) {
            throw new IllegalArgumentException("Ultrasonic sensor angle must be between 0 and 180");
        }
        ArdMap.put("UltrasonicAngle", angle);
    }

    /**
     * Set the step motor position
     * @param position Step motor position
     */
    public void setStepMotorPosition(int position) {
        ArdMap.put("StepMotor", position);
    }

    /**
     * Start the control thread
     */
    public void startControl() {
        remoteControlling = true;
        this.start();
    }

    /**
     * Stop the control thread
     */
    public void stopControl() {
        remoteControlling = false;
    }

    /**
     * Trigger a shot
     */
    public void shoot() {
        ArdMap.put("Shoot", 1);
    }

    /**
     * Get a value from the control map
     * @param key The key to look up
     * @return The value associated with the key
     */
    public int getValue(String key) {
        return ArdMap.getOrDefault(key, 0);
    }

    /**
     * For backward compatibility with existing code
     */
    public void setFront(boolean front) {
        setAllMotorsDirection(front);
    }

    /**
     * For backward compatibility with existing code
     */
    public void setSpeed(int newSpeed) {
        setAllMotorsSpeed(newSpeed);
    }

    /**
     * For backward compatibility with existing code
     */
    public void setDriveYaw(int newYaw) throws Exception {
        setSteeringAngle(newYaw);
    }

    /**
     * For backward compatibility with existing code
     */
    public void setHeadView(int yaw, int pitch) throws Exception {
        setHeadPosition(yaw, pitch);
    }

    /**
     * For backward compatibility with existing code
     */
    public void setSensorsView(int yaw, int pitch) throws Exception {
        setCameraAngle(yaw);
        setUltrasonicAngle(pitch);
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
