package com.sensia.tools.client.swetools.editors.sensorml.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.sensia.gwt.relaxNG.RNGParserCallback;
import com.sensia.gwt.relaxNG.XMLSensorMLParser;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGDefine;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.relaxNG.RNGGroup;
import com.sensia.relaxNG.RNGInterleave;
import com.sensia.relaxNG.RNGList;
import com.sensia.relaxNG.RNGOneOrMore;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGRef;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagList;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.relaxNG.RNGText;
import com.sensia.relaxNG.RNGValue;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.relaxNG.XSDAnyURI;
import com.sensia.relaxNG.XSDBoolean;
import com.sensia.relaxNG.XSDDateTime;
import com.sensia.relaxNG.XSDDecimal;
import com.sensia.relaxNG.XSDDouble;
import com.sensia.relaxNG.XSDInteger;
import com.sensia.relaxNG.XSDString;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ICallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorAttributeWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorChoiceWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericHorizontalContainerWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorValueWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorZeroOrMoreWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd.SensorXSDAnyURIWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd.SensorXSDDateTimeWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd.SensorXSDDecimalWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd.SensorXSDDoubleWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd.SensorXSDIntegerWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd.SensorXSDStringWidget;

public final class NameRefResolver {

	private String remoteFile;
	private RNGGrammar currentGrammar;
	
	public NameRefResolver() {}
	
	public void build(ISensorWidget currentWidget,final RNGGrammar grammar) {
		//first get the root node to find the typeof tag
		ISensorWidget root = currentWidget;
		this.currentGrammar = grammar;
		
		while(root.getParent() != null) {
			root = root.getParent();
		}
		
		//find the typeof tag
		ISensorWidget typeOfWidget = AbstractSensorElementWidget.findWidget(root, "typeOf", TAG_DEF.SML,TAG_TYPE.ELEMENT);
		if(typeOfWidget != null) {
			//get href
			remoteFile = typeOfWidget.getValue("href", true);
		} 
	}
	
	public void resolvePath(ISensorWidget currentWidget, final List<String> path,final ICallback<String> callback) {
		if(path != null && path.size() > 0 && path.get(0).equals("this")) {
			List<String> newPath = new ArrayList<String>(path.subList(1, path.size()));
			proceedSearch(newPath,currentGrammar,callback);
		} else  if(remoteFile != null){
			//download and transform the XML document into a RNG profile
			final XMLSensorMLParser parser = new XMLSensorMLParser();
			
			parser.parse(remoteFile, new RNGParserCallback() {
				
				@Override
				public void onParseDone(final RNGGrammar grammar) {
					proceedSearch(path,grammar,callback);
				}
			});
		} else {
			Window.alert("No TypeOf tag has been found to resolve the path");
		}
	}
	
	private void proceedSearch(List<String> path, RNGGrammar grammar, ICallback<String> callback) {
		if(path != null && path.size() > 0) {
			RNGGroup rootGrammar = grammar.getStartPattern();
			
			List<RNGTag> results = findTags(rootGrammar,path); 
			
			if(results.isEmpty()) {
				Window.alert("No path "+path.toString()+"has been resolved into the remote file "+remoteFile);
			} else if(results.size() > 1) {
				Window.alert("Many matches for the path "+path.toString()+"has been resolved into the remote file "+remoteFile);
			} else {
				//find label
				RNGTag t = results.get(0);
				List<String> dataPath = new ArrayList<String>();
				
				dataPath.add("label");
				results = findTags(t,dataPath);
				
				String label = null;
				String uom   = "";
				
				//find label
				if(results.isEmpty()) {
					//no label, takes the name attribute instead
					RNGAttribute nameAtt = ((RNGTagList) t).getChildAttribute("name");
					label = AbstractSensorElementWidget.toNiceLabel(nameAtt.getChildValue().getText());
				} else {
					RNGElement tagElt = (RNGElement) results.get(0);
					label = tagElt.getChildValue().getText();
				}
				
				//find uom
				dataPath.clear();
				dataPath.add("code");
				results = findTags(t,dataPath);
				if(!results.isEmpty()) {
					if(results.get(0) instanceof RNGAttribute) {
						RNGAttribute tagElt = (RNGAttribute) results.get(0);
						uom = tagElt.getChildValue().getText();
					}
				} 
				
				callback.callback(label,uom);
			}
		}
	}
	private List<RNGTag> findTags(RNGTag root, List<String> path) {
		List<RNGTag> results = new ArrayList<RNGTag>();
		findRecursiveTags(root, path, 0, results);
		return results;
	}
	
	private void findRecursiveTags(RNGTag tag, List<String> path,int index, List<RNGTag> results) {
		int idx = index;
		String name = path.get(index);
		if(tag instanceof RNGElement) {
			RNGElement rootElement = (RNGElement) tag;
			if(rootElement.getName().equals(name)) {
				idx++;
			} else {
				//check attribute "name"
				RNGAttribute nameAtt = rootElement.getChildAttribute("name");
				if(nameAtt != null && nameAtt.getChildValue().getText().equals(name)){
					idx++;
				} else {
					nameAtt = rootElement.getChildAttribute("id");
					if(nameAtt != null && nameAtt.getChildValue().getText().equals(name)){
						idx++;
					} 
				}
			}
		} else if(tag instanceof RNGAttribute) {
			RNGAttribute rootAtt= (RNGAttribute) tag;
			if(rootAtt.getName().equals(name)) {
				idx++;
			}
		}
		//we have found the element
		if(idx == path.size()) {
			results.add(tag);
			return;
		} else {
			if (tag instanceof RNGDefine || tag instanceof RNGGroup || tag instanceof RNGOptional || tag instanceof RNGZeroOrMore
					|| tag instanceof RNGOneOrMore || tag instanceof RNGElement) {
				List<RNGTag> children = ((RNGTagList) tag).getChildren();
				for(RNGTag child : children) {
					findRecursiveTags(child, path, idx,results);
				}
			}
		}
	}
}
