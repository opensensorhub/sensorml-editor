package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RNGOptionalPanel extends AbstractPanel<RNGOptional>{

	private Panel patternContainer;
	private Panel headerPanel;
	private Label addButton;
	
	public RNGOptionalPanel(final RNGOptional tag,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		container = new HorizontalPanel();
		patternContainer = new HorizontalPanel();
		
		final String label = Utils.findLabel(tag);
		
		addButton = new Label();
		headerPanel = new HorizontalPanel();
		container.add(headerPanel);
		container.add(patternContainer);
		
		if(tag.isSelected()) {
			addButton.addStyleName("rng-optional-select-remove");
			headerPanel.add(new Label(label));
			headerPanel.add(addButton);
		} else {
			addButton.addStyleName("rng-optional-select-add");
			headerPanel.add(new Label(label));
			headerPanel.add(addButton);
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
				//ViewerPanel.getInstance(null).redraw();
				
				if(refreshHandler != null) {
					GWT.log("refresh");
					refreshHandler.refresh();
				}
				
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
		if(element instanceof EditSectionElementPanel || element instanceof RNGChoicePanel) {
			headerPanel.addStyleName("rng-disclosure");
		} 
		headerPanel.clear();
		headerPanel.add(addButton);
		headerPanel.add(element.getPanel());
		//patternContainer.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGOptional> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
