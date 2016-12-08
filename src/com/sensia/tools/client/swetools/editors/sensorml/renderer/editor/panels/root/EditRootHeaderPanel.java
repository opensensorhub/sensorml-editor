package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.root;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.root.ViewRootHeaderPanel;

public class EditRootHeaderPanel extends ViewRootHeaderPanel{

	
	public EditRootHeaderPanel() {
	}
	
	@Override
	public void addKeywords(Panel keywordsPanel) {
		this.keywordsPanel.add(keywordsPanel);
	}
}
