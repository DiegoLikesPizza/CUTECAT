package com.CUTECAT.app;

import com.CUTECAT.modes.AutoMode;
import com.CUTECAT.modes.ManualMode;
import com.CUTECAT.modes.SemiAutoMode;
import com.CUTECAT.modes.modebase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main JavaFX application class for the CUTECAT project.
 * This class handles the start screen and navigation to different modes.
 */
public class CUTECAT extends Application {

    private static final String ARDUINO_IP = "172.16.11.181";
    private static final int ARDUINO_PORT = 80;
    private static final int CAMERA_PORT = 81;
    private static final String CAMERA_IP = "172.16.11.207";

    private Stage primaryStage;
    private modebase currentMode;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Set up application exit handler
        Platform.setImplicitExit(true);
        primaryStage.setOnCloseRequest(event -> {
            stop();
            Platform.exit();
        });

        showStartScreen();
    }

    @Override
    public void stop() {
        // Clean up resources when the application is stopping
        if (currentMode != null) {
            currentMode.shutdown();
            currentMode = null;
        }
    }

    /**
     * Displays the start screen with buttons to select different modes.
     */
    private void showStartScreen() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.getStylesheets().add(getClass().getResource("/styles/dark-theme.css").toExternalForm());

        // Add custom title bar
        CustomTitleBar titleBar = new CustomTitleBar(primaryStage, "CUTECAT Control Panel");
        root.setTop(titleBar);

        // Create content
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("CUTECAT Vehicle Control");
        titleLabel.getStyleClass().add("title-label");

        Label descriptionLabel = new Label("Select a control mode:");
        descriptionLabel.getStyleClass().add("description-label");

        Button manualButton = WidgetFactory.createButton("Manual Mode", e -> openManualMode());
        Button semiAutoButton = WidgetFactory.createButton("Semi-Auto Mode", e -> openSemiAutoMode());
        Button autoButton = WidgetFactory.createButton("Auto Mode", e -> openAutoMode());

        content.getChildren().addAll(
            titleLabel,
            descriptionLabel,
            manualButton,
            semiAutoButton,
            autoButton
        );

        root.setCenter(content);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Opens the Manual Mode window.
     */
    private void openManualMode() {
        // Shut down the current mode if one exists
        if (currentMode != null) {
            currentMode.shutdown();
        }

        // Create and show the new mode
        ManualMode manualMode = new ManualMode(primaryStage, ARDUINO_IP, ARDUINO_PORT, CAMERA_PORT, CAMERA_IP);
        currentMode = manualMode;
        manualMode.show();
    }

    /**
     * Opens the Semi-Auto Mode window.
     */
    private void openSemiAutoMode() {
        // Shut down the current mode if one exists
        if (currentMode != null) {
            currentMode.shutdown();
        }

        // Create and show the new mode
        SemiAutoMode semiAutoMode = new SemiAutoMode(primaryStage, ARDUINO_IP, ARDUINO_PORT, CAMERA_PORT, CAMERA_IP);
        currentMode = semiAutoMode;
        semiAutoMode.show();
    }

    /**
     * Opens the Auto Mode window.
     */
    private void openAutoMode() {
        // Shut down the current mode if one exists
        if (currentMode != null) {
            currentMode.shutdown();
        }

        // Create and show the new mode
        AutoMode autoMode = new AutoMode(primaryStage, ARDUINO_IP, ARDUINO_PORT, CAMERA_PORT, CAMERA_IP);
        currentMode = autoMode;
        autoMode.show();
    }
}
