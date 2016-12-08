package com.sensia.tools.client.swetools.editors.sensorml.panels.gml.edit;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class GMLEditDescriptionPanel extends AbstractPanel<RNGElement>{

	
	public GMLEditDescriptionPanel(RNGElement element) {
		super(element);
		
		container = new HorizontalPanel();
		container.add(new HTML(Utils.findLabel(element.getParent())+":"+SMLEditorConstants.HTML_SPACE));
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
