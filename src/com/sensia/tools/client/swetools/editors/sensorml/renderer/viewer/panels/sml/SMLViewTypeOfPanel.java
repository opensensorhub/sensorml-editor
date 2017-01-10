package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.element.ViewSectionElementPanel;

public class SMLViewTypeOfPanel extends ViewSectionElementPanel{

	private String remotePath = "";
	
	public SMLViewTypeOfPanel(RNGElement tag, IRefreshHandler refreshHandler) {
		super(tag, refreshHandler);
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGAttribute) {
			RNGAttribute hrefAtt = (RNGAttribute) element.getTag();
			if(hrefAtt.getName().equals("href")) {
				remotePath = hrefAtt.getChildValueText();
			}
		}
		super.addInnerElement(element);
	}
	
	public String getRemotePath () {
		return remotePath;
	}
}