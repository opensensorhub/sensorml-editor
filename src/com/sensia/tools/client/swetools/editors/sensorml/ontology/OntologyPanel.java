package com.sensia.tools.client.swetools.editors.sensorml.ontology;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.user.client.ui.*;
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
		
		// source list
		SMLHorizontalPanel sourcesPanel = new SMLHorizontalPanel();
		ontologyPanel.add(sourcesPanel);
		sourcesPanel.add(new HTML("Source:"+SensorConstants.HTML_SPACE));
		final ListBox sourcesBox = new ListBox();
        sourcesBox.addItem("XDOMES", "https://xdomes.org/sparql");
        sourcesBox.addItem("MMI", "http://mmisw.org/sparql");
        sourcesBox.setStyleName("ontology-sourcesBox");
        sourcesPanel.add(sourcesBox);
		
		// search box
		final SMLHorizontalPanel searchPanel = new SMLHorizontalPanel();
		ontologyPanel.add(searchPanel);
		final TextBox searchBox = new TextBox();
		searchPanel.add(new HTML("Search:"+SensorConstants.HTML_SPACE));
		searchPanel.add(searchBox);

		// add hard coded filter
        CheckBox uriCheckbox = new CheckBox("URI");
        CheckBox descriptionCheckbox = new CheckBox("Description");

        uriCheckbox.setValue(true);
        descriptionCheckbox.setValue(true);

        searchPanel.add(uriCheckbox);
        searchPanel.add(descriptionCheckbox);

        searchBox.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if(event.getNativeEvent().getKeyCode()  == KeyCodes.KEY_ENTER) {

                    String source = sourcesBox.getSelectedValue();
                    String searchTerm = searchBox.getValue();

                    // show loading indicator
                    searchPanel.add(new Image(GWT.getModuleBaseURL() + "images/ajax-loader-small.gif"));


                    // compute filter
                    List<IOntologySearch.ONTOLOGY_FILTER> filters = new ArrayList<>();
                    if (uriCheckbox.getValue()) {
                        filters.add(IOntologySearch.ONTOLOGY_FILTER.URI);
                    }

                    if (descriptionCheckbox.getValue()) {
                        filters.add(IOntologySearch.ONTOLOGY_FILTER.DESCRIPTION);
                    }

                    // execute search query
                    IOntologySearch onto = new MMIRegistry(source);
                    onto.search(searchTerm, new ILoadOntologyCallback() {
                        @Override
                        public void onLoad(List<String> headers, Object[][] data) {
                            ontologyTable = new GenericTable();
                            ontologyTable.setEditable(false);
                            Panel panelTable = ontologyTable.createTable();
                            ontologyTable.populateTable(headers, data);
                            ontologyPanel.remove(2); // remove old table
                            searchPanel.remove(4); // remove loading indicator
                            ontologyPanel.add(panelTable);
                            ontologyPanel.fireEvent(new ResizeEvent(0, 0) {
                            });
                        }
                    }, filters);
                }
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
}
