package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.EditIconPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGZeroOrMorePatternPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.rng.EditRNGChoicePatternPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel.SPACING;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditSubSectionElementPanel extends EditElementPanel{

	protected IPanel<?> nameIPanel;
	protected Panel labelPanel;
	protected Panel namedLabelPanel;
	
	protected Panel definition;
	protected Panel description;
	protected Panel innerContainer;
	protected IRefreshHandler refreshHandler;
	protected Panel headerPanel;
	protected Panel detailsPanel;
	
	protected boolean hasLabel=false;
	protected boolean hasName=false;
	protected boolean showDataTypeName=false;
	
	protected boolean displayHeader = false;
	protected boolean displayDefaultName= false;
	
	public EditSubSectionElementPanel(RNGElement element,boolean defaultDisplayName) {
		this(element,null,defaultDisplayName);
	}
	
	public EditSubSectionElementPanel(RNGElement element, IRefreshHandler refreshHandler,boolean defaultDisplayName) {
		super(element,refreshHandler);
		innerContainer = new SMLVerticalPanel(true);
		labelPanel = new SimplePanel();
		
		namedLabelPanel = new SMLHorizontalPanel();
		namedLabelPanel.add(labelPanel);
		
		definition = new SimplePanel();
		definition.setVisible(false);
		
		description = new SimplePanel();
		description.setVisible(false);
		
		detailsPanel = new SimplePanel();
		detailsPanel.setVisible(false);
		
		headerPanel = new SMLHorizontalPanel(SPACING.RIGHT);
		headerPanel.add(namedLabelPanel);
		headerPanel.add(definition);
		headerPanel.add(description);
		headerPanel.add(detailsPanel);
		
		headerPanel.setVisible(false);
		
		container.add(headerPanel);
		container.add(innerContainer);
		
		container.addStyleName("subsection-panel");
		headerPanel.addStyleName("subsection-header");
		innerContainer.addStyleName("subsection-inner");
		//innerContainer.addStyleName("edit-subsection-element-inner-panel");
		displayDefaultName = defaultDisplayName;
	}
	
	public EditSubSectionElementPanel(RNGElement element, IRefreshHandler refreshHandler) {
		this(element,refreshHandler,true);
	}
	
	public void setShowDataType(boolean show) {
		showDataTypeName = show;
	}
	
	public void setDataTypeName(boolean show) {
		namedLabelPanel.clear();
		if(show) {
			namedLabelPanel.add(labelPanel);
			namedLabelPanel.add(new HTML("("+Utils.toNiceLabel(getTag().getName())+")"));
		} else {
			namedLabelPanel.add(labelPanel);
		}
	}
	
	
	public EditSubSectionElementPanel(RNGElement element) {
		this(element,null);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		IPanel currentElement = element;
		if(element instanceof RNGZeroOrMorePatternPanel) {
			currentElement = ((RNGZeroOrMorePatternPanel)element).getIPanelPattern();
		} else if(element instanceof EditRNGChoicePatternPanel) {
			currentElement = ((EditRNGChoicePatternPanel)element).getIPanelPattern();
		}
		
		String eltName = currentElement.getName();
		
		if(currentElement instanceof AbstractGenericLinePanel) {
			displayHeader = handleLine(currentElement);
		} else if(eltName.equals("name")) {
			if(displayDefaultName) {
				 handleName(currentElement);
				 displayHeader = true;
			} else {
				displayHeader |= handleName(currentElement);
			}
		} else if(eltName.equals("label")){
			displayHeader |= handleLabel(element);
		} else if(eltName.equals("definition") 
				|| eltName.equals("role") 
				|| eltName.equals("arcrole")){
			displayHeader |= handleDefinition(currentElement);
		} else if(eltName.equals("description")){
			displayHeader |= handleDescription(currentElement);
		} else {
			innerContainer.add(element.getPanel());
		}
		
		if(displayHeader) {
			headerPanel.setVisible(true);
			if(showDataTypeName) {
				setShowDataType(true);
			}
		} else {
			headerPanel.setVisible(false);
		}
		
		if(currentElement instanceof EditSubSectionElementPanel) {
			EditSubSectionElementPanel subSection = (EditSubSectionElementPanel) currentElement;
			if(!subSection.isInLine()) {
				if(!hasLabel() && subSection.hasLabel()) {
					// hide the name 
					subSection.removeInnerStyle("subsection-inner");
					//handleLabel(subSection.getLabelIPanel());
					labelPanel.clear();
					labelPanel.add(subSection.getLabelPanel());
					labelPanel.setVisible(true);
				} 
			} 
			if(subSection.hasNameOrLabel()) {
				innerContainer.addStyleName("subsection-inner");
			}
		}

		if(currentElement instanceof AbstractGenericLinePanel) {
			if(hasNameOrLabel()) {
				innerContainer.addStyleName("subsection-inner");
			} else {
				innerContainer.removeStyleName("subsection-inner");
			}
		} 
	}
	
	protected boolean handleLine(IPanel element) {
		boolean displayHeader = this.displayHeader;
		
		AbstractGenericLinePanel eltPanel = (AbstractGenericLinePanel) element;
		
		// replace the line label/name by this name/label
		if(!eltPanel.isLabeled() && nameIPanel != null) {
			eltPanel.setLabel(nameIPanel.getPanel());
			displayHeader = false;
			//if(hasNameOrLabel()) {
			//	innerContainer.addStyleName("edit-subsection-element-inner-panel");
			//}
			innerContainer.removeStyleName("subsection-inner");
		}
		innerContainer.add(element.getPanel());
		
		if((displayHeader) ) {
			// hide the name 
			innerContainer.removeStyleName("subsection-inner");
		}
		// remove inner container style because it becomes a line itself
		//innerContainer.removeStyleName("edit-subsection-element-inner-panel");
		isInLine = true;
		if(!hasLabel) {
			displayHeader = false;
		}
		return displayHeader;
	}
	
	protected boolean handleName(IPanel element) {
		nameIPanel = element;
		labelPanel.add(element.getPanel());
		labelPanel.setVisible(true);
		hasName = true;
		
		return true;
	}
	
	protected boolean handleDefinition(IPanel element) {
		definition.setVisible(true);
		definition.add(element.getPanel());
		
		return false;
	}
	
	protected boolean handleDescription(IPanel<? extends RNGTag> element) {
		RNGElement tag = (RNGElement) element.getTag();
		EditIconPanel<RNGElement> iconPanel = new EditIconPanel<RNGElement>(tag, 
				new Image(GWT.getModuleBaseURL()+"images/icon_question.png"), "description-icon",false);
		for(IPanel child : element.getElements()) {
			iconPanel.addElement(child);
		}
		description.setVisible(true);
		description.add(iconPanel.getPanel());
		
		return false;
	}
	
	protected boolean handleLabel(IPanel element) {
		nameIPanel = element;
		// label comes after name if any
		labelPanel.clear();
		labelPanel.add(element.getPanel());
		labelPanel.setVisible(true);
		hasLabel = true;
		
		return true;
	}
	
	public void setLabelVisible(boolean isVisible) {
		this.headerPanel.setVisible(isVisible);
	}
	
	public void removeInnerStyle(String style) {
		innerContainer.removeStyleName(style);
	}
	
	public void setDetailsPanel(Widget widget) {
		detailsPanel.clear();
		detailsPanel.add(widget);
		detailsPanel.setVisible(true);
	}
	
	public boolean hasLabel() {
		return hasLabel;
	}
	
	public IPanel getLabelIPanel() {
		return nameIPanel;
	}
	
	public Panel getLabelPanel() {
		return headerPanel;
	}
	
	public boolean hasNameOrLabel() {
		return headerPanel.isVisible();
	}
}
