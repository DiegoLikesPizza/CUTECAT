package com.CUTECAT.app;


import com.CUTECAT.modes.AutoMode;
import com.CUTECAT.modes.ManualMode;
import com.CUTECAT.modes.SemiAutoMode;
import com.CUTECAT.modes.modebase;
import com.CUTECAT.GUI.AndreasGUI;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Region;

import java.io.InputStream;
import java.net.URL;

public class CUTECAT extends Application {

    private static final String ARDUINO_IP = "172.16.11.181";
    private static final int ARDUINO_PORT = 81;
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
        CustomTitleBar titleBar = new CustomTitleBar(primaryStage);

        ImageView camera = new ImageView();
        camera.setLayoutX(1200);
        camera.setLayoutY(150);
        camera.setFitWidth(700);
        camera.setFitHeight(400);
        camera.setPreserveRatio(true);

        ImageView camera2 = new ImageView();
        camera2.setLayoutX(1200);
        camera2.setLayoutY(150);
        camera2.setFitWidth(700);
        camera2.setFitHeight(400);
        camera2.setPreserveRatio(true);

        ImageView camera3 = new ImageView();
        camera3.setLayoutX(1200);
        camera3.setLayoutY(150);
        camera3.setFitWidth(700);
        camera3.setFitHeight(400);
        camera3.setPreserveRatio(true);


        src.main.mjpeg.FXMjpegViewer fxViewer = new src.main.mjpeg.FXMjpegViewer(camera, camera2, camera3);

        try {
            String url = "http://172.16.11.207:81/stream";
            InputStream input = new URL(url).openStream();
            src.main.mjpeg.MjpegReceiver receiver = new src.main.mjpeg.MjpegReceiver(input, fxViewer);
            Thread mjpegThread = new Thread(receiver);
            mjpegThread.setDaemon(true);
            mjpegThread.start();
        } catch (Exception e) {
            System.err.println("Fehler");
            e.printStackTrace();
        }
        
        // Create content area
        Pane content = new Pane();
        //content.setHgap(10);
        //content.setVgap(10);
        content.setPadding(new Insets(20));
        content.getStyleClass().add("content-area");


        Pane content2 = new Pane();
        //content.setHgap(10);
        //content.setVgap(10);
        content2.setPadding(new Insets(20));
        content2.getStyleClass().add("content-area");

        Pane content3 = new Pane();
        //content.setHgap(10);
        //content.setVgap(10);
        content3.setPadding(new Insets(20));
        content3.getStyleClass().add("content-area");

        Pane content4 = new Pane();
        //content.setHgap(10);
        //content.setVgap(10);
        content4.setPadding(new Insets(20));
        content4.getStyleClass().add("content-area");

        // Add widgets
        AndreasGUI.addWidgets(content);
        AndreasGUI.addWidgets2(content2, camera);
        AndreasGUI.addWidgets3(content3, camera2);
        AndreasGUI.addWidgets4(content4, camera3);



        // Create scene
        Scene scene = new Scene(root,300,300);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/styles/dark-theme.css").toExternalForm());

        TabPane tabPane = new TabPane();
        tabPane.setLayoutX(0);
        tabPane.setLayoutY(20);
        tabPane.getStyleClass().add("styled-tab-pane");
        Tab tab1 = new Tab("Home              ");
        tab1.setContent(content);

        Tab tab2 = new Tab("Ferngesteuerter Modus");
        tab2.setContent(content2);
        // Closeable tabs (default)
        tab1.setClosable(false);
        tab2.setClosable(false);
        tabPane.getStyleClass().add("visible-tab-close-button");

        Tab tab3 = new Tab("Halbautomatischer Modus");
        tab3.setContent(content3);
        // Closeable tabs (default)
        tab3.setClosable(false);
        tabPane.getStyleClass().add("visible-tab-close-button");

        Tab tab4 = new Tab("Vollautomatischer Modus");
        tab4.setContent(content4);
        tab4.setClosable(false);
        tabPane.getStyleClass().add("visible-tab-close-button");

        tabPane.getTabs().addAll(tab1,   tab2, tab3, tab4);

        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();


        /*tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (oldValue == tab1) {
                    if (newValue == tab2 || newValue == tab3 || newValue == tab4 ) {
                        //Auswahl auf den alten Tab zurücksetzen
                        tabPane.getSelectionModel().select(oldValue);
                    }
                }
                if (oldValue == tab2) {
                    if (newValue == tab1 || newValue == tab3 || newValue == tab4 ) {
                        //Auswahl auf den alten Tab zurücksetzen
                        tabPane.getSelectionModel().select(oldValue);
                    }
                }
                if (oldValue == tab3) {
                    if (newValue == tab1 || newValue == tab2 || newValue == tab4 ) {
                        //Auswahl auf den alten Tab zurücksetzen
                        tabPane.getSelectionModel().select(oldValue);
                    }
                }
                if (oldValue == tab4) {
                    if (newValue == tab1 || newValue == tab3 || newValue == tab2 ) {
                        //Auswahl auf den alten Tab zurücksetzen
                        tabPane.getSelectionModel().select(oldValue);
                    }
                }
            }
        });*/


        // Add components to root
        root.getChildren().addAll(titleBar, tabPane);

        // Configure stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CUTECAT");
        //primaryStage.setFullScreen(true);
        primaryStage.setHeight(1080);
        primaryStage.setWidth(1920);
        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        
        // Apply rounded corners
        setRoundedCorners(root, 10, 10);
        
        primaryStage.show();

        /*scene.setOnMouseClicked(e ->{
            if (Handlung) {
                opensecondStage();
                primaryStage.close();
            }
        });

        scene.setOnMouseClicked(e2 ->{
            if (Handlung2) {
                openthirdStage();
                primaryStage.close();
            }
        });*/


    }




    private void setRoundedCorners(Region root, double arcWidth, double arcHeight) {
        Rectangle clip = new Rectangle(root.getWidth(), root.getHeight());
        clip.setArcWidth(arcWidth);
        clip.setArcHeight(arcHeight);
        root.setClip(clip);

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