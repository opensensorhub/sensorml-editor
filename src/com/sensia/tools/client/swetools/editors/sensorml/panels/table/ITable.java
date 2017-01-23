package com.sensia.tools.client.swetools.editors.sensorml.panels.table;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

public interface ITable<T> {

	void poupulateTable(final List<String> headers,final T[][] values);
	
	public T[][] getValues();
	
	Widget getTablePanel();
	
	public void addDataChangeHandler(IDataChangeHandler handler);
}
