package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RNGZeroOrMorePopupPanel extends AbstractPanel<RNGZeroOrMore>{

	private VerticalPanel patternContainer;
	private int nbPattern = 0;
	
	public RNGZeroOrMorePopupPanel(final RNGZeroOrMore tag,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		patternContainer = new VerticalPanel();
		patternContainer.addStyleName("rng-zeroormore-pattern");
		
		final String label = Utils.findLabel(tag);
		HTML addButton = new HTML();
		addButton.addStyleName("rng-optional-select");
		
		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.add(new HTML(Utils.toNiceLabel(label)));
		headerPanel.add(addButton);
		headerPanel.addStyleName("rng-optional-select-label");
		
		container.add(headerPanel);
		container.add(patternContainer);
		
		
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				/*tag.newOccurence();
				
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}*/
				// display into a popup
				List<RNGTag> tags = tag.newOccurence();
				Utils.displaySaveDialogBox(tags, refreshHandler, new VerticalPanel(), getName(), new AdvancedRendererSML());
				
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
		patternContainer.add(patternPanel.getPanel());
	}

	@Override
	protected AbstractPanel<RNGZeroOrMore> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Panel getPatternPanel() {
		return patternContainer;
	}
}
