package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.rng;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

public class EditRNGChoicePatternPanel extends AbstractPanel<RNGChoice>{

	private Panel patternContainer;
	private HTML removeButton;
	protected IPanel pattern;
	
	public EditRNGChoicePatternPanel(final RNGChoice tag,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		container = new SMLHorizontalPanel();
		container.addStyleName("edit-rng-choice-pattern");
		
		patternContainer = new FlowPanel();
		patternContainer.addStyleName("edit-rng-choice-pattern");
		
		removeButton = new HTML();
		removeButton.addStyleName("rng-optional-select-remove");
		removeButton.addStyleName("rng-shift-remove");
		
		container.add(patternContainer);
		
		removeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				// remove pattern from pattern instances
				tag.setSelectedIndex(-1);
				
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});
		
		//container.addStyleName("rng-optional-pattern");
		container.add(removeButton);
		container.add(patternContainer);
		
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		patternContainer.add(element.getPanel());
		pattern = element;
	}
	
	protected AbstractPanel<RNGChoice> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public IPanel getIPanelPattern() {
		return pattern;
	}
	
}
