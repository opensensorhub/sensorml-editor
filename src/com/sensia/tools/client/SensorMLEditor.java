package com.sensia.tools.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.gwt.relaxNG.RNGParser;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;
import com.sensia.tools.client.swetools.editors.sensorml.panels.ViewerPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SensorMLEditor implements EntryPoint {


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RNGParser.clearCache();
		
		RootPanel root = RootPanel.get("editor-area");
		if (root != null) {
			SensorMLEditor editor = new SensorMLEditor();
			editor.open(root);
		}
	}
	
	private ViewerPanel viewerPanel;
	private RNGProcessorSML sgmlEditorProcessor;
	
	public SensorMLEditor() {
		//init a processor to handle the parsing of a document
		sgmlEditorProcessor = new RNGProcessorSML();
	}

	public void open(Panel parent) {
		LayoutPanel p = new LayoutPanel();
		parent.add(getViewer());
	}

	private Widget getViewer(){
		//viewerPanel = new ViewerPanel(sgmlEditorProcessor);
		viewerPanel = ViewerPanel.getInstance(sgmlEditorProcessor);
		sgmlEditorProcessor.setRefreshHandler(viewerPanel);
	    return viewerPanel;
	}
}
