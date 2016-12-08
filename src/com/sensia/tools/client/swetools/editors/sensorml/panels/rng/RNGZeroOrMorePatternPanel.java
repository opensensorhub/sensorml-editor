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
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.edit.EditSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RNGZeroOrMorePatternPanel extends AbstractPanel<RNGZeroOrMore>{

	private Panel patternContainer;
	private Label removeButton;
	
	public RNGZeroOrMorePatternPanel(final RNGZeroOrMore tag,final int indexPattern,final IRefreshHandler refreshHandler) {
		super(tag);
		
		patternContainer = new VerticalPanel();
		
		final String label = Utils.findLabel(tag);
		
		removeButton = new Label();
		Panel hPanel = new HorizontalPanel();
		hPanel.add(removeButton);
		hPanel.add(new Label(label));
		
		container.add(hPanel);
		container.add(patternContainer);
		
		removeButton.addStyleName("rng-optional-select-remove");
		
		removeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				// remove pattern from pattern instances
				tag.getPatternInstances().remove(indexPattern);
				
				//TODO: use MVC or MVP to update the view
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});
		
		patternContainer.addStyleName("rng-optional-pattern");
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element instanceof EditSectionElementPanel) {
			
			element.getPanel().removeStyleName("disclosure-noborder");
			element.getPanel().addStyleName("section-panel disclosure-border");
			container.clear();
			Panel hPanel = new HorizontalPanel();
			hPanel.add(removeButton);
			hPanel.add(element.getPanel());
			
			hPanel.addStyleName("rng-disclosure");
			container.add(hPanel);
		} else {
			patternContainer.add(element.getPanel());
		}
	}

	@Override
	protected AbstractPanel<RNGZeroOrMore> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
