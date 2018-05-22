/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/


package com.sensia.tools.client.swetools.editors.sensorml.ontology;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ICallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.Property;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

/**
 * The Class GenericTable allows to create generic table from any Property objects.
 */
public class GenericTable{

	/** The data provider. */
	private ListDataProvider<Property> dataProvider;
	
	/** The table res. */
	private CellTable.Resources tableRes = GWT.create(TableRes.class);
	
	/** The table. */
	@UiField(provided=true)
	private CellTable<Property> table;
	
	/** The selected property. */
	private Property selectedProperty;
	
	/** The original data. */
	private List<Property> originalData;
	
	/** The filtered data. */
	private List<Property> filteredData;
	
	/** The lentgh pattern. */
	private int lentghPattern = 0;
	
	/** The is editable. */
	private boolean isEditable = false;

	private boolean isSortable = false;

	/**
	 * Instantiates a new generic table.
	 */
	public GenericTable() {
		init();
	}

	private ICallback<Integer> removeHandlerCallback;

	/**
	 * Inits the.
	 */
	private void init() {
		//init global values
		dataProvider = new ListDataProvider<Property>();
		originalData = new ArrayList<Property>();
		filteredData = new ArrayList<Property>();
	}
	
	/**
	 * Populates the table.
	 *
	 * @param headers the headers to display
	 * @param values the values to set
	 */
	public void populateTable(final List<String> headers,final Object[][] values) {
		//clears/creates the list
		init();
		this.originalData = getPropertiesFromValues(values);
		
		if(table == null) {
			//creates the table object
			createTable();
		} else {
			//remove every columns
			int nbColumn = table.getColumnCount();
			for(int i=0;i< nbColumn;i++) {
				table.removeColumn(i);
			}
		}		
		
		int position = 0;
		//add every column based on header list
		for(final String colName : headers) {
			final int currentPosition = position;
			final Column<Property, String> column = new Column<Property, String>(new TextCell()) {
				
				@Override
				public String getValue(Property object) {
					return object.properties.get(currentPosition);
				}
			};
			column.setSortable(isSortable);

			SafeHtmlHeader colHeader = new SafeHtmlHeader(new SafeHtml() {

				@Override
				public String asString() {
					  return "<p style=\"text-align:center;\">"+colName+"</p>"; 
				} 

		    }); 
			colHeader.setHeaderStyleNames("data-table-header");
			
			table.addColumn(column,colHeader);
			position++;
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
			
			//updates when the user remove a row
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

				    if(removeHandlerCallback != null) {
						removeHandlerCallback.callback(new Integer(index));
					}
				    }
			});
			
			table.addColumn(removeColumn, removeRowLabelHeader);
		}
		
		table.setRowCount(originalData.size());
	    table.setRowData(originalData);
	    
	    dataProvider.setList(originalData);
	    dataProvider.refresh();
	}
	
	
	/**
	 * Creates the table.
	 *
	 * @return the panel
	 */
	public Panel createTable() {
		if(table == null) {
			table  = new CellTable<Property>(10,tableRes);
			table.setStyleName("ontology-table-result");
			
			dataProvider.addDataDisplay(table);
			
			table.setSkipRowHoverCheck(true);
		    table.setSkipRowHoverFloatElementCheck(true);
			table.setSkipRowHoverStyleUpdate(true);
			table.setVisibleRange(0, 100000);
			table.setWidth("100%", true);
			table.setColumnWidth(2, 60.0, Unit.PX);
			
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
		
		SMLVerticalPanel vPanel = new SMLVerticalPanel();
		
		/*SMLHorizontalPanel hPanel = new SMLHorizontalPanel();
		vPanel.add(hPanel);		
		final TextBox searchBox = new TextBox();
		hPanel.add(new HTML("Search :"+SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE));
		hPanel.add(searchBox);
        searchBox.addKeyUpHandler(new KeyUpHandler() {
            
            @Override
            public void onKeyUp(KeyUpEvent event) {
                setFilter(searchBox.getText());
            }
        });*/
		
		/*ScrollPanel sPanel = new ScrollPanel();
		sPanel.setStyleName("ontology-table-panel");
		sPanel.add(table);
		vPanel.add(sPanel);*/
		vPanel.add(table);
		
		return vPanel;
	}

	public void setSortable(boolean sortable) {
		isSortable = sortable;
	}

	/**
	 * Sets the filter.
	 *
	 * @param pattern the new filter
	 */
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
	
	/**
	 * Deselect.
	 */
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
	
	/**
	 * Filter pattern.
	 *
	 * @param filterText the filter text
	 * @param inputList the input list
	 * @return the list
	 */
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
	
	/**
	 * Gets the selected value.
	 *
	 * @return the selected value
	 */
	public String getSelectedValue() {
		String value = null;
		if(selectedProperty != null) {
			//find def property
			//we supposed that it exists at least one
			value = selectedProperty.properties.get(0);

		}
		return value;
	}
	
	/**
	 * Gets the properties from values.
	 *
	 * @param values the values
	 * @return the properties from values
	 */
	private List<Property> getPropertiesFromValues(final Object[][] values ) {
		final List<Property> properties = new ArrayList<Property>();
		
		for(int i=0;i < values.length;i++){
			final Property property = new Property(values[0].length);
			for(int j=0;j < values[i].length;j++) {
				if(values[i][j] != null) {
					property.properties.set(j,values[i][j].toString());
				} else {
					property.properties.set(j,"");
				}
			}
			properties.add(property);
		}
		return properties;
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
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
	
	/**
	 * Gets the removes the row label header.
	 *
	 * @return the removes the row label header
	 */
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
	
	/**
	 * Sets the editable.
	 *
	 * @param isEditable the new editable
	 */
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public void onRemoveHandler(ICallback<Integer> callback) {
		this.removeHandlerCallback = callback;
	}
}