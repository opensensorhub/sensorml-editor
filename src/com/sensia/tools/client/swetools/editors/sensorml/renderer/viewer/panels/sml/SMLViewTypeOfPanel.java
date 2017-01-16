package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml;

import com.google.gwt.user.client.ui.Anchor;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewXLinkHrefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.element.ViewSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

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
			if(element instanceof ViewXLinkHrefPanel) {
					Anchor anchor = ((ViewXLinkHrefPanel)element).getAnchorHref();
					anchor.setHref(Utils.getCurrentURL(anchor.getHref()));
			}
		}
		super.addInnerElement(element);
	}
	
	public String getRemotePath () {
		return remotePath;
	}
}
