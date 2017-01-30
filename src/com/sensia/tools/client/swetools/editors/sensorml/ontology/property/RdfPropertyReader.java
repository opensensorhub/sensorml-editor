/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.ontology.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ILoadFileCallback;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * The Class RdfPropertyReader.
 */
public class RdfPropertyReader implements IOntologyPropertyReader{

	/** The class col def map. */
	private Map<String,Integer> classColDefMap;
	
	/** The original data. */
	private List<Property> originalData;
	
	/**
	 * Instantiates a new rdf property reader.
	 */
	public RdfPropertyReader() {
		init();
	}
	
	/**
	 * Inits the.
	 */
	private void init() {
		//init global values
		classColDefMap = new TreeMap<String,Integer>();
		originalData = new ArrayList<Property>();
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.ontology.property.IOntologyPropertyReader#loadOntology(java.lang.String, com.sensia.tools.client.swetools.editors.sensorml.ontology.property.ILoadOntologyCallback)
	 */
	public void loadOntology(String url,final ILoadOntologyCallback callback) {
		classColDefMap.clear();
		originalData.clear();
		
		classColDefMap.put("Definition ref", 0);
		ILoadFileCallback cb = new ILoadFileCallback() {
			@Override
			public void onLoad(String content) {
				Document ontologyRoot = XMLParser.parse(content);
				parseOntology(ontologyRoot.getDocumentElement());
				
				
				callback.onLoad(getHeadersFromClassDef(), getValuesFromData());
			}
		};
		
		Utils.getFile(url, cb);
	}
	
	/**
	 * Gets the headers from class def.
	 *
	 * @return the headers from class def
	 */
	private List<String> getHeadersFromClassDef() {
		return new ArrayList<String>(classColDefMap.keySet());
	}
	
	/**
	 * Gets the values from data.
	 *
	 * @return the values from data
	 */
	private Object[][] getValuesFromData() {
		int colNumber = (!originalData.isEmpty())? originalData.get(0).properties.size() : 0;
		
		Object [][] values = new Object[originalData.size()] [colNumber];
		
		int i=0;
		for(Property p : originalData) {
			for(int j =0;j < p.properties.size();j++) {
				values[i][j] = p.properties.get(j);
			}
			i++;
		}
		return values;
	}
	
	/**
	 * Parses the ontology.
	 *
	 * @param element the element
	 */
	private void parseOntology(Element element){
		String className = "Property";
		
		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element elt = (Element) node;
				
				String nodeName = getRealName(elt.getNodeName());
				
				if(nodeName.equals(className)){
					Property property = new Property(classColDefMap.size());
					
					parseProperty(property,elt);
					originalData.add(property);
					
				} else if(nodeName.equals("DatatypeProperty")){
					parseDatatypeProperty(elt);
				} else if (nodeName.equals("Class")) {
					//should get the label
					for(int j=0;j < node.getChildNodes().getLength();j++) {
						Node childClassNode = node.getChildNodes().item(j);
						if(childClassNode.getNodeType() == Node.ELEMENT_NODE) {
							Element child = (Element) childClassNode;
							String classChildNodeName = getRealName(child.getNodeName());
							if(classChildNodeName.equals("label")) {
								className = childClassNode.getChildNodes().item(0).getNodeValue();
							}
						}
					}
				} else {
					parseOntology((Element) node);
				}
			}
		}
	}
	
	/**
	 * Parses the datatype property.
	 *
	 * @param propertyNode the property node
	 */
	private void parseDatatypeProperty(Element propertyNode) {
		NodeList children = propertyNode.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			String nodeName = getRealName(node.getNodeName());

			if(nodeName.equals("label")) {
				//classColDefMap.put(getRealName(node.getChildNodes().item(0).getNodeValue()),classColDefMap.size()+1);
				String colValue = node.getChildNodes().item(0).getNodeValue();
				int index = classColDefMap.size();
				classColDefMap.put(colValue,index);
				break;
			}
		}
	}
	
	/**
	 * Parses the property.
	 *
	 * @param property the property
	 * @param propertyNode the property node
	 */
	private void parseProperty(final Property property,Element propertyNode) {
		//add attributes if any
        if (propertyNode.hasAttributes()) {
        	NamedNodeMap attributes = propertyNode.getAttributes();
		
			for (int j = 0; j < attributes.getLength(); j++) {
				Node attr = attributes.item(j);
				if(attr.getNodeName().equals("rdf:about")) {
					property.properties.set(0, attr.getNodeValue());
				}
			}
		}
        
		NodeList children = propertyNode.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = node.getNodeName();
				
				if(classColDefMap.containsKey(nodeName) &&  node.getChildNodes().getLength() > 0) {
					int key = classColDefMap.get(nodeName);
					String value = node.getChildNodes().item(0).getNodeValue();
					property.properties.set(key, value);
				}
			} 
		}
	}
	
	/**
	 * Gets the real name.
	 *
	 * @param name the name
	 * @return the real name
	 */
	private String getRealName(String name) {
		String res = name;
		if(res.contains(":")) {
			res = res.split(":")[1];
		}
		
		if(res.contains("_")) {
			res = res.split("_")[1];
		}
		return res;
	}
}

