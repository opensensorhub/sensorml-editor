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
			innerContainer.addStyleName("edit-subsection-element-inner-panel");
		} else if(element.getName().equals("field") && (labelFound || nameFound)){
			// take the field name
			((EditSubSectionElementPanel)element).setLabelVisible(false);
		}
		super.addInnerElement(element);
	}
}
