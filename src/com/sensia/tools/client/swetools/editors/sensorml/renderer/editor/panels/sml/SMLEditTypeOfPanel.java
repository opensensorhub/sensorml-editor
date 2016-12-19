package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSectionElementPanel;

public class SMLEditTypeOfPanel extends EditSectionElementPanel{

	private String remotePath = "";
	
	public SMLEditTypeOfPanel(RNGElement tag, IRefreshHandler refreshHandler) {
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
