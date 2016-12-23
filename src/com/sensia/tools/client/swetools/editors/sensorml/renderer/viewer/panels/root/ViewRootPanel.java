/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.root;

import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGOneOrMore;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.DisclosureElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.element.ViewSectionElementPanel;

/**
 * The Class SensorSectionsWidget is corresponding to the root panel element. It handles the title, 
 * description, keywords elements.
 */
public class ViewRootPanel extends AbstractPanel<RNGElement>{

	protected ViewRootHeaderPanel headerDocumentPanel;
	
	protected Panel id;
	
	public ViewRootPanel(RNGElement element) {
		super(element);
		init();
	}

	protected void init() {
		headerDocumentPanel = new ViewRootHeaderPanel();
		container.add(headerDocumentPanel);
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addInnerElement(IPanel element) {
		String name = element.getName();
		switch(name) {
			case "identifier" : headerDocumentPanel.addIdentifier(element.getPanel());break;
			case "keywords" : headerDocumentPanel.addKeywords(element.getPanel());break;
			case "name" : {
				Panel titlePanel = element.getPanel();
				titlePanel.addStyleName("header-title-panel");
				headerDocumentPanel.addTitle(titlePanel); break;
			}
			case "description" : headerDocumentPanel.setDescription(element.getPanel()); break;
			case "id" : id = element.getPanel();break;
			default:{
				if(element instanceof DisclosureElementPanel) {
					element.getPanel().removeStyleName("disclosure-noborder");
					element.getPanel().addStyleName("section-panel disclosure-border");
					container.add(element.getPanel());
				}  else if(element.getTag() instanceof RNGZeroOrMore || 
						element.getTag() instanceof RNGOneOrMore ||
						element.getTag() instanceof RNGChoice ||
						element.getTag() instanceof RNGOptional){
					container.add(element.getPanel());
				} else {
					ViewSectionElementPanel section = new ViewSectionElementPanel((RNGElement) element.getTag(),refreshHandler);
					section.addElement(element);
					container.add(section.getPanel());
				}
				
				break;
			}
		}
	}


	@Override
	public String getName() {
		return "Root Panel";
	}
	
	
	public ViewRootHeaderPanel getHeader() {
		return headerDocumentPanel;
	}
	
	public Panel getId() {
		return id;
	}
}
