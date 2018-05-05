package com.tek.dforge.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.tek.dforge.core.DiscordForge;
import com.tek.dforge.ui.layout.ILayoutItem;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class ActionEntry implements ILayoutItem{
	
	public String name;
	public String id;
	public Method method;
	public Object instance;
	
	public ActionEntry(String name, String id, Method method, Object instance) {
		this.name = name;
		this.id = id;
		this.method = method;
		this.instance = instance;
		
		this.method.setAccessible(true);
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public Object getInstance() {
		return instance;
	}
	
	public void invoke() {
		try {
			method.invoke(instance);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void add(ArrayList<Node> nodes) {
		JFXButton button = new JFXButton(" " + name + " ");
		button.setPadding(new Insets(5, 5, 5, 5));
		button.setStyle("-fx-background-color: #" + DiscordForge.getInstance().getConfig().getProperty("secondaryColor") + ";");
		
		button.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			invoke();
		});
		
		nodes.add(button);
	}
	
}
