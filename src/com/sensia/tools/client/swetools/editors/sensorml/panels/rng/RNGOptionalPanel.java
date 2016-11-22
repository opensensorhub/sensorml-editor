package com.sensia.tools.client.swetools.editors.sensorml.panels.rng;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RNGOptionalPanel extends AbstractPanel<RNGOptional>{

	private CheckBox checkbox;
	private Panel patternContainer;
	
	public RNGOptionalPanel(final RNGOptional tag,final RNGTagVisitor visitor) {
		super(tag);
		patternContainer = new VerticalPanel();
		
		final String label = Utils.findLabel(tag);
		checkbox = new CheckBox(label);
		container.add(checkbox);
		container.add(patternContainer);
		
		checkbox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				patternContainer.clear();
				tag.setSelected(checkbox.getValue());
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
		patternContainer.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGOptional> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
