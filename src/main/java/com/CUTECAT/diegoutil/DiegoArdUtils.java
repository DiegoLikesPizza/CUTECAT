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
     * Sends a CSV string to an Arduino at the specified IP address and port.
     * 
     * @param ip The IP address of the Arduino
     * @param port The port number
     * @param csvData The CSV data to send
     * @return true if the data was sent successfully, false otherwise
     */
    public static boolean sendToArduino(String ip, int port, String csvData) {
        HttpURLConnection connection = null;
        
        try {
            // Encode the CSV data
            String encodedData = URLEncoder.encode(csvData, StandardCharsets.UTF_8.toString());
            
            // Create the URL with the data as a query parameter
            URL url = new URL("http://" + ip + ":" + port + "/?" + encodedData);
            
            // Open a connection
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(1000); // 1 second timeout
            connection.setReadTimeout(1000);    // 1 second timeout
            
            // Get the response code
            int responseCode = connection.getResponseCode();
            
            // Return true if the response code is 200 (OK)
            return responseCode == HttpURLConnection.HTTP_OK;
            
        } catch (IOException e) {
            System.err.println("Error sending data to Arduino: " + e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    /**
     * Checks if an Arduino is reachable at the specified IP address and port.
     * 
     * @param ip The IP address of the Arduino
     * @param port The port number
     * @return true if the Arduino is reachable, false otherwise
     */
    public static boolean isArduinoReachable(String ip, int port) {
        HttpURLConnection connection = null;
        
        try {
            // Create the URL
            URL url = new URL("http://" + ip + ":" + port);
            
            // Open a connection
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(1000); // 1 second timeout
            
            // Get the response code
            int responseCode = connection.getResponseCode();
            
            // Return true if the response code is 200 (OK)
            return responseCode == HttpURLConnection.HTTP_OK;
            
        } catch (IOException e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}