package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;

public class SWEEditDataRecordPanel extends EditSubSectionElementPanel {

	public SWEEditDataRecordPanel(RNGElement element,IRefreshHandler refreshHandler) {
		super(element,refreshHandler);
		setDataTypeName(true);
	}
}
