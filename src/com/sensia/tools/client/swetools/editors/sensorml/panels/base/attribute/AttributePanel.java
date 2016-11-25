package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.ViewValuePanel;

public class AttributePanel extends AbstractPanel<RNGAttribute> {

	public AttributePanel(RNGAttribute tag) {
		super(tag);
		container = new HorizontalPanel();
	}

	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element instanceof ViewValuePanel) {
			((ViewValuePanel) element).setNiceLabel(isNiceLabel);
		} else if(element instanceof EditValuePanel) {
			((EditValuePanel) element).setNiceLabel(isNiceLabel);
		}
		container.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
