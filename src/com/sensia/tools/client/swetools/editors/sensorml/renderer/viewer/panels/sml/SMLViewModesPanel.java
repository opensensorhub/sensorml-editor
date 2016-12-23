package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.element.ViewSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class SMLViewModesPanel extends ViewSectionElementPanel{

	public SMLViewModesPanel(RNGElement tag, IRefreshHandler refreshHandler) {
		super(tag, refreshHandler);
	}
	
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("ModeChoice")) {
			RNGElement modeChoiceElt = (RNGElement) element.getTag();
			RNGAttribute idAttribute = modeChoiceElt.getChildAttribute("id");
			if(idAttribute != null) {
				labelPanel.clear();
				labelPanel.add(new HTML(Utils.toNiceLabel(idAttribute.getChildValueText())));
			}
		}

		super.addInnerElement(element);
	}
}
