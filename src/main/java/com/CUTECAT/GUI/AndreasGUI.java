package com.CUTECAT.GUI;

import com.CUTECAT.app.CUTECAT;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;

;

public class AndreasGUI {



    public static void addWidgets(Pane content){
        //Headline links
        VBox HeadlControls = createSection("Headline links");

        Label Label1a = new Label("   CUTECAT");
        Label1a.getStyleClass().add("styled-titel");
        Label1a.setMinWidth(1300);
        Label1a.setMaxWidth(1300);
        Label1a.setFont(new Font("Arial",28));
        Label1a.setLayoutX(0);
        Label1a.setLayoutY(50);


        content.getChildren().addAll(
                Label1a);

        //Headline rechts
        VBox HeadrControls = createSection("Headline rechts");

        Label Label1b = new Label("Gymnasium Beilngries");
        Label1b.getStyleClass().add("styled-titel");
        Label1b.setMinWidth(650);
        Label1b.setMaxWidth(650);
        Label1b.setLayoutX(1300);
        Label1b.setLayoutY(50);
        Label1b.setFont(new Font("Arial",28));

        content.getChildren().addAll(
                Label1b);

        //Buttons mit verschiedenen Modi
        VBox Modi = createSection("Modi");
        Button b1 = new Button("Ferngesteuerter Modus");
        //Style button
        b1.getStyleClass().add("styled-button-a");
        b1.setPrefSize(600,150);
        b1.setLayoutX(1200);
        b1.setLayoutY(200);
        //Button aktivieren
        b1.setOnAction(e -> CUTECAT.Handlung=true);


        Button b2 = new Button("Modus 2");
        //Style button
        b2.getStyleClass().add("styled-button-a");
        b2.setPrefSize(600,150);
        b2.setLayoutX(1200);
        b2.setLayoutY(400);
        //Button aktivieren
        b2.setOnAction(e -> CUTECAT.Handlung2=true);


        Button b3 = new Button("Modus 3");
        //Style button
        b3.getStyleClass().add("styled-button-a");
        b3.setPrefSize(600,150);
        b3.setLayoutX(1200);
        b3.setLayoutY(600);


        content.getChildren().addAll(
                /*createLabel(" "),*/  b1, b2, b3

        );

        Button b4 = new Button("Infos");
        //Style button
        b4.setStyle("-fx-background-color: #F0F0F0;");
        b4.setStyle("-fx-border-color: #008000; -fx-border-width: 50px;");
        //b4.setStyle("-fx-border-style: solid;");
        b4.setStyle("-fx-text-fill: #0a0000");
        b4.setStyle("-fx-font-size: 20px;");
        b4.setPrefSize(200,100);
        b4.setLayoutX(200);
        b4.setLayoutY(850);

        Button b5 = new Button("Impressum");
        //Style button
        b5.setStyle("-fx-background-color: #F0F0F0;");
        b5.setStyle("-fx-border-color: #008000; -fx-border-width: 50px;");
        //b5.setStyle("-fx-border-style: solid;");
        b5.setStyle("-fx-text-fill: #0a0000");
        b5.setStyle("-fx-font-size: 20px;");
        b5.setPrefSize(200,100);
        b5.setLayoutX(500);
        b5.setLayoutY(850);

        Button b6 = new Button("kp");
        //Style button
        b6.setStyle("-fx-background-color: #F0F0F0;");
        b6.setStyle("-fx-border-color: #008000; -fx-border-width: 50px;");
        //b6.setStyle("-fx-border-style: solid;");
        b6.setStyle("-fx-text-fill: #0a0000");
        b6.setStyle("-fx-font-size: 20px;");
        b6.setPrefSize(200,100);
        b6.setLayoutX(800);
        b6.setLayoutY(850);

        content.getChildren().addAll(b4, b5, b6);



    }

