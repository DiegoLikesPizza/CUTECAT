# CUTECAT
**C**ybernatic **U**narmed (but **T**errifying) **E**xploration and **C**hassis **A**imed **T**hread
###### by Maja, Diego, Jakob, Jonathan, Ferdinand und Andreas 

## Overview
CUTECAT is a control application for a small vehicle equipped with a camera, ultrasonic sensor, and a plastic ball shooting mechanism. The application communicates with an Arduino-based vehicle via HTTP, sending CSV-formatted control commands.

## Features
- **Three Control Modes**:
  - **Manual Mode**: Full manual control of all vehicle functions
  - **Semi-Auto Mode**: Arduino aims and shoots automatically, but user controls movement
  - **Auto Mode**: Arduino operates autonomously after target input
- **Intuitive GUI**: Modern dark-themed interface with mode-specific controls
- **Keyboard Controls**: 
  - WASD keys for vehicle movement
  - Arrow keys for turret/barrel control
  - Spacebar for shooting
- **Direct Input**: Sliders and text fields for precise control of servos and motors
- **Real-time Feedback**: Status indicators and mission logs

## Technical Details
The application communicates with the Arduino at IP address 172.16.11.207:80 by sending CSV-formatted strings containing the following values:

1. Motor1 Direction
2. Motor1 Power
3. Motor2 Direction
4. Motor2 Power
5. Motor3 Direction
6. Motor3 Power
7. Motor4 Direction
8. Motor4 Power
9. Steering Servo Position
10. Turret Servo Position
11. Barrel Servo Position
12. Camera Servo Position
13. Ultrasonic Servo Position

## Getting Started
1. Ensure the Arduino vehicle is powered on and connected to the network
2. Launch the CUTECAT application
3. Select a control mode from the start screen
4. Use the appropriate controls for the selected mode

## Requirements
- Java 22 or higher
- JavaFX 22.0.1 or higher
- Network connection to the Arduino vehicle

## Building from Source
The project uses Maven for dependency management and building:

```bash
mvn clean package
```

This will create an executable JAR file in the `target` directory.
