package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.gmd;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.element.AdvancedElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class GMDAdvancedObjectPanel extends AdvancedElementPanel{

	public GMDAdvancedObjectPanel(RNGElement element) {
		super(element,element.getName().replaceAll(".*_",  ""),null);
	}
}