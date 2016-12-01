package com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.edit;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.DisclosureElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditDisclosureElementPanel extends DisclosureElementPanel{

	public EditDisclosureElementPanel(RNGElement tag, final Widget advancedPanel) {
		super(tag);
		
		container.clear();
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(sectionPanel);
		hPanel.add(advancedPanel);
		container.add(hPanel);
		
		advancedPanel.addStyleName("rng-advanced-button-section");
		
		sectionPanel.addCloseHandler(new CloseHandler<DisclosurePanel>() {
			
			@Override
			public void onClose(CloseEvent<DisclosurePanel> event) {
				advancedPanel.removeStyleName("rng-advanced-button-section");
			}
		});
		
		sectionPanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {

			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				advancedPanel.addStyleName("rng-advanced-button-section");
				
			}
		});
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		//innerContent.add(element.getPanel());
		if(element.getTag() instanceof RNGData<?>) {
			Label label = new Label(Utils.findLabel(getTag()));
			HorizontalPanel hPanel = new HorizontalPanel();
			hPanel.add(label);
			hPanel.add(element.getPanel());
		} else {
			super.addInnerElement(element);
		}
	}
}