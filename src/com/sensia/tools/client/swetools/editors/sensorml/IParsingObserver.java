package com.sensia.tools.client.swetools.editors.sensorml;

import java.io.Serializable;

import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public interface IParsingObserver extends Serializable{

	/**
	 * Callback the tree of created Widgets .
	 * @param root The root element of the Tree-based structure
	 */
	void parseDone(ISensorWidget topElement);
}
