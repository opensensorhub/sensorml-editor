package com.sensia.tools.client.swetools.editors.sensorml.panels.charts.table;

public interface IDataChangeListener {

	void notifyAddChanges(Number[][] values);
	void notifyRemoveChanges(Number[][] values);
}
