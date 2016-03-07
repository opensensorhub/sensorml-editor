package com.sensia.tools.client.swetools.editors.sensorml.ontology.property;

import java.util.ArrayList;
import java.util.List;

public class Property {

	public List<String> properties;
	
	public Property(final int size) {
		properties=new ArrayList<String>(size);
		for(int i=0;i< size;i++) {
			properties.add(null);
		}
	}
}
