/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.advancedviewer;

import com.google.gwt.core.shared.GWT;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGValue;
import com.sensia.relaxNG.XSDAnyURI;
import com.sensia.relaxNG.XSDBoolean;
import com.sensia.relaxNG.XSDDateTime;
import com.sensia.relaxNG.XSDDecimal;
import com.sensia.relaxNG.XSDDouble;
import com.sensia.relaxNG.XSDInteger;
import com.sensia.relaxNG.XSDString;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd.ViewXSDAnyURIPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd.ViewXSDDateTimePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd.ViewXSDDecimalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd.ViewXSDDoublePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd.ViewXSDIntegerPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd.ViewXSDStringPanel;

/**
 * <p>
 * <b>Title:</b> RNGRenderer
 * </p>
 * 
 * <p>
 * <b>Description:</b><br/>
 * Renders content of an RNG grammar using GWT widgets
 * </p>
 * 
 * <p>
 * Copyright (c) 2011
 * </p>.
 *
 * @author Alexandre Robin
 * @date Aug 27, 2011
 */
public class AdvancedViewerRenderer extends AdvancedRendererSML{
	
	
	public AdvancedViewerRenderer() {
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGData)
	 */
	@Override
	public void visit(RNGData<?> data) {
		EditValuePanel editPanel = new EditValuePanel(data, getRefreshHandler());
		editPanel.setEnable(false);
		push(editPanel);
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDString)
	 */
	@Override
	public void visit(XSDString data) {
		push(new ViewXSDStringPanel(data,getRefreshHandler()));
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDBoolean)
	 */
	@Override
	public void visit(XSDBoolean data) {
		GWT.log("into XSDBoolean");
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDecimal)
	 */
	@Override
	public void visit(XSDDecimal data) {
		push(new ViewXSDDecimalPanel(data,getRefreshHandler()));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDouble)
	 */
	@Override
	public void visit(XSDDouble data) {
		push(new ViewXSDDoublePanel(data,getRefreshHandler()));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDInteger)
	 */
	@Override
	public void visit(XSDInteger data) {
		push(new ViewXSDIntegerPanel(data,getRefreshHandler()));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDAnyURI)
	 */
	@Override
	public void visit(XSDAnyURI data) {
		push(new ViewXSDAnyURIPanel(data,getRefreshHandler()));
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDateTime)
	 */
	@Override
	public void visit(XSDDateTime data) {
		push(new ViewXSDDateTimePanel(data,getRefreshHandler()));
		
	}
}
