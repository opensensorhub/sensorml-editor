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
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.ViewerPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.edit.EditSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RNGZeroOrMorePanel extends AbstractPanel<RNGZeroOrMore>{

	private VerticalPanel patternContainer;
	private int nbPattern = 0;
	private IRefreshHandler refreshHandler;
	
	public RNGZeroOrMorePanel(final RNGZeroOrMore tag,final IRefreshHandler refreshHandler) {
		super(tag);
		this.refreshHandler = refreshHandler;
		patternContainer = new VerticalPanel();
		patternContainer.addStyleName("rng-zeroormore-pattern");
		
		final String label = Utils.findLabel(tag);
		Label addButton = new Label();
		addButton.addStyleName("rng-optional-select");
		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.add(addButton);
		headerPanel.add(new Label(label));
		
		container.add(headerPanel);
		container.add(patternContainer);
		
		
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				//RNGRendererSML newRenderer = new RNGRendererSML();
				tag.newOccurence();
				//container.add(newRenderer.getRoot().getPanel());
				//TODO: use MVC or MVP to update the view
				//ViewerPanel.getInstance(null).redraw();
				
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});
	}
	
	@Override
	public String getName() {
		return "ZeroOrMore";
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		//patternContainer.add(element.getPanel());
		RNGZeroOrMorePatternPanel patternPanel = new RNGZeroOrMorePatternPanel(getTag(), nbPattern++,refreshHandler);
		patternPanel.addElement(element);
		
		patternContainer.add(patternPanel.getPanel());
	}

	@Override
	protected AbstractPanel<RNGZeroOrMore> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
