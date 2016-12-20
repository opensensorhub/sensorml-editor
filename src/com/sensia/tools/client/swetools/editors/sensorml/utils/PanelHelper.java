package com.sensia.tools.client.swetools.editors.sensorml.utils;

import java.util.ArrayList;
import java.util.List;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGDefine;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGRef;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagList;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

public class PanelHelper {

	private PanelHelper(){}
	
	public static IPanel findPanel(IPanel root,String nameElement) {
		IPanel result = null;
		
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
}
