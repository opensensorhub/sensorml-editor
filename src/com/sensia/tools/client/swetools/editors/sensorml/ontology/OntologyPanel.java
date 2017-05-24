package com.sensia.tools.client.swetools.editors.sensorml.ontology;

import java.util.List;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.ILoadOntologyCallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.IOntologySearch;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

public class OntologyPanel {
	
	public SMLVerticalPanel ontologyPanel;
	private GenericTable ontologyTable;
	
	
	public OntologyPanel(){
		ontologyPanel = new SMLVerticalPanel();
		ontologyPanel.addStyleName("ontology-panel");
		ontologyTable = new GenericTable();
        ontologyTable.setEditable(false);
		
		// dialog panels
		SMLHorizontalPanel hPanel;
		
        // source list
		hPanel = new SMLHorizontalPanel();
		ontologyPanel.add(hPanel);
        hPanel.add(new HTML("Source:"+SensorConstants.HTML_SPACE));
		final ListBox sourcesBox = new ListBox();
        sourcesBox.addItem("XDOMES", "https://xdomes.org/sparql");
        sourcesBox.addItem("MMI", "http://mmisw.org/sparql");
        sourcesBox.setStyleName("ontology-sourcesBox");
		hPanel.add(sourcesBox);
		
		// search box
		hPanel = new SMLHorizontalPanel();
		ontologyPanel.add(hPanel);
		final TextBox searchBox = new TextBox();
        hPanel.add(new HTML("Search:"+SensorConstants.HTML_SPACE));
        hPanel.add(searchBox);
        searchBox.addChangeHandler(new ChangeHandler() {            
            @Override
            public void onChange(ChangeEvent event) {
                String source = sourcesBox.getSelectedValue();
                String searchTerm = searchBox.getValue();
                
                IOntologySearch onto = new MMIRegistry(source);
                onto.search(searchTerm, new ILoadOntologyCallback() {                    
                    @Override
                    public void onLoad(List<String> headers, Object[][] data) {
                        ontologyTable = new GenericTable();
                        ontologyTable.setEditable(false);
                        Panel panelTable = ontologyTable.createTable();
                        ontologyTable.populateTable(headers, data);
                        ontologyPanel.remove(2);
                        ontologyPanel.add(panelTable);
                        ontologyPanel.fireEvent(new ResizeEvent(0,0) {});
                    }
                });             
            }
        });
        
        // result table
        Panel panelTable = ontologyTable.createTable();
        ontologyPanel.add(panelTable);
	}
	
	public String getSelectedValue() {
		return ontologyTable.getSelectedValue();
	}
	
	public Panel getPanel() {
		return ontologyPanel;
	}
	
	//ALGO PART
}
