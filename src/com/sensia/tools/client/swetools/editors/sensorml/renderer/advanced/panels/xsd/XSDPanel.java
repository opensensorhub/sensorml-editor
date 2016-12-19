package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd;

import com.google.gwt.user.client.ui.TextBox;
import com.sensia.relaxNG.RNGData;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class XSDPanel<T extends RNGData<?>> extends EditValuePanel {

	//protected TextBoxBase textBox;

	//protected HTML htmlTextBox;

	public XSDPanel(final T data, final int length, final String allowedChars, IRefreshHandler refreshHandler) {
		super(data,refreshHandler);
		
		/*if (length >= 0 && length <= DEFAULT_TEXBOX_VALUE_SIZE) {
			((TextBox) textBox).setVisibleLength(DEFAULT_TEXBOX_VALUE_SIZE);
			((TextBox) textBox).setVisibleLength(DEFAULT_TEXBOX_VALUE_SIZE);
		} else {
			((TextBox) textBox).setVisibleLength(length);
			((TextBox) textBox).setMaxLength(length);
		} */
		((TextBox) textBox).setVisibleLength(DEFAULT_TEXBOX_VALUE_SIZE);
		((TextBox) textBox).setVisibleLength(DEFAULT_TEXBOX_VALUE_SIZE);
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
