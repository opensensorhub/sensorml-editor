package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd;

import com.sensia.relaxNG.XSDInteger;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class XSDIntegerPanel extends EditValuePanel{

    private static final int VISIBLE_LENGTH = 15;
    private static final String INTEGER_NUMBER_REGEX = "^[-+]?[0-9]+$";
	
	public XSDIntegerPanel(final XSDInteger data, IRefreshHandler refreshHandler) {
	    super(data,refreshHandler);
        setTextBoxSize(VISIBLE_LENGTH);
        setValidationRegex(INTEGER_NUMBER_REGEX);
	}
}
