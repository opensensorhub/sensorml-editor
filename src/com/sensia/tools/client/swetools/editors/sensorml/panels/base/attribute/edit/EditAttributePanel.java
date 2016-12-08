package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditAttributePanel extends AbstractPanel<RNGAttribute> {

	public EditAttributePanel(RNGAttribute tag) {
		super(tag);
		container = new HorizontalPanel();
		//container.add(new Label(Utils.findLabel(tag)));
	}

	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		container.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
