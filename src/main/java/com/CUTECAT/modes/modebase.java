package com.CUTECAT.modes;

import com.CUTECAT.app.CustomTitleBar;
import com.CUTECAT.diegoutil.DiegoArdUtils;
import com.CUTECAT.modes.capabilities.MovementCapable;
import com.CUTECAT.modes.capabilities.ShootingCapable;
import com.CUTECAT.modes.capabilities.TargetingCapable;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.CUTECAT.diegoutil.DiegoStringUtils.toCsv;

/**
 * Base class for all vehicle control modes.
 * Implements common functionality for Arduino communication and UI.
 */
public abstract class modebase implements MovementCapable, ShootingCapable, TargetingCapable {

    // Arduino connection parameters
    protected final String arduinoIp;
    protected final int arduinoPort;
    protected final int cameraPort;

    // Camera parameters
    protected final String cameraIp;

    // UI components
    protected Stage stage;
    protected Scene scene;
    protected BorderPane root;
    protected VBox controlsContainer;
    protected Label statusLabel;

    // Camera stream components
    protected WebView cameraWebView;
    protected TitledPane cameraPane;

    // Vehicle state
    public int[] controlValues;
    protected static final int MOTOR1_DIR = 0;
    protected static final int MOTOR1_POWER = 1;
    protected static final int MOTOR2_DIR = 2;
    protected static final int MOTOR2_POWER = 3;
    protected static final int MOTOR3_DIR = 4;
    protected static final int MOTOR3_POWER = 5;
    protected static final int MOTOR4_DIR = 6;
    protected static final int MOTOR4_POWER = 7;
    protected static final int STEERING_SERVO = 8;
    protected static final int TURRET_SERVO = 9;
    protected static final int BARREL_SERVO = 10;
    protected static final int CAMERA_SERVO = 11;
    protected static final int ULTRASONIC_SERVO = 12;

    // Control flags
    protected boolean isForwardPressed = false;
    protected boolean isBackwardPressed = false;
    protected boolean isLeftPressed = false;
    protected boolean isRightPressed = false;

    // Background tasks
    protected ScheduledExecutorService executor;
    // Last sent data to compare to new data
    private String lastSentCsv = "";

    /**
     * Creates a new mode instance.
     *
     * @param arduinoIp   The IP address of the Arduino
     * @param arduinoPort The port number of the Arduino
     * @param cameraPort  The port number for the camera stream
     * @param cameraIp    The IP address of the camera (if null, uses arduinoIp)
     */
    public modebase(String arduinoIp, int arduinoPort, int cameraPort, String cameraIp) {
        this.arduinoIp = arduinoIp;
        this.arduinoPort = arduinoPort;
        this.cameraPort = cameraPort;
        this.cameraIp = cameraIp != null ? cameraIp : arduinoIp;

        // Initialize control values with defaults
        controlValues = new int[13];
        Arrays.fill(controlValues, 90); // Default servo position

        controlValues[MOTOR1_DIR] = 1;
        controlValues[MOTOR2_DIR] = 1;
        controlValues[MOTOR3_DIR] = 1;
        controlValues[MOTOR4_DIR] = 1;

        // Set motor powers to 0
        controlValues[MOTOR1_POWER] = 0;
        controlValues[MOTOR2_POWER] = 0;
        controlValues[MOTOR3_POWER] = 0;
        controlValues[MOTOR4_POWER] = 0;

        // Create a new stage
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        // Create the UI
        setupUI();

        // Set up key event handlers
        setupKeyHandlers();

        // Start the control loop
        startControlLoop();
    }

    /**
     * Sets up the user interface.
     */
    protected void setupUI() {
        root = new BorderPane();
        root.setPadding(new Insets(20));
        root.getStylesheets().add(getClass().getResource("/styles/dark-theme.css").toExternalForm());

        // Add custom title bar
        CustomTitleBar titleBar = new CustomTitleBar(stage);
        root.setTop(titleBar);

        // Create main content container with controls and camera
        BorderPane contentPane = new BorderPane();

        // Create controls container
        controlsContainer = new VBox(10);
        controlsContainer.setPadding(new Insets(20, 0, 0, 0));

        // Add mode-specific controls
        addModeControls(controlsContainer);

        // Set up camera stream
        setupCameraStream();

        // Add controls to the left side of the content pane
        contentPane.setLeft(controlsContainer);

        // Add camera pane to the right side of the content pane
        //contentPane.setRight(cameraPane);

        // Add status label at the bottom
        statusLabel = new Label("Status: Ready");
        statusLabel.getStyleClass().add("status-label");
        VBox bottomContainer = new VBox(10);
        bottomContainer.getChildren().add(statusLabel);
        root.setBottom(bottomContainer);

        // Set the content pane in the center
        root.setCenter(contentPane);

        // Create the scene
        scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
    }

