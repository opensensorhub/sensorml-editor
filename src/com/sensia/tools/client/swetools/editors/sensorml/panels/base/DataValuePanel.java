package com.sensia.tools.client.swetools.editors.sensorml.panels.base;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class DataValuePanel extends AbstractPanel<RNGData<?>>{
	private static final int DEFAULT_TEXBOX_VALUE_SIZE = 50;

	protected TextBox textBox;

	protected HTML htmlTextBox;

	private String niceLabel;
	
	private boolean isNiceLabel;
	
	private MODE currentMode;
	
	public DataValuePanel(final RNGData<?> data) {
		super(data);
		currentMode = MODE.VIEW;
		isNiceLabel = true;
		
		htmlTextBox = new HTML();
		textBox = new TextBox();
		
		// put saved value in text box
		if (data.getStringValue() != null) {
			if(isNiceLabel) {
				niceLabel = Utils.toNiceLabel(data.getStringValue().trim());
			} else {
				niceLabel = data.getStringValue().trim();
			}
			
			textBox.setText(data.getStringValue().trim());
			htmlTextBox.setText(niceLabel);
			
			int valueLength = data.getStringValue().trim().length();
			if(valueLength > 0) {
				textBox.setVisibleLength(data.getStringValue().trim().length());
			} else {
				textBox.setVisibleLength(DEFAULT_TEXBOX_VALUE_SIZE);
			}
			
			textBox.addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {
					data.setStringValue(textBox.getText());
				}
			});
		}

		// add into the main container
		container.add(htmlTextBox);
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
	protected void activeMode(MODE mode) {
		currentMode = mode;
		refreshTextFields();
	}

	@Override
	public String getName() {
		return "value";
	}
	
	public void setNiceLabel(boolean isNiceLabel) {
		this.isNiceLabel = isNiceLabel;
		refreshTextFields();
	}
	
	private void refreshTextFields() {
		container.clear();
		
		// update html value with edited value
		if(isNiceLabel) {
			niceLabel = Utils.toNiceLabel(getTag().getStringValue());
		} else {
			niceLabel = getTag().getStringValue();
		}
		
		htmlTextBox.setText(niceLabel);
		if(currentMode == MODE.EDIT) {
			container.add(textBox);
		} else if (currentMode == MODE.VIEW) {
			container.add(htmlTextBox);
		}
	}
}
