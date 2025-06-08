import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class KeyInputHandler extends JPanel implements KeyListener {

    private final ArrayList<Integer> keyStates;
    private final int MAX_ROTATION = 130;
    private final int MIN_ROTATION = 70;
    private final int MAX_SENSOR = 180;
    private final int MIN_SENSOR = 0;
    private final int MAX_TURM = 180;
    private final int MIN_TURM = 0;
    private final int MAX_LAUF = 70;
    private final int MIN_LAUF = 0;

    private final String[] labels = {
            "front", "left", "back", "right",
            "sensorvert", "sensorhori", "turm", "lauf", "schuss"
    };

    public KeyInputHandler() {
        keyStates = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 90, 90, 90, 90, 0));
        setFocusable(true);
        addKeyListener(this);

        // UI Frame
        JFrame frame = new JFrame("Key Input Handler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.add(this);
        frame.setVisible(true);
        // Timer zur Konsolenausgabe
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //printKeyStates();
            }
        }, 0, 100);
    }

    private void printKeyStates() {
        StringBuilder labelLine = new StringBuilder();
        StringBuilder valueLine = new StringBuilder();

        for (int i = 0; i < keyStates.size(); i++) {
            String label = String.format("%-12s", labels[i]);
            String value = String.format("%-12d", keyStates.get(i));
            labelLine.append(label);
            valueLine.append(value);
        }

        System.out.println(labelLine);
        System.out.println(valueLine);
    }

    public ArrayList<Integer> getKeyStates() {
        return new ArrayList<>(keyStates);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                keyStates.set(2, 0);
                keyStates.set(0, 1);
                break;
            case KeyEvent.VK_S:
                keyStates.set(0, 0);
                keyStates.set(2, 1);
                break;
            case KeyEvent.VK_A:
                keyStates.set(3, 0);
                keyStates.set(1, 1);
                break;
            case KeyEvent.VK_D:
                keyStates.set(1, 0);
                keyStates.set(3, 1);
                break;
            case KeyEvent.VK_CAPS_LOCK:
                int currentSensorVert = keyStates.get(4);
                if (currentSensorVert + 10 <= MAX_ROTATION) {
                    keyStates.set(4, currentSensorVert + 10);
                }
                break;
            case KeyEvent.VK_SHIFT:
                currentSensorVert = keyStates.get(4);
                if (currentSensorVert - 10 >= MIN_ROTATION) {
                    keyStates.set(4, currentSensorVert - 10);
                }
                break;
            case KeyEvent.VK_E:
                int currentSensorHori = keyStates.get(5);
                if (currentSensorHori + 10 <= MAX_SENSOR) {
                    keyStates.set(5, currentSensorHori + 10);
                }
                break;
            case KeyEvent.VK_Q:
                currentSensorHori = keyStates.get(5);
                if (currentSensorHori - 10 >= MIN_SENSOR) {
                    keyStates.set(5, currentSensorHori - 10);
                }
                break;
            case KeyEvent.VK_LEFT:
                int turm = keyStates.get(6);
                if (turm - 10 >= MIN_TURM) {
                    keyStates.set(6, turm - 10);
                }
                break;
            case KeyEvent.VK_RIGHT:
                turm = keyStates.get(6);
                if (turm + 10 <= MAX_TURM) {
                    keyStates.set(6, turm + 10);
                }
                break;
            case KeyEvent.VK_UP:
                int lauf = keyStates.get(7);
                if (lauf + 1 <= MAX_LAUF) {
                    keyStates.set(7, lauf + 1);
                }
                break;
            case KeyEvent.VK_DOWN:
                lauf = keyStates.get(7);
                if (lauf - 1 >= MIN_LAUF) {
                    keyStates.set(7, lauf - 1);
                }
                break;
            case KeyEvent.VK_ENTER:
                keyStates.set(8, 1);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                keyStates.set(0, 0);
                break;
            case KeyEvent.VK_A:
                keyStates.set(1, 0);
                break;
            case KeyEvent.VK_S:
                keyStates.set(2, 0);
                break;
            case KeyEvent.VK_D:
                keyStates.set(3, 0);
                break;
            case KeyEvent.VK_ENTER:
                keyStates.set(8, 0);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        new KeyInputHandler();
    }
}