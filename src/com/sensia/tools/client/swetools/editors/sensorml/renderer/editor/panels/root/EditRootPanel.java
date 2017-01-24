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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGRef;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.GenericVerticalContainerPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGZeroOrMorePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGZeroOrMorePatternPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.root.RootRenderer;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.root.ViewRootHeaderPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.root.ViewRootPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.CloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SaveCloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * The Class SensorSectionsWidget is corresponding to the root panel element. It handles the title, 
 * description, keywords elements.
 */
public class EditRootPanel extends ViewRootPanel{

	private Set<String> skipList;
	private RNGElement rootTag;
	
	public EditRootPanel(RNGElement rootTag,IRefreshHandler refreshHandler) {
		super(rootTag,refreshHandler);
		
		this.rootTag = rootTag;
		skipList = new HashSet<String>();
		skipList.add("definition");
		skipList.add("id");
		skipList.add("gml.id");
	}

	@Override
	protected void init() {
		headerDocumentPanel = new EditRootHeaderPanel();
		container.add(headerDocumentPanel);
		
		Label addSectionButton = new Label("");
		addSectionButton.addStyleName("rng-optional-select-add");
		
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(new Label("Add section"));
		hPanel.add(addSectionButton);
		hPanel.addStyleName("addsection-button");
		container.add(hPanel);
		
		addSectionButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Utils.displaySaveDialogBox(rootTag, refreshHandler, new VerticalPanel(), "Add section", new RootRenderer());
			}
		});
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
		if( (tag instanceof RNGOptional && ((RNGOptional)tag).isSelected()) ||
			 (element instanceof RNGZeroOrMorePatternPanel)	) {
			element.getPanel().addStyleName("rng-shift-remove");
		}
		
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
			
			List<RNGTag> children = null;
			if(tag instanceof RNGZeroOrMore) {
				children = ((RNGZeroOrMore)tag).getChildren();
			} else {
				children = ((RNGOptional)tag).getChildren();
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
			headerDocumentPanel.addKeywords(element);
			found = true;
		}
		
		return found;
	}
	
	@Override
	public String getName() {
		return "Edit Root Panel";
	}
}
