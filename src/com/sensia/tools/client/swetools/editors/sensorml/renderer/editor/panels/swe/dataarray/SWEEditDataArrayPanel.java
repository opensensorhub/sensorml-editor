package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.dataarray;

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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.chart.GenericCurveChart;
import com.sensia.tools.client.swetools.editors.sensorml.panels.chart.GenericTable;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.line.EditGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEditEncodingPanel;
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

	protected SWEEditDataArrayLinePanel linePanel;
	private Panel beforeLineContentPanel;
	
	private String label;
	
	public SWEEditDataArrayPanel(RNGElement tag) {
		super(tag);
		linePanel = getLinePanel(tag);
		
		beforeLineContentPanel = new VerticalPanel();
		
		container.add(beforeLineContentPanel);
		container.add(linePanel.getPanel());
	}

	protected SWEEditDataArrayLinePanel getLinePanel(RNGElement tag) {
		return new SWEEditDataArrayLinePanel(tag);
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
		} else if(element.getName().equals("label")) {
			label = ((RNGElement)element.getTag()).getChildValueText();
			linePanel.setLabel(label);
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
	
	public void setTableVisible(boolean isVisible) {
		linePanel.setTableVisible(isVisible);
	}
	
	public void setGraphVisible(boolean isVisible) {
		linePanel.setGraphVisible(isVisible);
	}
	
	public Object [] [] getValues() {
		return linePanel.getValues();
	}
	
	public void appendToLine(Panel panel) {
		linePanel.appendToLine(panel);
	}
	
	public List<Integer> getCodesIdx() {
		return linePanel.getCodeIdx();
	}
}
