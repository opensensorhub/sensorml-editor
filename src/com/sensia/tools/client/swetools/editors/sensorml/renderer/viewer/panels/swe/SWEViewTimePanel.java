package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.ValueGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.line.EditValueGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.line.ViewValueGenericLinePanel;

// label
// uom
// value
public class SWEViewTimePanel extends ViewValueGenericLinePanel{

	
	protected Panel uomPanel;
	
	public SWEViewTimePanel(RNGElement tag) {
		super(tag);
		uomPanel = new SimplePanel();
		afterDotsPanel.add(uomPanel);
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		 if(element.getName().equals("uom")){
			uomPanel.add(element.getPanel());
		} else {
			super.addInnerElement(element);
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}

