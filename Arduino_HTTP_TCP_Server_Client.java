import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.*;

public class Arduino_HTTP_TCP_Server_Client {
    public static void main(String[] args) {
        String arduinoIP = "192.168.178.180";
        //KeyInputHandler keyInputHandler = new KeyInputHandler();

        // Threadzum Empfangen
        new Thread(() -> {
            while (true) {
                try {
                    URL url = new URL("http://" + arduinoIP + "/messwerte");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println("Messwerte vom Arduino: " + line);
                    }
                    in.close();
                } catch (Exception e) {
                    System.err.println("Fehler beim Abrufen der Messwerte: " + e.getMessage());
                }
            }
        }).start();

        // Thread zum Senden
        new Thread(() -> {
            try (
                    Socket socket = new Socket(arduinoIP, 81);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Scanner scanner = new Scanner(System.in);
            ) {
                while (true) {
                    RandomCommandCreator rand = new RandomCommandCreator();
                    //CommandReader reader = new CommandReader(keyInputHandler);
                    int i = scanner.nextInt();
                    ArrayList commands = rand.createrandomCommands(i);//reader.getCurrentCommands();
                    String csv = toCsv(commands);
                    System.out.println(csv);
                    out.println(csv);
                    //String response = in.readLine();
                    //System.out.println("Antwort vom Arduino: " + response);
                    //Thread.sleep(3000);
                }
            } catch (Exception e) {
                System.err.println("Fehler beim Senden an Arduino: " + e.getMessage());
            }
        }).start();
    }

    private static String toCsv(ArrayList<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(',');
            }
        }
        return sb.toString();
    }
}

