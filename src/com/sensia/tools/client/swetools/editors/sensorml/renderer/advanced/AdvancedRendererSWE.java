/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced;

import com.google.gwt.core.shared.GWT;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AbstractAdvancedAttributeOntologyIconPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedAttributeDefinitionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedAttributePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedAttributeReferenceFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkArcrolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkHrefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkRolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkTitlePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.swe.SWEAdvancedDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.swe.SWEAdvancedIdentifierPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.swe.SWEAdvancedIntervalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.swe.SWEAdvancedLabelPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.swe.SWEAdvancedSignificanFiguresPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.swe.SWEAdvancedValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.swe.SWEAdvancedValuesPanel;

/**
 * <p>
 * Renders content of an RNG grammar describing SWE Common data components using
 * GWT widgets
 * </p>
 *
 * @author Alexandre Robin
 * @date Aug 27, 2011
 */
public class AdvancedRendererSWE extends AdvancedRendererRNG {
	
    protected final static String XLINK = "http://www.w3.org/1999/xlink";
    
    
    @Override
	public void visit(RNGElement elt) {
		IPanel<RNGElement> widget = null;
		
		final String name = elt.getName();
		
		if(name.equalsIgnoreCase("description")) {
			widget = new SWEAdvancedDescriptionPanel(elt);
		} else if(name.equalsIgnoreCase("label")) {
			widget = new SWEAdvancedLabelPanel(elt);
		} else if(name.equalsIgnoreCase("identifier")) {
			widget = new SWEAdvancedIdentifierPanel(elt);
		} else if(name.equalsIgnoreCase("value")) {
			widget = new SWEAdvancedValuePanel(elt);
		} else if(name.equalsIgnoreCase("values")) {
			widget = new SWEAdvancedValuesPanel(elt);
		} else if(name.equalsIgnoreCase("interval")) {
			widget = new SWEAdvancedIntervalPanel(elt);
		} else if(name.equalsIgnoreCase("significantFigures")) {
			widget = new SWEAdvancedSignificanFiguresPanel(elt);
		} else if(name.equalsIgnoreCase("uom")) {
		    this.visitChildren(elt.getChildren());
		    return;
		}
		
		if(widget != null) {
			pushAndVisitChildren(widget, elt.getChildren());
		} else {
			super.visit(elt);
		}
	}
	
    @Override
    public void visit(RNGAttribute att) {
        
        String nsUri = att.getNamespace();
        String name = att.getName();
                
        // xlink attributes
        if (nsUri != null && nsUri.equalsIgnoreCase(XLINK)) {
            if(name.equals("role")){
                pushAndVisitChildren(new AdvancedXLinkRolePanel(att,getRefreshHandler()),att.getChildren());
            } else if(name.equals("arcrole")) {
                pushAndVisitChildren(new AdvancedXLinkArcrolePanel(att,getRefreshHandler()),att.getChildren());
            } else if(name.equals("href")) {
                pushAndVisitChildren(new AdvancedXLinkHrefPanel(att,getRefreshHandler()),att.getChildren());
            } else if(name.equals("title")) {
                pushAndVisitChildren(new AdvancedXLinkTitlePanel(att,getRefreshHandler()),att.getChildren());
            } else {
                GWT.log("[WARN] Unsupported XLink element: "+name+". Skipped.");
                super.visit(att);
            }
        } 
        
        // SWE attributes
        else if(name.equals("referenceFrame")) {
            pushAndVisitChildren(new AdvancedAttributeReferenceFramePanel(att,getRefreshHandler()),att.getChildren());
        } else if(name.equals("localFrame")) {
            pushAndVisitChildren(new AdvancedAttributePanel(att,getRefreshHandler()),att.getChildren());
        } else if(name.equals("definition")) {
            pushAndVisitChildren(new AdvancedAttributeDefinitionPanel(att,getRefreshHandler()),att.getChildren());
        } else if(name.equals("reason")) {
            pushAndVisitChildren(new AbstractAdvancedAttributeOntologyIconPanel(att,getRefreshHandler()),att.getChildren());
        } else if(name.equals("id")) {
            pushAndVisitChildren(new AdvancedAttributePanel(att,getRefreshHandler()),att.getChildren());
        } else if(name.equals("name")) {
            pushAndVisitChildren(new AdvancedAttributePanel(att,getRefreshHandler()),att.getChildren());
        } else if (name.equals("code")) {
            pushAndVisitChildren(new AdvancedAttributePanel(att,"Unit",null), att.getChildren());
        } else {
            super.visit(att);
        }
    }
	
	@Override
	protected String getDefaultStyle() {
		return "advanced";
	}
}