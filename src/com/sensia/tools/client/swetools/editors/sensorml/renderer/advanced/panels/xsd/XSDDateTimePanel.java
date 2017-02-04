package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd;

import com.sensia.relaxNG.XSDDateTime;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class XSDDateTimePanel extends EditValuePanel{

    private static final int VISIBLE_LENGTH = 28;
    private static final String ISO_8601_REGEX = "^(-?(?:[1-9][0-9]*)?[0-9]{4})-(1[0-2]|0[1-9])-(3[01]|0[1-9]|[12][0-9])" + 
                                                 "(T(2[0-3]|[01][0-9]):([0-5][0-9]):([0-5][0-9])(\\.[0-9]+)?)?" +
                                                 "(Z|[+-](?:2[0-3]|[01][0-9]):[0-5][0-9])?$";
			
	public XSDDateTimePanel(XSDDateTime data, IRefreshHandler refreshHandler) {
		super(data,refreshHandler);
		setTextBoxSize(VISIBLE_LENGTH);
		setValidationRegex(ISO_8601_REGEX);
	}
}
