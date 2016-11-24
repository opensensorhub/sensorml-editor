package com.sensia.tools.client.swetools.editors.sensorml.panels.document;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EditHeaderDocumentPanel extends HeaderDocumentPanel{

	
	public EditHeaderDocumentPanel() {
	}
	
	@Override
	public void addKeywords(Panel keywordsPanel) {
		this.keywordsPanel.add(keywordsPanel);
	}
}
