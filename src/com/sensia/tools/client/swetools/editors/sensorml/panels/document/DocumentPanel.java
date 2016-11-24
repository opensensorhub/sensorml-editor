/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.document;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGOneOrMore;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.DisclosureElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * The Class SensorSectionsWidget is corresponding to the root panel element. It handles the title, 
 * description, keywords elements.
 */
public class DocumentPanel extends AbstractPanel{

	protected HeaderDocumentPanel headerDocumentPanel;
	
	public DocumentPanel() {
		init();
	}

	protected void init() {
		headerDocumentPanel = new HeaderDocumentPanel();
		container.add(headerDocumentPanel);
	}

	@Override
	protected AbstractPanel newInstance() {
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
			default:{
				if(element instanceof DisclosureElementPanel) {
					((DisclosureElementPanel)element).setHeaderStr(Utils.toNiceLabel(element.getName()));
					element.getPanel().removeStyleName("disclosure-noborder");
					element.getPanel().addStyleName("section-panel disclosure-border");
					container.add(element.getPanel());
				}  else if(element.getTag() instanceof RNGZeroOrMore || 
						element.getTag() instanceof RNGOneOrMore ||
						element.getTag() instanceof RNGChoice ||
						element.getTag() instanceof RNGOptional){
					container.add(element.getPanel());
				} else {
					DisclosurePanel sectionPanel = new DisclosurePanel(Utils.toNiceLabel(element.getName()));
					sectionPanel.setAnimationEnabled(true);
					sectionPanel.setOpen(true);
					sectionPanel.add(element.getPanel());
					sectionPanel.addStyleName("section-panel");
					sectionPanel.getContent().addStyleName("disclosure-generic-content-panel");
					
					if(element.getTag() instanceof RNGElement) {
						RNGElement castTag = (RNGElement) element.getTag();
						if(castTag.getChildAttribute("name") != null) {
							// get the corresponding panel
							List<IPanel<? extends RNGTag>> children = element.getElements();
							
							for(IPanel<? extends RNGTag> child : children) {
								if(child.getTag() instanceof RNGAttribute && child.getName().equals("name")){
									String nameStr = ((RNGAttribute)child.getTag()).getChildValueText();
									
									sectionPanel.getHeaderTextAccessor().setText(Utils.toNiceLabel(nameStr));
									child.getPanel().setVisible(false);
									break;
								}
							}
						}
					}
					
					container.add(sectionPanel);
					element.getPanel().removeStyleName("disclosure-noborder");
					element.getPanel().addStyleName("disclosure-border");
				}
				
				break;
			}
		}
	}


	@Override
	public String getName() {
		return "Root Panel";
	}
}
