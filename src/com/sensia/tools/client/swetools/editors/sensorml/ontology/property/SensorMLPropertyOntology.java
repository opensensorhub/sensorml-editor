package com.sensia.tools.client.swetools.editors.sensorml.ontology.property;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ILoadFileCallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.TableRes;
import com.sensia.tools.client.swetools.editors.sensorml.utils.BoyerMoore;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class SensorMLPropertyOntology implements IOntologyPropertyReader{

	private List<Property> originalData;
	
	public SensorMLPropertyOntology() {
		originalData = new ArrayList<Property>();
	}
	
	public void loadOntology(String url,final ILoadOntologyCallback callback) {
		originalData.clear();
		
		ILoadFileCallback cb = new ILoadFileCallback() {
			@Override
			public void onLoad(String content) {
				Document ontologyRoot = XMLParser.parse(content);
				parseOntology(ontologyRoot.getDocumentElement());
				
				callback.onLoad(getHeaders(), getValuesFromData());
			}
		};
		
		Utils.getFile(url, cb);
	}
	
	private List<String> getHeaders() {
		List<String> headers = new ArrayList<String>();
		headers.add("Definition URL");
		headers.add("Title");
		headers.add("Definition");
		headers.add("Creator");
		headers.add("PreLabel");
		return headers;
	}
	
	private Object[][] getValuesFromData() {
		int colNumber = (!originalData.isEmpty())? 5 : 0;
		
		Object [][] values = new Object[originalData.size()] [colNumber];
		
		int i=0;
		for(Property p : originalData) {
			values[i][0] = p.getDefUrl();
			values[i][1] = p.getTitle();
			values[i][2] = p.getDef();
			values[i][3] = p.getCreator();
			values[i][4] = p.getPreLabel();
			i++;
		}
		return values;
	}
	
	private void parseOntology(Element element){
		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element elt = (Element) node;
				if(elt.getNodeName().equals("Property")){
					Property property = new Property();
					parseProperty(property,elt);
					originalData.add(property);
					
				} else {
					parseOntology((Element) node);
				}
			}
		}
	}
	
	private void parseProperty(final Property property,Element propertyNode) {
		//add attributes if any
        if (propertyNode.hasAttributes()) {
        	NamedNodeMap attributes = propertyNode.getAttributes();
		
			for (int j = 0; j < attributes.getLength(); j++) {
				Node attr = attributes.item(j);
				if(attr.getNodeName().equals("rdf:about")) {
					property.setDefUrl(attr.getNodeValue());
				}
			}
		}
        
		NodeList children = propertyNode.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				if(node.getNodeName().equals("skos_definition")) {
					property.setDef(node.getChildNodes().item(0).getNodeValue());
				} else if(node.getNodeName().equals("skos_prefLabel")) {
					property.setPreLabel(node.getChildNodes().item(0).getNodeValue());
				} else if(node.getNodeName().equals("dc_creator")) {
					property.setCreator(node.getChildNodes().item(0).getNodeValue());
				} else if(node.getNodeName().equals("dc_title")) {
					property.setTitle(node.getChildNodes().item(0).getNodeValue());
				}
			} 
		}
	}
	
	private class Property {
		
		private String defUrl;
		private String def;
		private String preLabel;
		private String creator;
		private String title;
		
		public Property() {
			defUrl = "";
			def = "";
			preLabel = "";
			creator = "";
			title = "";
		}
		
		public String getDefUrl() {
			return defUrl;
		}
		public void setDefUrl(String defUrl) {
			this.defUrl = defUrl;
		}
		public String getDef() {
			return def;
		}
		public void setDef(String def) {
			this.def = def;
		}
		public String getPreLabel() {
			return preLabel;
		}
		public void setPreLabel(String preLabel) {
			this.preLabel = preLabel;
		}
		public String getCreator() {
			return creator;
		}
		public void setCreator(String creator) {
			this.creator = creator;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		
		@Override
		public String toString() {
			return "Property [defUrl=" + defUrl + ", def=" + def + ", preLabel=" + preLabel + ", creator=" + creator + ", title=" + title + "]";
		}
	}
}
