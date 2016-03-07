package com.sensia.tools.client.swetools.editors.sensorml.ontology.property;

import com.google.gwt.user.client.ui.Panel;

public interface IOntologyPropertyReader {

	String getSelectedValue();
	
	Panel createTable();
	
	void loadOntology(String url);
	
	void setFilter(String filter);
}
