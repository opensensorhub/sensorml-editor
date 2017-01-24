package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;

public class SWEEditFieldPanel extends EditSubSectionElementPanel{

	public SWEEditFieldPanel(RNGElement element) {
		super(element);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("name")) {
			label.setVisible(true);
			innerContainer.addStyleName("edit-subsection-element-inner-panel");
		}
		super.addInnerElement(element);
	}
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
