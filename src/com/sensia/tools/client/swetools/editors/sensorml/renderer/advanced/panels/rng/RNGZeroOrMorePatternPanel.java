package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

public class RNGZeroOrMorePatternPanel extends AbstractPanel<RNGZeroOrMore>{

	private Panel patternContainer;
	private HTML removeButton;
	protected IPanel<?> pattern;
	
	public RNGZeroOrMorePatternPanel(final RNGZeroOrMore tag,final int indexPattern,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		
		container = new SMLHorizontalPanel();
        container.addStyleName("rng-zeroormore-pattern");
        
		patternContainer = new SMLVerticalPanel();
		patternContainer.addStyleName("rng-zeroormore-pattern");
		
		removeButton = new HTML();
		removeButton.addStyleName("remove-button");
        
        removeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// remove pattern from pattern instances
				tag.getPatternInstances().remove(indexPattern);
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});
		
        container.add(removeButton);
        container.add(patternContainer);
	}
	
	@Override
	public String getName() {
		return null;
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
	    patternContainer.add(element.getPanel());
		pattern = element;
	}

	@Override
	protected AbstractPanel<RNGZeroOrMore> newInstance() {
		return null;
	}
	

	public IPanel<?> getIPanelPattern() {
		return pattern;
	}
	
}
