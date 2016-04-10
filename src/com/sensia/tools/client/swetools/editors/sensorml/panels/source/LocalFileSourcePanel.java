/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.source;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE;

/**
 * The Class LocalFileSourcePanel allows to load document from local file.
 * It handles a file upload button + textfield to select document from local 
 * storage and load it using the SML processor.
 */
public class LocalFileSourcePanel extends AbstractSourcePanel{

	/** The file upload panel. */
	private FileUploadPanel fileUploadPanel;
	
	/**
	 * Instantiates a new local file source panel.
	 *
	 * @param smlEditorProcessor the sml editor processor
	 * @param editBox the edit box
	 */
	public LocalFileSourcePanel(RNGProcessorSML smlEditorProcessor,final CheckBox editBox) {
		super(smlEditorProcessor,editBox);
		this.fileUploadPanel = new FileUploadPanel();
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.source.ISourcePanel#parseContent()
	 */
	@Override
	public void parseContent() {
		final String fileContent = fileUploadPanel.getContents();
		if(fileContent != null && !fileContent.trim().isEmpty()){
			try {
				//set the default mode for the editor
				smlEditorProcessor.setMode(MODE.VIEW);
				//parse the document corresponding to the selected uploaded file
				smlEditorProcessor.parse(fileUploadPanel.getFileName(), fileContent);
			} catch (Exception e) {
				Window.alert("An error occured while parsing the file. Check that it is a valid XML file");
			}
			checkEditButton();
		} else {
			Window.alert("The content seems empty or invalid");
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.source.ISourcePanel#getPanel()
	 */
	@Override
	public Panel getPanel() {
		return fileUploadPanel.getPanel();
	}

}
