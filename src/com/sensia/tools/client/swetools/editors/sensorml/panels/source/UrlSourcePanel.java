/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/
package com.sensia.tools.client.swetools.editors.sensorml.panels.source;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;
import com.sensia.tools.client.swetools.editors.sensorml.panels.ViewerPanel.MODE;

/**
 * The Class UrlSourcePanel is in charge of load a document from an url.
 */
public class UrlSourcePanel extends AbstractSourcePanel {

	
	/** The container. */
	private SimplePanel container;
	
	/** The url box. */
	private TextBox urlBox;
	
	/**
	 * Instantiates a new url source panel.
	 *
	 * @param smlEditorProcessor the sml editor processor
	 * @param editBox the edit box
	 */
	public UrlSourcePanel(final RNGProcessorSML smlEditorProcessor,final CheckBox editBox) {
		super(smlEditorProcessor,editBox);
		this.container = new SimplePanel();
		this.urlBox = new TextBox();
		
		urlBox.setStyleName("file-download");
		container.add(urlBox);
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
		smlEditorProcessor.setMode(MODE.VIEW);
		smlEditorProcessor.parse(urlBox.getText());
	}
}
