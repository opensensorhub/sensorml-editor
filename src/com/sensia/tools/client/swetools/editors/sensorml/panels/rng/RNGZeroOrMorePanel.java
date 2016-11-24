package com.sensia.tools.client.swetools.editors.sensorml.panels.rng;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.ViewerPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RNGZeroOrMorePanel extends AbstractPanel<RNGZeroOrMore>{

	private Panel patternContainer;
	
	public RNGZeroOrMorePanel(final RNGZeroOrMore tag) {
		super(tag);
		
		patternContainer = new VerticalPanel();
		
		final String label = Utils.findLabel(tag);
		Label addButton = new Label();
		addButton.addStyleName("rng-optional-select");
		Panel hPanel = new HorizontalPanel();
		hPanel.add(addButton);
		hPanel.add(new Label(label));
		
		container.add(hPanel);
		container.add(patternContainer);
		
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				//RNGRendererSML newRenderer = new RNGRendererSML();
				tag.newOccurence();
				//container.add(newRenderer.getRoot().getPanel());
				//TODO: use MVC or MVP to update the view
				ViewerPanel.getInstance(null).redraw();
			}
		});
	}
	
	@Override
	public String getName() {
		return "";
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		patternContainer.add(element.getPanel());
		patternContainer.add(new Label(element.getName()));
	}

	@Override
	protected AbstractPanel<RNGZeroOrMore> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
