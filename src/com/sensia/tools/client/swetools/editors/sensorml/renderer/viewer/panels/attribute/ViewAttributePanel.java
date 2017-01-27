package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

public class ViewAttributePanel extends AbstractPanel<RNGAttribute> {

	protected ViewValuePanel valuePanel;
	
	public ViewAttributePanel(RNGAttribute tag) {
		super(tag);
		container = new SMLHorizontalPanel();
	}

	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		container.add(element.getPanel());
		setValueNiceLabel(element, isNiceLabel);
		if(element instanceof ViewValuePanel) {
			valuePanel = (ViewValuePanel) element;
		}
	}

	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
