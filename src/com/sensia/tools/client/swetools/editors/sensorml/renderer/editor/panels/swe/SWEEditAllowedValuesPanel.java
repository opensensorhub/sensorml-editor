package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;

public class SWEEditAllowedValuesPanel extends EditSimpleElementPanel{

	private Panel innerValuesPanel;
	private Panel valuesPanel;
	
	private Panel intervalPanel;
	
	private boolean isFirst = true;
	
	public SWEEditAllowedValuesPanel(RNGElement element) {
		super(element,"AllowedValues");
		
		container = new HorizontalPanel();
		valuesPanel = new HorizontalPanel();
		innerValuesPanel = new HorizontalPanel();
		intervalPanel = new HorizontalPanel();
		
		valuesPanel.add(new HTML("["));
		valuesPanel.add(innerValuesPanel);
		valuesPanel.add(new HTML("]"));
		
		container.add(valuesPanel);
		container.add(intervalPanel);
		
		container.addStyleName("allowedValues");
		
		valuesPanel.setVisible(false);
		intervalPanel.setVisible(false);
	}
	
	@Override
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("value")) {
			valuesPanel.setVisible(true);
			if(!isFirst) {
				innerValuesPanel.add(new HTML(" / "));
			} else {
				isFirst = false;
			}
			innerValuesPanel.add(element.getPanel());
		} else if(element.getName().equals("interval")) {
			intervalPanel.setVisible(true);
			intervalPanel.add(element.getPanel());
		}
	}

}
