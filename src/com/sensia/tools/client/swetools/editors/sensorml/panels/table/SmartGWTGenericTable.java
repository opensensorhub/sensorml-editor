package com.sensia.tools.client.swetools.editors.sensorml.panels.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.RemoveRecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RemoveRecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class SmartGWTGenericTable<T> implements ITable<T>{

	private ListGrid grid;
	private Canvas panel;
	private IDataChangeHandler handler;
	
	private List<String> attributes;
	private com.smartgwt.client.widgets.Button addNewEntryButton;
	
	public SmartGWTGenericTable(boolean editable) {
		grid = new ListGrid();
		grid.setWidth100();  
		grid.setHeight100();
		grid.setShowAllRecords(true);  
		grid.setCanEdit(editable);  
		grid.setEditEvent(ListGridEditEvent.CLICK);  
		grid.setEditByCell(editable); 
		grid.setAutoFitData(Autofit.HORIZONTAL);  
		grid.addStyleName("smartgwt-generic-table");
		grid.setCanRemoveRecords(editable);
		
		grid.addRemoveRecordClickHandler(new RemoveRecordClickHandler() {
			
			@Override
			public void onRemoveRecordClick(RemoveRecordClickEvent event) {
				if(handler != null) {
					handler.onChange();
				}
			}
		});
		
		grid.addEditCompleteHandler(new EditCompleteHandler() {
			
			@Override
			public void onEditComplete(EditCompleteEvent event) {
				if(handler != null) {
					handler.onChange();
				}
			}
		});
		
		if(editable) {
			addNewEntryButton = new com.smartgwt.client.widgets.Button("Add");
			addNewEntryButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
				
				@Override
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					grid.startEditingNew();  
				}
			});
		}
	}

	@Override
	public void poupulateTable(List<String> headers, T[][] values) {
		DataSource dataSource = new DataSource();
		attributes = new ArrayList<String>();
		dataSource.setID("dataSourceTableId");
		DataSourceField[] gridFields = new DataSourceField[headers.size()+1];

		// add headers
		int i=0;
		
		// add primary key
		DataSourceIntegerField pkField = new DataSourceIntegerField("itemPkID");  
        pkField.setHidden(true);  
        pkField.setPrimaryKey(true); 
        gridFields[i++] = pkField;
        
		for(String header:headers) {
			if(values.length > 0 && values[0].length > 0) {
				gridFields[i] = new DataSourceField("col_"+i, getFieldType(values[0][0]),header);
			} else {
				gridFields[i] = new DataSourceField("col_"+i, FieldType.TEXT,header);
			}
			attributes.add("col_"+i);
			i++;
		}
		
		dataSource.setFields(gridFields);
		dataSource.setClientOnly(true);
		grid.setDataSource(dataSource);
		grid.setAutoFetchData(true);  
		
		// add values
		ListGridRecord[] records = new ListGridRecord[values.length];
		int uniqueId = 0;
		ListGridRecord newRecord = null;
		for(i=0;i < values.length;i++) {
			newRecord = new ListGridRecord();
			newRecord.setAttribute("itemPkID",uniqueId++);
			for(int j=0; j < values[0].length;j++) {
				newRecord.setAttribute("col_"+(j+1), values[i][j]);
			}
			records[i] = newRecord;
		}
		dataSource.setTestData(records);	
		grid.redraw();
		grid.fetchData();
	}

	@Override
	public T[][] getValues() {
		DataSource dataSource = grid.getDataSource();
		Record [] records = dataSource.getCacheData();
		int nbCol = 0;
		if(attributes != null) {
			nbCol = attributes.size();
		}
		
		T[][] values = (T[][]) new Object[records.length][nbCol];
		int i=0;
		for(Record record:records) {
			int j=0;
			for(String attribute: attributes) {
				values[i][j] = (T) record.getAttributeAsObject(attribute);	
				j++;
			}
			i++;
		}
		
		return values;
	}
	
	public Canvas getTablePanel() {
		if(panel == null) {
			panel = new Canvas();
			panel.setWidth100();
			panel.setHeight100();
			// add Add button
			if(addNewEntryButton != null) {
				 VLayout vLayout = new VLayout();  
				 vLayout.setHeight100();
				 vLayout.setWidth100();
				 vLayout.addMember(grid);
				 vLayout.addMember(addNewEntryButton);
				 addNewEntryButton.addStyleName("table-add-button");
				 vLayout.addStyleName("smartgwt-generic-table-vlayout");
				 panel.addChild(vLayout);
			} else {
				panel.addChild(grid);
			}
			
			panel.adjustForContent(true);
		}
		
		return panel;
	}
	
	private FieldType getFieldType(T value) {
		if(value instanceof Integer) {
			return FieldType.INTEGER;
		} else if(value instanceof String) {
			return FieldType.TEXT;
		} else if(value instanceof Double) {
			return FieldType.FLOAT;
		} else if(value instanceof Float) {
			return FieldType.FLOAT;
		} else if(value instanceof Long) {
			return FieldType.TEXT;
		} else  {
			return FieldType.TEXT;
		}
	}
	
	public void addDataChangeHandler(IDataChangeHandler handler) {
		this.handler = handler;
	}
}
