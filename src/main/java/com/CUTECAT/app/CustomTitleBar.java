package com.CUTECAT.app;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.animation.RotateTransition;
import javafx.util.Duration;

public class CustomTitleBar extends HBox {
    private double xOffset = 0;
    private double yOffset = 0;

    public CustomTitleBar(Stage stage) {
        setAlignment(Pos.CENTER_LEFT);
        setPrefHeight(40);
        getStyleClass().add("title-bar");

        // Title
        Label title = new Label("CUTECAT");
        title.getStyleClass().add("window-title");
        
        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Window controls
        Button minimizeBtn = createWindowButton("/images/minimize.png", "minimize-button", e -> stage.setIconified(true), false);

        Button closeBtn = createWindowButton("/images/close.png", "close-button", e -> stage.close(), true);

        HBox windowControls = new HBox(minimizeBtn, closeBtn);
        windowControls.getStyleClass().add("window-controls");

        getChildren().addAll(title, spacer, windowControls);

        // Window dragging
        setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    private Button createWindowButton(String imagePath, String styleClass, javafx.event.EventHandler<javafx.event.ActionEvent> action, Boolean rotation) {
        Button button = new Button();
        ImageView imageView = null;
        
        // Load image
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imageView = new ImageView(image);
            imageView.setFitWidth(12);
            imageView.setFitHeight(12);
            button.setGraphic(imageView);
        } catch (Exception e) {
            // Fallback to text if image loading fails
            button.setText(styleClass.equals("close-button") ? "×" :
                    styleClass.equals("minimize-button") ? "−" : "□");
        }
        
        button.getStyleClass().add(styleClass);
        button.setOnAction(action);

        // Add rotation effect on hover only for the ImageView
        if (imageView != null && rotation) {
            ImageView finalImageView = imageView;
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(200), finalImageView);
            rotateTransition.setByAngle(180);
            
            button.setOnMouseEntered(e -> rotateTransition.play());
            button.setOnMouseExited(e -> {
                rotateTransition.stop();
                finalImageView.setRotate(0);
            });
        }

        return button;
    }
} 