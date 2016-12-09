package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;

public class EditSubSectionElementPanel extends EditElementPanel{

	private IPanel<?> namePanel;
	private Panel label;
	private Panel definition;
	private Panel innerContainer;
	
	public EditSubSectionElementPanel(RNGElement element) {
		super(element);
		innerContainer = new VerticalPanel();
		label = new SimplePanel();
		label.setVisible(false);
		label.addStyleName("edit-subsection-element-panel");
		label.add(new HTML(element.getName()));
		
		definition = new SimplePanel();
		definition.setVisible(false);
		
		Panel hPanel = new HorizontalPanel();
		hPanel.add(label);
		hPanel.add(definition);
		
		container.add(hPanel);
		container.add(innerContainer);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element instanceof AbstractGenericLinePanel) {
			AbstractGenericLinePanel eltPanel = (AbstractGenericLinePanel) element;
			if(!eltPanel.isLabeled() && namePanel != null) {
				eltPanel.setLabel(namePanel.getPanel());
			}
			innerContainer.add(element.getPanel());
		} else if(element.getName().equals("name")) {
			label.clear();
			namePanel = element;
			innerContainer.addStyleName("edit-subsection-element-inner-panel");
			label.add(element.getPanel());
		} else if(element.getName().equals("label")){
			namePanel = element;
			label.clear();
			label.setVisible(true);
			definition.setVisible(true);
			innerContainer.addStyleName("edit-subsection-element-inner-panel");
			label.add(element.getPanel());
		} else if(element.getName().equals("definition") 
				|| element.getName().equals("role") 
				|| element.getName().equals("arcrole")){
			label.setVisible(true);
			definition.setVisible(true);
			innerContainer.addStyleName("edit-subsection-element-inner-panel");
			definition.add(element.getPanel());
		} else {
			//super.addInnerElement(element);
			innerContainer.add(element.getPanel());
		}
	}
}
