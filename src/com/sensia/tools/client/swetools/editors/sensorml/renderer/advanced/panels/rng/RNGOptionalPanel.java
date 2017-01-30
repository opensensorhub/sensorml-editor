package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel.SPACING;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RNGOptionalPanel extends AbstractPanel<RNGOptional>{

	private Panel patternContainer;
	private Panel headerPanel;
	private HTML addButton;
	
	public RNGOptionalPanel(final RNGOptional tag,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		container = new FlowPanel();
		patternContainer = new SMLHorizontalPanel(SPACING.RIGHT);
		
		final String label = Utils.findLabel(tag);
		
		addButton = new HTML();
		headerPanel = new SMLHorizontalPanel(SPACING.RIGHT);
		container.add(headerPanel);
		container.add(patternContainer);

		HTML htmlLabel = new HTML(Utils.toNiceLabel(label));
		if(tag.isSelected()) {
			addButton.addStyleName("remove-button");
			addButton.addStyleName("shift-remove");
			headerPanel.add(addButton);
			headerPanel.add(htmlLabel);
		} else {
			addButton.addStyleName("select-add");
			headerPanel.add(htmlLabel);
			headerPanel.add(addButton);
		}
		
		headerPanel.addStyleName("rng-optional-select-label");
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(addButton.getStyleName().contains("select-add")){
					tag.setSelected(true);
				} else {
					tag.setSelected(false);
					//TODO: clear previous input
				}
				
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
				
			}
		});
		
		patternContainer.setVisible(false);
	}
	
	@Override
	public String getName() {
		return "";
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element instanceof EditSectionElementPanel || element instanceof RNGChoicePanel ) {
			headerPanel.addStyleName("rng-disclosure");
		} 
		if(!(element instanceof EditElementPanel)){
			headerPanel.clear();
			headerPanel.add(addButton);
			headerPanel.add(element.getPanel());
		} else {
			patternContainer.setVisible(true);
			patternContainer.add(element.getPanel());
		}
		//patternContainer.add(element.getPanel());
		
	}

	@Override
	protected AbstractPanel<RNGOptional> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
