package com.tek.dforge.ui.layout;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Layout {
	
	private ArrayList<ILayoutItem> items;
	
	public Layout() {
		items = new ArrayList<ILayoutItem>();
	}
	
	public void addItem(ILayoutItem item) {
		items.add(item);
	}
	
	public void display(Pane pane) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		for(ILayoutItem item : items) {
			item.add(nodes);
		}
		
		pane.getChildren().addAll(nodes);
	}
	
}
