package com.tek.dforge.ui.layout.items;

import java.util.ArrayList;

import com.tek.dforge.core.DiscordForge;
import com.tek.dforge.ui.layout.ILayoutItem;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class Space implements ILayoutItem{

	@Override
	public void add(ArrayList<Node> nodes) {
		Label label = new Label("SPACE");
		label.setStyle("-fx-text-fill: #" + DiscordForge.getInstance().getConfig().getProperty("primaryColor") + ";");
		nodes.add(label);
	}

}
