package com.sensia.tools.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.gwt.relaxNG.RNGParser;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;
import com.sensia.tools.client.swetools.editors.sensorml.panels.CenterPanel;

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
	
	private Widget centerPanel;
	
	public SensorMLEditor() {
		//init a processor to handle the parsing of a document
		RNGProcessorSML sgmlEditorProcessor = new RNGProcessorSML();
		initPanels(sgmlEditorProcessor);
	}

	private void initPanels(final RNGProcessorSML sgmlEditorProcessor) {
		initCenterPanel(sgmlEditorProcessor);
	}
	
	public void open(Panel parent) {
		parent.add(getViewer());
	}

	private Widget getViewer(){
		// Create a Dock Panel
	    DockPanel dock = new DockPanel();
	    dock.setStyleName("cw-DockPanel");
	    dock.setSpacing(4);
	    dock.setHorizontalAlignment(DockPanel.ALIGN_LEFT);
	    // Add text all around
	    
	    //dock.add(navigationPanel, DockPanel.WEST);
	    dock.add(centerPanel, DockPanel.CENTER);

	    // Return the content
	    dock.ensureDebugId("cwDockPanel");
	    return dock;
	}
	

	//------ INIT DIFFERENT PARTS -----//
	private void initCenterPanel(final RNGProcessorSML sgmlEditorProcessor) {
		centerPanel = new CenterPanel(sgmlEditorProcessor);
	}
}
