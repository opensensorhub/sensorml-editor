package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.PanelHelper;

public class SWEEditDataRecordPanel extends EditSubSectionElementPanel {

	private boolean nameFound;
	private boolean labelFound;
	
	public SWEEditDataRecordPanel(RNGElement element,IRefreshHandler refreshHandler) {
		super(element,refreshHandler);
		nameFound = false;
		labelFound = false;
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("name")){
			nameFound = true;
		} else if(element.getName().equals("label")){
			labelFound = true;
		} else if(element.getName().equalsIgnoreCase("field") && element instanceof EditSubSectionElementPanel 
				&& (labelFound || nameFound)){
			setDetailsPanel(buildAdvancedButton(new AdvancedRendererSML()));
			// take the field name
			((EditSubSectionElementPanel)element).setLabelVisible(false);
			((EditSubSectionElementPanel)element).removeInnerStyle("edit-subsection-element-inner-panel");
		}
		super.addInnerElement(element);
	}
}
