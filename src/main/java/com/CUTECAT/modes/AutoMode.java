package com.CUTECAT.modes;

import com.CUTECAT.app.WidgetFactory;
import com.CUTECAT.diegoutil.DiegoMathUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Automatic control mode for the vehicle.
 * In this mode, the Arduino operates autonomously after the user inputs a target.
 */
public class AutoMode extends modebase {
    
    // UI components
    private TextField targetXField;
    private TextField targetYField;
    private Button startMissionButton;
    private Button abortMissionButton;
    private Canvas mapCanvas;
    private GraphicsContext gc;
    private ListView<String> missionLogListView;
    private ObservableList<String> missionLogItems;
    
    // Mission state
    private AtomicBoolean missionActive = new AtomicBoolean(false);
    private List<PathPoint> path = new ArrayList<>();
    private double vehicleX = 0;
    private double vehicleY = 0;
    private double vehicleHeading = 0; // in degrees, 0 is north
    
    /**
     * Creates a new auto mode instance.
     * 
     * @param parentStage The parent stage
     * @param arduinoIp The IP address of the Arduino
     * @param arduinoPort The port number of the Arduino
     */
    public AutoMode(Stage parentStage, String arduinoIp, int arduinoPort) {
        super(parentStage, arduinoIp, arduinoPort);
    }
    
    @Override
    protected String getModeName() {
        return "Auto Mode";
    }
    
    @Override
    protected void addModeControls(VBox container) {
        // Add instructions
        Label instructionsLabel = new Label(
            "Enter target coordinates and start the mission.\n" +
            "The vehicle will autonomously navigate to the target."
        );
        instructionsLabel.getStyleClass().add("instructions-label");
        instructionsLabel.setPadding(new Insets(0, 0, 20, 0));
        
        // Create target input controls
        VBox targetControls = new VBox(10);
        targetControls.setPadding(new Insets(10));
        
        Label targetTitle = WidgetFactory.createSectionTitle("Target Coordinates");
        
        // Target X coordinate
        HBox targetXBox = new HBox(10);
        Label targetXLabel = new Label("Target X (cm):");
        targetXLabel.setPrefWidth(100);
        targetXField = new TextField("100");
        targetXField.setPrefWidth(100);
        targetXBox.getChildren().addAll(targetXLabel, targetXField);
        
        // Target Y coordinate
        HBox targetYBox = new HBox(10);
        Label targetYLabel = new Label("Target Y (cm):");
        targetYLabel.setPrefWidth(100);
        targetYField = new TextField("100");
        targetYField.setPrefWidth(100);
        targetYBox.getChildren().addAll(targetYLabel, targetYField);
        
        // Mission control buttons
        HBox missionButtons = new HBox(10);
        startMissionButton = WidgetFactory.createButton("Start Mission", e -> startMission());
        abortMissionButton = WidgetFactory.createButton("Abort Mission", e -> abortMission());
        abortMissionButton.setDisable(true); // Initially disabled
        missionButtons.getChildren().addAll(startMissionButton, abortMissionButton);
        
        // Add all components to the target controls
        targetControls.getChildren().addAll(targetTitle, targetXBox, targetYBox, missionButtons);
        
        // Create map display
        VBox mapDisplay = new VBox(10);
        mapDisplay.setPadding(new Insets(10));
        
        Label mapTitle = WidgetFactory.createSectionTitle("Mission Map");
        
        // Create canvas for map
        mapCanvas = new Canvas(400, 300);
        gc = mapCanvas.getGraphicsContext2D();
        drawMap();
        
        // Add all components to the map display
        mapDisplay.getChildren().addAll(mapTitle, mapCanvas);
        
        // Create mission log
        VBox missionLog = new VBox(10);
        missionLog.setPadding(new Insets(10));
        
        Label logTitle = WidgetFactory.createSectionTitle("Mission Log");
        
        // Create list view for mission log
        missionLogItems = FXCollections.observableArrayList();
        missionLogListView = new ListView<>(missionLogItems);
        missionLogListView.setPrefHeight(150);
        
        // Add all components to the mission log
        missionLog.getChildren().addAll(logTitle, missionLogListView);
        
        // Add all components to the main container
        container.getChildren().addAll(instructionsLabel, targetControls, mapDisplay, missionLog);
    }
    
