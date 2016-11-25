package com.sensia.tools.client.swetools.editors.sensorml.panels.base;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditValuePanel extends AbstractPanel<RNGData<?>>{
	protected static final int DEFAULT_TEXBOX_VALUE_SIZE = 20;
	
	protected TextBox textBox;

	private String niceLabel;
	
	private boolean isNiceLabel;
	
	public EditValuePanel(final RNGData<?> data) {
		super(data);
		isNiceLabel = true;
		
		textBox = new TextBox();
		
		// put saved value in text box
		if (data.getStringValue() != null) {
			if(isNiceLabel) {
				niceLabel = Utils.toNiceLabel(data.getStringValue().trim());
			} else {
				niceLabel = data.getStringValue().trim();
			}
			
			textBox.setText(data.getStringValue().trim());
			
			int valueLength = data.getStringValue().trim().length();
			if(valueLength > 0) {
				textBox.setVisibleLength(data.getStringValue().trim().length());
			} else {
				textBox.setVisibleLength(DEFAULT_TEXBOX_VALUE_SIZE);
			}
			
		}

		textBox.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				GWT.log(textBox.getText());
				data.setStringValue(textBox.getText());
			}
		});
		// add into the main container
		container.add(textBox);
	}

	@Override
	public void addInnerElement(IPanel<? extends RNGTag> element) {
		// Do nothing
	}

	@Override
	protected AbstractPanel<RNGData<?>> newInstance() {
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
