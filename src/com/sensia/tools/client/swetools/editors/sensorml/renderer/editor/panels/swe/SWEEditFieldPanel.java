package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;

public class SWEEditFieldPanel extends EditSubSectionElementPanel{

	public SWEEditFieldPanel(RNGElement element,IRefreshHandler refreshHandler) {
		super(element,refreshHandler);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("name")) {
			namedlabel.setVisible(true);
			innerContainer.addStyleName("edit-subsection-element-inner-panel");
			detailsPanel.add(buildAdvancedButton(new AdvancedRendererSML()));
			detailsPanel.setVisible(true);
		}
		super.addInnerElement(element);
	}
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
