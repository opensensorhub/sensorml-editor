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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGRef;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.root.RootRenderer;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.root.ViewRootPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;
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
	}

	@Override
	protected void init() {
		headerDocumentPanel = new EditRootHeaderPanel();
		container.add(headerDocumentPanel);
		
		Label addSectionButton = new Label("");
		addSectionButton.addStyleName("add-button");
		
		SMLHorizontalPanel hPanel = new SMLHorizontalPanel();
		hPanel.add(new Label("Add section"));
		hPanel.add(addSectionButton);

		hPanel.addStyleName("v-align-middle");
		
		headerDocumentPanel.add(hPanel);
		headerDocumentPanel.add(new HTML("<hr  style=\"width:100%;\" />"));
		
		addSectionButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				Utils.displaySaveDialogBox(rootTag, refreshHandler, new SMLVerticalPanel(), "Add section", new RootRenderer());
			}
		});
	}
	@Override
	protected AbstractPanel newInstance() {
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
			
			List<RNGTag> children = null;
			if(tag instanceof RNGZeroOrMore) {
				children = ((RNGZeroOrMore)tag).getChildren();
			} else {
				children = ((RNGOptional)tag).getChildren();
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
		
		if (name.equals("id")) {
            headerDocumentPanel.addIdentifier(element.getPanel());
            found = true;
        } else if(name.equals("description")) {
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