    /**
     * Starts the autonomous mission.
     */
    private void startMission() {
        try {
            // Parse target coordinates
            double targetX = Double.parseDouble(targetXField.getText());
            double targetY = Double.parseDouble(targetYField.getText());
            
            // Update UI
            startMissionButton.setDisable(true);
            abortMissionButton.setDisable(false);
            missionActive.set(true);
            
            // Clear previous path
            path.clear();
            
            // Add log entry
            addLogEntry("Mission started. Target: (" + targetX + ", " + targetY + ")");
            
            // Plan path to target
            planPath(targetX, targetY);
            
            // Draw the path on the map
            drawMap();
            
            // Start mission thread
            Thread missionThread = new Thread(() -> executeMission());
            missionThread.setDaemon(true);
            missionThread.start();
            
        } catch (NumberFormatException e) {
            addLogEntry("Error: Invalid target coordinates");
        }
    }
    
    /**
     * Aborts the current mission.
     */
    private void abortMission() {
        missionActive.set(false);
        startMissionButton.setDisable(false);
        abortMissionButton.setDisable(true);
        
        // Stop the vehicle
        stopMovement();
        
        // Add log entry
        addLogEntry("Mission aborted");
    }
    
    /**
     * Plans a path to the target.
     * 
     * @param targetX The target X coordinate
     * @param targetY The target Y coordinate
     */
    private void planPath(double targetX, double targetY) {
        // Simple direct path for now
        // In a real implementation, this would use path planning algorithms
        
        // Start point (vehicle's current position)
        path.add(new PathPoint(vehicleX, vehicleY, "Start"));
        
        // Add some intermediate points
        double distance = DiegoMathUtils.distance(vehicleX, vehicleY, targetX, targetY);
        int numPoints = (int) (distance / 20); // One point every 20cm
        
        for (int i = 1; i < numPoints; i++) {
            double fraction = (double) i / numPoints;
            double x = vehicleX + (targetX - vehicleX) * fraction;
            double y = vehicleY + (targetY - vehicleY) * fraction;
            path.add(new PathPoint(x, y, "Waypoint " + i));
        }
        
        // End point (target)
        path.add(new PathPoint(targetX, targetY, "Target"));
    }
    
    /**
     * Executes the planned mission.
     */
    private void executeMission() {
        // Skip the first point (start position)
        for (int i = 1; i < path.size() && missionActive.get(); i++) {
            PathPoint point = path.get(i);
            
            // Add log entry
            addLogEntry("Moving to " + point.name + " (" + point.x + ", " + point.y + ")");
            
            // Calculate direction to the point
            double dx = point.x - vehicleX;
            double dy = point.y - vehicleY;
            double targetHeading = Math.toDegrees(Math.atan2(dy, dx));
            
            // Turn to face the point
            turnToHeading(targetHeading);
            
            // Move to the point
            moveToPoint(point);
            
            // Update vehicle position
            vehicleX = point.x;
            vehicleY = point.y;
            
            // Update the map
            Platform.runLater(this::drawMap);
            
            // If this is the target point, perform targeting and shooting
            if (i == path.size() - 1) {
                addLogEntry("Target reached. Activating targeting system.");
                
                // Activate targeting
                activateCamera();
                activateUltrasonicSensor();
                
                // Auto-target
                boolean targeted = autoTarget();
                
                if (targeted) {
                    addLogEntry("Target acquired. Firing.");
                    shoot();
                } else {
                    addLogEntry("Targeting failed.");
                }
            }
        }
        
        // Mission complete
        if (missionActive.get()) {
            addLogEntry("Mission completed successfully");
            Platform.runLater(() -> {
                startMissionButton.setDisable(false);
                abortMissionButton.setDisable(true);
                missionActive.set(false);
            });
        }
    }
    
