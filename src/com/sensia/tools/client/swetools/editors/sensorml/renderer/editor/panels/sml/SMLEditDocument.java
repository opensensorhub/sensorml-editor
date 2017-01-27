package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel.SPACING;

public class SMLEditDocument extends AbstractPanel<RNGElement>{

	private Panel attributesPanel;
	private Panel linkPanel;
	private Panel descriptionPanel;
	
	public SMLEditDocument(RNGElement tag) {
		super(tag);
		attributesPanel = new SMLHorizontalPanel();
		linkPanel = new SimplePanel();
		descriptionPanel = new SimplePanel();
		
		container = new SMLHorizontalPanel(SPACING.RIGHT);
		container.add(linkPanel);
		container.add(descriptionPanel);
		container.add(attributesPanel);
		
		container.addStyleName("panel-document");
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGAttribute) {
			attributesPanel.add(element.getPanel());
		} else if(element.getName().equals("description")){
			descriptionPanel.add(element.getPanel());
		} else if(element.getName().equals("URL")){
			linkPanel.add(element.getPanel());
		}
		
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}

}
