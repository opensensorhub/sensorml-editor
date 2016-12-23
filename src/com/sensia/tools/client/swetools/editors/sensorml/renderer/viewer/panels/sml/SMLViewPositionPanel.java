package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditPositionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.position.SMLViewPositionByTrajectoryPanel;

public class SMLViewPositionPanel extends SMLEditPositionPanel{

	public SMLViewPositionPanel(RNGElement tag, IRefreshHandler refreshHandler) {
		super(tag, refreshHandler);
		advancedButtonPanel.setVisible(false);
		// if vector => by location
		// if dataRecord => by position
		// if dataArray => by trajectory
	}

	@Override
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("DataArray")) {
			SMLViewPositionByTrajectoryPanel positionPanel =
					new SMLViewPositionByTrajectoryPanel((RNGElement) element.getTag(), refreshHandler);
			positionPanel.addElement(element);
			super.addInnerElement(positionPanel);
		} else {
			super.addInnerElement(element);
		}
	}
}
