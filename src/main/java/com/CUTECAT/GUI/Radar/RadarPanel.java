package radar;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RadarPanel extends Pane {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private double angle = 0;
    private final double centerX, centerY, radius;
    private final List<double[]> objects = new ArrayList<>();
    private final Random random = new Random();

    public RadarPanel() {
        this.setStyle("-fx-background-color: black;");
        canvas = new Canvas(400, 400);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);

        centerX = canvas.getWidth() / 2;
        centerY = canvas.getHeight() / 2;
        radius = 200;

        // Zufällig ein paar Objekte auf dem Radar platzieren
        for (int i = 0; i < 10; i++) {
            double objAngle = random.nextDouble() * 360;
            double objRadius = random.nextDouble() * radius;
            double x = centerX + objRadius * Math.cos(Math.toRadians(objAngle));
            double y = centerY + objRadius * Math.sin(Math.toRadians(objAngle));
            objects.add(new double[]{x, y});
        }
    }

    public void startScanning() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawRadar();
            }
        };
        timer.start();
    }

    private void drawRadar() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Radar-Kreis zeichnen
        gc.setStroke(Color.DARKGREEN);
        gc.setLineWidth(1);
        for (int r = 50; r <= radius; r += 50) {
            gc.strokeOval(centerX - r, centerY - r, r * 2, r * 2);
        }

        // Radar-Linien (z.B. Himmelsrichtungen)
        for (int i = 0; i < 360; i += 45) {
            double x = centerX + radius * Math.cos(Math.toRadians(i));
            double y = centerY + radius * Math.sin(Math.toRadians(i));
            gc.strokeLine(centerX, centerY, x, y);
        }

        // Radar-Scan-Strahl
        gc.setStroke(Color.LIME);
        gc.setLineWidth(2);
        double endX = centerX + radius * Math.cos(Math.toRadians(angle));
        double endY = centerY + radius * Math.sin(Math.toRadians(angle));
        gc.strokeLine(centerX, centerY, endX, endY);

        // Detektierte Objekte anzeigen (rot, wenn im aktuellen Scanbereich)
        for (double[] obj : objects) {
            double dx = obj[0] - centerX;
            double dy = obj[1] - centerY;
            double objAngle = Math.toDegrees(Math.atan2(dy, dx));
            if (objAngle < 0) objAngle += 360;

            // Innerhalb von ±2° zum Scanwinkel = erkannt
            if (Math.abs(objAngle - angle) < 2 || Math.abs(objAngle - angle + 360) < 2) {
                gc.setFill(Color.RED);
            } else {
                gc.setFill(Color.DARKRED);
            }
            gc.fillOval(obj[0] - 4, obj[1] - 4, 8, 8);
        }

        // Winkel aktualisieren
        angle += 1.5;
        if (angle >= 360) angle -= 360;
    }
}