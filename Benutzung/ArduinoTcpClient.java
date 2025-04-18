package Benutzung;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ArduinoTcpClient {
    public static void main(String[] args) {
        String host = "192.168.178.180"; // IP des GIGA (von Serial-Monitor)
        int port = 1234;              // Port muss passen
        try (Socket socket = new Socket(host, port)) {               // Verbindung aufbauen :contentReference[oaicite:12]{index=12}
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));     // InputStream :contentReference[oaicite:13]{index=13}
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // OutputStream :contentReference[oaicite:14]{index=14}
            while (true) {
                // Beispiel: Befehl senden
                Scanner sc = new Scanner(System.in);
                String command = sc.nextLine();
                System.out.println("Sende: " + command.trim());
                out.print(command);
                out.flush();

                // Antwort lesen
                String response = in.readLine();
                System.out.println("Antwort: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
