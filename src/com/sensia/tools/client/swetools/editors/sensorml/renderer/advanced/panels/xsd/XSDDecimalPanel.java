package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd;

import com.sensia.relaxNG.XSDDecimal;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class XSDDecimalPanel extends EditValuePanel{
	    
    private static final int VISIBLE_LENGTH = 15;
    private static final String DECIMAL_NUMBER_REGEX = "^[-+]?[0-9]*\\.?[0-9]+$";
    
	public XSDDecimalPanel(final XSDDecimal data, IRefreshHandler refreshHandler) {
		super(data,refreshHandler);
		setTextBoxSize(VISIBLE_LENGTH);
		setValidationRegex(DECIMAL_NUMBER_REGEX);
	}
}
