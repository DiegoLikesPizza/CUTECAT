package com.CUTECAT.GUI;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.event.*;
import javafx.stage.Stage;

;

public class AndreasGUI {
    public static boolean Handlung = false;


    public static void addWidgets(GridPane parent){
        //Headline links
        VBox HeadlControls = createSection("Headline links");

        Label Label1 = new Label("CUTECAT");
        Label1.getStyleClass().add("styled-text-field");
        Label1.setMinWidth(1300);
        Label1.setMaxWidth(1300);
        Label1.setFont(new Font("Arial",24));


        HeadlControls.getChildren().addAll(
                Label1

        );

        //Headline rechts
        VBox HeadrControls = createSection("Headline rechts");

        Label Label2 = new Label("Gymnasium Beilngries");
        Label2.getStyleClass().add("styled-text-field");
        Label2.setMinWidth(600);
        Label2.setMaxWidth(600);
        Label2.setFont(new Font("Arial",24));

        HeadrControls.getChildren().addAll(
                Label2

        );

        //Buttons mit verschiedenen Modi
        VBox Modi = createSection("Modi");
        Button b1 = new Button("Modus 1");
        //Style button
        b1.setStyle("-fx-background-color: #F0F0F0;");
        b1.setStyle("-fx-border-color: #008000; -fx-border-width: 50px;");
        //b1.setStyle("-fx-border-style: solid;");
        b1.setStyle("-fx-text-fill: #0a0000");
        b1.setStyle("-fx-font-size: 50px;");
        b1.setPrefSize(500,100);
        //b1.setTranslateX(-200);


        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //SecondStage
                Handlung = true;

            }
        };
        b1.setOnAction(event);


        Button b2 = new Button("Modus 2");
        //Style button
        b2.setStyle("-fx-background-color: #F0F0F0;");
        b2.setStyle("-fx-border-color: #008000; -fx-border-width: 50px;");
        //b2.setStyle("-fx-border-style: solid;");
        b2.setStyle("-fx-text-fill: #0a0000");
        b2.setStyle("-fx-font-size: 50px;");
        b2.setPrefSize(500,100);


        Button b3 = new Button("Modus 3");
        //Style button
        b3.setStyle("-fx-background-color: #F0F0F0;");
        b3.setStyle("-fx-border-color: #008000; -fx-border-width: 50px;");
        //b3.setStyle("-fx-border-style: solid;");
        b3.setStyle("-fx-text-fill: #0a0000");
        b3.setStyle("-fx-font-size: 50px;");
        b3.setPrefSize(500,100);


        Modi.getChildren().addAll(
                /*createLabel(" "),*/  b1, b2, b3

        );

        parent.add(HeadlControls, 0, 0);
        parent.add(HeadrControls, 1, 0);
        parent.add(Modi, 1, 2);



    }


    private static VBox createSection(String title) {
        VBox section = new VBox(10);
        section.getStyleClass().add("container-box");

        //Label titleLabel = new Label(title);
        //titleLabel.getStyleClass().add("container-box-header");

        //section.getChildren().add(titleLabel);
        return section;
    }

    private static Label createLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("styled-label");
        return label;
    }


}
