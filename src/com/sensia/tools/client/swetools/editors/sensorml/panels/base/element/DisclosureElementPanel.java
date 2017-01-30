package com.sensia.tools.client.swetools.editors.sensorml.panels.base.element;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

public class DisclosureElementPanel extends AbstractPanel<RNGElement>{

	protected Panel innerContent;
	protected DisclosurePanel sectionPanel;
	
	public DisclosureElementPanel(RNGElement tag) {
		this(tag,null);
	}
	
	public DisclosureElementPanel(RNGElement tag,IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		innerContent = new SMLVerticalPanel();
		
		sectionPanel = new DisclosurePanel("");
		sectionPanel.setAnimationEnabled(true);
		sectionPanel.setOpen(true);
		sectionPanel.add(innerContent);
		sectionPanel.addStyleName("section-panel");
		//sectionPanel.getContent().addStyleName("disclosure-generic-content-panel");
		
		container.add(sectionPanel);
		container.addStyleName("disclosure-generic disclosure-border");
		
		innerContent.addStyleName("disclosure-generic-content-panel");
		
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		innerContent.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
