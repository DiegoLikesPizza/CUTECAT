package com.CUTECAT.app;

import com.CUTECAT.GUI.AndreasGUI;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Region;



public class CUTECAT extends Application {
    private double xOffset = 200;
    private double yOffset = 0;
    public static boolean Handlung = false;
    public static boolean Handlung2 = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        // Create main container
        VBox root = new VBox();
        root.getStyleClass().add("main-container");

        // Add custom title bar
        CustomTitleBar titleBar = new CustomTitleBar(primaryStage);
        
        // Create content area
        Pane content = new Pane();
        //content.setHgap(10);
        //content.setVgap(10);
        content.setPadding(new Insets(20));
        content.getStyleClass().add("content-area");
        // Add widgets
        AndreasGUI.addWidgets(content);

        // Add components to root
        root.getChildren().addAll(titleBar, content);

        // Create scene
        Scene scene = new Scene(root,300,300);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/styles/dark-theme.css").toExternalForm());

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

        scene.setOnMouseClicked(e ->{
            if (Handlung) {
                opensecondStage();
                primaryStage.close();
            }
        });

        scene.setOnMouseClicked(e ->{
            if (Handlung2) {
                openthirdStage();
                primaryStage.close();
            }
        });



        /*
        // Erzeugen von Stage 2(Modus 1)
        Stage Stage2 = new Stage();

        // Create 2. main container
        VBox root2 = new VBox();
        root2.getStyleClass().add("main-container");

        // Add custom title bar
        CustomTitleBar titleBar2 = new CustomTitleBar(Stage2);


        // Create content area
        Pane content2 = new Pane();
        //content2.setHgap(10);
        //content2.setVgap(10);
        content2.setPadding(new Insets(20));
        content2.getStyleClass().add("content-area");

        // Add widgets
        AndreasGUI.addWidgets2(content2);

        // Add components to root2
        root2.getChildren().addAll(titleBar2,content2);

        // Create scene
        Scene scene2 = new Scene(root2,300,300);
        scene2.setFill(Color.TRANSPARENT);
        scene2.getStylesheets().add(getClass().getResource("/styles/dark-theme.css").toExternalForm());

        // Configure stage2
        Stage2.initStyle(StageStyle.TRANSPARENT);
        Stage2.setScene(scene2);
        Stage2.setTitle("CUTECAT");
        //Stage2.setFullScreen(true);
        Stage2.setHeight(1080);
        Stage2.setWidth(1920);

        // Apply rounded corners
        setRoundedCorners(root2, 10, 10);

        //Stage2.show();
        */
    }

    private void opensecondStage() {
        //Erzeugen von Stage 2(Modus 1)
        Stage Stage2 = new Stage();

        // Create 2. main container
        VBox root2 = new VBox();
        root2.getStyleClass().add("main-container");

        // Add custom title bar
        CustomTitleBar titleBar2 = new CustomTitleBar(Stage2);


        // Create content area
        Pane content2 = new Pane();
        //content2.setHgap(10);
        //content2.setVgap(10);
        content2.setPadding(new Insets(20));
        content2.getStyleClass().add("content-area");

        // Add widgets
        AndreasGUI.addWidgets2(content2);

        // Add components to root2
        root2.getChildren().addAll(titleBar2,content2);

        // Create scene
        Scene scene2 = new Scene(root2,300,300);
        scene2.setFill(Color.TRANSPARENT);
        scene2.getStylesheets().add(getClass().getResource("/styles/dark-theme.css").toExternalForm());

        // Configure stage2
        Stage2.initStyle(StageStyle.TRANSPARENT);
        Stage2.setScene(scene2);
        Stage2.setTitle("CUTECAT");
        //Stage2.setFullScreen(true);
        Stage2.setHeight(1080);
        Stage2.setWidth(1920);

        // Apply rounded corners
        setRoundedCorners(root2, 10, 10);

        Stage2.show();
    }

    private void openthirdStage() {
        //Erzeugen von Stage 3(Modus 2)
        Stage Stage3 = new Stage();

        // Create 2. main container
        VBox root3 = new VBox();
        root3.getStyleClass().add("main-container");

        // Add custom title bar
        CustomTitleBar titleBar3 = new CustomTitleBar(Stage3);


        // Create content area
        Pane content3 = new Pane();
        //content3.setHgap(10);
        //content3.setVgap(10);
        content3.setPadding(new Insets(20));
        content3.getStyleClass().add("content-area");

        // Add widgets
        AndreasGUI.addWidgets3(content3);

        // Add components to root2
        root3.getChildren().addAll(titleBar3, content3);

        // Create scene
        Scene scene3 = new Scene(root3,300,300);
        scene3.setFill(Color.TRANSPARENT);
        scene3.getStylesheets().add(getClass().getResource("/styles/dark-theme.css").toExternalForm());

        // Configure stage2
        Stage3.initStyle(StageStyle.TRANSPARENT);
        Stage3.setScene(scene3);
        Stage3.setTitle("CUTECAT");
        //Stage2.setFullScreen(true);
        Stage3.setHeight(1080);
        Stage3.setWidth(1920);

        // Apply rounded corners
        setRoundedCorners(root3, 10, 10);

        Stage3.show();
    }


    private void setRoundedCorners(Region root, double arcWidth, double arcHeight) {
        Rectangle clip = new Rectangle(root.getWidth(), root.getHeight());
        clip.setArcWidth(arcWidth);
        clip.setArcHeight(arcHeight);
        root.setClip(clip);

        // Adjust clip size dynamically if the window is resized
        root.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            clip.setWidth(newBounds.getWidth());
            clip.setHeight(newBounds.getHeight());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }




}