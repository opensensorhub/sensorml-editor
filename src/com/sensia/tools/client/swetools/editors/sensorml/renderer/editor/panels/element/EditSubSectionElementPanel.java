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
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel.SPACING;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditSubSectionElementPanel extends EditElementPanel{

	protected Panel namePanel;
	protected IPanel<?> defPanel;
	protected Panel label;
	protected Panel definition;
	protected Panel description;
	protected Panel innerContainer;
	protected IRefreshHandler refreshHandler;
	
	public EditSubSectionElementPanel(RNGElement element, IRefreshHandler refreshHandler,boolean b) {
		this(element,refreshHandler);
	}
	
	public EditSubSectionElementPanel(RNGElement element, boolean b) {
		this(element,null,b);
	}
	
	public EditSubSectionElementPanel(RNGElement element, IRefreshHandler refreshHandler) {
		super(element,refreshHandler);
		innerContainer = new SMLVerticalPanel(true);
		label = new SimplePanel();
		label.setVisible(false);
		//label.add(new HTML(Utils.toNiceLabel(element.getName())));
		//label.add(new HTML(element.getName()));
		
		definition = new SimplePanel();
		definition.setVisible(false);
		
		description = new SimplePanel();
		description.setVisible(false);
		
		Panel headerPanel = new SMLHorizontalPanel(SPACING.RIGHT);
		headerPanel.add(label);
		headerPanel.add(definition);
		headerPanel.add(description);
		
		container.add(headerPanel);
		container.add(innerContainer);
		
		container.addStyleName("subsection-panel");
		headerPanel.addStyleName("subsection-header");
	}
	
	public EditSubSectionElementPanel(RNGElement element) {
		this(element,null);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element instanceof AbstractGenericLinePanel) {
			AbstractGenericLinePanel eltPanel = (AbstractGenericLinePanel) element;
			if(!eltPanel.isLabeled() && namePanel != null) {
				eltPanel.setLabel(namePanel);
			} 
			
			if(!label.isVisible()) {
				innerContainer.removeStyleName("subsection-inner");
			}
			if(defPanel != null) {
				//eltPanel.setDefinition(defPanel.getPanel());
			}
			innerContainer.add(element.getPanel());
			isInLine = true;
		} else if(element.getName().equals("name")) {
			label.clear();
			namePanel = element.getPanel();
			//innerContainer.addStyleName("subsection-inner");
			label.add(element.getPanel());
			if(definition.isVisible() || description.isVisible()) {
				label.setVisible(true);
				innerContainer.addStyleName("subsection-inner");
			}
		} else if(element.getName().equals("label")){
			namePanel = element.getPanel();
			label.clear();
			label.setVisible(true);
			definition.setVisible(true);
			description.setVisible(true);
			innerContainer.addStyleName("subsection-inner");
			label.add(element.getPanel());
		} else if(element.getName().equals("definition") 
				|| element.getName().equals("role") 
				|| element.getName().equals("arcrole")){
			defPanel = element;
			if(namePanel != null) {
				label.setVisible(true);
				definition.setVisible(true);
				description.setVisible(true);
				innerContainer.addStyleName("subsection-inner");
			}
			definition.add(element.getPanel());
		} else if(element.getName().equals("description")){
			RNGElement tag = (RNGElement) element.getTag();
			EditIconPanel<RNGElement> iconPanel = new EditIconPanel<RNGElement>(tag, 
					new Image(GWT.getModuleBaseURL()+"images/icon_question.png"), "description-icon",false);
			for(IPanel child : element.getElements()) {
				iconPanel.addElement(child);
			}
			description.add(iconPanel.getPanel());
			description.setVisible(true);
		} else {
			//super.addInnerElement(element);
			innerContainer.add(element.getPanel());
		}
	}
	
	/*public void setLabelVisible(boolean isVisible) {
		this.label.setVisible(isVisible);;
	}*/
	
	public void setShowDataType(boolean v){
		
	}
}