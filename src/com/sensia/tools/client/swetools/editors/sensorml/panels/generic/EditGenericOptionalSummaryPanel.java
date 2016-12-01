package com.sensia.tools.client.swetools.editors.sensorml.panels.generic;

import com.google.gwt.user.client.ui.Label;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.DynamicDisclosureElementPanel;

public class EditGenericOptionalSummaryPanel<T extends RNGTag> extends AbstractPanel<T>{
	
	public EditGenericOptionalSummaryPanel(final RNGOptional tag,final IRefreshHandler refreshHandler) {
		
	}
	@Override
	public String getName() {
		return "";
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		/*if(element instanceof DisclosureElementPanel) {
			
			final Label removeButton = new Label();
			removeButton.addStyleName("rng-optional-select-remove");
			
			Label advancedPane = new Label("...");
			((DisclosureElementPanel)element).addToHeader(advancedPane);
			((DisclosureElementPanel)element).addToHeader(removeButton);
		}*/
		container.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<T> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
