package com.CUTECAT.app;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

public class WidgetFactory {
    public static void addWidgets(GridPane parent) {

        // Input Controls Section 1
        VBox inputControls = createSection("Input Controls");
        
        // Text input
        TextField textField = new TextField();
        textField.setPromptText("Enter text here");
        textField.getStyleClass().add("styled-text-field");
        
        TextArea textArea = new TextArea();
        textArea.setPromptText("Multiline text input");
        textArea.setPrefRowCount(3);
        textArea.getStyleClass().add("styled-text-area");
        
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(
            "Option 1", "Option 2", "Option 3"
        ));
        comboBox.getStyleClass().add("styled-combo-box");
        
        inputControls.getChildren().addAll(
            createLabel("Text Field:"), textField,
            createLabel("Text Area:"), textArea,
            createLabel("Combo Box:"), comboBox
        );

        // Selection Controls Section
        VBox selectionControls = createSection("Selection Controls");
        
        CheckBox checkBox1 = new CheckBox("Enable Feature 1");
        CheckBox checkBox2 = new CheckBox("Enable Feature 2");
        checkBox1.getStyleClass().add("styled-check-box");
        checkBox2.getStyleClass().add("styled-check-box");
        
        ToggleGroup radioGroup = new ToggleGroup();
        RadioButton radio1 = new RadioButton("Option A");
        RadioButton radio2 = new RadioButton("Option B");
        radio1.setToggleGroup(radioGroup);
        radio2.setToggleGroup(radioGroup);
        radio1.getStyleClass().add("styled-radio-button");
        radio2.getStyleClass().add("styled-radio-button");
        
        Slider slider = new Slider(0, 100, 50);
        slider.getStyleClass().add("styled-slider");
        
        selectionControls.getChildren().addAll(
            createLabel("Checkboxes:"), checkBox1, checkBox2,
            createLabel("Radio Buttons:"), radio1, radio2,
            createLabel("Slider:"), slider
        );

        // Progress Section
        VBox progressControls = createSection("Progress Indicators");
        
        ProgressBar progressBar = new ProgressBar(0.7);
        progressBar.getStyleClass().add("styled-progress-bar");
        
        ProgressIndicator progressIndicator = new ProgressIndicator(0.7);
        progressIndicator.getStyleClass().add("styled-progress-indicator");
        
        progressControls.getChildren().addAll(
            createLabel("Progress Bar:"), progressBar,
            createLabel("Progress Indicator:"), progressIndicator
        );

        // Container Examples Section
        VBox containerExamples = createSection("Container Examples");
        
        // Tab Pane Example
        TabPane tabPane = new TabPane();
        tabPane.getStyleClass().add("styled-tab-pane");
        Tab tab1 = new Tab("Tab 1", new Label("Tab 1 Content"));
        Tab tab2 = new Tab("Tab 2", new Label("Tab 2 Content"));
          // Closeable tabs (default)
        tab1.setClosable(true);
        tab2.setClosable(true);
        tabPane.getStyleClass().add("visible-tab-close-button");
        tabPane.getTabs().addAll(tab1, tab2);
        
        // List View Example
        ListView<String> listView = new ListView<>(FXCollections.observableArrayList(
            "Item 1", "Item 2", "Item 3"
        ));
        listView.getStyleClass().add("styled-list-view");
        listView.setPrefHeight(100);
        
        // Scroll Pane Example
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("styled-scroll-pane");
        VBox scrollContent = new VBox(10);
        for (int i = 1; i <= 10; i++) {
            scrollContent.getChildren().add(new Label("Scroll Content " + i));
        }
        scrollPane.setContent(scrollContent);
        scrollPane.setPrefHeight(100);
        
        containerExamples.getChildren().addAll(
            createLabel("Tab Pane:"), tabPane,
            createLabel("List View:"), listView,
            createLabel("Scroll Pane:"), scrollPane
        );

        // Status Labels Section
        VBox statusSection = createSection("Status Indicators");
        
        Label successLabel = new Label("Success Message");
        successLabel.getStyleClass().add("status-success");
        
        Label warningLabel = new Label("Warning Message");
        warningLabel.getStyleClass().add("status-warning");
        
        Label errorLabel = new Label("Error Message");
        errorLabel.getStyleClass().add("status-error");
        
        statusSection.getChildren().addAll(
            successLabel, warningLabel, errorLabel
        );

        // Add all sections to the grid
        parent.add(inputControls, 0, 0);
        parent.add(selectionControls, 1, 0);
        parent.add(progressControls, 0, 1);
        parent.add(containerExamples, 1, 1);
        parent.add(statusSection, 0, 2, 2, 1); // Span 2 columns

    }

    private static VBox createSection(String title) {
        VBox section = new VBox(10);
        section.getStyleClass().add("container-box");
        
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("container-box-header");
        
        section.getChildren().add(titleLabel);
        return section;
    }

    private static Label createLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("styled-label");
        return label;
    }
} 