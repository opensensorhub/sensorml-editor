package com.sensia.tools.client.swetools.editors.sensorml.panels.base;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class ViewValuePanel extends AbstractPanel<RNGValue>{
	private static final int DEFAULT_TEXBOX_VALUE_SIZE = 50;

	protected HTML htmlTextBox;

	private String niceLabel;
	
	private boolean isNiceLabel;
	
	public ViewValuePanel(final RNGValue data) {
		super(data);
		isNiceLabel = true;
		
		htmlTextBox = new HTML();
		
		// put saved value in text box
		if (data.getText() != null) {
			if(isNiceLabel) {
				niceLabel = Utils.toNiceLabel(data.getText().trim());
			} else {
				niceLabel = data.getText().trim();
			}
			
			htmlTextBox.setText(niceLabel);
		}

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
	}
}
