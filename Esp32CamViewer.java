import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

public class Esp32CamViewer extends Thread {
    private final JLabel imageLabel;
    private final String camUrl;

    public Esp32CamViewer(JLabel imageLabel, String ip) {
        this.imageLabel = imageLabel;
        this.camUrl = "http://" + ip + "/capture";
    }

    @Override
    public void run() {
        while (true) {
            try {
                InputStream stream = new URL(camUrl).openStream();
                BufferedImage img = ImageIO.read(stream);
                if (img != null) {
                    ImageIcon icon = new ImageIcon(img);
                    imageLabel.setIcon(icon);
                }
                stream.close();
                Thread.sleep(1000); // alle 1 Sekunde ein neues Bild
            } catch (Exception e) {
                System.err.println("Fehler beim Abrufen des Bildes: " + e.getMessage());
            }
        }
    }

    // Beispiel: In deiner GUI main()
    public static void main(String[] args) {
        JFrame frame = new JFrame("ESP32-CAM Viewer");
        JLabel label = new JLabel();
        frame.add(label);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        String ip = "192.168.1.123"; // <-- IP der ESP32-CAM
        new Esp32CamViewer(label, ip).start();
    }
}
