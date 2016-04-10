/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

/**
 * The Class SensorSectionsWidget is corresponding to the root panel element. It handles the title, 
 * description, keywords elements.
 */
public class SensorSectionsWidget extends AbstractSensorElementWidget{

	/** The container. */
	private VerticalPanel container;
	
	/** The title panel. */
	private HTML titlePanel;
	
	/** The description panel. */
	private HorizontalPanel descriptionPanel;
	
	/** The identifier panel. */
	private HorizontalPanel identifierPanel;
	
	/** The keyword panel. */
	private HorizontalPanel keywordPanel;
	
	/** The line description panel. */
	private HorizontalPanel lineDescriptionPanel;
	
	/** The line identifier panel. */
	private HorizontalPanel lineIdentifierPanel;
	
	/** The line title panel. */
	private HorizontalPanel lineTitlePanel;
	
	/** The end panel. */
	private Panel endPanel;
	
	/** The start panel. */
	private Panel startPanel;
	
	/** The title heading start tag. */
	private String titleHeadingStartTag = "<h2>";
	
	/** The title heading end tag. */
	private String titleHeadingEndTag = "</h2>";
	
	
	/** The line title. */
	private HTML lineTitle;
	
	/** The line description. */
	private HTML lineDescription;
	
	/** The desc id keyword panel. */
	private Panel descIdKeywordPanel;
	
	/**
	 * Instantiates a new sensor sections widget.
	 */
	public SensorSectionsWidget() {
		super("", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		
		container = new VerticalPanel();
		container.setWidth("1024px");
		container.setSpacing(5);
		titlePanel = new HTML();
		titlePanel.addStyleName("document-title");
		
		descriptionPanel = new HorizontalPanel();
		identifierPanel  = new HorizontalPanel();
		keywordPanel  = new HorizontalPanel();
		
		//add edit mode
		lineDescriptionPanel = new HorizontalPanel();
		lineIdentifierPanel = new HorizontalPanel();
		lineTitlePanel = new HorizontalPanel();
		
		lineIdentifierPanel.add(identifierPanel);
		lineDescriptionPanel.add(descriptionPanel);
		
		//add edit mode identifier
		List<String> advancedTags = new ArrayList<String>();
		advancedTags.add("identifier");
		
		Panel advancedPanel = getSimpleEditPanel(new IButtonCallback() {
			@Override
			public void onClick() {
				refreshChildren(getElements());
				refreshParents(getParent());
				for(ISensorWidget child : getElements()) {
					if (child.getName().equals("identifier")) {
						identifierPanel.clear();
						HTML identifier = new HTML(child.getValue("codeSpace", true)+": "+child.getValue("identifier", true));
						identifierPanel.add(identifier);
						break;
					}
				}
			}
		},advancedTags);
		lineIdentifierPanel.add(advancedPanel);
		
		//add edit mode description
		advancedTags = new ArrayList<String>();
		advancedTags.add("description");
		
		advancedPanel = getSimpleEditPanel(new IButtonCallback() {
			@Override
			public void onClick() {
				refreshChildren(getElements());
				refreshParents(getParent());
			}
		},advancedTags);
		lineDescriptionPanel.add(advancedPanel);
		
		//add title
		lineTitlePanel.add(titlePanel);
		//add edit mode identifier
		advancedTags = new ArrayList<String>();
		advancedTags.add("name");
		advancedTags.add("id");
		
		//add the advanced panel. It will display the dots for title and id element
		advancedPanel = getSimpleEditPanel(new IButtonCallback() {
			@Override
			public void onClick() {
				refreshChildren(getElements());
				refreshParents(getParent());
				for(ISensorWidget child : getElements()) {
					if(child.getName().equals("name")){
						String value = child.getValue("name", true);
						if(value != null && !value.isEmpty()) {
							titlePanel.setHTML(titleHeadingStartTag+value+titleHeadingEndTag);
						}
					} if (child.getName().equals("id") && child.getType() == TAG_TYPE.ATTRIBUTE) {
						if(!child.getElements().isEmpty()) {
							//the first one should be a value widget
							String value = child.getElements().get(0).getName();
							titlePanel.setHTML(titleHeadingStartTag+value+titleHeadingEndTag);
						}
					}
				}
			}
		},advancedTags);
		advancedPanel.addStyleName("advanced-title-panel");
		
		lineTitlePanel.add(advancedPanel);
				
		container.add(lineTitlePanel);
		
		//draws a horizontal line
		lineTitle = new HTML("<hr  style=\"width:100%;\" />");
		container.add(lineTitle);

		//each panel is added with its corresponding advanced panel
		//the advanced panel is hidden by default
		//this vertical panel handles the while stack of panels
		descIdKeywordPanel = new VerticalPanel();
		descIdKeywordPanel.add(lineIdentifierPanel);
		descIdKeywordPanel.add(lineDescriptionPanel);
		descIdKeywordPanel.add(keywordPanel);
		
		container.add(descIdKeywordPanel);
		
		//draw horizontal line
		lineDescription = new HTML("<hr  style=\"width:100%;\" />");
		container.add(lineDescription);
		
		startPanel = new VerticalPanel();
		container.add(startPanel);
		
		endPanel = new VerticalPanel();
		container.add(endPanel);
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getPanel()
	 */
	@Override
	public Panel getPanel() {
		return container;
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getName().equals("name")){
			String value = widget.getValue("name", true);
			if(value != null && !value.isEmpty()) {
				titlePanel.setHTML(titleHeadingStartTag+value+titleHeadingEndTag);
			}
		} else if(widget.getName().equals("description")) {
			descriptionPanel.add(widget.getPanel());
		} else if (widget.getName().equals("identifier")) {
			HTML identifier = new HTML(widget.getValue("codeSpace", true)+": "+widget.getValue("identifier", true));
			identifierPanel.add(identifier);
		} else if (widget.getName().equals("id") && widget.getType() == TAG_TYPE.ATTRIBUTE) {
			if(!widget.getElements().isEmpty()) {
				//the first one should be a value widget
				String value = widget.getElements().get(0).getName();
				titlePanel.setHTML(titleHeadingStartTag+value+titleHeadingEndTag);
			}
		} else if (widget.getName().equals("KeywordList")) {
			keywordPanel.add(widget.getPanel());
		} else if(widget.getType() == TAG_TYPE.ZERO_OR_MORE){
			endPanel.add(widget.getPanel());
		} else {
			startPanel.add(widget.getPanel());
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE)
	 */
	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorSectionsWidget();
	}
	
	/**
	 * Sets the inner sections.
	 *
	 * @param isInnerSection the new inner sections
	 */
	public void setInnerSections(boolean isInnerSection) {
		if(isInnerSection) {
			titleHeadingStartTag = "<h4>";
			titleHeadingEndTag = "</h4>";
			titlePanel.removeStyleName("document-title");
			titlePanel.addStyleName("document-title-inner");
			lineTitle.addStyleName("sections-line-title-separator-inner");
			lineDescription.addStyleName("sections-line-description-separator-inner");
			descIdKeywordPanel.addStyleName("sections-description-inner");
		} else {
			titleHeadingStartTag = "<h2>";
			titleHeadingEndTag = "</h2>";
			titlePanel.removeStyleName("document-title-inner");
			titlePanel.addStyleName("document-title");
			lineTitle.removeStyleName("sections-line-title-separator-inner");
			lineDescription.removeStyleName("sections-line-description-separator-inner");
			descIdKeywordPanel.removeStyleName("sections-description-inner");
		}
	}
}
