package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;

public class SWEditEncodingPanel extends EditSubSectionElementPanel{

	private String tokenSeparator;
	private String blockSeparator;
	private String decimalSeparator;
	
	public SWEditEncodingPanel(RNGElement element) {
		super(element);
	}

	public void addInnerElement(IPanel element) {
		if(element.getName().equals("TextEncoding")) {
			RNGElement elt = (RNGElement) element.getTag();
			RNGAttribute tokenSeparator = elt.getChildAttribute("tokenSeparator");
			RNGAttribute blockSeparator = elt.getChildAttribute("blockSeparator");
			RNGAttribute decimalSeparator = elt.getChildAttribute("decimalSeparator");
			
			if(tokenSeparator != null) {
				this.setTokenSeparator(tokenSeparator.getChildValueText());
			}
			if(blockSeparator != null) {
				this.setBlockSeparator(blockSeparator.getChildValueText());
			}
			if(decimalSeparator != null) {
				this.setDecimalSeparator(decimalSeparator.getChildValueText());
			}
		}
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}

	public String getTokenSeparator() {
		return tokenSeparator;
	}

	public void setTokenSeparator(String tokenSeparator) {
		this.tokenSeparator = tokenSeparator;
	}

	public String getBlockSeparator() {
		return blockSeparator;
	}

	public void setBlockSeparator(String blockSeparator) {
		this.blockSeparator = blockSeparator;
	}

	public String getDecimalSeparator() {
		return decimalSeparator;
	}

	public void setDecimalSeparator(String decimalSeparator) {
		this.decimalSeparator = decimalSeparator;
	}
}
