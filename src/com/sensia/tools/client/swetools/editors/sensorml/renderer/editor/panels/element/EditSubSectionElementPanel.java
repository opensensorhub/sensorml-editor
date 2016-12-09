package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element;

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
	private Panel innerContainer;
	
	public EditSubSectionElementPanel(RNGElement element) {
		super(element);
		innerContainer = new VerticalPanel();
		label = new SimplePanel();
		label.setVisible(false);
		label.addStyleName("edit-subsection-element-panel");
		
		container.add(label);
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
			namePanel = element;
		} else if(element.getName().equals("label")){
			namePanel = element;
			label.setVisible(true);
			innerContainer.addStyleName("edit-subsection-element-inner-panel");
			label.add(element.getPanel());
		} else {
			//super.addInnerElement(element);
			innerContainer.add(element.getPanel());
		}
	}
}
