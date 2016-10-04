/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorAttributeRefWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericHorizontalContainerWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericXLinkWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.gml.GMLSensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SMLComponentWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SMLContactWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SMLDocumentWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SMLKeywordsWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SMLLinkWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SMLSensorAttributeWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SMLSensorEventWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SMLSensorModeChoiceWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SMLSensorModeWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SMLSensorSetValueWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SMLSensorSpatialFrame;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SensorSectionWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.SensorSectionsWidget;

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
public class RNGRendererSML extends RNGRendererSWE implements RNGTagVisitor {

	/** The render sections list. */
	private Map<String,String> renderSectionsList = new HashMap<String,String>();
	
	/** The render elements. */
	private Map<String,RENDER_ELEMENT_TYPE> renderElements= new HashMap<String,RENDER_ELEMENT_TYPE>();
	
	/** The root sections list. */
	private Set<String> rootSectionsList = new HashSet<String>();
	
	/** The skip list. */
	private Set<String> skipList = new HashSet<String>();
	
	/** The Constant SML_NS_1. */
	protected final static String SML_NS_1 = "http://www.opengis.net/sensorML/1.0.1";
	
	/** The Constant SML_NS_2. */
	protected final static String SML_NS_2 = "http://www.opengis.net/sensorML/2.0";
	
	/** The Constant GML_NS_1. */
	protected final static String GML_NS_1 = "http://www.opengis.net/gml";
	
	/** The Constant GML_NS_2. */
	protected final static String GML_NS_2 = "http://www.opengis.net/gml/3.2";
	
	/** The Constant GMD. */
	protected final static String GMD = "http://www.isotc211.org/2005/gmd";
	
	/** The Constant GCO. */
	protected final static String GCO = "http://www.isotc211.org/2005/gco";
	
	/**
	 * The Enum RENDER_ELEMENT_TYPE.
	 */
	enum RENDER_ELEMENT_TYPE {
		
		/** The line. */
		LINE,
		
		/** The generic horizontal. */
		GENERIC_HORIZONTAL,
		
		/** The generic vertical. */
		GENERIC_VERTICAL
	}
	
