package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

public abstract class AbstractEditAttributeHrefPanel extends AbstractPanel<RNGAttribute>{

	public AbstractEditAttributeHrefPanel(RNGAttribute tag) {
		super(tag);
	}
	
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		 if(element.getTag() instanceof RNGData<?>) {
			//((EditValuePanel)valuePanel).setNiceLabel(false);
			container.add(element.getPanel());
		}
	}

	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		return null;
	}
}