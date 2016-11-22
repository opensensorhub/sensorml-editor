package com.sensia.tools.client.swetools.editors.sensorml.panels.gmd;

import com.google.gwt.user.client.ui.Anchor;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

public class GMDUrl extends AbstractPanel<RNGElement>{

	private Anchor anchorHref;
	private IPanel<? extends RNGTag> valuePanel;
	
	public GMDUrl(RNGElement tag) {
		super(tag);
		
		anchorHref = new Anchor();
		anchorHref.setText("Link");
		anchorHref.setHref("");
		anchorHref.addStyleName("xlink");
		//opens in a new tab
		anchorHref.setTarget("_blank");
		
		container.add(anchorHref);
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGValue) {
			RNGValue value = (RNGValue) element.getTag();
			anchorHref.setHref(value.getText());
			valuePanel = (IPanel<RNGValue>) element;
		} else if(element.getTag() instanceof RNGData<?>) {
			RNGData<?> value = (RNGData<?>) element.getTag();
			anchorHref.setHref(value.getStringValue());
			valuePanel = (IPanel<RNGData<?>>) element;
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}

	protected void activeMode(MODE mode) {
		container.clear();
		
		if(mode == MODE.EDIT) {
			container.add(valuePanel.getPanel());
		} else if(mode == MODE.VIEW) {
			container.add(anchorHref);
			if(valuePanel.getTag() instanceof RNGValue) {
				anchorHref.setHref(((RNGValue)valuePanel.getTag()).getText());
			} else if(valuePanel.getTag() instanceof RNGData<?>) {
				anchorHref.setHref(((RNGData<?>)valuePanel.getTag()).getStringValue());
			}
		}
	}
}