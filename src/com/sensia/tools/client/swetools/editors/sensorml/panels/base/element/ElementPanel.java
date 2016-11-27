package com.sensia.tools.client.swetools.editors.sensorml.panels.base.element;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.ViewAbstractGenericLinePanel;

public class ElementPanel extends AbstractPanel<RNGElement>{

	private Panel namePanel;
	
	public ElementPanel(RNGElement element) {
		super(element);
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGAttribute && element.getName().equals("name")){
			namePanel = new SimplePanel();
			namePanel = element.getPanel();
		} else if(element instanceof ViewAbstractGenericLinePanel){
			ViewAbstractGenericLinePanel cast = (ViewAbstractGenericLinePanel) element;
			if(!cast.isLabeled() && namePanel != null) {
				cast.setLabel(namePanel);
			}
		}
		container.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
