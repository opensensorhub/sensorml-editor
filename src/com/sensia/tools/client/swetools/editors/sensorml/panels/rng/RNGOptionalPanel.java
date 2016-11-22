package com.sensia.tools.client.swetools.editors.sensorml.panels.rng;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RNGOptionalPanel extends AbstractPanel<RNGOptional>{

	private Panel patternContainer;
	
	public RNGOptionalPanel(final RNGOptional tag,final RNGTagVisitor visitor) {
		super(tag);
		patternContainer = new VerticalPanel();
		
		final String label = Utils.findLabel(tag);
		
		final Label addButton = new Label();
		addButton.addStyleName("rng-optional-select-add");
		Panel hPanel = new HorizontalPanel();
		hPanel.add(addButton);
		hPanel.add(new Label(label));
		
		container.add(hPanel);
		container.add(patternContainer);
		
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				patternContainer.clear();
				if(addButton.getStyleName().contains("rng-optional-select-add")) {
					addButton.removeStyleName("rng-optional-select-add");
					addButton.addStyleName("rng-optional-select-remove");
					tag.setSelected(true);
				} else {
					addButton.removeStyleName("rng-optional-select-remove");
					addButton.addStyleName("rng-optional-select-add");
					tag.setSelected(false);
				}
				
				tag.accept(visitor);
			}
		});
	}
	
	@Override
	public String getName() {
		return "";
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		GWT.log("Into optional");
		patternContainer.add(element.getPanel());
		patternContainer.add(new Label("Into Optional"));
	}

	@Override
	protected AbstractPanel<RNGOptional> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
