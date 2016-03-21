package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.dataarray;

import java.util.ArrayList;
import java.util.List;

import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public final class SWESensorDataArrayHelper {
	private static final String VS = " vs ";
	
	private SWESensorDataArrayHelper() {}
	
	/**
	 * Get the list of headers corresponding to the fields. The headers are the list of the UOM values of the Quantity/Time.
	 * @param fields The concerned fields
	 * @return The list of the UOM in the order
	 */
	public static List<String> getTableHeaders(List<ISensorWidget> fields) {
		final List<String> headers = new ArrayList<String>();
		
		//headers are provided by Label(UOM) from Widget
		//handle data 
		for(final ISensorWidget field : fields) {
			String label = "";
			String uom = "";
			
			//handle Vector
			ISensorWidget widget = AbstractSensorElementWidget.findWidget(field, "Vector", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
			if(widget != null) {
				List<ISensorWidget> coordinates = AbstractSensorElementWidget.findWidgets(field, "coordinate", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
				
				//add UOM if any
				uom = getUom(widget, uom);
				
				for(ISensorWidget coordinate : coordinates) {
					label = coordinate.getValue("name",false);
					
					//add to header list
					headers.add(getHeader(label,uom));
				}
				
			} else {
			
				if(widget == null) {
				//handle Category
					widget = AbstractSensorElementWidget.findWidget(field, "Category", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
					if(widget != null) {
						
					}
				} 
				
				//handle Time
				if(widget == null) {
					widget = AbstractSensorElementWidget.findWidget(field, "Time", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
					if(widget != null) {
						String tLabel = getWidgetValue(widget,"label");
						
						//add label if any
						if(tLabel != null) {
							label = tLabel;
						}
						
						//add UOM if any
						uom = getUom(widget, uom);
					}
				}
				
				//handle Quantity
				if(widget == null) {
					widget = AbstractSensorElementWidget.findWidget(field, "Quantity", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
					if(widget != null) {
						String qLabel = getWidgetValue(widget,"label");
						
						//add label if any
						if(qLabel != null) {
							label = qLabel;
						}
						
						//add UOM if any
						uom = getUom(widget, uom);
					}
				}
				
				//add to header list
				headers.add(getHeader(label,uom));
			}
		}
				
		return headers;
		
	}
	
	private static String getHeader(String label,String uom) {
		String result = "";
		if(label != null && !label.isEmpty()) {
			result = label;
			if(uom != null && !uom.isEmpty()) {
				result +=" ("+uom+")";
			} 
		} else {
			result = uom;
		}
		
		return result;
	}
	
	private static String getUom(ISensorWidget widget,String originalValue) {
		String result = originalValue;
		//add UOM if any
		ISensorWidget uomWidget = AbstractSensorElementWidget.findWidget(widget, "uom", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		if(uomWidget != null) {
			String qUom = uomWidget.getValue("code", true);
			if(qUom != null) {
				result = Utils.getUOMSymbol(qUom);
			}
		}
		
		return result;
	}
	
	private static String getWidgetValue(ISensorWidget widget,String value) {
		String labelStr = "";
		if(widget != null) {
			ISensorWidget label = AbstractSensorElementWidget.findWidget(widget, value, TAG_DEF.SWE, TAG_TYPE.ELEMENT);
			if(label != null) {
				String labelValue = label.getValue(value, true);
				if(labelValue != null) {
					labelStr = labelValue;
				}
			}
		}
		
		return labelStr;
	}
	
	/**
	 * Get the title from the labels/name of the fields.
	 * @param fields The fields widget tags
	 * @return The title built as label1 vs label2 vs .. labeln
	 */
	public static String getTitle(final List<ISensorWidget> fields) {
		//<swe:DataRecord> <swe:field name=".."> ... </swe:field> </swe:DataRecord>
		String concatenedAxisNames = "";
		for(ISensorWidget field : fields) {
			//prior label
			String labelField = field.getValue("label", true);
			if(labelField == null) {
				//if no label, takes the attribute name
				labelField = AbstractSensorElementWidget.toNiceLabel(field.getValue("name", true));
			}
			concatenedAxisNames +=  labelField + VS;
		}
		
		//remove extra "vs"
		if(!concatenedAxisNames.isEmpty()) {
			concatenedAxisNames = concatenedAxisNames.substring(0, concatenedAxisNames.length()-VS.length());
		}
		
		return concatenedAxisNames;
	}
	
	public static List<String> getTitles(final List<ISensorWidget> fields) {
		List<String> titles = new ArrayList<String>();
		
		for(ISensorWidget field : fields) {
			//prior label
			String labelField = field.getValue("label", true);
			if(labelField == null) {
				//if no label, takes the attribute name
				labelField = field.getValue("name", true);
			}
			titles.add(labelField);
		}
		
		return titles;
	}
	/**
	 * Get the values as a list of Object.
	 * @param blocks
	 * @param tokenSeparator
	 * @param elementCount
	 * @return
	 */
	public static Object[][] getValues(final List<String> blocks,final String tokenSeparator, final int elementCount) {
		
		Object[][] values = null;
		
		if(blocks.size() > 0) {
			int nbElementInBlock  = blocks.get(0).split(tokenSeparator).length;
			values = new Object[blocks.size()] [nbElementInBlock];
			
			int i=0;
			for(final String block : blocks) {
				final String[] valuesInBlock = block.split(tokenSeparator);
				for(int j=0;j < valuesInBlock.length;j++) {
					values[i][j] = valuesInBlock[j].trim().replaceAll("\\s+", " ");
				}
				i++;
			}
		}
		return values;
	}
	
	
}
