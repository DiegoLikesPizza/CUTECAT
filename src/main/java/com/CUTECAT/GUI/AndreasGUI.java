package com.CUTECAT.GUI;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AndreasGUI {
    public static void addWidgets(GridPane parent){
        //Headline links
        VBox HeadlControls = createSection("Headline links");

        TextField textField1 = new TextField();
        textField1.setPromptText("CUTECAT");
        textField1.getStyleClass().add("styled-text-field");
        textField1.setMinWidth(1600);
        textField1.setMaxWidth(1600);


        HeadlControls.getChildren().addAll(
                /*createLabel(" "),*/ textField1

        );

        //Headline rechts
        VBox HeadrControls = createSection("Headline rechts");

        TextField textField2 = new TextField();
        textField2.setPromptText("Gymnasium Beilngries");
        textField2.getStyleClass().add("styled-text-field");
        textField2.setMinWidth(200);
        textField2.setMaxWidth(200);

        HeadrControls.getChildren().addAll(
                /*createLabel(" "),*/ textField2

        );

        //Buttons mit verschiedenen Modi
        Button b1 = new Button("Modus 1");
        b1.getStyleClass().add("styled-text-field");
        Button b2 = new Button("Modus 2");
        b2.getStyleClass().add("styled-text-field");



        HeadrControls.getChildren().addAll(
                /*createLabel(" "),*/  b1, b2

        );

        parent.add(HeadlControls, 0, 0);
        parent.add(HeadrControls, 1, 0);


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
