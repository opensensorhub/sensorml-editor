package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.line;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class SensorGenericLineWidget extends AbstractSensorElementWidget{
	
	protected VerticalPanel multiLinesPanel;
	protected HorizontalPanel linePanel;
	protected Panel labelPanel;
	protected HTML dotSeparatorLabel;
	protected Panel optPanel;
	protected Panel iconPanel;
	protected Panel defPanel;
	
	protected Panel advancedPanel;
	
	protected boolean isLabelProvided;
	protected boolean isDefinitionProvided;
	
	protected ISensorWidget titleValueWidget;
	protected boolean hasTitle;
	
	public SensorGenericLineWidget(String name, TAG_DEF def, TAG_TYPE type) {
		super(name, def, type);
		multiLinesPanel = new VerticalPanel();
		init();
	}

	public void init() {
		isLabelProvided = false;
		isDefinitionProvided = false;
		hasTitle = false;
		linePanel = new HorizontalPanel();
		HorizontalPanel labelAndDefPanel = new HorizontalPanel();
		labelPanel = new HorizontalPanel();
		dotSeparatorLabel = new HTML(getDotsLine());
		optPanel = new HorizontalPanel();
		
		iconPanel = new HorizontalPanel();
		//for generic ones
		defPanel = new HorizontalPanel();
		
		labelAndDefPanel.add(labelPanel);
		labelAndDefPanel.add(defPanel);
		
		SimplePanel labelAndDefPanelForCorrectDots = new SimplePanel();
		labelAndDefPanelForCorrectDots.add(labelAndDefPanel);
		
		labelAndDefPanelForCorrectDots.addStyleName("line-generic-label-panel");
		//defPanel.addStyleName("def-generic-label-panel");
		
		advancedPanel = getEditPanel(new IButtonCallback() {
			
			@Override
			public void onClick() {
				if(titleValueWidget != null) {
					labelPanel.clear();
					labelPanel.add(new HTML(splitAndCapitalize(titleValueWidget.getName())));
					
					refreshChildren(getElements());
					refreshParents(getParent());
				}
			}
		});
		
		//order elements
		linePanel.add(labelAndDefPanelForCorrectDots);
		linePanel.add(dotSeparatorLabel);
		linePanel.add(optPanel);
		linePanel.add(iconPanel);
		linePanel.add(advancedPanel);
		
		multiLinesPanel.add(linePanel);
		activeMode(getMode());
		
		//hide dots until optPanel is added
		dotSeparatorLabel.setVisible(false);
	}
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget instanceof SensorGenericLineWidget) {
			for(ISensorWidget child : widget.getElements()) {
				addSensorWidget(child);
			}
		}
		if(widget.appendTo() == APPENDER.OVERRIDE_LINE) {
			multiLinesPanel.clear();
			multiLinesPanel.add(widget.getPanel());
		}else if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("name")){
			//provide name as label only if label does not exist
			if(!isLabelProvided) {
				//get the associated value
				titleValueWidget = widget.getElements().get(0);
				labelPanel.add(new HTML(splitAndCapitalize(titleValueWidget.getName())));
				hasTitle = true;
			}
		} 
		//handle generic panel like identifier
		else if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("definition")){
			if(widget.getPanel().isAttached()){
				widget.getPanel().getParent().removeFromParent();
				widget.getPanel().removeFromParent();
			}
			defPanel.add(widget.getPanel());
			isDefinitionProvided = true;
		} else if(widget.isIcon()) {
			iconPanel.add(widget.getPanel());
		} 
		else if(widget.getType() == TAG_TYPE.VALUE || (widget.getName().equals("value"))){//case of Term: value
			optPanel.add(widget.getPanel());
			if(!hasTitle) {
				String parentName = widget.getParent().getName();
				if(parentName != null) {
					labelPanel.add(new HTML(toNiceLabel(widget.getParent().getName())));
					//get the associated value
					titleValueWidget = widget.getParent();
				}
			}
			dotSeparatorLabel.setVisible(true);
		} else if( widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("description")) {
			optPanel.add(widget.getPanel());
			dotSeparatorLabel.setVisible(true);
		} else if (widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("label")) {
			labelPanel.clear();
			labelPanel.add(widget.getPanel());
			isLabelProvided=true;
			hasTitle = true;
			optPanel.clear();
		} else {
			//looking for label
			//prior display for label if exists
			for(ISensorWidget child : widget.getElements()) {
				//for <element name="swe:label"> or <element name="gml:name">
				if(child.getType() == TAG_TYPE.ELEMENT) {
					if((child.getName().equals("label"))) {
						labelPanel.clear();
						labelPanel.add(child.getPanel());
						isLabelProvided=true;
						hasTitle = true;
						//get the associated value
						titleValueWidget = findWidget(child, "value", TAG_DEF.RNG, TAG_TYPE.VALUE);
						break;
					} else if((child.getName().equals("name") && !isLabelProvided)) {
						labelPanel.clear();
						labelPanel.add(child.getPanel());
						//get the associated value
						titleValueWidget = findWidget(child, "value", TAG_DEF.RNG, TAG_TYPE.VALUE);
						hasTitle = true;
					}
				}
			}
			
			//looking for element to append to line
			recursiveAppendTo(widget);
		}
	}

	private boolean recursiveAppendTo(ISensorWidget widget) {
		if(widget.appendTo() == APPENDER.HORIZONTAL || widget.appendTo() == APPENDER.HORIZONTAL_STRICT) {
			if(widget.isIcon()) {
				iconPanel.add(widget.getPanel());
			} //handle generic panel like identifier
			else if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("definition")){
				defPanel.add(widget.getPanel());
				isDefinitionProvided = true;
			} else if(widget.isIcon()) {
				iconPanel.add(widget.getPanel());
			} else {
				optPanel.add(widget.getPanel());
				if(!isDefinitionProvided) {
					//look for the first def label
					ISensorWidget defWidget = findWidget(widget, "definition", TAG_TYPE.ATTRIBUTE);
					if(defWidget != null) {
						defPanel.add(defWidget.getPanel());
						isDefinitionProvided = true;
					}
				}
				dotSeparatorLabel.setVisible(true);
			}
			return widget.appendTo() == APPENDER.HORIZONTAL_STRICT;
		} else if(widget.appendTo() == APPENDER.VERTICAL || widget.appendTo() == APPENDER.VERTICAL_STRICT) {
			multiLinesPanel.add(widget.getPanel());
			return widget.appendTo() == APPENDER.VERTICAL_STRICT;
		} else {
			boolean append = false;
			for(ISensorWidget child : widget.getElements()) {
				append = recursiveAppendTo(child);
				if(append) {
					break;
				}
			}
			return append;
		}
	}
	
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorGenericLineWidget(getName(),getDef(),getType());
	}

	@Override
	public Panel getPanel() {
		return multiLinesPanel;
	}

	@Override
	protected void activeMode(MODE mode) {
	}
}
