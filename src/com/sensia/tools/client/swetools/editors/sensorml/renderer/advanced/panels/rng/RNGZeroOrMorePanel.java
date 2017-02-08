package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RNGZeroOrMorePanel extends AbstractPanel<RNGZeroOrMore>{

	private SMLVerticalPanel patternsContainer;
	private int nbPattern = 0;
	
	public RNGZeroOrMorePanel(final RNGZeroOrMore tag,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		
        container.addStyleName("rng-zeroormore");
        
        // occurence patterns container
		patternsContainer = new SMLVerticalPanel();
		container.add(patternsContainer);
		
		// add more button + label
		SMLHorizontalPanel AddMorePanel = new SMLHorizontalPanel();
		final String label = Utils.findLabel(tag);
		AddMorePanel.add(new HTML(Utils.toNiceLabel(label)));
		HTML addButton = new HTML();
        addButton.addStyleName("add-button");
        AddMorePanel.add(addButton);		
		container.add(AddMorePanel);
		
		addButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
			    // add new pattern instance
				tag.newOccurence();				
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
		RNGZeroOrMorePatternPanel patternPanel = new RNGZeroOrMorePatternPanel(getTag(), nbPattern++,refreshHandler);
		patternPanel.addElement(element);
		patternsContainer.add(patternPanel.getPanel());
	}

	@Override
	protected AbstractPanel<RNGZeroOrMore> newInstance() {
		return null;
	}
}
