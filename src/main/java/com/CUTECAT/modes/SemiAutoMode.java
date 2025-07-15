package com.CUTECAT.modes;

import com.CUTECAT.app.WidgetFactory;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Semi-automatic control mode for the vehicle.
 * In this mode, the Arduino aims and shoots automatically, but the user controls the vehicle's movement.
 */
public class SemiAutoMode extends modebase {

    // UI components
    private Slider steeringSlider;
    private TextField steeringField;
    private Button activateTargetingButton;
    private Button deactivateTargetingButton;
    private Label targetDistanceLabel;
    private boolean targetingActive = false;

    /**
     * Creates a new semi-auto mode instance.
     *
     * @param arduinoIp   The IP address of the Arduino
     * @param arduinoPort The port number of the Arduino
     * @param cameraPort  The port number for the camera stream
     * @param cameraIp    The IP address of the camera
     */
    public SemiAutoMode(String arduinoIp, int arduinoPort, int cameraPort, String cameraIp) {
        super(arduinoIp, arduinoPort, cameraPort, cameraIp);
    }

    @Override
    protected String getModeName() {
        return "Semi-Auto Mode";
    }

    @Override
    protected void addModeControls(VBox container) {
        // Add instructions
        Label instructionsLabel = new Label(
            "Use W, A, S, D keys to drive the vehicle.\n" +
            "The Arduino will aim and shoot automatically when targeting is activated."
        );
        instructionsLabel.getStyleClass().add("instructions-label");
        instructionsLabel.setPadding(new Insets(0, 0, 20, 0));

        // Create movement controls
        VBox movementControls = new VBox(10);
        movementControls.setPadding(new Insets(10));

        Label movementTitle = WidgetFactory.createSectionTitle("Movement Controls");

        // Create a grid for the movement controls
        GridPane movementGrid = new GridPane();
        movementGrid.setHgap(10);
        movementGrid.setVgap(10);
        movementGrid.setPadding(new Insets(10));

        // Steering servo
        Label steeringLabel = new Label("Steering:");
        steeringLabel.getStyleClass().add("control-label");
        steeringSlider = createServoSlider(controlValues[STEERING_SERVO]);
        steeringField = createAngleTextField(controlValues[STEERING_SERVO]);
        setupServoControl(steeringSlider, steeringField, STEERING_SERVO);

        // Add controls to the grid
        movementGrid.add(steeringLabel, 0, 0);
        movementGrid.add(steeringSlider, 1, 0);
        movementGrid.add(steeringField, 2, 0);

        // Add all components to the movement controls
        movementControls.getChildren().addAll(movementTitle, movementGrid);

        // Create targeting controls
        VBox targetingControls = new VBox(10);
        targetingControls.setPadding(new Insets(10));

        Label targetingTitle = WidgetFactory.createSectionTitle("Targeting Controls");

        // Target distance display
        targetDistanceLabel = new Label("Target Distance: Not detected");
        targetDistanceLabel.getStyleClass().add("status-label");

        // Targeting buttons
        HBox targetingButtons = new HBox(10);
        targetingButtons.setPadding(new Insets(10));

        activateTargetingButton = WidgetFactory.createButton("Activate Targeting", e -> activateTargeting());
        deactivateTargetingButton = WidgetFactory.createButton("Deactivate Targeting", e -> deactivateTargeting());
        deactivateTargetingButton.setDisable(true); // Initially disabled

        targetingButtons.getChildren().addAll(activateTargetingButton, deactivateTargetingButton);

        // Add all components to the targeting controls
        targetingControls.getChildren().addAll(targetingTitle, targetDistanceLabel, targetingButtons);

        // Add all components to the main container
        container.getChildren().addAll(instructionsLabel, movementControls, targetingControls);
    }

    /**
     * Activates the automatic targeting system.
     */
    private void activateTargeting() {
        targetingActive = true;
        activateTargetingButton.setDisable(true);
        deactivateTargetingButton.setDisable(false);

        // Activate camera and ultrasonic sensor
        activateCamera();
        activateUltrasonicSensor();

        // Start a thread to update the target distance
        Thread targetingThread = new Thread(() -> {
            while (targetingActive) {
                try {
                    // Get the target distance
                    double distance = getTargetDistance();

                    // Update the UI
                    javafx.application.Platform.runLater(() -> {
                        targetDistanceLabel.setText(String.format("Target Distance: %.2f cm", distance));
                    });

                    // Auto-target if a target is detected
                    if (distance > 0 && distance < 300) {
                        boolean targeted = autoTarget();

                        if (targeted) {
                            // Shoot automatically
                            shoot();

                            // Wait a bit before shooting again
                            Thread.sleep(2000);
                        }
                    }

                    // Sleep for a short time
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        targetingThread.setDaemon(true);
        targetingThread.start();
    }

    /**
     * Deactivates the automatic targeting system.
     */
    private void deactivateTargeting() {
        targetingActive = false;
        activateTargetingButton.setDisable(false);
        deactivateTargetingButton.setDisable(true);

        // Reset the target distance label
        targetDistanceLabel.setText("Target Distance: Not detected");

        // Reset the servos
        resetShooter();
    }

    /**
     * Creates a slider for controlling a servo.
     * 
     * @param initialValue The initial value
     * @return The created slider
     */
    private Slider createServoSlider(int initialValue) {
        Slider slider = new Slider(0, 180, initialValue);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(45);
        slider.setMinorTickCount(4);
        slider.setSnapToTicks(false);
        slider.setPrefWidth(300);
        return slider;
    }

    /**
     * Creates a text field for entering an angle.
     * 
     * @param initialValue The initial value
     * @return The created text field
     */
    private TextField createAngleTextField(int initialValue) {
        TextField textField = new TextField(String.valueOf(initialValue));
        textField.setPrefWidth(60);
        return textField;
    }

    /**
     * Sets up the connection between a slider, a text field, and a control value.
     * 
     * @param slider The slider
     * @param textField The text field
     * @param controlIndex The index of the control value
     */
    private void setupServoControl(Slider slider, TextField textField, int controlIndex) {
        // Update text field when slider changes
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int value = newVal.intValue();
            textField.setText(String.valueOf(value));
            controlValues[controlIndex] = value;
        });

        // Update slider when text field changes
        textField.setOnAction(e -> {
            try {
                int value = Integer.parseInt(textField.getText());
                value = Math.max(0, Math.min(180, value)); // Constrain to 0-180
                slider.setValue(value);
                controlValues[controlIndex] = value;
            } catch (NumberFormatException ex) {
                // Restore the previous value if parsing fails
                textField.setText(String.valueOf(controlValues[controlIndex]));
            }
        });
    }
}
