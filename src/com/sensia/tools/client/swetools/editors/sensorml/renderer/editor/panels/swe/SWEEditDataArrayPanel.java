package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.chart.GenericCurveChart;
import com.sensia.tools.client.swetools.editors.sensorml.panels.chart.GenericTable;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.ModelHelper;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * This class is in charge of building the data array line + icons.
 * Several elements have to be found before creating graphic + table structures.
 * These elements are:
 * - At least two fields containing a quantity
 * - values (from swe:values)
 * - encoding (from swe:TextEncoding) providing  token, block and decimal separators 
 * @author mathieu.dhainaut@gmail.com
 *
 */
public class SWEEditDataArrayPanel extends AbstractPanel<RNGElement>{

	private SWEEditDataArrayLinePanel linePanel;
	private Panel beforeLineContentPanel;
	
	public SWEEditDataArrayPanel(RNGElement tag) {
		super(tag);
		linePanel = new SWEEditDataArrayLinePanel(tag);
		
		beforeLineContentPanel = new VerticalPanel();
		
		container.add(beforeLineContentPanel);
		container.add(linePanel.getPanel());
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("values")) {
			linePanel.addElement(element);	
		} else if(element.getName().equals("encoding")) {
			linePanel.addElement(element);
		} else if(element.getName().equals("elementType")){
			linePanel.addElement(element);
			beforeLineContentPanel.add(element.getPanel());
		}
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
	
	class SWEEditDataArrayLinePanel extends AbstractGenericLinePanel<RNGElement> {

		private Object [][] values;
		private String title;
		private List<String> axis;
		private String tokenSeparator;
		private String blockSeparator;
		private String decimalSeparator;
		
		private IPanel valuesPanel;
		private IPanel elementTypePanel;
		private IPanel encodingPanel;
		
		private int computeAcceptance=0;
		
		// values + elementType + encoding
		private static final int COMPUTE_ACCEPTANCE = 3;
		
		public SWEEditDataArrayLinePanel(RNGElement tag) {
			super(tag);
			container.setVisible(false);
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
			String valuesStr = ((RNGElement)valuesPanel.getTag()).getChildValueText().trim().replaceAll("\\s+", " ");
			String [] blocks = valuesStr.split(blockSeparator);
			
			if(blocks.length > 0) {
				int nbElementInBlock  = blocks[0].split(tokenSeparator).length;
				values = new Object[blocks.length] [nbElementInBlock];
				
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

		protected void handleEncoding(IPanel<RNGElement> encodingPanel) {
			if(encodingPanel instanceof SWEditEncodingPanel) {
				blockSeparator = ((SWEditEncodingPanel)encodingPanel).getBlockSeparator();
				tokenSeparator = ((SWEditEncodingPanel)encodingPanel).getTokenSeparator();
				decimalSeparator = ((SWEditEncodingPanel)encodingPanel).getDecimalSeparator();
			}
		}
		
		protected void handleElementType(IPanel<RNGElement> element) {
			List<RNGElement> fields = ModelHelper.findTags(SMLEditorConstants.SWE_NS_2, "field", element.getTag());
			
			axis = new ArrayList<String>();
			//looking for name/label
			title = "";
			for(int i=0; i < fields.size();i++) {
				RNGElement currentField = fields.get(i);
				// looking for label
				String niceLabel = Utils.toNiceLabel(ModelHelper.findLabel(currentField));
				
				// looking for uom
				List<RNGElement> uoms = ModelHelper.findTags(SMLEditorConstants.SWE_NS_2, "uom", currentField);
				if(uoms.size() > 0) {
					for(RNGElement uomElt : uoms) {
						RNGAttribute code = uomElt.getChildAttribute("code");
						if(code != null) {
							axis.add(Utils.getUOMSymbol(code.getChildValueText()));
						}
					}
				}
				// build title
				if(i == fields.size() -1 ) {
					title += niceLabel;
				} else {
					title += niceLabel + " vs ";
				}
				
			}
			Panel labelPanel = new HorizontalPanel();
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
					final GenericTable table = new GenericTable();
					Panel tablePanel = table.createTable();
					
					table.setEditable(false);
					//Get headers
					//populates the table with the headers + data
					//headers are corresponding to the label/name of the field
					table.poupulateTable(axis, values);
					
					Utils.displayDialogBox(tablePanel,title);
				}
			});
			
			afterDotsPanel.add(tableImageWrapper);
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
					
					Panel chartPanel = chart.createChart(title);
					
					chart.populateTable(title, axis, values);
					
					Utils.displayDialogBox(chartPanel,title);
				}
			});
			
			afterDotsPanel.add(graphicImageWrapper);
		}
	}
}
