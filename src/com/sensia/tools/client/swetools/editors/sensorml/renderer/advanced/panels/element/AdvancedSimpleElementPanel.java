package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.element;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class AdvancedSimpleElementPanel extends AbstractPanel<RNGElement>{

	
	public AdvancedSimpleElementPanel(RNGElement element) {
		super(element);
		container = new SMLHorizontalPanel();
	}
	
	public AdvancedSimpleElementPanel(RNGElement element,String label) {
		super(element);
		
		container = new SMLHorizontalPanel();
		container.add(new HTML(Utils.toNiceLabel(label)+":"+SMLEditorConstants.HTML_SPACE));
		container.addStyleName("advanced-simple-element-panel");
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
