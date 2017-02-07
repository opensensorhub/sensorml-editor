package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.root;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.root.ViewRootHeaderPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

public class EditRootHeaderPanel extends ViewRootHeaderPanel{

	private Panel lineKeywords;
	private int nbKeywords=0;
	
	public EditRootHeaderPanel() {
		super();
		keywordsPanel.clear();
		/*lineKeywords = new SMLHorizontalPanel();
		lineKeywords.addStyleName("keywords-panel");
		lineKeywords.add(new HTML("Keywords:"+SMLEditorConstants.HTML_SPACE));
		keywordsPanel.add(lineKeywords);*/
	}
	
	@Override
	public void addKeywords(IPanel keywordsPanel) {
		/*this.lineKeywords.add(keywordsPanel.getPanel());
		this.keywordsPanel.setVisible(true);
		nbKeywords++;
		// display only 3 elements/line
		if(nbKeywords%3 == 0) {
			lineKeywords = new SMLHorizontalPanel();
			this.keywordsPanel.add(lineKeywords);
			nbKeywords = 0;
		}*/
	    this.keywordsPanel.add(keywordsPanel.getPanel());
	    this.keywordsPanel.setVisible(true);
	}
	
}
