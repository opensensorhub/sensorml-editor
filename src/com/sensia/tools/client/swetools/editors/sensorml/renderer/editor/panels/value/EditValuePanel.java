package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

public class EditValuePanel extends AbstractPanel<RNGData<?>>{
	protected static final int DEFAULT_TEXBOX_VALUE_SIZE = 20;
	
	protected TextBoxBase textBox;

	private String focusTmpText="";
	
	public EditValuePanel(final RNGData<?> data,final IRefreshHandler refreshHandler) {
	    this(data, false, refreshHandler);
	}
	
	public EditValuePanel(final RNGData<?> data, boolean largeText, final IRefreshHandler refreshHandler) {
		super(data,refreshHandler);
		container = new SMLHorizontalPanel();
		
		isNiceLabel = true;
		
		if (largeText) {
		    textBox = new TextArea();
            textBox.addStyleName("textArea");
            textBox.setWidth("");
            ((TextArea)textBox).setVisibleLines(3);
		} else {
		    textBox = new TextBox();
    		textBox.addStyleName("textbox");
    		((TextBox)textBox).setVisibleLength(DEFAULT_TEXBOX_VALUE_SIZE);
		}
		
		// put saved value in text box
        if (data.getStringValue() != null)
            textBox.setText(data.getStringValue().trim());
        
		textBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				data.setStringValue(textBox.getText());
			}
		});
        
        textBox.addValueChangeHandler(new ValueChangeHandler<String>() {            
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                if(!focusTmpText.equals(textBox.getText()) && refreshHandler != null) {
                    refreshHandler.refresh();
                }
            }
        });
		
		/*textBox.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				if(!focusTmpText.equals(textBox.getText()) && refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});*/
		
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
	
	public void setPlaceholderText(String text) {
	    textBox.getElement().setPropertyString("placeholder", text);
	}
}
