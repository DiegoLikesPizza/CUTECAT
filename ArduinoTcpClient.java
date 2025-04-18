import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ArduinoTcpClient {
    public static void main(String[] args) {
        String host = "192.168.178.180";
        int port = 1234;
        try (Socket socket = new Socket(host, port)) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                Scanner sc = new Scanner(System.in);
                String command = sc.nextLine();
                System.out.println("Sende: " + command.trim());
                out.print(command);
                out.flush();
                String response = in.readLine();
                System.out.println("Antwort: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
