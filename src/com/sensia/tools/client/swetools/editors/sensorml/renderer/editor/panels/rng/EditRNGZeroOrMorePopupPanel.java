package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.rng;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditRNGZeroOrMorePopupPanel extends AbstractPanel<RNGZeroOrMore>{

	private SMLVerticalPanel patternContainer;
	private int nbPattern = 0;
	
	private String label = "zeroOrMore";
	
	public EditRNGZeroOrMorePopupPanel(final RNGZeroOrMore tag,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		patternContainer = new SMLVerticalPanel();
		patternContainer.addStyleName("rng-zeroormore-pattern");
		
		label = Utils.findLabel(tag);
		HTML addButton = new HTML();
		addButton.addStyleName("rng-optional-select");
		
		SMLHorizontalPanel headerPanel = new SMLHorizontalPanel();
		headerPanel.add(new HTML(Utils.toNiceLabel(label)));
		headerPanel.add(addButton);
		headerPanel.addStyleName("v-align-middle");
		
		container.add(patternContainer);
		container.add(headerPanel);
		
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				/*tag.newOccurence();
				
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}*/
				// display into a popup
				List<RNGTag> tags = tag.newOccurence();
				Utils.displaySaveDialogBox(tags, refreshHandler, new SMLVerticalPanel(), getName(), new AdvancedRendererSML());
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
				
			}
		});
	}
	
	@Override
	public String getName() {
		return label;
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		patternContainer.add(element.getPanel());
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
