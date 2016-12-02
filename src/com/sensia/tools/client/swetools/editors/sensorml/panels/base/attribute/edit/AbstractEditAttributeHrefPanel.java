package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public abstract class AbstractEditAttributeHrefPanel extends AbstractPanel<RNGAttribute> {

	public AbstractEditAttributeHrefPanel(RNGAttribute tag) {
		super(tag);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if (element.getTag() instanceof RNGData<?>) {
			// ((EditValuePanel)valuePanel).setNiceLabel(false);
			Panel hPanel = new HorizontalPanel();
			hPanel.add(new HTML(Utils.findLabel(getTag())));
			hPanel.add(element.getPanel());
			container.add(hPanel);
		}
	}

	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		return null;
	}
}