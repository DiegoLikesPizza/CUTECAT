package com.CUTECAT.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Region;

public class CUTECAT extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        // Create main container
        VBox root = new VBox();
        root.getStyleClass().add("main-container");

        // Add custom title bar
        CustomTitleBar titleBar = new CustomTitleBar(primaryStage);
        
        // Create content area
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.setPadding(new Insets(20));
        content.getStyleClass().add("content-area");
        
        // Add widgets
        WidgetFactory.addWidgets(content);

        // Add components to root
        root.getChildren().addAll(titleBar, content);

        // Create scene
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/styles/dark-theme.css").toExternalForm());

        // Configure stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CUTECAT");
        
        // Apply rounded corners
        setRoundedCorners(root, 10, 10);
        
        primaryStage.show();
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