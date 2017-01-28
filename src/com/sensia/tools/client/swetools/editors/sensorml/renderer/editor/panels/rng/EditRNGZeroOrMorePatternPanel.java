package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.rng;

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
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.element.AdvancedSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGZeroOrMorePatternPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditRNGZeroOrMorePatternPanel extends RNGZeroOrMorePatternPanel{

	private Panel patternContainer;
	private HTML removeButton;
	
	public EditRNGZeroOrMorePatternPanel(final RNGZeroOrMore tag,final int indexPattern,final IRefreshHandler refreshHandler) {
		super(tag,indexPattern,refreshHandler);
		container = new HorizontalPanel();
		container.addStyleName("edit-rng-zeroormore-pattern");
		
		patternContainer = new VerticalPanel();
		patternContainer.addStyleName("edit-rng-zeroormore-pattern");
		
		removeButton = new HTML();
		removeButton.addStyleName("rng-optional-select-remove");
		removeButton.addStyleName("rng-shift-remove");
	
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
	
	@Override
	protected AbstractPanel<RNGZeroOrMore> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
