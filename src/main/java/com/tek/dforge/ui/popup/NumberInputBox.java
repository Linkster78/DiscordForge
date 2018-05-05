package com.tek.dforge.ui.popup;

import com.jfoenix.controls.JFXButton;
import com.tek.dforge.core.DiscordForge;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NumberInputBox {
	
	public static long display(String title, String initContents) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        JFXButton closeButton = new JFXButton("Close");
        closeButton.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
        closeButton.setOnAction(e -> window.close());
        
        TextField area = new TextField();
        area.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            area.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		        
		        try{
		        	Long.parseLong(area.getText());
		        }catch(Exception e) { 
		        	Platform.runLater(() -> {
		        		area.setText("0");
		        	});
		        }
		    }
		});
        area.setText(initContents == null ? "" : initContents);
        
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(area, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("primaryColor") + ";");

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("res/stylesheet.css");
        window.setScene(scene);
        window.showAndWait();
        
        try {
    	    return Long.parseLong(area.getText());
        }catch(Exception e) {
        	return -1;
        }
    }
	
}
