package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

public class GMLEditIdentifierPanel extends AbstractPanel<RNGElement>{

	protected static final String CODE_SPACE_LABEL = "codeSpace";	
	private Panel valuePanel;
	
	public GMLEditIdentifierPanel(RNGElement element) {
		super(element);
		
		container = new SMLHorizontalPanel();		
		container.add(new HTML("Unique ID:"+SMLEditorConstants.HTML_SPACE));
        valuePanel = new SimplePanel();
		container.add(valuePanel);
	}
	
	@Override
	public void addInnerElement(IPanel element) {
		if (element instanceof EditValuePanel) {
		    ((EditValuePanel) element).setPlaceholderText("Enter URI");
		    valuePanel.add(element.getPanel());
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}

	@Override
	public String getName() {
		return getTag().getName();
	}

}
