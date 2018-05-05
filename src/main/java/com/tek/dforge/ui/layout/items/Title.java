package com.tek.dforge.ui.layout.items;

import java.util.ArrayList;

import com.tek.dforge.ui.layout.ILayoutItem;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class Title implements ILayoutItem{

	private String text;
	
	public Title(String text) {
		this.text = text;
	}
	
	@Override
	public void add(ArrayList<Node> nodes) {
		Label title = new Label();
		title.setPadding(new Insets(5, 5, 5, 0));
		title.setStyle("-fx-font-size: 24px;");
		title.setText(text);
		
		nodes.add(title);
	}

}
