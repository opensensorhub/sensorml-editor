package com.sensia.tools.client.swetools.editors.sensorml.ontology.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ILoadFiledCallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.TableRes;
import com.sensia.tools.client.swetools.editors.sensorml.utils.BoyerMoore;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RdfPropertyReader {

	private ListDataProvider<Property> dataProvider;
	private List<Property> originalData;
	private List<Property> filteredData;
	private int lentghPattern = 0;
	
	private CellTable.Resources tableRes = GWT.create(TableRes.class);
	
	@UiField(provided=true)
	private CellTable<Property> table;
	
	private Property selectedProperty;
	
	private Map<String,Integer> classColDefMap;
	
	public RdfPropertyReader() {
		originalData = new ArrayList<Property>();
		dataProvider = new ListDataProvider<Property>();
		filteredData = new ArrayList<Property>();
		classColDefMap = new HashMap<String,Integer>();
	}
	
	public void loadOntology(String url) {
		ILoadFiledCallback cb = new ILoadFiledCallback() {
			@Override
			public void onLoad(String content) {
				Document ontologyRoot = XMLParser.parse(content);
				parseOntology(ontologyRoot.getDocumentElement());
				//update table
				updateTable();
				dataProvider.setList(originalData);
			}
		};
		
		Utils.getFile(url, cb);
	}
	
	public void setFilter(final String pattern) {
		if(pattern.isEmpty()) {
			if(lentghPattern > 0) {
				//restore original
				dataProvider.setList(originalData);
			} 
			lentghPattern = 0;
			//otherwise no filter is needed
		} else {
			//use boyer Moore String matching algorithm to match corresponding pattern
			BoyerMoore bm = new BoyerMoore(pattern);
			List<Property> newFilteredList = null;
			if(filteredData.isEmpty()) {
				newFilteredList = filterPattern(bm, originalData);
			} else {
				//get filter direction
				if(pattern.length() > lentghPattern) {
					lentghPattern = pattern.length();
					//up
					newFilteredList = filterPattern(bm, filteredData);
				} else {
					//down
					lentghPattern = pattern.length();
					newFilteredList = filterPattern(bm, originalData);
				}
			}
			filteredData = newFilteredList;
			dataProvider.setList(filteredData);
		}
	}
	
	private List<Property>  filterPattern(final BoyerMoore bm, final List<Property> inputList) {
		List<Property> newFilteredList = new ArrayList<Property>();
		for(final Property property : inputList) {
			//check defUrl
			for(String currentProperty : property.properties) {
				if((bm.search(currentProperty.getBytes(), 0) > 1)) {
					newFilteredList.add(property);
					break;
				}
			}
		}
		return newFilteredList;
	}
	
	public String getSelectedValue() {
		String value = null;
		if(selectedProperty != null) {
			//find def property
			//we supposed that it exists at least one
			value = selectedProperty.properties.get(0);
			
		}
		return value;
	}
	
	private void updateTable() {
		if(table == null) {
			createTable();
		}
		Set<String> colNames = classColDefMap.keySet();
		
		for(final String colName : colNames) {
			final Column<Property, String> column = new Column<Property, String>(new TextCell()) {
				
				@Override
				public String getValue(Property object) {
					return object.properties.get(classColDefMap.get(colName));
				}
			};
			column.setSortable(false);
			table.addColumn(column,colName);
		}
	}
	
	public Panel createTable() {
		if(table == null) {
			table  = new CellTable<Property>(10,tableRes);
			table.setStyleName("ontology-table-result");
			
			dataProvider.addDataDisplay(table);
			
			table.setSkipRowHoverCheck(true);
		    table.setSkipRowHoverFloatElementCheck(true);
			table.setSkipRowHoverStyleUpdate(true);
			table.setVisibleRange(0, 100000);
			
			// Add a selection model to handle user selection.
		    final SingleSelectionModel<Property> selectionModel = new SingleSelectionModel<Property>();
		    table.setSelectionModel(selectionModel);
		    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		    	  Property selected = selectionModel.getSelectedObject();
			      if (selected != null) {
			    	  selectedProperty = selected;
			      }
		      }
		    });
		}
		ScrollPanel sPanel = new ScrollPanel();
		sPanel.setStyleName("ontology-table-panel");
		sPanel.add(table);
		return sPanel;
	}
	
	private void parseOntology(Element element){
		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element elt = (Element) node;
				
				String nodeName = getRealName(elt.getNodeName());
				
				if(nodeName.equals("Property")){
					Property property = new Property();
					property.properties = new ArrayList<String>(100);
					
					parseProperty(property,elt);
					originalData.add(property);
					
				} else if(nodeName.equals("DatatypeProperty")){
					parseDatatypeProperty(elt);
				} else {
					parseOntology((Element) node);
				}
			}
		}
	}
	
	private void parseDatatypeProperty(Element propertyNode) {
		NodeList children = propertyNode.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			String nodeName = getRealName(node.getNodeName());

			if(nodeName.equals("label")) {
				classColDefMap.put(getRealName(node.getChildNodes().item(0).getNodeValue()),classColDefMap.size()+1);
				break;
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
					property.properties.add(attr.getNodeValue());
				}
			}
		}
        
		NodeList children = propertyNode.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = getRealName(node.getNodeName());
				
				if(classColDefMap.containsKey(nodeName) &&  node.getChildNodes().getLength() > 0) {
					int key = classColDefMap.get(nodeName);
					String value = node.getChildNodes().item(0).getNodeValue();
					property.properties.set(key, value);
				}
			} 
		}
	}
	
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

