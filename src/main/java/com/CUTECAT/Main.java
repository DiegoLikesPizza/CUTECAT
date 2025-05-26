package com.CUTECAT;

import com.CUTECAT.modes.ManualMode;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    static ManualMode manualMode = new ManualMode();
    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        manualMode.startControl();
        manualMode.setRemainingAmmo(5); // Set some initial ammo

        while (true) {
            testConnection();

            printMenu();
            try {
                int choice = scanner.nextInt();
                handleChoice(choice);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    public static void testConnection() {
        try (Socket socket = new Socket("172.16.10.127", 81)) {
            System.out.println("Successfully connected to Arduino!");
        } catch (IOException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Manual Mode Test Menu ===");
        System.out.println("1. Drive forward (speed 100)");
        System.out.println("2. Drive forward (speed 200)");
        System.out.println("3. Drive backward (speed 100)");
        System.out.println("4. Move with direction");
        System.out.println("5. Shoot");
        System.out.println("6. Check ammo count");
        System.out.println("7. Stop and exit");
        System.out.print("Choose an option: ");
    }

    private static void handleChoice(int choice) throws Exception {
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
                if (manualMode.canShoot()) {
                    manualMode.shoot();
                    System.out.println("Shot fired!");
                } else {
                    System.out.println("Cannot shoot - no ammo left!");
                }
                break;
            case 6:
                System.out.println("Remaining ammo: " + manualMode.getAmmoCount());
                break;
            case 7:
                manualMode.stopControl();
                System.out.println("Stopping control and exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option!");
        }
    }
}