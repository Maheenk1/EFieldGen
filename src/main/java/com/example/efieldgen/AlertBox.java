package com.example.efieldgen;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * AlertBox class that creates an alert for users to view when an error has occurred.
 * @author Maheen Khan
 * @version 12.0.1
 */
public class AlertBox {
    /**
     * Method that creates and launches a new alert window.
     * @param title String representing the title of the AlertBox.
     * @param message String representing the message in the AlertBox.
     */
    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 150, 100);
        window.setScene(scene);
        window.showAndWait();
    }
}
