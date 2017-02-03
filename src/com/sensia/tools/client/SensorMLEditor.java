package com.sensia.tools.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.gwt.relaxNG.RNGParser;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;
import com.sensia.tools.client.swetools.editors.sensorml.controller.IController;
import com.sensia.tools.client.swetools.editors.sensorml.panels.EditorPanel;
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
		
		RootLayoutPanel root = RootLayoutPanel.get();
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
		HorizontalSplitPanel p = new HorizontalSplitPanel();
		p.setLeftWidget(getViewer());
	    //p.setRightWidget(getEditor(controller));
	    p.setSplitPosition("1200px");
		parent.add(p);
	}

	private Widget getViewer(){
		//viewerPanel = new ViewerPanel(sgmlEditorProcessor);
		viewerPanel = ViewerPanel.getInstance(sgmlEditorProcessor);
		sgmlEditorProcessor.setRefreshHandler(viewerPanel);
		// Create a Dock Panel
	    DockPanel dock = new DockPanel();
	    dock.setStyleName("cw-DockPanel");
	    dock.setSpacing(4);
	    dock.setHorizontalAlignment(DockPanel.ALIGN_LEFT);
	    // Add text all around
	    
	    //dock.add(navigationPanel, DockPanel.WEST);
	    dock.add(viewerPanel, DockPanel.CENTER);

	    // Return the content
	    dock.ensureDebugId("cwDockPanel");
	    return dock;
	}
	

	private Widget getEditor(IController controller){
		return new EditorPanel(sgmlEditorProcessor,controller);
	}
}
