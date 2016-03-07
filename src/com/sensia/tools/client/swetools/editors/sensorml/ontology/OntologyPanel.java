package com.sensia.tools.client.swetools.editors.sensorml.ontology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.IOntologyPropertyReader;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.RdfPropertyReader;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.SensorMLPropertyOntology;

public class OntologyPanel {
	
	public VerticalPanel ontologyPanel;
	private static Map<String,String> sources = new HashMap<String,String>();
	private IOntologyPropertyReader rdfPropertyReader;
	
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
		
		final TextBox searchBox = new TextBox();
		
		//default use SensorML ontology
		//rdfPropertyReader = new RdfPropertyReader();
		//Panel resultTablePanel = rdfPropertyReader.createTable();
		
		rdfPropertyReader = new SensorMLPropertyOntology();
		Panel resultTablePanel = rdfPropertyReader.createTable();
		
		final HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(new HTML("Source :"+SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE));
		hPanel.add(sourcesBox);
		hPanel.add(new HTML("Search :"+SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE));
		hPanel.add(searchBox);
		
		final VerticalPanel vPanel = new VerticalPanel();
		vPanel.add(hPanel);
		vPanel.add(resultTablePanel);
		
		ontologyPanel.add(vPanel);
		
		//load ontology 
		rdfPropertyReader.loadOntology(sources.get(sourcesBox.getSelectedValue()));
		
		sourcesBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				//rdfPropertyReader.loadOntology(sources.get(sourcesBox.getSelectedValue()));
				String source = sourcesBox.getSelectedValue();
				
				Panel resultTablePanel =null;
				
				if(source.equals("SensorML Property")) {
					rdfPropertyReader = new SensorMLPropertyOntology();
				} else {
					rdfPropertyReader = new RdfPropertyReader();
				}
				
				resultTablePanel = rdfPropertyReader.createTable();
				
				vPanel.clear();
				vPanel.add(hPanel);
				vPanel.add(resultTablePanel);
				
				rdfPropertyReader.loadOntology(sources.get(sourcesBox.getSelectedValue()));
				
			}
		});
		
		//add key listener on searchBox
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				rdfPropertyReader.setFilter(searchBox.getText());
			}
		});
	}
	
	public String getSelectedValue() {
		return rdfPropertyReader.getSelectedValue();
	}
	
	public Panel getPanel() {
		return ontologyPanel;
	}
	
	
	//ALGO PART
}
