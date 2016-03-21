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

public class SWESensorDataArrayWidget extends AbstractSensorElementWidget{

	private boolean displayGraph=true;
	private boolean displayTable=true;
	private boolean displayTitle=true;
	
	private Panel container;
	
	private boolean isInit = false;
	
	private Panel defPanel;
	private Panel titlePanel;
	private Panel iconPanel;
	
	public SWESensorDataArrayWidget() {
		super("DataArray", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		defPanel = new HorizontalPanel();
		titlePanel = new HorizontalPanel();
		iconPanel = new HorizontalPanel();
		container = new HorizontalPanel();
		
		container.add(titlePanel);
		container.add(iconPanel);
		container.add(defPanel);
	}

	@Override
	public Panel getPanel() {
		if(!isInit) {
			build();
			isInit = true;
		}
		return container;
	}

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
	
	@Override
	protected void activeMode(MODE mode) {
		
	}

	@Override
	public void refresh() {
		titlePanel.clear();
		
		if(displayTitle) {
			buildTitle();
		}
	}
	
	@Override
	protected void addSensorWidget(final ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("definition")){
			defPanel.add(widget.getPanel());
		}
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorDataArrayWidget();
	}

	@Override
	public APPENDER appendTo() {
		return APPENDER.HORIZONTAL_STRICT;
	}
	
	private void buildTitle() {
		final String title = SWESensorDataArrayHelper.getTitle(getFields());
		titlePanel.add(new HTML(title));
	}
	
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
	
	public boolean isDisplayTable() {
		return displayTable;
	}

	public void setDisplayTable(boolean displayTable) {
		this.displayTable = displayTable;
	}

	public boolean isDisplayGraph() {
		return displayGraph;
	}

	public void setDisplayGraph(boolean displayGraph) {
		this.displayGraph = displayGraph;
	}

	public boolean isDisplayTitle() {
		return displayTitle;
	}

	public void setDisplayTitle(boolean displayTitle) {
		this.displayTitle = displayTitle;
	}
	
	private class GraphicImageWrapperHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			
			final Object[][] values = getObjectValues();
			
			List<String> axis    = SWESensorDataArrayHelper.getTableHeaders(getFields());
			
			String title = SWESensorDataArrayHelper.getTitle(getFields());
			
			if(values.length != getElementCount()) {
				Window.alert("DataArray: The number of fields is not corresponding to the elementCount");
			} else {
				final GenericCurveChart chart = new GenericCurveChart();
				
				Panel chartPanel = chart.createChart(title);
				
				chart.poupulateTable(title, axis, values);
				
				displayEditPanel(chartPanel,"View DataArray values as Chart",null);
			}
		}
		
	}
	
	private class TableImageWrapperHandler implements ClickHandler{

		
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
	
	public List<String> getHeaders() {
		return SWESensorDataArrayHelper.getTableHeaders(getFields());
	}
	
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
