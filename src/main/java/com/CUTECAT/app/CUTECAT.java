package com.CUTECAT.app;

import com.CUTECAT.GUI.AndreasGUI;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Region;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


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
        AndreasGUI.addWidgets2(content2);
        AndreasGUI.addWidgets3(content3);
        AndreasGUI.addWidgets4(content4);



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
        tab1.setClosable(true);
        tab2.setClosable(true);
        tabPane.getStyleClass().add("visible-tab-close-button");

        Tab tab3 = new Tab("Halbautomatischer Modus");
        tab3.setContent(content3);
        // Closeable tabs (default)
        tab3.setClosable(true);
        tabPane.getStyleClass().add("visible-tab-close-button");

        Tab tab4 = new Tab("Vollautomatischer Modus");
        tab4.setContent(content4);
        tab4.setClosable(true);
        tabPane.getStyleClass().add("visible-tab-close-button");

        tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);

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