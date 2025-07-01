package com.CUTECAT.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Custom title bar for the application windows.
 * Provides minimize and close buttons, and allows dragging the window.
 */
public class CustomTitleBar extends HBox {
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    /**
     * Creates a new custom title bar.
     * 
     * @param stage The stage (window) this title bar belongs to
     * @param title The title text to display
     */
    public CustomTitleBar(Stage stage, String title) {
        super();
        
        this.setPadding(new Insets(5, 10, 5, 10));
        this.setAlignment(Pos.CENTER_LEFT);
        this.getStyleClass().add("title-bar");
        
        // Make the title bar draggable
        this.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        
        this.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        
        // Title label
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("title-bar-label");
        
        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Minimize button
        Button minimizeButton = createImageButton("/images/minimize.png", e -> stage.setIconified(true));
        minimizeButton.getStyleClass().add("title-bar-button");
        
        // Close button
        Button closeButton = createImageButton("/images/close.png", e -> stage.close());
        closeButton.getStyleClass().add("title-bar-button");
        
        this.getChildren().addAll(titleLabel, spacer, minimizeButton, closeButton);
    }
    
    /**
     * Creates a button with an image.
     * 
     * @param imagePath The path to the image resource
     * @param action The action to perform when the button is clicked
     * @return The created button
     */
    private Button createImageButton(String imagePath, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button button = new Button();
        
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(12);
            imageView.setFitWidth(12);
            button.setGraphic(imageView);
        } catch (Exception e) {
            button.setText(imagePath.contains("close") ? "X" : "_");
        }
        
        button.setOnAction(action);
        return button;
    }
}