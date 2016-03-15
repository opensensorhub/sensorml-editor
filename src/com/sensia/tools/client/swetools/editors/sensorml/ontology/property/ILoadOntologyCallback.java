package com.sensia.tools.client.swetools.editors.sensorml.ontology.property;

import java.util.List;

public interface ILoadOntologyCallback {

	void onLoad(List<String> headers, Object[][] data);
}
