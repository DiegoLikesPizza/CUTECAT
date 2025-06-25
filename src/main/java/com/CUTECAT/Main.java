package com.CUTECAT;

import com.CUTECAT.modes.AutoMode;
import com.CUTECAT.modes.ManualMode;
import com.CUTECAT.modes.SemiAutoMode;
import com.CUTECAT.modes.modebase;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Main class for testing the tank controle system.
 * Provides a command-line interface to test all three modes:
 * - Manual mode: User controls everything
 * - Semi-auto mode: Automatic aiming and shooting, manual movement
 * - Full-auto mode: Automatic pathfinding, movement, aiming, and shooting
 */
public class Main {
    // Mode instances
    static ManualMode manualMode = new ManualMode();
    static SemiAutoMode semiAutoMode = new SemiAutoMode();
    static AutoMode autoMode = new AutoMode();

    // Current active mode
    static modebase currentMode = manualMode;

    // Input scanner
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize all modes
        manualMode.startControl();
        manualMode.setRemainingAmmo(10);

        semiAutoMode.startControl();
        semiAutoMode.setRemainingAmmo(10);

        autoMode.startControl();
        autoMode.setRemainingAmmo(10);

        System.out.println("CUTECAT Tank Control System");
        System.out.println("---------------------------");

        while (true) {
            testConnection();

            printMainMenu();
            try {
                int choice = scanner.nextInt();
                handleMainMenuChoice(choice);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    /**
     * Test the connection to the Arduino
     */
    public static void testConnection() {
        try (Socket socket = new Socket("172.16.10.127", 81)) {
            System.out.println("Successfully connected to Arduino!");
        } catch (IOException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    /**
     * Print the main menu
     */
    private static void printMainMenu() {
        System.out.println("\n=== CUTECAT Tank Control System ===");
        System.out.println("1. Manual Mode");
        System.out.println("2. Semi-Auto Mode");
        System.out.println("3. Full-Auto Mode");
        System.out.println("4. Exit");
        System.out.print("Choose a mode: ");
    }

    /**
     * Handle the main menu choice
     */
    private static void handleMainMenuChoice(int choice) throws Exception {
        switch (choice) {
            case 1:
                System.out.println("Entering Manual Mode");
                currentMode = manualMode;
                manualModeMenu();
                break;
            case 2:
                System.out.println("Entering Semi-Auto Mode");
                currentMode = semiAutoMode;
                semiAutoModeMenu();
                break;
            case 3:
                System.out.println("Entering Full-Auto Mode");
                currentMode = autoMode;
                autoModeMenu();
                break;
            case 4:
                manualMode.stopControl();
                semiAutoMode.stopControl();
                autoMode.stopControl();
                System.out.println("Stopping all modes and exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    /**
     * Manual mode menu
     */
    private static void manualModeMenu() {
        boolean inManualMode = true;

        while (inManualMode) {
            System.out.println("\n=== Manual Mode Menu ===");
            System.out.println("1. Drive forward (speed 100)");
            System.out.println("2. Drive forward (speed 200)");
            System.out.println("3. Drive backward (speed 100)");
            System.out.println("4. Move with direction");
            System.out.println("5. Aim manually");
            System.out.println("6. Shoot");
            System.out.println("7. Check ammo count");
            System.out.println("8. Return to main menu");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        manualMode.driveWOSteer(true, 100);
                        System.out.println("Driving forward at speed 100");
                        break;
                    case 2:
                        manualMode.driveWOSteer(true, 200);
                        System.out.println("Driving forward at speed 200");
                        break;
                    case 3:
                        manualMode.driveWOSteer(false, 100);
                        System.out.println("Driving backward at speed 100");
                        break;
                    case 4:
                        System.out.print("Enter speed (0-255): ");
                        int speed = scanner.nextInt();
                        System.out.print("Enter direction (-90 to 90): ");
                        int direction = scanner.nextInt();
                        manualMode.move(speed, direction);
                        System.out.println("Moving with speed " + speed + " and direction " + direction);
                        break;
                    case 5:
                        System.out.print("Enter yaw (0-180): ");
                        int yaw = scanner.nextInt();
                        System.out.print("Enter pitch (0-180): ");
                        int pitch = scanner.nextInt();
                        manualMode.aimManually(yaw, pitch);
                        System.out.println("Aiming with yaw " + yaw + " and pitch " + pitch);
                        break;
                    case 6:
                        manualMode.shoot();
                        break;
                    case 7:
                        System.out.println("Remaining ammo: " + manualMode.getAmmoCount());
                        break;
                    case 8:
                        inManualMode = false;
                        manualMode.stopMovement();
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    /**
     * Semi-auto mode menu
     */
    private static void semiAutoModeMenu() {
        boolean inSemiAutoMode = true;

        while (inSemiAutoMode) {
            System.out.println("\n=== Semi-Auto Mode Menu ===");
            System.out.println("1. Drive forward (speed 100)");
            System.out.println("2. Drive forward (speed 200)");
            System.out.println("3. Drive backward (speed 100)");
            System.out.println("4. Move with direction");
            System.out.println("5. Set target (for auto-aiming)");
            System.out.println("6. Shoot (auto-aimed)");
            System.out.println("7. Check ammo count");
            System.out.println("8. Return to main menu");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        semiAutoMode.driveWOSteer(true, 100);
                        System.out.println("Driving forward at speed 100");
                        break;
                    case 2:
                        semiAutoMode.driveWOSteer(true, 200);
                        System.out.println("Driving forward at speed 200");
                        break;
                    case 3:
                        semiAutoMode.driveWOSteer(false, 100);
                        System.out.println("Driving backward at speed 100");
                        break;
                    case 4:
                        System.out.print("Enter speed (0-255): ");
                        int speed = scanner.nextInt();
                        System.out.print("Enter direction (-90 to 90): ");
                        int direction = scanner.nextInt();
                        semiAutoMode.move(speed, direction);
                        System.out.println("Moving with speed " + speed + " and direction " + direction);
                        break;
                    case 5:
                        System.out.print("Enter target distance (cm): ");
                        int distance = scanner.nextInt();
                        System.out.print("Enter target angle (-180 to 180): ");
                        int angle = scanner.nextInt();
                        try {
                            semiAutoMode.aim(distance, angle);
                        } catch (Exception e) {
                            System.out.println("Aiming error: " + e.getMessage());
                        }
                        break;
                    case 6:
                        semiAutoMode.shoot();
                        break;
                    case 7:
                        System.out.println("Remaining ammo: " + semiAutoMode.getAmmoCount());
                        break;
                    case 8:
                        inSemiAutoMode = false;
                        semiAutoMode.stopMovement();
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    /**
     * Full-auto mode menu
     */
    private static void autoModeMenu() {
        boolean inAutoMode = true;

        while (inAutoMode) {
            System.out.println("\n=== Full-Auto Mode Menu ===");
            System.out.println("1. Start auto mode");
            System.out.println("2. Stop auto mode");
            System.out.println("3. Add path point");
            System.out.println("4. Clear path");
            System.out.println("5. Simulate target found");
            System.out.println("6. Check ammo count");
            System.out.println("7. Return to main menu");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        autoMode.startAutoMode();
                        break;
                    case 2:
                        autoMode.stopAutoMode();
                        break;
                    case 3:
                        System.out.print("Enter X coordinate: ");
                        int x = scanner.nextInt();
                        System.out.print("Enter Y coordinate: ");
                        int y = scanner.nextInt();
                        System.out.print("Enter direction (-90 to 90): ");
                        int direction = scanner.nextInt();
                        autoMode.addPathPoint(x, y, direction);
                        break;
                    case 4:
                        autoMode.clearPath();
                        break;
                    case 5:
                        System.out.print("Enter target distance (cm): ");
                        int distance = scanner.nextInt();
                        System.out.print("Enter target angle (-180 to 180): ");
                        int angle = scanner.nextInt();
                        autoMode.simulateTargetFound(distance, angle);
                        break;
                    case 6:
                        System.out.println("Remaining ammo: " + autoMode.getAmmoCount());
                        break;
                    case 7:
                        inAutoMode = false;
                        autoMode.stopAutoMode();
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }
}
