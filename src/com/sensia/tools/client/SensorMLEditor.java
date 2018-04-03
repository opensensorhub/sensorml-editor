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
		RootLayoutPanel root = RootLayoutPanel.get();
		root.addStyleName("root-app");
		if (root != null) {
			SensorMLEditor editor = new SensorMLEditor();
			editor.open(root);
		}
	}
	
	private RNGProcessorSML sgmlEditorProcessor;
	
	public SensorMLEditor() {
		//init a processor to handle the parsing of a document
		sgmlEditorProcessor = new RNGProcessorSML();
	}

	public void open(Panel parent) {
		parent.add(getViewer());
	}

	private Widget getViewer(){
		return ViewerPanel.getInstance(sgmlEditorProcessor);
	}
	

	private Widget getEditor(IController controller){
		return new EditorPanel(sgmlEditorProcessor,controller);
	}
}
