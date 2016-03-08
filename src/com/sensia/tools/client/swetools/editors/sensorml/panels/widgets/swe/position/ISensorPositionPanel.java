package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public interface ISensorPositionPanel {

	Panel getPanel();
	
	void build(ISensorWidget element);
}
