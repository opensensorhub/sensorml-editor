/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.sensia.gwt.relaxNG.RNGParserCallback;
import com.sensia.gwt.relaxNG.XMLSensorMLParser;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGDefine;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.relaxNG.RNGGroup;
import com.sensia.relaxNG.RNGOneOrMore;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagList;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ICallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

/**
 * The Class NameRefResolver resolves a path from a grammar.
 */
public final class NameRefResolver {

	/** The remote file. */
	private String remoteFile;
	
	/** The current grammar. */
	private RNGGrammar currentGrammar;
	
	/**
	 * Instantiates a new name ref resolver.
	 */
	public NameRefResolver() {}
	
	
	/**
	 * Resolve the path. The path can be resolved from a remote file or from the current instance.
	 * It will compare the first part of the path and check if "this" is found.
	 *
	 * @param currentWidget the current widget
	 * @param path the path
	 * @param callback the callback
	 */
	public void resolvePath(IPanel currentWidget, final List<String> path,final ICallback<String> callback) {
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
	
	/**
	 * Proceed search.
	 *
	 * @param path the path
	 * @param grammar the grammar
	 * @param callback the callback
	 */
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
					label = Utils.toNiceLabel(nameAtt.getChildValueText());
				} else {
					RNGElement tagElt = (RNGElement) results.get(0);
					label = tagElt.getChildValueText();
				}
				
				//find uom
				dataPath.clear();
				dataPath.add("code");
				results = findTags(t,dataPath);
				if(!results.isEmpty()) {
					if(results.get(0) instanceof RNGAttribute) {
						RNGAttribute tagElt = (RNGAttribute) results.get(0);
						uom = tagElt.getChildValueText();
					}
				} 
				
				callback.callback(label,uom);
			}
		}
	}
	
	/**
	 * Find tags.
	 *
	 * @param root the root
	 * @param path the path
	 * @return the list
	 */
	private List<RNGTag> findTags(RNGTag root, List<String> path) {
		List<RNGTag> results = new ArrayList<RNGTag>();
		findRecursiveTags(root, path, 0, results);
		return results;
	}
	
	/**
	 * Find recursive tags.
	 *
	 * @param tag the tag
	 * @param path the path
	 * @param index the index
	 * @param results the results
	 */
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
				if(nameAtt != null && nameAtt.getChildValueText().equals(name)){
					idx++;
				} else {
					nameAtt = rootElement.getChildAttribute("id");
					if(nameAtt != null && nameAtt.getChildValueText().equals(name)){
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

	public String getRemoteFile() {
		return remoteFile;
	}

	public void setRemoteFile(String remoteFile) {
		this.remoteFile = remoteFile;
	}

	public RNGGrammar getCurrentGrammar() {
		return currentGrammar;
	}

	public void setCurrentGrammar(RNGGrammar currentGrammar) {
		this.currentGrammar = currentGrammar;
	}
	

}
