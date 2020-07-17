package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.dataarray;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.chart.GenericCurveChart;
import com.sensia.tools.client.swetools.editors.sensorml.panels.table.IDataChangeHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.table.ITable;
import com.sensia.tools.client.swetools.editors.sensorml.panels.table.SmartGWTGenericTable;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.line.EditGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEditEncodingPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.CloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.ModelHelper;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;

public class SWEEditDataArrayLinePanel extends EditGenericLinePanel<RNGElement> {

	private String [][] values;
	private String title;
	private List<String> axisNames;
	private String tokenSeparator;
	private String blockSeparator;
	private String decimalSeparator;
	
	private IPanel valuesPanel;
	private IPanel elementTypePanel;
	private IPanel encodingPanel;
	
	private int computeAcceptance=0;
	
	private Panel graphIconPanel;
	private Panel tableIconPanel;
	
	private List<Integer> codeIdx = new ArrayList<Integer>();
	
	private String label;
	
	protected boolean editable;
	
	// values + elementType + encoding
	private static final int COMPUTE_ACCEPTANCE = 3;
	
	public SWEEditDataArrayLinePanel(RNGElement tag,IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		container.setVisible(false);
		
		graphIconPanel = new SimplePanel();
		tableIconPanel = new SimplePanel();
		
		editable = true;
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("values")) {
			computeAcceptance++;
			valuesPanel = element;
		} else if(element.getName().equals("encoding")) {
			computeAcceptance++;
			encodingPanel = element;
			
		} else if(element.getName().equals("elementType")){
			computeAcceptance++;
			elementTypePanel = element;
		}
		
