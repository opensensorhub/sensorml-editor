package com.sensia.tools.client.swetools.editors.sensorml.panels.generic;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditKeywordsSimplePanel  extends AbstractPanel<RNGElement>{

	private String name;
	
	private static final String EXTENSION="List";
	
	public EditKeywordsSimplePanel(RNGElement tag){
		super(tag);
		
		name = tag.getName().substring(0, tag.getName().length()-EXTENSION.length());
		name = Utils.toNiceLabel(name)+"s";
	
		container = new HorizontalPanel();
		container.add(new Label(name));
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		//container.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}

}