    /**
     * Turns the vehicle to face a specific heading.
     * 
     * @param targetHeading The target heading in degrees
     */
    private void turnToHeading(double targetHeading) {
        // Calculate the difference between current and target heading
        double headingDiff = targetHeading - vehicleHeading;
        
        // Normalize to -180 to 180 degrees
        while (headingDiff > 180) headingDiff -= 360;
        while (headingDiff < -180) headingDiff += 360;
        
        // Determine turn direction
        if (headingDiff > 0) {
            // Turn right
            addLogEntry("Turning right by " + Math.abs(headingDiff) + " degrees");
            turnRight((int) Math.min(Math.abs(headingDiff), 45));
        } else if (headingDiff < 0) {
            // Turn left
            addLogEntry("Turning left by " + Math.abs(headingDiff) + " degrees");
            turnLeft((int) Math.min(Math.abs(headingDiff), 45));
        }
        
        // Simulate turning time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Update vehicle heading
        vehicleHeading = targetHeading;
    }
    
    /**
     * Moves the vehicle to a specific point.
     * 
     * @param point The target point
     */
    private void moveToPoint(PathPoint point) {
        // Calculate distance to the point
        double distance = DiegoMathUtils.distance(vehicleX, vehicleY, point.x, point.y);
        
        // Move forward
        addLogEntry("Moving forward " + distance + " cm");
        moveForward(50); // 50% power
        
        // Simulate movement time (1 second per 20cm)
        try {
            Thread.sleep((long) (distance / 20 * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Stop movement
        stopMovement();
    }
    
    /**
     * Draws the map on the canvas.
     */
    private void drawMap() {
        // Clear the canvas
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
        
        // Draw grid lines
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(0.5);
        
        for (int x = 0; x <= mapCanvas.getWidth(); x += 20) {
            gc.strokeLine(x, 0, x, mapCanvas.getHeight());
        }
        
        for (int y = 0; y <= mapCanvas.getHeight(); y += 20) {
            gc.strokeLine(0, y, mapCanvas.getWidth(), y);
        }
        
        // Draw path
        if (!path.isEmpty()) {
            gc.setStroke(Color.BLUE);
            gc.setLineWidth(2);
            
            for (int i = 0; i < path.size() - 1; i++) {
                PathPoint p1 = path.get(i);
                PathPoint p2 = path.get(i + 1);
                
                // Scale coordinates to fit the canvas
                double x1 = p1.x * mapCanvas.getWidth() / 200;
                double y1 = mapCanvas.getHeight() - (p1.y * mapCanvas.getHeight() / 200);
                double x2 = p2.x * mapCanvas.getWidth() / 200;
                double y2 = mapCanvas.getHeight() - (p2.y * mapCanvas.getHeight() / 200);
                
                gc.strokeLine(x1, y1, x2, y2);
            }
        }
        
        // Draw vehicle
        double vehicleCanvasX = vehicleX * mapCanvas.getWidth() / 200;
        double vehicleCanvasY = mapCanvas.getHeight() - (vehicleY * mapCanvas.getHeight() / 200);
        
        gc.setFill(Color.RED);
        gc.fillOval(vehicleCanvasX - 5, vehicleCanvasY - 5, 10, 10);
        
        // Draw vehicle heading
        double headingX = vehicleCanvasX + 15 * Math.cos(Math.toRadians(vehicleHeading));
        double headingY = vehicleCanvasY - 15 * Math.sin(Math.toRadians(vehicleHeading));
        
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeLine(vehicleCanvasX, vehicleCanvasY, headingX, headingY);
        
        // Draw target
        if (!path.isEmpty()) {
            PathPoint target = path.get(path.size() - 1);
            double targetCanvasX = target.x * mapCanvas.getWidth() / 200;
            double targetCanvasY = mapCanvas.getHeight() - (target.y * mapCanvas.getHeight() / 200);
            
            gc.setFill(Color.GREEN);
            gc.fillRect(targetCanvasX - 5, targetCanvasY - 5, 10, 10);
        }
    }
    
    /**
     * Adds an entry to the mission log.
     * 
     * @param message The log message
     */
    private void addLogEntry(String message) {
        Platform.runLater(() -> {
            missionLogItems.add(message);
            missionLogListView.scrollTo(missionLogItems.size() - 1);
        });
    }
    
    /**
     * Represents a point in the vehicle's path.
     */
    public static class PathPoint {
        public final double x;
        public final double y;
        public final String name;
        
        public PathPoint(double x, double y, String name) {
            this.x = x;
            this.y = y;
            this.name = name;
        }
    }
}