    /**
     * Sets up the camera stream component.
     */
    protected void setupCameraStream() {
        // Create WebView for camera stream
        cameraWebView = new WebView();
        cameraWebView.setPrefSize(480, 320);

        // Load the camera stream URL using the exact format from the camera's documentation
        // Try using the exact format mentioned in the issue description: "172.16.11.207:81/stream"
        String cameraStreamUrl = cameraIp + ":" + cameraPort + "/stream";
        // Add the http:// prefix for the WebView
        cameraWebView.getEngine().load("http://" + cameraStreamUrl);
        System.out.println("Loading camera stream from: " + cameraStreamUrl);

        // Create a titled pane to contain the camera view
        cameraPane = new TitledPane("Camera Stream", cameraWebView);
        cameraPane.setCollapsible(true);
        cameraPane.setExpanded(true);
        cameraPane.setPrefWidth(500);

        // Set VBox growth priority
        VBox.setVgrow(cameraPane, Priority.ALWAYS);
    }

    /**
     * Sets up key event handlers for keyboard controls.
     */
    protected void setupKeyHandlers() {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.W) {
                isForwardPressed = true;
                moveForward(150);
            } else if (event.getCode() == KeyCode.S) {
                isBackwardPressed = true;
                moveBackward(150);
            } else if (event.getCode() == KeyCode.A) {
                isLeftPressed = true;
                turnLeft(45);
            } else if (event.getCode() == KeyCode.D) {
                isRightPressed = true;
                turnRight(45);
            } else if (event.getCode() == KeyCode.I) {
                adjustBarrel(Math.min(controlValues[BARREL_SERVO] + 5, 180));
            } else if (event.getCode() == KeyCode.K) {
                adjustBarrel(Math.max(controlValues[BARREL_SERVO] - 5, 0));
            } else if (event.getCode() == KeyCode.J) {
                rotateTurret(Math.max(controlValues[TURRET_SERVO] - 5, 0));
            } else if (event.getCode() == KeyCode.L) {
                rotateTurret(Math.min(controlValues[TURRET_SERVO] + 5, 180));
            } else if (event.getCode() == KeyCode.SPACE) {
                shoot();
            }
        });

        scene.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.W) {
                isForwardPressed = false;
                if (!isBackwardPressed) stopMovement();
            } else if (event.getCode() == KeyCode.S) {
                isBackwardPressed = false;
                if (!isForwardPressed) stopMovement();
            } else if (event.getCode() == KeyCode.A) {
                isLeftPressed = false;
                if (!isRightPressed) controlValues[STEERING_SERVO] = 90; // Center steering
            } else if (event.getCode() == KeyCode.D) {
                isRightPressed = false;
                if (!isLeftPressed) controlValues[STEERING_SERVO] = 90; // Center steering
            }
        });
    }

    /**
     * Starts the control loop that sends commands to the Arduino.
     */
    protected void startControlLoop() {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::sendControlValues, 0, 50, TimeUnit.MILLISECONDS);

        // Add shutdown hook to stop the executor when the application exits
        stage.setOnCloseRequest(event -> {
            stopControlLoop();
        });
    }

    /**
     * Stops the control loop.
     */
    protected void stopControlLoop() {
        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }
    }

    /**
     * Checks if the Arduino is reachable before sending commands
     * @return true if the Arduino is reachable, false otherwise
     */
    protected boolean checkArduinoConnection() {
        boolean isReachable = DiegoArdUtils.isArduinoReachable(arduinoIp, arduinoPort);
        if (!isReachable) {
            Platform.runLater(() -> {
                statusLabel.setText("Status: Arduino not reachable at " + arduinoIp + ":" + arduinoPort);
                System.err.println("Arduino not reachable at " + arduinoIp + ":" + arduinoPort);
            });
        }
        return isReachable;
    }

    /**
     * Sends the current control values to the Arduino.
     * First checks if the Arduino is reachable, then sends the control values.
     * Updates the status label based on the result.
     */
    protected void sendControlValues() {
        try {

            ArrayList<Integer> list = new ArrayList<>();

            for (int i : controlValues) {
                list.add(i);
            }

            String csvData = toCsv(list);

            // Prüfen, ob Daten gleich geblieben sind
            if (csvData.equals(lastSentCsv)) {
                //System.out.println("Same Data: " + csvData + " -> Not sent");
                return; // Nichts hat sich geändert, also abbrechen
            }
            else{
                lastSentCsv = csvData;
            }

            System.out.println("Sending data to Arduino at " + arduinoIp + ":" + arduinoPort + " - Data: " + csvData);
            boolean success = DiegoArdUtils.sendToArduino(arduinoIp, arduinoPort, csvData);

            Platform.runLater(() -> {
                if (success) {
                    statusLabel.setText("Status: Connected - Sending commands");
                    System.out.println("Successfully sent data(" + csvData + ") to Arduino");
                } else {
                    statusLabel.setText("Status: Connection error - Check Arduino and network");
                    System.out.println("Failed to send data to Arduino - Connection error");
                }
            });
        } catch (Exception e) {
            System.err.println("Error sending data to Arduino: " + e.getMessage());
            e.printStackTrace();
            Platform.runLater(() -> {
                String errorMsg = e.getMessage();
                if (errorMsg != null && errorMsg.contains("Connection timed out")) {
                    statusLabel.setText("Status: Connection timed out - Arduino might be busy");
                } else if (errorMsg != null && errorMsg.contains("Connection refused")) {
                    statusLabel.setText("Status: Connection refused - Check Arduino IP and port");
                } else {
                    statusLabel.setText("Status: Error - " + errorMsg);
                }
            });
        }
    }

    /**
     * Shows this mode's window.
     */
    public void show() {
        stage.show();
    }

    /**
     * Hides this mode's window.
     */
    public void hide() {
        stage.hide();
    }

    /**
     * Shuts down this mode and releases all resources.
     * This should be called when the mode is no longer needed.
     */
    public void shutdown() {
        // Stop the control loop
        stopControlLoop();

        // Clean up WebView resources
        if (cameraWebView != null) {
            // Load about:blank to stop any active connections
            Platform.runLater(() -> {
                cameraWebView.getEngine().load("about:blank");
                cameraWebView = null;
            });
        }
    }

    /**
     * Gets the name of this mode.
     * 
     * @return The mode name
     */
    protected abstract String getModeName();

    /**
     * Adds mode-specific controls to the UI.
     * 
     * @param container The container to add controls to
     */
    protected abstract void addModeControls(VBox container);

    // Implementation of MovementCapable interface

    @Override
    public void moveForward(int speed) {
        // Set motor directions to forward (1)
        controlValues[MOTOR1_DIR] = 1;
        controlValues[MOTOR2_DIR] = 1;
        controlValues[MOTOR3_DIR] = 1;
        controlValues[MOTOR4_DIR] = 1;

        // Set motor powers
        controlValues[MOTOR1_POWER] = speed;
        controlValues[MOTOR2_POWER] = speed;
        controlValues[MOTOR3_POWER] = speed;
        controlValues[MOTOR4_POWER] = speed;
    }

    @Override
    public void moveBackward(int speed) {
        // Set motor directions to backward (0)
        controlValues[MOTOR1_DIR] = 0;
        controlValues[MOTOR2_DIR] = 0;
        controlValues[MOTOR3_DIR] = 0;
        controlValues[MOTOR4_DIR] = 0;

        // Set motor powers
        controlValues[MOTOR1_POWER] = speed;
        controlValues[MOTOR2_POWER] = speed;
        controlValues[MOTOR3_POWER] = speed;
        controlValues[MOTOR4_POWER] = speed;
    }

    @Override
    public void turnLeft(int angle) {
        controlValues[STEERING_SERVO] = 90 - angle;
    }

    @Override
    public void turnRight(int angle) {
        controlValues[STEERING_SERVO] = 90 + angle;
    }

    @Override
    public void stopMovement() {
        // Set motor powers to 0
        controlValues[MOTOR1_POWER] = 0;
        controlValues[MOTOR2_POWER] = 0;
        controlValues[MOTOR3_POWER] = 0;
        controlValues[MOTOR4_POWER] = 0;
    }

    // Implementation of ShootingCapable interface

    @Override
    public void shoot() {
        // Trigger the shooting mechanism
        // This is just a placeholder - the actual implementation would depend on how the Arduino handles shooting
        Platform.runLater(() -> statusLabel.setText("Status: Shooting!"));
    }

    @Override
    public void adjustBarrel(int angle) {
        controlValues[BARREL_SERVO] = angle;
    }

    @Override
    public void rotateTurret(int angle) {
        controlValues[TURRET_SERVO] = angle;
    }

    @Override
    public void prepareShooter() {
        // Prepare the shooting mechanism
        Platform.runLater(() -> statusLabel.setText("Status: Preparing shooter"));
    }

    @Override
    public void resetShooter() {
        // Reset the shooting mechanism
        controlValues[BARREL_SERVO] = 90;
        controlValues[TURRET_SERVO] = 90;
    }

    // Implementation of TargetingCapable interface

    @Override
    public void activateCamera() {
        Platform.runLater(() -> statusLabel.setText("Status: Camera activated"));
    }

    @Override
    public void adjustCamera(int angle) {
        controlValues[CAMERA_SERVO] = angle;
    }

    @Override
    public void activateUltrasonicSensor() {
        Platform.runLater(() -> statusLabel.setText("Status: Ultrasonic sensor activated"));
    }

    @Override
    public void adjustUltrasonicSensor(int angle) {
        controlValues[ULTRASONIC_SERVO] = angle;
    }

    @Override
    public double getTargetDistance() {
        // This would normally get the distance from the Arduino
        // For now, return a dummy value
        return 100.0;
    }

    @Override
    public boolean autoTarget() {
        // This would normally use the camera to find a target and aim at it
        // For now, just return true
        Platform.runLater(() -> statusLabel.setText("Status: Auto-targeting"));
        return true;
    }
}
