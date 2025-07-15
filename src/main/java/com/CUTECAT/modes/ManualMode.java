package com.CUTECAT.modes;

import com.CUTECAT.app.WidgetFactory;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Manual control mode for the vehicle.
 * Allows full manual control of all vehicle functions.
 */
public class ManualMode extends modebase {

    // UI components
    private Slider steeringSlider;
    private Slider turretSlider;
    private Slider barrelSlider;
    private Slider cameraSlider;
    private Slider ultrasonicSlider;
    private TextField steeringField;
    private TextField turretField;
    private TextField barrelField;
    private TextField cameraField;
    private TextField ultrasonicField;

    /**
     * Creates a new manual mode instance.
     *
     * @param arduinoIp   The IP address of the Arduino
     * @param arduinoPort The port number of the Arduino
     * @param cameraPort  The port number for the camera stream
     * @param cameraIp    The IP address of the camera
     */
    public ManualMode(String arduinoIp, int arduinoPort, int cameraPort, String cameraIp) {
        super( arduinoIp, arduinoPort, cameraPort, cameraIp);
    }

    @Override
    protected String getModeName() {
        return "Manual Mode";
    }

    @Override
    protected void addModeControls(VBox container) {
        // Add instructions
        Label instructionsLabel = new Label(
            "Use W, A, S, D keys to drive the vehicle.\n" +
            "Use arrow keys to control the turret and barrel.\n" +
            "Press SPACE to shoot."
        );
        instructionsLabel.getStyleClass().add("instructions-label");
        instructionsLabel.setPadding(new Insets(0, 0, 20, 0));

        // Create servo control sliders
        VBox servoControls = new VBox(10);
        servoControls.setPadding(new Insets(10));

        Label servoTitle = WidgetFactory.createSectionTitle("Servo Controls");

        // Create a grid for the servo controls
        GridPane servoGrid = new GridPane();
        servoGrid.setHgap(10);
        servoGrid.setVgap(10);
        servoGrid.setPadding(new Insets(10));

        // Steering servo
        Label steeringLabel = new Label("Steering:");
        steeringLabel.getStyleClass().add("control-label");
        steeringSlider = createServoSlider(controlValues[STEERING_SERVO]);
        steeringField = createAngleTextField(controlValues[STEERING_SERVO]);
        setupServoControl(steeringSlider, steeringField, STEERING_SERVO);

        // Turret servo
        Label turretLabel = new Label("Turret:");
        turretLabel.getStyleClass().add("control-label");
        turretSlider = createServoSlider(controlValues[TURRET_SERVO]);
        turretField = createAngleTextField(controlValues[TURRET_SERVO]);
        setupServoControl(turretSlider, turretField, TURRET_SERVO);

        // Barrel servo
        Label barrelLabel = new Label("Barrel:");
        barrelLabel.getStyleClass().add("control-label");
        barrelSlider = createServoSlider(controlValues[BARREL_SERVO]);
        barrelField = createAngleTextField(controlValues[BARREL_SERVO]);
        setupServoControl(barrelSlider, barrelField, BARREL_SERVO);

        // Camera servo
        Label cameraLabel = new Label("Camera:");
        cameraLabel.getStyleClass().add("control-label");
        cameraSlider = createServoSlider(controlValues[CAMERA_SERVO]);
        cameraField = createAngleTextField(controlValues[CAMERA_SERVO]);
        setupServoControl(cameraSlider, cameraField, CAMERA_SERVO);

        // Ultrasonic servo
        Label ultrasonicLabel = new Label("Ultrasonic:");
        ultrasonicLabel.getStyleClass().add("control-label");
        ultrasonicSlider = createServoSlider(controlValues[ULTRASONIC_SERVO]);
        ultrasonicField = createAngleTextField(controlValues[ULTRASONIC_SERVO]);
        setupServoControl(ultrasonicSlider, ultrasonicField, ULTRASONIC_SERVO);

        // Add controls to the grid
        servoGrid.add(steeringLabel, 0, 0);
        servoGrid.add(steeringSlider, 1, 0);
        servoGrid.add(steeringField, 2, 0);

        servoGrid.add(turretLabel, 0, 1);
        servoGrid.add(turretSlider, 1, 1);
        servoGrid.add(turretField, 2, 1);

        servoGrid.add(barrelLabel, 0, 2);
        servoGrid.add(barrelSlider, 1, 2);
        servoGrid.add(barrelField, 2, 2);

        servoGrid.add(cameraLabel, 0, 3);
        servoGrid.add(cameraSlider, 1, 3);
        servoGrid.add(cameraField, 2, 3);

        servoGrid.add(ultrasonicLabel, 0, 4);
        servoGrid.add(ultrasonicSlider, 1, 4);
        servoGrid.add(ultrasonicField, 2, 4);

        // Add action buttons
        HBox actionButtons = new HBox(10);
        actionButtons.setPadding(new Insets(10));

        actionButtons.getChildren().addAll(
            WidgetFactory.createButton("Reset Servos", e -> resetServos()),
            WidgetFactory.createButton("Shoot", e -> shoot())
        );

        // Add all components to the servo controls
        servoControls.getChildren().addAll(servoTitle, servoGrid, actionButtons);

        // Add all components to the main container
        container.getChildren().addAll(instructionsLabel, servoControls);
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

    /**
     * Resets all servos to their default positions.
     */
    private void resetServos() {
        // Set all servos to 90 degrees (center position)
        steeringSlider.setValue(90);
        turretSlider.setValue(90);
        barrelSlider.setValue(90);
        cameraSlider.setValue(90);
        ultrasonicSlider.setValue(90);
    }
}
