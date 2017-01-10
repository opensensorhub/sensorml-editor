package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.EditIconPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditSubSectionElementPanel extends EditElementPanel{

	protected IPanel<?> namePanel;
	protected IPanel<?> defPanel;
	protected Panel label;
	protected Panel definition;
	protected Panel description;
	protected Panel innerContainer;
	protected IRefreshHandler refreshHandler;
	
	public EditSubSectionElementPanel(RNGElement element, IRefreshHandler refreshHandler) {
		super(element,refreshHandler);
		innerContainer = new VerticalPanel();
		label = new SimplePanel();
		label.setVisible(false);
		label.addStyleName("edit-subsection-label-panel");
		label.add(new HTML(Utils.toNiceLabel(element.getName())));
		//label.add(new HTML(element.getName()));
		
		definition = new SimplePanel();
		definition.setVisible(false);
		
		description = new SimplePanel();
		description.setVisible(false);
		
		Panel hPanel = new HorizontalPanel();
		hPanel.add(label);
		hPanel.add(definition);
		hPanel.add(description);
		
		container.add(hPanel);
		container.add(innerContainer);
		container.addStyleName("edit-subsection-element-panel");
	}
	
	public EditSubSectionElementPanel(RNGElement element) {
		this(element,null);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element instanceof AbstractGenericLinePanel) {
			AbstractGenericLinePanel eltPanel = (AbstractGenericLinePanel) element;
			if(!eltPanel.isLabeled() && namePanel != null) {
				eltPanel.setLabel(namePanel.getPanel());
			}
			if(defPanel != null) {
				//eltPanel.setDefinition(defPanel.getPanel());
			}
			innerContainer.add(element.getPanel());
			isInLine = true;
		} else if(element.getName().equals("name")) {
			label.clear();
			namePanel = element;
			//innerContainer.addStyleName("edit-subsection-element-inner-panel");
			label.add(element.getPanel());
		} else if(element.getName().equals("label")){
			namePanel = element;
			label.clear();
			label.setVisible(true);
			definition.setVisible(true);
			description.setVisible(true);
			innerContainer.addStyleName("edit-subsection-element-inner-panel");
			label.add(element.getPanel());
		} else if(element.getName().equals("definition") 
				|| element.getName().equals("role") 
				|| element.getName().equals("arcrole")){
			defPanel = element;
			label.setVisible(true);
			definition.setVisible(true);
			description.setVisible(true);
			innerContainer.addStyleName("edit-subsection-element-inner-panel");
			definition.add(element.getPanel());
		} else if(element.getName().equals("description")){
			RNGElement tag = (RNGElement) element.getTag();
			EditIconPanel<RNGElement> iconPanel = new EditIconPanel<RNGElement>(tag, 
					new Image(GWT.getModuleBaseURL()+"images/icon_question.png"), "description-icon",false);
			for(IPanel child : element.getElements()) {
				iconPanel.addElement(child);
			}
			description.add(iconPanel.getPanel());
		} else {
			//super.addInnerElement(element);
			innerContainer.add(element.getPanel());
		}
	}
	
	public void setLabelVisible(boolean isVisible) {
		this.label.setVisible(isVisible);;
	}
}