    //Ferngesteuerter Modus
    public static void addWidgets2(Pane content2){
        //Headline
        VBox HeadControls2 = createSection("Headline");

        Label Label2a = new Label("Ferngesteuerter Modus");
        Label2a.getStyleClass().add("styled-titel");
        Label2a.setPrefWidth(1950);
        Label2a.setLayoutX(0);
        Label2a.setLayoutY(50);
        Label2a.setFont(new Font("Arial",28));

        content2.getChildren().addAll(
                Label2a);

        //Anzeige von verschiedenen Parametern

        //Rotation Turm
        Label Label2b = new Label("Rotation Turm");
        Label2b.getStyleClass().add("styled-text-field");
        Label2b.setMinWidth(300);
        Label2b.setMaxWidth(300);
        Label2b.setLayoutX(50);
        Label2b.setLayoutY(150);
        Label2b.setFont(new Font("Arial",22));


        //Neigung Rohr
        Label Label2c = new Label("Neigung Rohr");
        Label2c.getStyleClass().add("styled-text-field");
        Label2c.setMinWidth(300);
        Label2c.setMaxWidth(300);
        Label2c.setLayoutX(50);
        Label2c.setLayoutY(250);
        Label2c.setFont(new Font("Arial",22));


        //Munition
        Label Label2d = new Label("Munition");
        Label2d.getStyleClass().add("styled-text-field");
        Label2d.setMinWidth(300);
        Label2d.setMaxWidth(300);
        Label2d.setLayoutX(50);
        Label2d.setLayoutY(350);
        Label2d.setFont(new Font("Arial",22));


        //Gasdruck
        Label Label2e = new Label("Gasdruck");
        Label2e.getStyleClass().add("styled-text-field");
        Label2e.setMinWidth(300);
        Label2e.setMaxWidth(300);
        Label2e.setLayoutX(50);
        Label2e.setLayoutY(450);
        Label2e.setFont(new Font("Arial",22));


        content2.getChildren().addAll(
                Label2b, Label2c, Label2d, Label2e);

        VBox Entfernung = createSection1("Entfernung");
        Label Label2f = new Label("Entfernung in m");
        Label2f.getStyleClass().add("styled-text-field");
        Label2f.setMinWidth(300);
        Label2f.setMaxWidth(300);
        Label2f.setLayoutX(500);
        Label2f.setLayoutY(450);
        Label2f.setFont(new Font("Arial",22));
        content2.getChildren().addAll(
                Label2f);


        //Informationen zur steuerung
        String [][] steuerung ={
                {"W","Vorwärts"},
                {"S","Rückwärts"},
                {"A","Links"},
                {"D","Rechts"},
                {"→","Turm rechts"},
                {"←","Turm links"},
                {"↑","Rohr neigen"},
                {"↓","Rohr senken"},
        };
        for (int i =0;i<steuerung.length;i++){
            Label taste = new Label(steuerung[i][0]);
            taste.getStyleClass().add("styled-text-field");
            taste.setMinWidth(100);
            taste.setMaxWidth(100);
            taste.setLayoutX(100+i*150);
            taste.setLayoutY(850);
            taste.setFont(new Font("Arial",18));

            Label funktion = new Label(steuerung[i][1]);
            funktion.getStyleClass().add("styled-text-field");
            funktion.setMinWidth(100);
            funktion.setMaxWidth(100);
            funktion.setLayoutX(100+i*150);
            funktion.setLayoutY(900);
            funktion.setFont(new Font("Arial",14));

            content2.getChildren().addAll(taste, funktion);
        }

    }

    //Ferngesteuerter Modus
    public static void addWidgets3(Pane content3){
        //Headline
        Label Label3a = new Label("Halbautomatischer Modus");
        Label3a.getStyleClass().add("styled-titel");
        Label3a.setPrefWidth(1950);
        Label3a.setLayoutX(0);
        Label3a.setLayoutY(50);
        Label3a.setFont(new Font("Arial",28));

        content3.getChildren().addAll(
                Label3a);

        //Anzeige von verschiedenen Parametern

        //Rotation Turm
        Label Label2b = new Label("Rotation Turm");
        Label2b.getStyleClass().add("styled-text-field");
        Label2b.setMinWidth(300);
        Label2b.setMaxWidth(300);
        Label2b.setLayoutX(50);
        Label2b.setLayoutY(150);
        Label2b.setFont(new Font("Arial",22));


        //Neigung Rohr
        Label Label2c = new Label("Neigung Rohr");
        Label2c.getStyleClass().add("styled-text-field");
        Label2c.setMinWidth(300);
        Label2c.setMaxWidth(300);
        Label2c.setLayoutX(50);
        Label2c.setLayoutY(250);
        Label2c.setFont(new Font("Arial",22));


        //Munition
        Label Label2d = new Label("Munition");
        Label2d.getStyleClass().add("styled-text-field");
        Label2d.setMinWidth(300);
        Label2d.setMaxWidth(300);
        Label2d.setLayoutX(50);
        Label2d.setLayoutY(350);
        Label2d.setFont(new Font("Arial",22));


        //Gasdruck
        Label Label2e = new Label("Gasdruck");
        Label2e.getStyleClass().add("styled-text-field");
        Label2e.setMinWidth(300);
        Label2e.setMaxWidth(300);
        Label2e.setLayoutX(50);
        Label2e.setLayoutY(450);
        Label2e.setFont(new Font("Arial",22));


        content3.getChildren().addAll(
                Label2b, Label2c, Label2d, Label2e);

        VBox Entfernung = createSection1("Entfernung");
        Label Label2f = new Label("Entfernung in m");
        Label2f.getStyleClass().add("styled-text-field");
        Label2f.setMinWidth(300);
        Label2f.setMaxWidth(300);
        Label2f.setLayoutX(500);
        Label2f.setLayoutY(450);
        Label2f.setFont(new Font("Arial",22));
        content3.getChildren().addAll(
                Label2f);

        //Informationen zur steuerung
        String [][] steuerung ={
                {"W","Vorwärts"},
                {"S","Rückwärts"},
                {"A","Links"},
                {"D","Rechts"},
                {"→","Turm rechts"},
                {"←","Turm links"},
                {"↑","Rohr neigen"},
                {"↓","Rohr senken"},
        };
        for (int i =0;i<steuerung.length;i++){
            Label taste = new Label(steuerung[i][0]);
            taste.getStyleClass().add("styled-text-field");
            taste.setMinWidth(100);
            taste.setMaxWidth(100);
            taste.setLayoutX(100+i*150);
            taste.setLayoutY(850);
            taste.setFont(new Font("Arial",18));

            Label funktion = new Label(steuerung[i][1]);
            funktion.getStyleClass().add("styled-text-field");
            funktion.setMinWidth(100);
            funktion.setMaxWidth(100);
            funktion.setLayoutX(100+i*150);
            funktion.setLayoutY(900);
            funktion.setFont(new Font("Arial",14));

            content3.getChildren().addAll(taste, funktion);
        }

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


    private static VBox createSection1(String title) {
        VBox section = new VBox(10);
        section.getStyleClass().add("container-box");
        section.setLayoutX(200);
        section.setLayoutY(200);
        return section;
    }

}
