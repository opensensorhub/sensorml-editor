package com.sensia.tools.client.swetools.editors.sensorml.panels.sml;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.DisclosureElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * 
 * Handle  a link pointing to a new viewer using the current xlink ref.
 * attributes:
 * - name
 * - xlink:title
 * - xlink:role
 * - xlink:href
 *
 */
public class SMLComponentPanel extends DisclosureElementPanel {

	private Anchor anchor;
	private IPanel<RNGAttribute> title;
	private IPanel<RNGAttribute> href;
	
	public SMLComponentPanel(RNGElement tag) {
		super(tag);
		anchor = new Anchor();
		anchor.setText("");
		anchor.setHref("");
		
		addContent(anchor);
	}
	
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		Widget panelToAdd = element.getPanel();
		if(element.getTag() instanceof RNGAttribute) {
			RNGAttribute att = (RNGAttribute) element.getTag();
			
			if(element.getName().equals("title")) {
				anchor.setText(anchor.getText());
				title = (IPanel<RNGAttribute>) element;
			}  else if(element.getName().equals("href")){
				anchor.setHref(Utils.getCurrentURL(att.getChildValueText()));
				href = (IPanel<RNGAttribute>) element;
			} else {
				super.addInnerElement(element);
			}
		} else {
			addContent(panelToAdd);
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel.MODE mode) {
		//TODO: use ontology icon to edit definition
		clear();
		
		String value = "";
		// update html value with edited value
		
		if(title != null) {
			if(!value.isEmpty()) {
				value += ": ";
			}
			value += title.getTag().getChildValueText();
		}
		
		anchor.setText(value);
		if(href != null) {
			anchor.setHref(Utils.getCurrentURL(href.getTag().getChildValueText()));
		}
		
		if(mode == MODE.EDIT) {
			if(title != null) {
				addContent(title.getPanel());
			}
			if(href != null) {
				addContent(href.getElements().get(0).getPanel());
			}
		} else if (mode == MODE.VIEW) {
			addContent(anchor);
		}
	}

}
