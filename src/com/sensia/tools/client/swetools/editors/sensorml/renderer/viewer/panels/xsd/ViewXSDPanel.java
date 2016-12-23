package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd;

import com.sensia.relaxNG.RNGData;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDPanel;

public class ViewXSDPanel<T extends RNGData<?>> extends XSDPanel<T> {

	public ViewXSDPanel(final T data, final int length, final String allowedChars, IRefreshHandler refreshHandler) {
		super(data,length, allowedChars, refreshHandler);
		
		textBox.setEnabled(false);
	}
}
