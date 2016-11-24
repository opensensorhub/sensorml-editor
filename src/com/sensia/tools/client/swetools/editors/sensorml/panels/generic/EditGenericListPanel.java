package com.sensia.tools.client.swetools.editors.sensorml.panels.generic;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditGenericListPanel  extends AbstractPanel<RNGElement>{

	private static final String EXTENSION="List";
	public EditGenericListPanel(RNGElement tag){
		super(tag);
		
		String name = getName().substring(0, getName().length()-EXTENSION.length());
		name = Utils.toNiceLabel(name);
	
		container = new HorizontalPanel();
		container.add(new Label(name+" (...)"));
	}
	
	@Override
	public String getName() {
		return getTag().getName();
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
