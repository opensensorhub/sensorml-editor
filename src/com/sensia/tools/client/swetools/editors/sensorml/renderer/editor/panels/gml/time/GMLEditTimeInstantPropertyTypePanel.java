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

public class GMLEditTimeInstantPropertyTypePanel extends EditSimpleElementPanel{

	private Panel nilReason;
	private Panel owns;
	private Panel others;
	
	public GMLEditTimeInstantPropertyTypePanel(final RNGElement element) {
		super(element,Utils.findLabel(element));
		
		container = new HorizontalPanel();
		nilReason = new SimplePanel();
		owns = new HorizontalPanel();
		others = new HorizontalPanel();
		
		nilReason.setVisible(false);
		owns.setVisible(false);
		
		container.addStyleName("TimeInstantPropertyType-panel");
		
		container.add(nilReason);
		container.add(owns);
		container.add(others);
	}
	
	@Override
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("nilReason")){
			nilReason.setVisible(true);
			nilReason.add(element.getPanel());
		} else if(element.getName().equals("owns")){
			owns.setVisible(true);
			owns.add(element.getPanel());
		} else {
			others.add(element.getPanel());
		}
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
