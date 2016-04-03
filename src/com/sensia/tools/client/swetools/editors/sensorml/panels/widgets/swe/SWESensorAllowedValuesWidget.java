package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class SWESensorAllowedValuesWidget extends AbstractSensorElementWidget{

	private Panel container;
	private Panel innerContainer;
	
	//handle <swe:value>
	private Panel valuesPanel;
	
	//handle <swe:interval>
	private Panel intervalPanel;
	
	
	private HTML values;
	
	public SWESensorAllowedValuesWidget() {
		super("AllowedValues",TAG_DEF.SWE,TAG_TYPE.ELEMENT);
		
		this.container = new HorizontalPanel();
	
		init();
	}

	private void init() {
		HTML b = new HTML("[");
		HTML a = new HTML("]");
		innerContainer = new HorizontalPanel();
		
		this.container.add(b);
		this.container.add(innerContainer);
		this.container.add(a);
		
		//add styles
		a.addStyleName("constraint-values");
		b.addStyleName("constraint-values");
	}
	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getName().equals("value") && widget.getType() == TAG_TYPE.ELEMENT) {
			String value = widget.getElements().get(0).getName();
			if(valuesPanel == null) {
				valuesPanel = new HorizontalPanel();
				values = new HTML(value);
				values.addStyleName("constraint-values");
				valuesPanel.add(values);
				innerContainer.add(valuesPanel);
			} else {
				values.setHTML(values.getHTML()+" / "+value);
			}
			
			
		} else if(widget.getName().equals("interval") && widget.getType() == TAG_TYPE.ELEMENT) {
			String interval = widget.getElements().get(0).getName();
			String [] spaceSplit = interval.split(" ");
			if(intervalPanel == null) {
				intervalPanel = new HorizontalPanel();
				innerContainer.add(intervalPanel);
			}
			HTML values = new HTML(spaceSplit[0]+";"+spaceSplit[1]);
			values.addStyleName("constraint-values");
			intervalPanel.add(values);
		}
	}

	@Override
	public void refresh() {
		container.clear();
		intervalPanel = null;
		valuesPanel = null;
		init();
		for(ISensorWidget child : getElements()) {
			addSensorWidget(child);
		}
	}
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorAllowedValuesWidget();
	}

}
