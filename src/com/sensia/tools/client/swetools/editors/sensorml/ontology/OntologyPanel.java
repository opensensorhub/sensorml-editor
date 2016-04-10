package com.sensia.tools.client.swetools.editors.sensorml.ontology;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.ILoadOntologyCallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.IOntologyPropertyReader;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.RdfPropertyReader;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.SensorMLPropertyOntology;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.dataarray.GenericTable;

public class OntologyPanel {
	
	public VerticalPanel ontologyPanel;
	private static Map<String,String> sources = new HashMap<String,String>();
	private GenericTable ontologyTable;
	
	static {
		sources.put("SensorML Property",SensorConstants.ONTOLOGY_SENSORML_SWE_PROPERTY_URL);
		sources.put("MMI MVCO",SensorConstants.ONTOLOGY_MMI_MVCO_PROPERTY_URL);
	}
	
	public OntologyPanel(){
		ontologyPanel = new VerticalPanel();
		ontologyPanel.setStyleName("ontology-panel");
		//create list of sources url pointing to the ontology server
		final ListBox sourcesBox = new ListBox();
		for(final String source : sources.keySet()) {
			sourcesBox.addItem(source);
		}
		sourcesBox.setStyleName("ontology-sourcesBox");
		
		//default use SensorML ontology
		//rdfPropertyReader = new RdfPropertyReader();
		//Panel resultTablePanel = rdfPropertyReader.createTable();
		
		ontologyTable = new GenericTable();
		ontologyTable.setEditable(false);
		
		Panel panelTable = ontologyTable.createTable();
		
		IOntologyPropertyReader rdfPropertyReader = new SensorMLPropertyOntology();
		
		final HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(new HTML("Source :"+SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE));
		hPanel.add(sourcesBox);
		
		final VerticalPanel vPanel = new VerticalPanel();
		vPanel.add(hPanel);
		vPanel.add(panelTable);
		
		ontologyPanel.add(vPanel);
		
		//load ontology 
		rdfPropertyReader.loadOntology(sources.get(sourcesBox.getSelectedValue()),new ILoadOntologyCallback() {
			
			@Override
			public void onLoad(List<String> headers, Object[][] data) {
				ontologyTable.poupulateTable(headers, data);
			}
		});
		
		sourcesBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				//rdfPropertyReader.loadOntology(sources.get(sourcesBox.getSelectedValue()));
				String source = sourcesBox.getSelectedValue();
				
				IOntologyPropertyReader rdfPropertyReader;
				
				if(source.equals("SensorML Property")) {
					rdfPropertyReader = new SensorMLPropertyOntology();
				} else {
					rdfPropertyReader = new RdfPropertyReader();
				}
				
				ontologyTable = new GenericTable();
				ontologyTable.setEditable(false);
				
				Panel panelTable = ontologyTable.createTable();
				
				vPanel.clear();
				vPanel.add(hPanel);
				vPanel.add(panelTable);
				
				rdfPropertyReader.loadOntology(sources.get(sourcesBox.getSelectedValue()),new ILoadOntologyCallback() {
					
					@Override
					public void onLoad(List<String> headers, Object[][] data) {
						ontologyTable.poupulateTable(headers, data);
					}
				});
				
			}
		});
	}
	
	public String getSelectedValue() {
		return ontologyTable.getSelectedValue();
	}
	
	public Panel getPanel() {
		return ontologyPanel;
	}
	
	//ALGO PART
}
