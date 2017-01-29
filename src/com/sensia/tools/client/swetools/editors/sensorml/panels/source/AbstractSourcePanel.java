/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.source;

import com.google.gwt.user.client.ui.CheckBox;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;

/**
 * The Class AbstractSourcePanel is in charge of loading document.
 */
public abstract class AbstractSourcePanel implements ISourcePanel{

	/** The sml editor processor. */
	protected RNGProcessorSML smlEditorProcessor;
	
	/** The edit. */
	private CheckBox edit;
	
	/**
	 * Instantiates a new abstract source panel.
	 *
	 * @param smlEditorProcessor the sml editor processor
	 * @param edit the edit
	 */
	public AbstractSourcePanel(final RNGProcessorSML smlEditorProcessor,final CheckBox edit) {
		this.smlEditorProcessor = smlEditorProcessor;
		this.edit = edit;
	}
	
	/**
	 * Check edit button.
	 */
	public void checkEditButton() {
		if(edit != null) {
			edit.setVisible(true);
			edit.setValue(true);
		}
	}
}
