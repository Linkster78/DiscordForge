package com.tek.dforge.ui.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.tek.dforge.core.DiscordForge;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BooleanInputBox {
	
	public static boolean display(String title) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        JFXButton closeButton = new JFXButton("Close");
        closeButton.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
        closeButton.setOnAction(e -> window.close());
        
        JFXCheckBox cb = new JFXCheckBox();
        
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(cb, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("primaryColor") + ";");

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("res/stylesheet.css");
        window.setScene(scene);
        window.showAndWait();
        
        return cb.isSelected();
    }
	
}
