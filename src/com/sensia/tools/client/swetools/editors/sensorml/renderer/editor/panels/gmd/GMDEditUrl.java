package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gmd;

import com.google.gwt.user.client.ui.Anchor;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

public class GMDEditUrl extends AbstractPanel<RNGElement>{

	private Anchor anchorHref;
	private IPanel<? extends RNGTag> valuePanel;
	
	public GMDEditUrl(RNGElement tag) {
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
}