	/**
	 * Instantiates a new RNG renderer sml.
	 */
	public RNGRendererSML() {
		//render section names
		renderSectionsList.put("identification","Identification");
		renderSectionsList.put("typeOf","Type of");
		renderSectionsList.put("characteristics","Characteristics");
		renderSectionsList.put("capabilities","Capabilities");
		renderSectionsList.put("outputs", "Outputs");
		renderSectionsList.put("classification","Classification");
		//TODO: make only one constraint section
		renderSectionsList.put("validTime","Constraints (valid time)");
		renderSectionsList.put("securityConstraints","Constraints (security)");
		renderSectionsList.put("legalConstraints","Constraints (legal)");
		renderSectionsList.put("inputs", "Inputs");
		renderSectionsList.put("localReferenceFrame", "Local Reference Frame");
		renderSectionsList.put("parameters", "Parameters");
		renderSectionsList.put("method", "Method");
		renderSectionsList.put("contacts", "Contacts");
		renderSectionsList.put("documentation", "Documentation");
		renderSectionsList.put("connections", "Connections");
		renderSectionsList.put("components", "Components");
		renderSectionsList.put("component", "Component");
		renderSectionsList.put("position", "Position");
		renderSectionsList.put("boundedBy", "Bounded By");
		renderSectionsList.put("history", "History");
		renderSectionsList.put("position", "Position");
		renderSectionsList.put("configuration", "Configuration");
		renderSectionsList.put("modes", "Modes");
		
		//render default defined list elements
		renderElements.put("OutputList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("InputList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("IdentifierList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("ClassifierList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("ParameterList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("ConnectionList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("CharacteristicList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("CapabilityList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("ContactList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("DocumentList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("ComponentList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("ConnectionList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("ContactList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("EventList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("Settings", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		
		//render default defined elements
		renderElements.put("input",RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("output",RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("parameter", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("field", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("coordinate", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("characteristic", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("identifier", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("capability", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("elementCount", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("classifier", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("axis", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("origin", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("ObservableProperty", RENDER_ELEMENT_TYPE.LINE);
		
		//skip list
		skipList.add("event");
		skipList.add("contactInfo");
		skipList.add("Security");
		skipList.add("Term");
		skipList.add("keywords");
		skipList.add("data");
		skipList.add("NormalizedCurve");
		skipList.add("function");
		skipList.add("mode");
		skipList.add("TimeInstant");
		skipList.add("time");
		
		rootSectionsList.add("PhysicalSystem");
		rootSectionsList.add("ProcessModel");
		rootSectionsList.add("AggregateProcess");
		rootSectionsList.add("SimpleProcess");
		rootSectionsList.add("PhysicalComponent");
		rootSectionsList.add("Component");
		
		//skip contact elements tags
		skipList.add("CI_ResponsibleParty");
		skipList.add("contactInfo");
		skipList.add("CI_Contact");
		skipList.add("phone");
		skipList.add("CI_Telephone");
		skipList.add("address");
		skipList.add("CI_Address");
		
		//skip documents tags
		skipList.add("CI_OnlineResource");
		skipList.add("linkage");
	}

	/** The root min level. */
	public int rootMinLevel = 1;
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer#visit(com.sensia.relaxNG.RNGGrammar)
	 */
	@Override
	public void visit(RNGGrammar grammar) { 
		//create top root element
		push(new SensorSectionsWidget());
		super.visit(grammar);
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRendererSWE#visit(com.sensia.relaxNG.RNGElement)
	 */
	@Override
	public void visit(RNGElement elt) {
		String eltName = elt.getName();
		String nsUri = elt.getNamespace();

		//get Name Space
		TAG_DEF ns = TAG_DEF.RNG;
		
		if (nsUri.equalsIgnoreCase(SML_NS_1) || nsUri.equalsIgnoreCase(SML_NS_2)) {
			ns = TAG_DEF.SML;
		} else if (nsUri.equalsIgnoreCase(SWE_NS_1) || nsUri.equalsIgnoreCase(SWE_NS_2)) {
			ns = TAG_DEF.SWE;
		} else if (nsUri.equalsIgnoreCase(GML_NS_1) || nsUri.equalsIgnoreCase(GML_NS_2)) {
			ns = TAG_DEF.GML;
		} else if(nsUri.equalsIgnoreCase(GMD)) {
			ns = TAG_DEF.GMD;
		} else if(nsUri.equalsIgnoreCase(GCO)) {
			ns = TAG_DEF.GCO;
		}
		
		//skip the element and visit children
		if(skipList.contains(eltName)) {
			visitChildren(elt.getChildren());
			return;
		}
		
		if(elt.getChildAttribute("href") != null && elt.getName().equals("component")) {
				pushAndVisitChildren(new SMLComponentWidget(), elt.getChildren());
			return;
		}
		
		//if element is a section
		//TODO: was > 2 for embedded document
		if(getStackSize() > 1 && rootSectionsList.contains(eltName)) {
			int oldRootValue = rootMinLevel;
			rootMinLevel = getStackSize()+1;
			SensorSectionsWidget sensorSections = new SensorSectionsWidget();
			sensorSections.setInnerSections(true);
			pushAndVisitChildren(sensorSections,elt.getChildren());
			rootMinLevel = oldRootValue;
			return;
		} else if(rootSectionsList.contains(eltName)) {
			visitChildren(elt.getChildren());
			return;
		}
		
		//special case of component
		//TODO:
		boolean isNewSectionForComponent = false;
		if(eltName.equalsIgnoreCase("component")) {
			isNewSectionForComponent = true;
		}
		
		if(getStackSize() == rootMinLevel || isNewSectionForComponent) {
			processSection(elt, eltName);
		} else {
			if(renderElements.containsKey(eltName)) { 
				RENDER_ELEMENT_TYPE type = renderElements.get(eltName);
				
				ISensorWidget widget = null;
				
				switch(type) {
					case GENERIC_VERTICAL : widget = renderVerticalWidget(eltName, ns, TAG_TYPE.ELEMENT);break;
					case GENERIC_HORIZONTAL : widget = renderHorizontalWidget(eltName, ns, TAG_TYPE.ELEMENT);break;
					case LINE : widget = renderLineWidget(eltName, ns, TAG_TYPE.ELEMENT);break;
					default:break;
				}
				pushAndVisitChildren(widget, elt.getChildren());
			}  else {
				if(eltName.equals("contact")) {
					pushAndVisitChildren(new SMLContactWidget(), elt.getChildren());
				} else if(eltName.equals("document") || eltName.equals("Document")) {
					pushAndVisitChildren(new SMLDocumentWidget(), elt.getChildren());
				} else if(eltName.equals("Link")) {
					pushAndVisitChildren(new SMLLinkWidget(), elt.getChildren());
				} else if(eltName.equals("SpatialFrame")) {
					pushAndVisitChildren(new SMLSensorSpatialFrame(), elt.getChildren());
				} else if(elt.getChildAttribute("href") != null) {
					pushAndVisitChildren(new SensorGenericXLinkWidget(eltName,ns), elt.getChildren());
				} else if(eltName.equals("ModeChoice")) {
					pushAndVisitChildren(new SMLSensorModeChoiceWidget(), elt.getChildren());
				} else if(eltName.equals("Mode")) {
					pushAndVisitChildren(new SMLSensorModeWidget(), elt.getChildren());
				} else if(eltName.equals("setValue") || eltName.equals("setMode")) {
					pushAndVisitChildren(new SMLSensorSetValueWidget(getRoot(),getGrammar()), elt.getChildren());
				} else if(eltName.equals("Event")) {
					pushAndVisitChildren(new SMLSensorEventWidget(), elt.getChildren());
				} else if(nsUri.equals(GML_NS_1) || nsUri.equals(GML_NS_2)) {
					pushAndVisitChildren(new SensorGenericHorizontalContainerWidget(elt.getName(), TAG_DEF.GML, TAG_TYPE.ELEMENT), elt.getChildren());
				} else if (nsUri.equals(SML_NS_1) || nsUri.equals(SML_NS_2)) {
					pushAndVisitChildren(new SensorGenericHorizontalContainerWidget(elt.getName(), TAG_DEF.SML, TAG_TYPE.ELEMENT), elt.getChildren());
				} else if (nsUri.equals(GMD)) {
					pushAndVisitChildren(new SensorGenericHorizontalContainerWidget(elt.getName(), TAG_DEF.GMD, TAG_TYPE.ELEMENT), elt.getChildren());
				} else if (nsUri.equals(GCO)) {
					pushAndVisitChildren(new SensorGenericHorizontalContainerWidget(elt.getName(), TAG_DEF.GCO, TAG_TYPE.ELEMENT), elt.getChildren());
				}else {
					super.visit(elt);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer#visit(com.sensia.relaxNG.RNGAttribute)
	 */
	@Override
	public void visit(RNGAttribute att) {
		//get ns
		TAG_DEF ns = TAG_DEF.RNG;
		String nsUri = att.getNamespace();
		if (nsUri.equals(SML_NS_1) || nsUri.equals(SML_NS_2)) {
			ns = TAG_DEF.SML;
		} else if (nsUri.equals(SWE_NS_1) || nsUri.equals(SWE_NS_2)) {
			ns = TAG_DEF.SWE;
		} else if (nsUri.equals(GML_NS_1) || nsUri.equals(GML_NS_2)) {
			ns = TAG_DEF.GML;
		} else if(nsUri.equals(GMD)) {
			ns = TAG_DEF.GMD;
		} else if(nsUri.equals(GCO)) {
			ns = TAG_DEF.GCO;
		}
		
		if(att.getName().equals("referenceFrame") || att.getName().equals("definition") || att.getName().equals("name")
				|| att.getName().equals("role") ||  att.getName().equals("arcrole")) {
			pushAndVisitChildren(new SMLSensorAttributeWidget(att), att.getChildren());
		} else if(att.getName().equals("href")) {
			pushAndVisitChildren(new SensorGenericXLinkWidget(att.getName(),ns), att.getChildren());
		} /*else if(att.getName().equals("ref")) {
			pushAndVisitChildren(new SensorAttributeRefWidget(getRoot()), att.getChildren());
		}*/ else {
			super.visit(att);
		}
	}
	
	/**
	 * Process section.
	 *
	 * @param elt the elt
	 * @param eltName the elt name
	 */
	protected void processSection(RNGElement elt, String eltName) {
		List<RNGTag> children = elt.getChildren();
		
		ISensorWidget widget = null;
		if(eltName.equals("name") || eltName.equals("identifier") || eltName.equals("description")) {
			widget =  new GMLSensorWidget(elt);
		} else if(eltName.equals("KeywordList")) {
			widget = new SMLKeywordsWidget();	
		} else {
			//it is a non pre-defined section
			//add default name
			String sectionName = "No Supported Name";
			
			if(renderSectionsList.containsKey(eltName)) {
				//for custom is to get section name from attribute->value children
				//lets the renderer find them and add to the section
				sectionName = renderSectionsList.get(eltName);
			} 
			widget = new SensorSectionWidget(eltName,sectionName);
			
			ISensorWidget existingTagSection = getWidget(eltName);
			if(existingTagSection != null) {
				List<RNGTag> revisitedNodes = new ArrayList<RNGTag>();
				revisitedNodes.add(elt);
				children = revisitedNodes;
			}
		}
		pushAndVisitChildren(widget,children);
	}
}
