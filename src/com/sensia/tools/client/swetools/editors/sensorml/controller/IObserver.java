package com.sensia.tools.client.swetools.editors.sensorml.controller;

import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public interface IObserver {
    
	void update(Observable model, Object hint);
  
	/**
	 * Callback the tree of created Widgets .
	 *
	 * @param topElement the top element
	 */
	void parseDone(ISensorWidget topElement);
}