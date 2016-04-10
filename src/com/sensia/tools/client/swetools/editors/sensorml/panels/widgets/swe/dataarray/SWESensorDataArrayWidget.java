/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.dataarray;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

/**
 * The Class SWESensorDataArrayWidget is corresponding to the <swe:DataArray> element.
 */
public class SWESensorDataArrayWidget extends AbstractSensorElementWidget{

	/** The display graph. */
	private boolean displayGraph=true;
	
	/** The display table. */
	private boolean displayTable=true;
	
	/** The display title. */
	private boolean displayTitle=true;
	
	/** The container. */
	private Panel container;
	
	/** The is init. */
	private boolean isInit = false;
	
	/** The def panel. */
	private Panel defPanel;
	
	/** The title panel. */
	private Panel titlePanel;
	
	/** The icon panel. */
	private Panel iconPanel;
	
	/**
	 * Instantiates a new SWE sensor data array widget.
	 */
	public SWESensorDataArrayWidget() {
		super("DataArray", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		defPanel = new HorizontalPanel();
		titlePanel = new HorizontalPanel();
		iconPanel = new HorizontalPanel();
		container = new HorizontalPanel();
		
		//add title icon def
		container.add(titlePanel);
		container.add(iconPanel);
		container.add(defPanel);
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getPanel()
	 */
	@Override
	public Panel getPanel() {
		if(!isInit) {
			build();
			isInit = true;
		}
		return container;
	}

	/**
	 * Builds the.
	 */
	private void build() {
		//------- BUILD TITLE
		if(displayTitle) {
			buildTitle();
		}
		
		//------- BUILD GRAPH
		if(displayGraph) {
			buildGraph();
		}
		
		//------- BUILD TABLE
		if(displayTable) {
			buildTable();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE)
	 */
	@Override
	protected void activeMode(MODE mode) {
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#refresh()
	 */
	@Override
	public void refresh() {
		titlePanel.clear();
		
		if(displayTitle) {
			buildTitle();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(final ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("definition")){
			defPanel.add(widget.getPanel());
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorDataArrayWidget();
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#appendTo()
	 */
	@Override
	public APPENDER appendTo() {
		return APPENDER.HORIZONTAL_STRICT;
	}
	
	/**
	 * Builds the title.
	 */
	private void buildTitle() {
		final String title = SWESensorDataArrayHelper.getTitle(getFields());
		titlePanel.add(new HTML(title));
	}
	
	/**
	 * Builds the graph.
	 */
	private void buildGraph() {
		Image graphicImage = new Image(GWT.getModuleBaseURL()+"images/data_array.png");
		graphicImage.setTitle("Graphic");
		
		FocusPanel graphicImageWrapper = new FocusPanel(graphicImage);
		//add icons
		graphicImageWrapper.addStyleName("graphic-icon");
		
		//add listeners
		graphicImageWrapper.addClickHandler(new GraphicImageWrapperHandler());
				
		iconPanel.add(graphicImageWrapper);
	}
	
	/**
	 * Builds the table.
	 */
	private void buildTable() {
		Image tableImage = new Image(GWT.getModuleBaseURL()+"images/table.png");
		tableImage.setTitle("Table");
		
		FocusPanel tableImageWrapper = new FocusPanel(tableImage);
		//add icons
		tableImageWrapper.addStyleName("graphic-icon");
		
		//add listeners
		tableImageWrapper.addClickHandler(new TableImageWrapperHandler());
		
		iconPanel.add(tableImageWrapper);
	}
	
	/**
	 * Checks if is display table.
	 *
	 * @return true, if is display table
	 */
	public boolean isDisplayTable() {
		return displayTable;
	}

	/**
	 * Sets the display table.
	 *
	 * @param displayTable the new display table
	 */
	public void setDisplayTable(boolean displayTable) {
		this.displayTable = displayTable;
	}

	/**
	 * Checks if is display graph.
	 *
	 * @return true, if is display graph
	 */
	public boolean isDisplayGraph() {
		return displayGraph;
	}

	/**
	 * Sets the display graph.
	 *
	 * @param displayGraph the new display graph
	 */
	public void setDisplayGraph(boolean displayGraph) {
		this.displayGraph = displayGraph;
	}

	/**
	 * Checks if is display title.
	 *
	 * @return true, if is display title
	 */
	public boolean isDisplayTitle() {
		return displayTitle;
	}

	/**
	 * Sets the display title.
	 *
	 * @param displayTitle the new display title
	 */
	public void setDisplayTitle(boolean displayTitle) {
		this.displayTitle = displayTitle;
	}
	
	/**
	 * The Class GraphicImageWrapperHandler.
	 */
	private class GraphicImageWrapperHandler implements ClickHandler{

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		@Override
		public void onClick(ClickEvent event) {
			
			//get headers + values and reset chart + table
			final Object[][] values = getObjectValues();
			
			List<String> axis    = SWESensorDataArrayHelper.getTableHeaders(getFields());
			
			String title = SWESensorDataArrayHelper.getTitle(getFields());
			
			if(values.length != getElementCount()) {
				Window.alert("DataArray: The number of fields is not corresponding to the elementCount");
			} else {
				final GenericCurveChart chart = new GenericCurveChart();
				
				Panel chartPanel = chart.createChart(title);
				
				chart.populateTable(title, axis, values);
				
				displayEditPanel(chartPanel,"View DataArray values as Chart",null);
			}
		}
		
	}
	
	/**
	 * The Class TableImageWrapperHandler.
	 */
	private class TableImageWrapperHandler implements ClickHandler{

		
		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		@Override
		public void onClick(ClickEvent event) {
			
			final Object[][] values = getObjectValues();
			List<String> headers    = getHeaders();
			
			if(values.length != getElementCount()) {
				Window.alert("DataArray: The number of fields is not corresponding to the elementCount");
			} else {
				final GenericTable table = new GenericTable();
				Panel tablePanel = table.createTable();
				
				table.setEditable(getMode() == MODE.EDIT);
				//Get headers
				//populates the table with the headers + data
				//headers are corresponding to the label/name of the field
				table.poupulateTable(headers, values);
				
				IButtonCallback saveCB = null;
				
				if(getMode() == MODE.EDIT) {
					saveCB = new IButtonCallback() {
						
						@Override
						public void onClick() {
							//get values from table and update RNG model
							Object[][] values = table.getValues();
							updateValues(values);
						}
					};
				}
				displayEditPanel(tablePanel,"View DataArray values as Table",saveCB);
			}
		}
	}

	/**
	 * Update values.
	 *
	 * @param values the values
	 */
	private void updateValues(Object[][] values) {
		String valuesStr = "";
		String tokenSeparator = getTokenSeparator();
		String blockSeparator = getBlockSeparator();
		
		for(int i=0;i < values.length;i++) {
			for(int j=0;j < values[0].length;j++) {
				valuesStr += values[i][j];
				if(j < values[0].length -1) {
					valuesStr += tokenSeparator;
				}
			}
			
			if(i < values.length -1 ){
				valuesStr += blockSeparator;
			}
		}
		
		ISensorWidget valuesWidget = findWidget(this, "values",TAG_TYPE.ELEMENT);
		if(valuesWidget != null) {
			valuesWidget.setValue("values", valuesStr);
		} else {
			GWT.log("Cannot update the values because the widget has not been found");
		}
		
		ISensorWidget elementCountWidget = findWidget(this, "elementCount",TAG_TYPE.ELEMENT);
		if(valuesWidget != null) {
			elementCountWidget.setValue("value", values.length+"");
		} else {
			GWT.log("Cannot update the elementCount because the widget has not been found");
		}
	}
	
	/**
	 * Gets the block separator.
	 *
	 * @return the block separator
	 */
	public String getBlockSeparator() {
		String result = "";
		for(final ISensorWidget child : getElements()) {
			if(child.getName().equals("encoding")) {
				result = child.getValue("blockSeparator",true);
				break;
			}
		}
		return result;
	}
	
	/**
	 * Gets the token separator.
	 *
	 * @return the token separator
	 */
	public String getTokenSeparator() {
		String result = "";
		for(final ISensorWidget child : getElements()) {
			if(child.getName().equals("encoding")) {
				result = child.getValue("tokenSeparator",true);
				break;
			}
		}
		return result;
	}
	
	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public String getValues() {
		String valuesStr = "";
		for(final ISensorWidget child : getElements()) {
			 if(child.getName().equals("values")) {
				valuesStr = child.getValue("values", true);
				if(valuesStr != null) {
					valuesStr = valuesStr.replaceAll("\n", "").replaceAll("\\s+", " ").trim();
				}
				break;
			} 
		}
		return valuesStr;
	}
	
	/**
	 * Gets the element count.
	 *
	 * @return the element count
	 */
	public int getElementCount() {
		int elementCount = 0;
		for(final ISensorWidget child : getElements()) {
			if(child.getName().equals("elementCount")) {
				elementCount = Integer.parseInt(child.getValue("value",true));
				break;
			} 
		}
		return elementCount;
	}
	
	/**
	 * Gets the fields.
	 *
	 * @return the fields
	 */
	public List<ISensorWidget> getFields() {
		List<ISensorWidget> fields = null;
		for(final ISensorWidget child : getElements()) {
			 if(child.getName().equals("elementType")) {
				fields = findWidgets(child, "field", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
				break;
			} 
		}
		return fields;
	}
	
	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public List<String> getHeaders() {
		return SWESensorDataArrayHelper.getTableHeaders(getFields());
	}
	
	/**
	 * Gets the object values.
	 *
	 * @return the object values
	 */
	public Object[][] getObjectValues() {
		String valuesStr = getValues();
		String blockSeparator = getBlockSeparator();
		String tokenSeparator = getTokenSeparator();
		int elementCount = getElementCount();
		
		//compute nbSeries (excluding X AXIS)
		final List<String> blocks = Arrays.asList(valuesStr.split(blockSeparator));
		
		return SWESensorDataArrayHelper.getValues(blocks, tokenSeparator, elementCount);
	}
	
}
