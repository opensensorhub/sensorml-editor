package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;

public class SMLEditKeywordListPanel extends EditElementPanel{

	public SMLEditKeywordListPanel(RNGElement tag) {
		super(tag,null);
		container = new HorizontalPanel();
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		container.add(element.getPanel());
		container.add(new HTML(SMLEditorConstants.HTML_SPACE));
	}
}
