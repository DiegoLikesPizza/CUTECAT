package com.CUTECAT.diegoutil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for communicating with Arduino devices over HTTP.
 */
public class DiegoArdUtils {

    /**
     * Maximum number of retry attempts for Arduino communication
     */
    private static final int MAX_RETRY_ATTEMPTS = 3;

    /**
     * Connection timeout in milliseconds
     */
    private static final int CONNECTION_TIMEOUT = 10000; // 10 seconds

    /**
     * Read timeout in milliseconds
     */
    private static final int READ_TIMEOUT = 10000; // 10 seconds

    /**
     * Sends a CSV string to an Arduino at the specified IP address and port.
     * Uses the default number of retry attempts.
     * The CSV data is sent directly in the request body using a POST request to the "/data" endpoint.
     * 
     * @param ip The IP address of the Arduino
     * @param port The port number
     * @param csvData The CSV data to send
     * @return true if the data was sent successfully, false otherwise
     */
    public static boolean sendToArduino(String ip, int port, String csvData) {
        return sendToArduino(ip, port, csvData, MAX_RETRY_ATTEMPTS);
    }

    /**
     * Sends a CSV string to an Arduino at the specified IP address and port with retry mechanism.
     * The CSV data is sent directly in the request body using a POST request to the "/data" endpoint.
     * 
     * @param ip The IP address of the Arduino
     * @param port The port number
     * @param csvData The CSV data to send
     * @param retryAttempts Number of retry attempts if connection fails
     * @return true if the data was sent successfully, false otherwise
     */
    public static boolean sendToArduino(String ip, int port, String csvData, int retryAttempts) {
        HttpURLConnection connection = null;
        int attempts = 0;

        while (attempts < retryAttempts) {
            try {
                // Create the URL with the /data endpoint
                URL url = new URL("http://" + ip + ":" + port);

                // Open a connection
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setReadTimeout(READ_TIMEOUT);

                // Set up the connection for sending data
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "text/plain");

                // Write the CSV data directly to the output stream
                try (java.io.OutputStream os = connection.getOutputStream()) {
                    byte[] input = csvData.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // Get the response code
                int responseCode = connection.getResponseCode();

                // Return true if the response code is 200 (OK)
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return true;
                } else {
                    System.err.println("Arduino returned non-OK response code: " + responseCode);
                }
            } catch (IOException e) {
                System.err.println("Attempt " + (attempts + 1) + " failed: " + e.getMessage());
                if (e.getMessage() != null && e.getMessage().contains("Connection timed out")) {
                    System.err.println("Connection timed out. Arduino might be busy or unreachable.");
                } else if (e.getMessage() != null && e.getMessage().contains("Connection refused")) {
                    System.err.println("Connection refused. Arduino might not be running or the port might be wrong.");
                } else if (e.getMessage() != null && e.getMessage().contains("HTTP response code: 404")) {
                    System.err.println("HTTP 404 Not Found. The Arduino endpoint '/data' does not exist or is not configured correctly. Try a different endpoint or check the Arduino code.");
                } else if (e.getMessage() != null && e.getMessage().contains("HTTP response code: 400")) {
                    System.err.println("HTTP 400 Bad Request. The Arduino could not understand the request.");
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            attempts++;

            // If we have more attempts to go, wait a bit before retrying
            if (attempts < retryAttempts) {
                try {
                    System.out.println("Retrying in 1 second... (Attempt " + (attempts + 1) + " of " + retryAttempts + ")");
                    Thread.sleep(1000); // Wait 1 second before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        return false;
    }

    /**
     * Checks if an Arduino is reachable at the specified IP address and port.
     * Uses the default number of retry attempts.
     * Sends a POST request to the "/data" endpoint to check if the Arduino is reachable.
     * 
     * @param ip The IP address of the Arduino
     * @param port The port number
     * @return true if the Arduino is reachable, false otherwise
     */
    public static boolean isArduinoReachable(String ip, int port) {
        return isArduinoReachable(ip, port, MAX_RETRY_ATTEMPTS);
    }

    /**
     * Checks if an Arduino is reachable at the specified IP address and port with retry mechanism.
     * Sends a POST request to the "/data" endpoint to check if the Arduino is reachable.
     * 
     * @param ip The IP address of the Arduino
     * @param port The port number
     * @param retryAttempts Number of retry attempts if connection fails
     * @return true if the Arduino is reachable, false otherwise
     */
    public static boolean isArduinoReachable(String ip, int port, int retryAttempts) {
        HttpURLConnection connection = null;
        int attempts = 0;

        while (attempts < retryAttempts) {
            try {
                // Create the URL with the /data endpoint
                URL url = new URL("http://" + ip + ":" + port + "/data");

                // Open a connection
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setReadTimeout(READ_TIMEOUT);

                // Set up the connection for sending data (required for POST)
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "text/plain");

                // For a POST request, we need to write something to the output stream
                try (java.io.OutputStream os = connection.getOutputStream()) {
                    // Just write an empty string
                    os.write(new byte[0]);
                }

                // Get the response code
                int responseCode = connection.getResponseCode();

                // Return true if the response code is 200 (OK)
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return true;
                } else {
                    System.err.println("Arduino returned non-OK response code: " + responseCode);
                }
            } catch (IOException e) {
                System.err.println("Attempt " + (attempts + 1) + " to reach Arduino failed: " + e.getMessage());
                if (e.getMessage() != null && e.getMessage().contains("Connection timed out")) {
                    System.err.println("Connection timed out. Arduino might be busy or unreachable.");
                } else if (e.getMessage() != null && e.getMessage().contains("Connection refused")) {
                    System.err.println("Connection refused. Arduino might not be running or the port might be wrong.");
                } else if (e.getMessage() != null && e.getMessage().contains("HTTP response code: 404")) {
                    System.err.println("HTTP 404 Not Found. The Arduino endpoint '/data' does not exist or is not configured correctly. Try a different endpoint or check the Arduino code.");
                } else if (e.getMessage() != null && e.getMessage().contains("HTTP response code: 400")) {
                    System.err.println("HTTP 400 Bad Request. The Arduino could not understand the request.");
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            attempts++;

            // If we have more attempts to go, wait a bit before retrying
            if (attempts < retryAttempts) {
                try {
                    System.out.println("Retrying connection check in 1 second... (Attempt " + (attempts + 1) + " of " + retryAttempts + ")");
                    Thread.sleep(1000); // Wait 1 second before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        return false;
    }
}
