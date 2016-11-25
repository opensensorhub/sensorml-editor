package com.sensia.tools.client.swetools.editors.sensorml.panels.rng;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.ViewerPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RNGOptionalPanel extends AbstractPanel<RNGOptional>{

	private Panel patternContainer;
	
	public RNGOptionalPanel(final RNGOptional tag) {
		super(tag);
		patternContainer = new VerticalPanel();
		
		final String label = Utils.findLabel(tag);
		
		final Label addButton = new Label();
		Panel hPanel = new HorizontalPanel();
		hPanel.add(addButton);
		hPanel.add(new Label(label));
		
		container.add(hPanel);
		container.add(patternContainer);
		
		if(tag.isSelected()) {
			addButton.addStyleName("rng-optional-select-remove");
		} else {
			addButton.addStyleName("rng-optional-select-add");
		}
		
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(addButton.getStyleName().contains("rng-optional-select-add")){
					tag.setSelected(true);
				} else {
					tag.setSelected(false);
				}
				
				/*RNGRendererSML newRenderer = new RNGRendererSML();
				for(RNGTag child : tag.getChildren()) {
					child.accept(newRenderer);
				}
				patternContainer.add(newRenderer.getRoot().getPanel());*/
				//TODO: use MVC or MVP to update the view
				ViewerPanel.getInstance(null).redraw();
				
			}
		});
		
		patternContainer.addStyleName("rng-optional-pattern");
	}
	
	@Override
	public String getName() {
		return "";
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		patternContainer.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGOptional> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
