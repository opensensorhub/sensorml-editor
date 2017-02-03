package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditSimpleElementPanel extends AbstractPanel<RNGElement>{

	
	public EditSimpleElementPanel(RNGElement element) {
		this(element, null);
	}
	
	public EditSimpleElementPanel(RNGElement element, String label) {
		super(element);		
		container = new SMLHorizontalPanel();
		if (label != null && !label.isEmpty())
		    container.add(new HTML(Utils.toNiceLabel(label)+":"+SMLEditorConstants.HTML_SPACE));
	}
	
	@Override
	public void addInnerElement(IPanel element) {
		container.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return getTag().getName();
	}

}
