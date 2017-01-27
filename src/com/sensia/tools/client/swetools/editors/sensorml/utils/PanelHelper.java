package com.sensia.tools.client.swetools.editors.sensorml.utils;

import java.util.List;

import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

public class PanelHelper {

	private PanelHelper(){}
	
	public static IPanel findPanel(IPanel root,String nameElement) {
		IPanel result = null;
		if(root == null) {
			return null;
		}
		
		if(root.getName().equals(nameElement)) {
			result = root;
		} else {
			List<IPanel> children = root.getElements();
			for(IPanel child: children) {
				if((result=findPanel(child, nameElement)) != null) {
					break;
				}
			}
		}
		
		return result;
	}
	
	public static IPanel findPanel(IPanel root,String nameElement,int currentLevel, int maxLevel) {
		IPanel result = null;
		if(currentLevel >= maxLevel) {
			return result;
		}
		if(root.getName().equals(nameElement)) {
			result = root;
		} else {
			List<IPanel> children = root.getElements();
			for(IPanel child: children) {
				if((result=findPanel(child, nameElement,currentLevel++,maxLevel)) != null) {
					break;
				}
			}
		}
		
		return result;
	}
	
	public static void findPanels(IPanel root,String nameElement,int currentLevel, int maxLevel,List<IPanel> results) {
		if(currentLevel >= maxLevel) {
			return;
		}
		if(root.getName().equals(nameElement)) {
			results.add(root);
		} 
		List<IPanel> children = root.getElements();
		for(IPanel child: children) {
			findPanels(child, nameElement,currentLevel++,maxLevel,results);
		}
	}
}
