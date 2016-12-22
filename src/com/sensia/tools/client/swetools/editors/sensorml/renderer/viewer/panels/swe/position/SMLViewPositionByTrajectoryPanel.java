package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.position;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.map.GenericLineMap;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.position.SMLEditPositionByTrajectoryPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class SMLViewPositionByTrajectoryPanel extends SMLEditPositionByTrajectoryPanel{

	public SMLViewPositionByTrajectoryPanel(RNGElement tag, IRefreshHandler refreshHandler) {
		super(tag, refreshHandler);
	}
	
	protected void createDialog(final GenericLineMap lineMap) {
		Utils.displayDialogBox(lineMap.getPanel(), "Line position");
	}
}
