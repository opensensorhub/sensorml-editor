/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/
package com.sensia.tools.client.swetools.editors.sensorml.panels.source;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;
import com.sensia.tools.client.swetools.editors.sensorml.panels.ViewerPanel.MODE;

/**
 * The Class UrlListSourcePanel is in charge of loading a document from a
 * predefined list of urls.
 */
public class UrlListSourcePanel extends AbstractSourcePanel {

    // default RNG profiles shown in drop down list
    private static Map<String,String> profiles = new HashMap<String,String>();
    static {
        profiles.put("Simple Deployment (Local)", "http://127.0.0.1:8888/sensormleditor/sensorml-relaxng/profiles/Simple_Deployment.rng");
        profiles.put("Simple Deployment", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/profiles/Simple_Deployment.rng");
        profiles.put("Frame Sensor Model (CSM)", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/profiles/RemoteSensing/frame-sensor-model.rng");
        profiles.put("Basic Physical Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/basic_sml/Basic_PhysicalProcess.rng");
        profiles.put("Basic Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/basic_sml/Basic_Process.rng");
        profiles.put("Basic Simple Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/basic_sml/Basic_SimpleProcess.rng");
        profiles.put("Simple Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/sml/SimpleProcess.rng");
        profiles.put("Physical Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/sml/PhysicalProcess.rng");
    }
        
	private SimplePanel container;
	private ListBox profileListBox;
		
	/**
	 * Instantiates a new url source panel.
	 *
	 * @param smlEditorProcessor the sml editor processor
	 * @param editBox the edit box
	 */
	public UrlListSourcePanel(final RNGProcessorSML smlEditorProcessor,final CheckBox editBox) {
		super(smlEditorProcessor,editBox);
		this.container = new SimplePanel();
		profileListBox = new ListBox();        
        profileListBox.addItem("---");
        for(final Entry<String, String> profile: profiles.entrySet()) {
            profileListBox.addItem(profile.getKey(), profile.getValue());
        }
		container.add(profileListBox);
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.source.ISourcePanel#getPanel()
	 */
	@Override
	public Panel getPanel() {
		return container;
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.source.ISourcePanel#parseContent()
	 */
	@Override
	public void parseContent() {
		smlEditorProcessor.setMode(MODE.EDIT);
		smlEditorProcessor.parse(profileListBox.getSelectedValue());
	}
}
