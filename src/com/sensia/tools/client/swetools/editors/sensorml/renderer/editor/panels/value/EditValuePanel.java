package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

public class EditValuePanel extends AbstractPanel<RNGData<?>>{
	protected static final int DEFAULT_TEXBOX_VALUE_SIZE = 20;
	
	protected TextBox textBox;

	private String focusTmpText="";
	
	public EditValuePanel(final RNGData<?> data,final IRefreshHandler refreshHandler) {
		super(data,refreshHandler);
		container = new SMLHorizontalPanel();
		
		isNiceLabel = true;
		
		textBox = new TextBox();
		textBox.addStyleName("textbox");
		// put saved value in text box
		if (data.getStringValue() != null) {
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
				data.setStringValue(textBox.getText());
			}
		});
		
		textBox.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				if(!focusTmpText.equals(textBox.getText()) && refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});
		
		textBox.addFocusHandler(new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent event) {
				focusTmpText = textBox.getText();
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
	
	public void setEnable(boolean isEnable) {
		textBox.setEnabled(isEnable);
	}
}
