package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.position.SMLEditPositionByLocationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.position.SMLEditPositionByPositionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.position.SMLEditPositionByTrajectoryPanel;

public class SMLEditPositionPanel extends EditSectionElementPanel{

	public SMLEditPositionPanel(RNGElement tag, IRefreshHandler refreshHandler) {
		super(tag, refreshHandler);
		
		// if vector => by location
		// if dataRecord => by position
		// if dataArray => by trajectory
	}

	@Override
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("DataArray")) {
			SMLEditPositionByTrajectoryPanel positionPanel =
					new SMLEditPositionByTrajectoryPanel((RNGElement) element.getTag(), refreshHandler);
			
			container.add(positionPanel.getPanel());
		} else if(element.getName().equals("Vector")) {
			SMLEditPositionByLocationPanel positionPanel =
					new SMLEditPositionByLocationPanel((RNGElement) element.getTag(), refreshHandler);
			
			container.add(positionPanel.getPanel());
		} else if(element.getName().equals("DataRecord")) {
			SMLEditPositionByPositionPanel positionPanel =
					new SMLEditPositionByPositionPanel((RNGElement) element.getTag(), refreshHandler);
			
			container.add(positionPanel.getPanel());
		} 
		super.addInnerElement(element);
	}
}
