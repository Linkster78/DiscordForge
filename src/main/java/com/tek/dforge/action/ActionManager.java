package com.tek.dforge.action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import com.tek.dforge.annotations.Action;
import com.tek.dforge.plugin.IForgePlugin;
import com.tek.dforge.ui.layout.Layout;

public class ActionManager {
	
	public HashMap<IForgePlugin, ArrayList<ActionEntry>> actionEntries;
	public HashMap<IForgePlugin, Layout> layouts;
	
	public ActionManager() {
		this.actionEntries = new HashMap<IForgePlugin, ArrayList<ActionEntry>>();
		this.layouts = new HashMap<IForgePlugin, Layout>();
	}
	
	public void annexEntries(IForgePlugin plugin, Object clazz) {
		for(Method method : clazz.getClass().getMethods()) {
			if(method.isAnnotationPresent(Action.class)) {
				Action action = (Action) method.getAnnotation(Action.class);
				
				/*
				 * Trust user here. assume that he's a cool and chill dude who can code properly. he'll get errors anyways /o.o/
				 */
				
				if(!layouts.containsKey(plugin)) layouts.put(plugin, new Layout());
				if(!actionEntries.containsKey(plugin)) actionEntries.put(plugin, new ArrayList<ActionEntry>());
				
				ActionEntry entry = new ActionEntry(action.name(), action.id(), method, clazz);
				actionEntries.get(plugin).add(entry);
				layouts.get(plugin).addItem(entry);
			}
		}
	}
	
	public ActionEntry getEntry(IForgePlugin plugin, String id) {
		for(ActionEntry entry : getEntries(plugin)) {
			if(entry.getId().equals(id)) return entry;
		}
		
		return null;
	}
	
	public void setLayout(IForgePlugin plugin, Layout layout) {
		layouts.put(plugin, layout);
	}
	
	public Layout getLayout(IForgePlugin plugin) {
		if(!layouts.containsKey(plugin)) layouts.put(plugin, new Layout());
		
		return layouts.get(plugin);
	}
	
	public ArrayList<ActionEntry> getEntries(IForgePlugin plugin){
		if(!actionEntries.containsKey(plugin)) actionEntries.put(plugin, new ArrayList<ActionEntry>());
		
		return actionEntries.get(plugin);
	}
}
