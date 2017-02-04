package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd;

import com.sensia.relaxNG.XSDDouble;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class XSDDoublePanel extends EditValuePanel{

    private static final int VISIBLE_LENGTH = 15;
    private static final String DOUBLE_NUMBER_REGEX = "^(([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?)|NaN|\\+?INF|-INF)$";
	
	public XSDDoublePanel(XSDDouble data, IRefreshHandler refreshHandler) {
	    super(data,refreshHandler);
        setTextBoxSize(VISIBLE_LENGTH);
        setValidationRegex(DOUBLE_NUMBER_REGEX);
	}
}
