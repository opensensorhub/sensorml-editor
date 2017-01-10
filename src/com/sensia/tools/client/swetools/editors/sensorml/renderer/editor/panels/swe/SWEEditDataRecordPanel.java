package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.PanelHelper;

public class SWEEditDataRecordPanel extends EditSubSectionElementPanel {

	private boolean nameFound;
	private boolean labelFound;
	
	public SWEEditDataRecordPanel(RNGElement element) {
		super(element);
		nameFound = false;
		labelFound = false;
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("name")){
			nameFound = true;
		} else if(element.getName().equals("label")){
			labelFound = true;
		} else if(element.getName().equals("field") && !labelFound && !nameFound && !element.isInLine()){
			// take the field name
			IPanel attPanel = PanelHelper.findPanel(element, "name", 0, 1);
			if(attPanel != null) {
				label.clear();
				label.add(attPanel.getPanel());
			}
		}
		super.addInnerElement(element);
	}
}
