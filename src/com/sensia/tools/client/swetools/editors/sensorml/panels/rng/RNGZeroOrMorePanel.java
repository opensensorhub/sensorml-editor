package com.sensia.tools.client.swetools.editors.sensorml.panels.rng;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RNGZeroOrMorePanel extends AbstractPanel<RNGZeroOrMore>{

	public RNGZeroOrMorePanel(final RNGZeroOrMore tag,final RNGTagVisitor visitor) {
		super(tag);
		
		final String label = Utils.findLabel(tag);
		Label addButton = new Label();
		addButton.addStyleName("rng-optional-select");
		Panel hPanel = new HorizontalPanel();
		hPanel.add(addButton);
		hPanel.add(new Label(label));
		
		container.add(hPanel);
		
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				RNGZeroOrMore cloneZeroOrMore = tag.clone();
				tag.addPatternInstance(cloneZeroOrMore.getChildren());
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
		container.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGZeroOrMore> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
