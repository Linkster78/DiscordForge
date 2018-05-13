package com.tek.dforge.ui;

import java.io.IOException;

import com.tek.dforge.core.DiscordForge;
import com.tek.dforge.util.Reference;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUI extends Application{
	
	private static GUI instance;
	private Stage primaryStage;
	
	final int width = 600, height = 457;
	
	public static void ilaunch(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;
		
		this.primaryStage = primaryStage;
		
		this.primaryStage.setTitle(Reference.NAME);
	    this.primaryStage.getIcons().add(new Image("res/icon.png"));
	    this.primaryStage.setResizable(true);
			
		this.primaryStage.setOnCloseRequest(e -> {
			DiscordForge.getInstance().close();
		});
	    
	    this.primaryStage.setScene(getMainScene());
	    
		this.primaryStage.show();
		
		this.primaryStage.setMaxWidth(this.primaryStage.getWidth());
		this.primaryStage.setMaxHeight(this.primaryStage.getHeight());
	}
	
	public Scene getMainScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUI.class.getResource("MainScene.fxml"));
            BorderPane borderPane = (BorderPane) loader.load();
            
           return new Scene(borderPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	
	public static GUI getInstance() {
    	return instance;
    }
	
}
