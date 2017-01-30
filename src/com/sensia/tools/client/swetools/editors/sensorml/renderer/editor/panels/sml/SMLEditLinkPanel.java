package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel.SPACING;

public class SMLEditLinkPanel extends AbstractPanel<RNGElement>{

	private Panel sourcePanel;
	private Panel destinationPanel;
	
	public SMLEditLinkPanel(RNGElement element) {
		super(element);
		container = new SMLHorizontalPanel(SPACING.RIGHT);
		
		sourcePanel = new SimplePanel();
		destinationPanel = new SimplePanel();
		SimplePanel linkIconPanel = new SimplePanel();
		linkIconPanel.addStyleName("link-icon-panel");
		
		//add line
		container.add(sourcePanel);
		container.add(linkIconPanel);
		container.add(destinationPanel);
	}
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("source")){
			this.sourcePanel.add(element.getPanel());
		} else if(element.getName().equals("destination")){
			this.destinationPanel.add(element.getPanel());
		} else {
			container.add(element.getPanel());
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
