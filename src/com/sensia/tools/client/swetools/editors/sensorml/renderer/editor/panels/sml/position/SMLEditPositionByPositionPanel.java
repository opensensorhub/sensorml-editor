package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.position;

import java.util.ArrayList;
import java.util.List;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditVectorPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.ModelHelper;
import com.sensia.tools.client.swetools.editors.sensorml.utils.PanelHelper;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;

public class SMLEditPositionByPositionPanel extends EditSubSectionElementPanel{

	public SMLEditPositionByPositionPanel(RNGElement tag, IRefreshHandler refreshHandler) {
		super(tag, refreshHandler);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("DataRecord")) {
			List<IPanel> vectorPanels = new ArrayList<IPanel>();
			PanelHelper.findPanels(element, "Vector", 0, 10,vectorPanels);
			
			SWEEditVectorPanel locationPanel=null;
			SWEEditVectorPanel orientationPanel=null;
			
			for(IPanel vectorPanel : vectorPanels) {
				// is location?
				if(ModelHelper.isAttributeValue(SMLEditorConstants.LOCATION_DEFINITION, vectorPanel.getTag())) {
					if(vectorPanel instanceof SWEEditVectorPanel) {
						locationPanel = (SWEEditVectorPanel) vectorPanel;
						locationPanel.setHtmlLabel("Location");
					}
				}
				
				// is orientation?
				if(ModelHelper.isAttributeValue(SMLEditorConstants.ORIENTATION_DEFINITION, vectorPanel.getTag())) {
					if(vectorPanel instanceof SWEEditVectorPanel) {
						orientationPanel = (SWEEditVectorPanel) vectorPanel;
						orientationPanel.setHtmlLabel("Orientation");
					}
				}	
			}
			
			if(locationPanel != null && orientationPanel != null) {
				List<RNGElement> tags = ModelHelper.findTags(SMLEditorConstants.SWE_NS_2, "value", orientationPanel.getTag());
				locationPanel.setHeadingCoordinateElt(tags.get(0));
			}
		}
		super.addInnerElement(element);
	}
}
