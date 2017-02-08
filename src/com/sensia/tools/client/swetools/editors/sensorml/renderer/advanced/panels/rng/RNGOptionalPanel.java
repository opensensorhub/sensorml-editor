package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel.SPACING;

public class RNGOptionalPanel extends AbstractPanel<RNGOptional>{

	private Panel patternContainer;
	private HTML addRemoveButton;
	
	public RNGOptionalPanel(final RNGOptional tag,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		
		container = new SMLHorizontalPanel();
		container.addStyleName("rng-optional");
		
		addRemoveButton = new HTML();
		
		// if selected we show remove button and the selected pattern
		if(tag.isSelected()) {
			addRemoveButton.addStyleName("remove-button");
			container.add(addRemoveButton);
			
			patternContainer = new SMLHorizontalPanel(SPACING.RIGHT);
			patternContainer.addStyleName("rng-optional-pattern");
			container.add(patternContainer);
		} 
		
		// if not selected, show label and add button
		else {
		    String label = Utils.findLabel(tag);
		    HTML htmlLabel = new HTML(Utils.toNiceLabel(label));
			container.add(htmlLabel);
			
			addRemoveButton.addStyleName("add-button");
			container.add(addRemoveButton);
		}
		
		addRemoveButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
			    // toggle selection
				tag.setSelected(!tag.isSelected());				
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}				
			}
		});
	}
	
	@Override
	public String getName() {
		return "optional";
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
	    /*if(element instanceof EditSectionElementPanel || element instanceof AdvancedElementPanel || element instanceof RNGChoicePanel ) {
			headerPanel.addStyleName("rng-disclosure v-align-top");
		}
		if(!(element instanceof EditElementPanel)){
			headerPanel.clear();
			headerPanel.add(addRemoveButton);
			headerPanel.add(element.getPanel());
		} else {
		    headerPanel.addStyleName("rng-disclosure");
		    patternContainer.setVisible(true);
			patternContainer.add(element.getPanel());
		}
		//patternContainer.add(element.getPanel());*/
	    
		if (tag.isSelected()) {
		    patternContainer.add(element.getPanel());
		}
	}

	@Override
	protected AbstractPanel<RNGOptional> newInstance() {
		return null;
	}

}