		if((computeAcceptance % COMPUTE_ACCEPTANCE) == 0) {
			handleEncoding(encodingPanel);
			handleValues(valuesPanel);
			handleElementType(elementTypePanel);			
			buildTable();
			buildGraph();
			container.setVisible(true);
		} 
	}
	
	protected void handleValues(IPanel<RNGElement> valuesPanel) {
		String valuesText = ((RNGElement)valuesPanel.getTag()).getChildValueText();
		if (valuesText != null) {
    	    String valuesStr = valuesText.trim().replaceAll("\\s+", " ");
    		String [] blocks = valuesStr.split(blockSeparator);
    		
    		if(blocks.length > 0) {
    			int nbElementInBlock  = blocks[0].split(tokenSeparator).length;
    			values = new String[blocks.length] [nbElementInBlock];
    			
    			int i=0;
    			for(final String block : blocks) {
    				final String[] valuesInBlock = block.split(tokenSeparator);
    				for(int j=0;j < valuesInBlock.length;j++) {
    					values[i][j] = valuesInBlock[j].trim();
    				}
    				i++;
    			}
    		}
		}
	}

	protected void handleEncoding(IPanel<RNGElement> encodingPanel) {
		if(encodingPanel instanceof SWEditEncodingPanel) {
			blockSeparator = ((SWEditEncodingPanel)encodingPanel).getBlockSeparator();
			tokenSeparator = ((SWEditEncodingPanel)encodingPanel).getTokenSeparator();
			decimalSeparator = ((SWEditEncodingPanel)encodingPanel).getDecimalSeparator();
		}
	}
	
	protected void handleElementType(IPanel<RNGElement> element) {
		List<RNGElement> fields = ModelHelper.findTags(SMLEditorConstants.SWE_NS_2, "field", element.getTag());
		
		axisNames = new ArrayList<String>();
		//looking for name/label
		title = "";
		int codeIdx = 0;
		
		for(int i=0; i < fields.size();i++) {
			RNGElement currentField = fields.get(i);
			
			// looking for label
			String niceLabel = Utils.toNiceLabel(ModelHelper.findLabel(currentField));
			List<RNGElement> labels = ModelHelper.findTags(SMLEditorConstants.SWE_NS_2, "label", currentField);
			if (!labels.isEmpty())
			    niceLabel = labels.get(0).getChildValueText();
			    
			// looking for uom
			List<RNGElement> uoms = ModelHelper.findTags(SMLEditorConstants.SWE_NS_2, "uom", currentField);
			if(uoms.size() > 0) {
				for(RNGElement uomElt : uoms) {
					String axisValue = "";
					
					RNGAttribute code = uomElt.getChildAttribute("code");
					if(code != null) {
						axisValue = Utils.getUOMSymbol(code.getChildValueText());
						codeIdx++; // increment code
						this.codeIdx.add(codeIdx);
					}
					
					// looking for axisID into parent
					if(uomElt.getParent() instanceof RNGElement) {
						RNGElement parent = (RNGElement) uomElt.getParent();
						RNGAttribute axisID = parent.getChildAttribute("axisID");
						if(axisID != null) {
							axisValue = axisID.getChildValueText() + " ("+axisValue+")";
						} else if(!axisValue.isEmpty()){
							// otherwise takes the field name
							axisValue = niceLabel + " ("+axisValue+")";
						} else {
							axisValue = niceLabel;
						}
					}
					axisNames.add(axisValue);
				}
			} else {
				codeIdx++;
			}
			// build title
			if (i == 0) {
				title = niceLabel;
			} else {
				title = niceLabel + " vs " + title;
			}
		}
		
		// override title if DataArray label is found
		if(label != null) {
			title = label;
		}
		
		Panel labelPanel = new SMLHorizontalPanel();
		labelPanel.add(new HTML(title));
		setLabel(labelPanel);
	}
	
	protected void buildTable() {
		Image tableImage = new Image(GWT.getModuleBaseURL()+"images/table.png");
		tableImage.setTitle("Table");
		
		FocusPanel tableImageWrapper = new FocusPanel(tableImage);
		//add icons
		tableImageWrapper.addStyleName("graphic-icon");
		
		//add listeners
		tableImageWrapper.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				final ITable<String> table = new SmartGWTGenericTable<String>(editable);
				Widget tablePanel = table.getTablePanel();
				
				//table.setEditable(editable);
				//Get headers
				//populates the table with the headers + data
				//headers are corresponding to the label/name of the field
				table.poupulateTable(axisNames, values);
				
				Utils.displayDialogBox(tablePanel,title,"dialog-datatable");
				
				// add handler for edit mode
				table.addDataChangeHandler(new IDataChangeHandler() {
					
					@Override
					public void onChange() {
						Object[][] values = table.getValues();
						// save into model
						RNGElement valueElt = (RNGElement) valuesPanel.getTag();
						
						String newValues = "";
						for(int i=0;i < values.length;i++) {
							for(int j=0;j < values[0].length;j++) {
								if(j == values[0].length-1) {
									newValues += values[i][j];
								} else {
									newValues += values[i][j]+tokenSeparator;
								}
							}
							newValues += blockSeparator;
						}
						valueElt.setChildValueText(newValues);
						handleValues(valuesPanel);
					}
				});
			}
		});
		
		tableIconPanel.add(tableImageWrapper);
		afterDotsPanel.add(tableIconPanel);
	}
	
	protected void buildGraph() {
		Image graphicImage = new Image(GWT.getModuleBaseURL()+"images/data_array.png");
		graphicImage.setTitle("Graphic");
		
		FocusPanel graphicImageWrapper = new FocusPanel(graphicImage);
		//add icons
		graphicImageWrapper.addStyleName("graphic-icon");
		
		//add listeners
		graphicImageWrapper.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				final GenericCurveChart chart = new GenericCurveChart();
				Widget chartPanel = chart.createChart(title, axisNames);
				chart.populateTable(title, axisNames, values);
				CloseWindow dialog = Utils.displayDialogBox(chartPanel,title,"dialog-chart");
				dialog.addResizedHandler(new ResizedHandler() {
					
					@Override
					public void onResized(ResizedEvent event) {
						chart.redraw();
					}
				});
			}
		});
		graphIconPanel.add(graphicImageWrapper);
		afterDotsPanel.add(graphIconPanel);
	}
	
	public void setTableVisible(boolean isVisible) {
		tableIconPanel.setVisible(isVisible);
	}
	
	public void setGraphVisible(boolean isVisible) {
		graphIconPanel.setVisible(isVisible);
	}
	
	public Object [] [] getValues() {
		return values;
	}

	public List<Integer> getCodeIdx() {
		return codeIdx;
	}
	
	public void setLabel(String label) {
		
	}
}