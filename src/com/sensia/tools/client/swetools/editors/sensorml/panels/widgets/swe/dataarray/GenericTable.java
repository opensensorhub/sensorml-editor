package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.dataarray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.TableRes;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.Property;
import com.sensia.tools.client.swetools.editors.sensorml.panels.charts.table.XYCoordinatesModel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.BoyerMoore;

public class GenericTable extends Composite{

	private ListDataProvider<Property> dataProvider;
	
	private CellTable.Resources tableRes = GWT.create(TableRes.class);
	
	@UiField(provided=true)
	private CellTable<Property> table;
	
	private Property selectedProperty;
	
	private List<Property> originalData;
	private List<Property> filteredData;
	
	private int lentghPattern = 0;
	
	private boolean isEditable = false;
	
	public GenericTable() {
		init();
	}
	
	private void init() {
		//init global values
		dataProvider = new ListDataProvider<Property>();
		originalData = new ArrayList<Property>();
		filteredData = new ArrayList<Property>();
	}
	
	public void poupulateTable(final List<String> headers,final Object[][] values) {
		init();
		this.originalData = getPropertiesFromValues(values);
		
		final Map<String,Integer> classColDefMap = new HashMap<String,Integer>();
		for(int i=0; i < headers.size();i++) {
			classColDefMap.put(headers.get(i), i);
		}
		
		if(table == null) {
			createTable();
		} else {
			int nbColumn = table.getColumnCount();
			for(int i=0;i< nbColumn;i++) {
				table.removeColumn(i);
			}
		}
		
		Set<String> colNames = classColDefMap.keySet();
		
		for(final String colName : colNames) {
			final Column<Property, String> column = new Column<Property, String>(new TextCell()) {
				
				@Override
				public String getValue(Property object) {
					return object.properties.get(classColDefMap.get(colName));
				}
			};
			column.setSortable(false);

			SafeHtmlHeader colHeader = new SafeHtmlHeader(new SafeHtml() {

				@Override
				public String asString() {
					  return "<p style=\"text-align:center;\">"+colName+"</p>"; 
				} 

		    }); 
			colHeader.setHeaderStyleNames("data-table-header");
			
			table.addColumn(column,colHeader);
		}
		
		//add extra column if editable
		if(isEditable) {
			SafeHtmlHeader removeRowLabelHeader = getRemoveRowLabelHeader();
			final Column<Property, String> removeColumn = new Column<Property, String>(
							new ButtonCell()) {
			  @Override
			  public String getValue(Property c) {
			    return "x";
			  }
			};
			
			removeColumn.setSortable(false);
			
			removeColumn.setFieldUpdater(new FieldUpdater<Property, String>() {

				  @Override
				  public void update(int index, Property object, String value) {
					List<Property> newOriginalData = new ArrayList<Property>();
					for(int i=0;i < originalData.size();i++) {
						if( i != index) {
							newOriginalData.add(originalData.get(i));
						}
					}
					originalData = newOriginalData;
					
					table.setRowCount(originalData.size());
				    table.setRowData(originalData);
				    
				    dataProvider.setList(originalData);
				    dataProvider.refresh();
				    
				    deselect();
				    }
			});
			
			table.addColumn(removeColumn, removeRowLabelHeader);
		}
		
		table.setRowCount(originalData.size());
	    table.setRowData(originalData);
	    
	    dataProvider.setList(originalData);
	    dataProvider.refresh();
	}
	
	
	public Panel createTable() {
		if(table == null) {
			table  = new CellTable<Property>(10,tableRes);
			table.setStyleName("ontology-table-result");
			
			dataProvider.addDataDisplay(table);
			
			table.setSkipRowHoverCheck(true);
		    table.setSkipRowHoverFloatElementCheck(true);
			table.setSkipRowHoverStyleUpdate(true);
			table.setVisibleRange(0, 100000);
			
			// Add a selection model to handle user selection.
		    final SingleSelectionModel<Property> selectionModel = new SingleSelectionModel<Property>();
		    table.setSelectionModel(selectionModel);
		    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		    	  Property selected = selectionModel.getSelectedObject();
			      if (selected != null) {
			    	  selectedProperty = selected;
			      }
		      }
		    });
		}
		
		VerticalPanel vPanel = new VerticalPanel();
		final TextBox searchBox = new TextBox();
		
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(new HTML("Search :"+SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE));
		hPanel.add(searchBox);
		
		ScrollPanel sPanel = new ScrollPanel();
		sPanel.setStyleName("ontology-table-panel");
		sPanel.add(table);
		
		vPanel.add(hPanel);
		vPanel.add(sPanel);
		
		//add key listener on searchBox
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				setFilter(searchBox.getText());
			}
		});
				
		return vPanel;
	}
	
	public void setFilter(final String pattern) {
		if(pattern.isEmpty()) {
			if(lentghPattern > 0) {
				//restore original
				dataProvider.setList(originalData);
				
				table.setRowCount(originalData.size());
			    table.setRowData(originalData);
			    
			    dataProvider.setList(originalData);
			    dataProvider.refresh();
			    
			    filteredData.clear();
			} 
			lentghPattern = 0;
			//otherwise no filter is needed
		} else {
			lentghPattern = pattern.length();
			
			//use boyer Moore String matching algorithm to match corresponding pattern
			//BoyerMoore bm = new BoyerMoore(pattern);
			List<Property> newFilteredList = null;
			if(filteredData.isEmpty()) {
				newFilteredList = filterPattern(pattern, originalData);
			} else {
				//get filter direction
				if(pattern.length() > lentghPattern) {
					//up
					newFilteredList = filterPattern(pattern, filteredData);
				} else {
					//down
					newFilteredList = filterPattern(pattern, originalData);
				}
			}
			filteredData = newFilteredList;
			
			table.setRowCount(filteredData.size());
		    table.setRowData(filteredData);
		    
		    dataProvider.setList(filteredData);
		    dataProvider.refresh();
		    
		    
		    deselect();
		}
	}
	
	private void deselect() {
		if(selectedProperty != null) {
			table.getSelectionModel().setSelected(selectedProperty, false);
			selectedProperty = null;
		} else {
			for(Property p : originalData) {
				if(table.getSelectionModel().isSelected(p)) {
					table.getSelectionModel().setSelected(p, false);
				}
			}
			for(Property p : filteredData) {
				if(table.getSelectionModel().isSelected(p)) {
					table.getSelectionModel().setSelected(p, false);
				}
			}
		}
	}
	
	private List<Property>  filterPattern(final String filterText, final List<Property> inputList) {
		List<Property> newFilteredList = new ArrayList<Property>();
		for(final Property property : inputList) {
			//check defUrl
			for(String currentProperty : property.properties) {
				//if((bm.search(currentProperty.getBytes(), 0) > 1)) {
				if(currentProperty.indexOf(filterText) >= 0){
					newFilteredList.add(property);
					break;
				}
			}
		}
		return newFilteredList;
	}
	
	public String getSelectedValue() {
		String value = null;
		if(selectedProperty != null) {
			//find def property
			//we supposed that it exists at least one
			value = selectedProperty.properties.get(0);
			
		}
		return value;
	}
	
	private List<Property> getPropertiesFromValues(final Object[][] values ) {
		final List<Property> properties = new ArrayList<Property>();
		
		for(int i=0;i < values.length;i++){
			final Property property = new Property(values[0].length);
			for(int j=0;j < values[i].length;j++) {
				property.properties.set(j,values[i][j].toString());
			}
			properties.add(property);
		}
		return properties;
	}

	public Object[][] getValues() {
		int colNumber = (!originalData.isEmpty())? originalData.get(0).properties.size() : 0;
		
		Object [][] values = new Object[originalData.size()] [colNumber];
		
		int i=0;
		for(Property p : originalData) {
			for(int j =0;j < p.properties.size();j++) {
				values[i][j] = p.properties.get(j);
			}
			i++;
		}
		return values;
	}
	
	private SafeHtmlHeader getRemoveRowLabelHeader() {
		SafeHtmlHeader removeRowLabelHeader = new SafeHtmlHeader(new SafeHtml() {

			@Override
			public String asString() {
				  return "<p style=\"text-align:center;\"></p>"; 
			} 

	    }); 
		removeRowLabelHeader.setHeaderStyleNames("data-table-header");
		return removeRowLabelHeader;
	}
	
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
}
