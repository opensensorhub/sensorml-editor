package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class SMLEditModePanel extends EditSectionElementPanel{

	public SMLEditModePanel(RNGElement tag, IRefreshHandler refreshHandler) {
		super(tag, refreshHandler);
	}
	
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("id") && element.getTag() instanceof RNGAttribute) {
			RNGAttribute idAtt = (RNGAttribute) element.getTag();
			if(idAtt != null) {
				labelPanel.clear();
				labelPanel.add(new HTML(Utils.toNiceLabel(idAtt.getChildValueText())));
			}
		} else if(element.getName().equals("characteristics") || 
				element.getName().equals("configuration")) {
			EditSubSectionElementPanel subSection = new EditSubSectionElementPanel((RNGElement) element.getTag());
			List<IPanel<? extends RNGTag>> children = element.getElements();
			
			for(IPanel<?> child : children) {
				subSection.addElement(child);
			}
			//subSection.setLabelVisible(true);
			super.addInnerElement(subSection);
		} else {
			super.addInnerElement(element);
		}
	}
}
