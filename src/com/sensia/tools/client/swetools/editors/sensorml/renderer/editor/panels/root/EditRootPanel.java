/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.root;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGRef;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.rng.RNGZeroOrMorePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.rng.RNGZeroOrMorePatternPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.root.ViewRootPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * The Class SensorSectionsWidget is corresponding to the root panel element. It handles the title, 
 * description, keywords elements.
 */
public class EditRootPanel extends ViewRootPanel{

	private Set<String> skipList;
	public EditRootPanel() {
		super();
		skipList = new HashSet<String>();
		skipList.add("definition");
		skipList.add("id");
		skipList.add("gml.id");
	}

	@Override
	protected void init() {
		headerDocumentPanel = new EditRootHeaderPanel();
		container.add(headerDocumentPanel);
	}
	@Override
	protected AbstractPanel newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addInnerElement(IPanel element) {
		if(skipList.contains(element.getName())) {
			return;
		}
		RNGTag tag = element.getTag();
		boolean found = false;
		
		if(tag instanceof RNGZeroOrMore || tag instanceof RNGOptional) {
			/** look for:
			 * <gml:description>
			        // some description
			    </gml:description>
			  <gml:identifier codeSpace="uniqueID">// some identifier</gml:identifier>
			  <gml:name>//some name</gml:name>
			  <sml:keywords>
			    <sml:KeywordList>
			      <sml:keyword>// some keyword</sml:keyword>
			    </sml:KeywordList>
			  </sml:keywords>
			 **/
			
			boolean shiftIntoMargin = false;
			List<RNGTag> children = null;
			if(tag instanceof RNGZeroOrMore) {
				children = ((RNGZeroOrMore)tag).getChildren();
			} else {
				children = ((RNGOptional)tag).getChildren();
				if( ((RNGOptional)tag).isSelected()) {
					element.getPanel().addStyleName("rng-shift-remove");
				}
			}
			
			// test zeroOrMore pattern to shift to the left
			if(element instanceof RNGZeroOrMorePanel) {
				((RNGZeroOrMorePanel)element).getPatternPanel().addStyleName("rng-shift-remove");
			}
			
			for(RNGTag child : children ){
				String name = child.toString();
				if(skipList.contains(name)) {
					found = true;
					break;
				}
				if(child instanceof RNGRef) {
					// split namespace
					String[] split = child.toString().split("\\.");
					if(split != null && split.length > 1) {
						name = split[1];
					} 
				}
				
				//
				found |= handleElement(name, element);
			}
		} else {
			found = handleElement(element.getName(), element);
		}
		
		if(!found) {
			super.addInnerElement(element);
		}
	}
	
	private boolean handleElement(String name,IPanel element) {
		boolean found = false;
		
		if(name.equals("description")) {
			headerDocumentPanel.setDescription(element.getPanel());
			found = true;
		} else if(name.equals("identifier")) {
			headerDocumentPanel.addIdentifier(element.getPanel());
			found = true;
		} else if(name.equals("name")){
			headerDocumentPanel.addTitle(element.getPanel());
			found = true;
		} else if(name.equals("keywords")){
			headerDocumentPanel.addKeywords(element.getPanel());
			found = true;
		}
		
		return found;
	}
	
	@Override
	public String getName() {
		return "Edit Root Panel";
	}
}
