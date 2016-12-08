package com.sensia.tools.client.swetools.editors.sensorml.panels.xsd;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class XSDPanel<T extends RNGData<?>> extends EditValuePanel {

	//protected TextBoxBase textBox;

	//protected HTML htmlTextBox;

	public XSDPanel(final T data, final int length, final String allowedChars) {
		super(data);
		
		if (length < 0) {
			((TextBox) textBox).setVisibleLength(DEFAULT_TEXBOX_VALUE_SIZE);
		} else if (length <= 60) {
			((TextBox) textBox).setVisibleLength(length);
			((TextBox) textBox).setMaxLength(length);
		} else {
			textBox.setVisibleLength(DEFAULT_TEXBOX_VALUE_SIZE);
		}
	}

	/*@Override
	public void addInnerElement(IPanel<? extends RNGTag> element) {
		// TODO Auto-generated method stub
		container.add(element.getPanel());

	}*/

	@Override
	protected AbstractPanel<RNGData<?>> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	protected void activeMode(MODE mode) {
		container.clear();
		
		// update html value with edited value
		htmlTextBox.setText(textBox.getText());
		if(mode == MODE.EDIT) {
			container.add(textBox);
		} else if (mode == MODE.VIEW) {
			container.add(htmlTextBox);
		}
	}*/

	@Override
	public String getName() {
		return getTag().getAnnotation();
	}
}
