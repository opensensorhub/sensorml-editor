package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.time;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.element.AdvancedSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class GMLEditTimePositionTypePanel extends EditSimpleElementPanel{

	private Panel calendarEraPanelPanel;
	private Panel indeterminatePositionPanel;
	private Panel valuePanel;
	
	private ListBox relativePosition;
	
	private static final List<String> relativePositionItems;
	static {
		relativePositionItems = new ArrayList<String>();
		relativePositionItems.add("Before");
		relativePositionItems.add("After");
		relativePositionItems.add("Begins");
		relativePositionItems.add("Ends");
		relativePositionItems.add("During");
		relativePositionItems.add("Equals");
		relativePositionItems.add("Contains");
		relativePositionItems.add("Overlaps");
		relativePositionItems.add("Meets");
		relativePositionItems.add("OverlappedBy");
		relativePositionItems.add("MetBy");
		relativePositionItems.add("BegunBy");
		relativePositionItems.add("EndedBy");
	}
	
	public GMLEditTimePositionTypePanel(final RNGElement element) {
		super(element,Utils.findLabel(element));
		
		container = new HorizontalPanel();
		indeterminatePositionPanel = new SimplePanel();
		calendarEraPanelPanel = new HorizontalPanel();
		valuePanel = new SimplePanel();
		relativePosition = new ListBox();
		
		indeterminatePositionPanel.add(relativePosition);
		
		for(String item: relativePositionItems) {
			relativePosition.addItem(item);
		}
		
		relativePosition.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				String selectedItem = relativePosition.getSelectedItemText();
				RNGAttribute indeterminatePositionAttr = element.getChildAttribute("indeterminatePosition");
				if(indeterminatePositionAttr != null) {
					indeterminatePositionAttr.setChildValueText(selectedItem);
				}
			}
		});
		
		container.add(relativePosition);
		container.add(calendarEraPanelPanel);
		container.add(valuePanel);
		
		indeterminatePositionPanel.setVisible(false);
		calendarEraPanelPanel.setVisible(false);
		
		container.addStyleName("TimePositionType-panel");
	}
	
	@Override
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("indeterminatePosition")){
			// get value
			String value = ((RNGAttribute)element.getTag()).getChildValueText();
			int index = Utils.indexOfIgnoreCase(relativePositionItems,value.toLowerCase());
			relativePosition.setSelectedIndex(index);
			indeterminatePositionPanel.setVisible(true);
		} else if(element.getName().equals("calendarEraName")){
			calendarEraPanelPanel.add(new HTML("("));
			calendarEraPanelPanel.add(element.getPanel());
			calendarEraPanelPanel.add(new HTML(")"));
			calendarEraPanelPanel.setVisible(true);
		} else {
			valuePanel.add(element.getPanel());
		}
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
