package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;

public class SWEEditAllowedValuesPanel extends EditSimpleElementPanel{

	private Panel valuesPanel;
	private boolean isFirst = true;
	
	public SWEEditAllowedValuesPanel(RNGElement element) {
		super(element,"AllowedValues");
		
		container = new HorizontalPanel();
		valuesPanel = new HorizontalPanel();
		
		container.add(new Label("["));
		container.add(valuesPanel);
		container.add(new Label("]"));
		
		container.addStyleName("allowedValues");
	}
	
	@Override
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("value")) {
			if(!isFirst) {
				valuesPanel.add(new Label(" / "));
			} else {
				isFirst = false;
			}
			valuesPanel.add(element.getPanel());
		}
	}

}
