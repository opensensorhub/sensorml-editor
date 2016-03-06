package com.sensia.tools.client.swetools.editors.sensorml.ontology;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;

public class OntologyPanel {
	public VerticalPanel ontologyPanel;
	private static List<String> sources = new ArrayList<String>();
	private SensorMLOntology sensorMLOntology;
	
	static {
		sources.add("SensorML");
	}
	
	public OntologyPanel(){
		ontologyPanel = new VerticalPanel();
		ontologyPanel.setStyleName("ontology-panel");
		//create list of sources url pointing to the ontology server
		final ListBox sourcesBox = new ListBox();
		for(final String source : sources) {
			sourcesBox.addItem(source);
		}
		sourcesBox.setStyleName("ontology-sourcesBox");
		
		final TextBox searchBox = new TextBox();
		
		//default use SensorML ontology
		sensorMLOntology = new SensorMLOntology();
		
		Panel resultTablePanel = sensorMLOntology.createTable();
		
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
		sensorMLOntology.loadOntology();
		
		//add key listener on searchBox
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				sensorMLOntology.setFilter(searchBox.getText());
				
			}
		});
	}
	
	public String getSelectedValue() {
		return sensorMLOntology.getSelectedValue();
	}
	
	public Panel getPanel() {
		return ontologyPanel;
	}
	
	
	//ALGO PART
}
