package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class ViewValuePanel extends AbstractPanel<RNGValue>{
	protected HTML htmlTextBox;

	private String niceLabel;
	
	private boolean isNiceLabel;
	
	public ViewValuePanel(final RNGValue data) {
		super(data);
		isNiceLabel = false;
		
		htmlTextBox = new HTML();
		
		computeName();

		// add into the main container
		container.add(htmlTextBox);
	}

	@Override
	public void addInnerElement(IPanel<? extends RNGTag> element) {
		// Do nothing
	}

	@Override
	protected AbstractPanel<RNGValue> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "value";
	}
	
	public void setNiceLabel(boolean isNiceLabel) {
		this.isNiceLabel = isNiceLabel;
		computeName();
	}
	
	public void setLabel(String label) {
		this.htmlTextBox.setText(label);
	}
	
	private void computeName() {
		// put saved value in text box
		if (getTag().getText() != null) {
			if(isNiceLabel) {
				niceLabel = Utils.toNiceLabel(getTag().getText().trim());
			} else {
				niceLabel = getTag().getText().trim();
			}
			
			htmlTextBox.setText(niceLabel);
		}
	}
}
