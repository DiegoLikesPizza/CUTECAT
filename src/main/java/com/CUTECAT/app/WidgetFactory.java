package com.CUTECAT.app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Factory class for creating UI widgets with consistent styling.
 */
public class WidgetFactory {
    
    /**
     * Creates a styled button.
     * 
     * @param text The button text
     * @param action The action to perform when the button is clicked
     * @return The created button
     */
    public static Button createButton(String text, EventHandler<ActionEvent> action) {
        Button button = new Button(text);
        button.getStyleClass().add("custom-button");
        button.setOnAction(action);
        return button;
    }
    
    /**
     * Creates a labeled slider control.
     * 
     * @param labelText The label text
     * @param min The minimum value
     * @param max The maximum value
     * @param initialValue The initial value
     * @return A VBox containing the label and slider
     */
    public static VBox createLabeledSlider(String labelText, double min, double max, double initialValue) {
        VBox container = new VBox(5);
        container.setPadding(new Insets(5));
        
        Label label = new Label(labelText);
        label.getStyleClass().add("control-label");
        
        Slider slider = new Slider(min, max, initialValue);
        slider.getStyleClass().add("custom-slider");
        
        Label valueLabel = new Label(String.format("%.0f", initialValue));
        valueLabel.getStyleClass().add("value-label");
        
        slider.valueProperty().addListener((obs, oldVal, newVal) -> 
            valueLabel.setText(String.format("%.0f", newVal.doubleValue())));
        
        HBox sliderBox = new HBox(10);
        sliderBox.getChildren().addAll(slider, valueLabel);
        
        container.getChildren().addAll(label, sliderBox);
        return container;
    }
    
    /**
     * Creates a labeled text field.
     * 
     * @param labelText The label text
     * @param initialValue The initial value
     * @return A HBox containing the label and text field
     */
    public static HBox createLabeledTextField(String labelText, String initialValue) {
        HBox container = new HBox(10);
        container.setPadding(new Insets(5));
        
        Label label = new Label(labelText);
        label.getStyleClass().add("control-label");
        label.setPrefWidth(120);
        
        TextField textField = new TextField(initialValue);
        textField.getStyleClass().add("custom-text-field");
        
        container.getChildren().addAll(label, textField);
        return container;
    }
    
    /**
     * Creates a section title label.
     * 
     * @param text The label text
     * @return The created label
     */
    public static Label createSectionTitle(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("section-title");
        label.setPadding(new Insets(10, 0, 5, 0));
        return label;
    }
}