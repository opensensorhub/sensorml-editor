package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

public class SWEViewIntervalPanel extends EditSimpleElementPanel{

	private Panel valuesPanel;
	
	public SWEViewIntervalPanel(RNGElement element) {
		super(element,"interval");
		
		container = new SMLHorizontalPanel();
		valuesPanel = new SMLHorizontalPanel();
		
		container.add(new HTML("["));
		container.add(valuesPanel);
		container.add(new HTML("]"));
		
		container.addStyleName("interval");
	}
	
	@Override
	public void addInnerElement(IPanel element) {
		if(element.getTag() instanceof RNGData || element.getTag() instanceof RNGValue) {
			String interval = "";
			if(element.getTag() instanceof RNGData) {
				interval = ((RNGData)element.getTag()).getStringValue();
			} else if(element.getTag() instanceof RNGValue) {
				interval = ((RNGValue)element.getTag()).getText();
			}
			String [] split = interval.split(" ");
			valuesPanel.add(new HTML(split[0]+";"+split[1]));
		}
	}

}
