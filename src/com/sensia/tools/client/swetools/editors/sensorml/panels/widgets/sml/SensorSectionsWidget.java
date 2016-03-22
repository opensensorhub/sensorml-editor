package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;

public class SensorSectionsWidget extends AbstractSensorElementWidget{

	private VerticalPanel container;
	private HTML namePanel;
	private HorizontalPanel descriptionPanel;
	private HorizontalPanel identifierPanel;
	private HorizontalPanel keywordPanel;
	
	private Panel endPanel;
	private Panel startPanel;
	
	private String titleHeadingStartTag = "<h2>";
	private String titleHeadingEndTag = "</h2>";
	
	
	private HTML lineTitle;
	private HTML lineDescription;
	
	private Panel descIdKeywordPanel;
	
	public SensorSectionsWidget() {
		super("", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		
		container = new VerticalPanel();
		container.setWidth("1024px");
		container.setSpacing(5);
		namePanel = new HTML();
		namePanel.addStyleName("document-title");
		
		descriptionPanel = new HorizontalPanel();
		identifierPanel  = new HorizontalPanel();
		keywordPanel  = new HorizontalPanel();
		
		container.add(namePanel);
		
		//draw horizontal line
		lineTitle = new HTML("<hr  style=\"width:100%;\" />");
		container.add(lineTitle);
		
		descIdKeywordPanel = new VerticalPanel();
		
		descIdKeywordPanel.add(identifierPanel);
		descIdKeywordPanel.add(descriptionPanel);
		descIdKeywordPanel.add(keywordPanel);
		
		container.add(descIdKeywordPanel);
		
		//HTML specTitle = new HTML("<h2>Specifications</h2>");
		//specTitle.addStyleName("document-title");
		
		//container.add(specTitle);
		//draw horizontal line
		lineDescription = new HTML("<hr  style=\"width:100%;\" />");
		container.add(lineDescription);
		
		startPanel = new VerticalPanel();
		container.add(startPanel);
		
		endPanel = new VerticalPanel();
		container.add(endPanel);
	}

	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getName().equals("name")){
			String value = widget.getValue("name", true);
			if(value != null && !value.isEmpty()) {
				namePanel.setHTML(titleHeadingStartTag+value+titleHeadingEndTag);
			}
		} else if(widget.getName().equals("description")) {
			descriptionPanel.add(widget.getPanel());
		} else if (widget.getName().equals("identifier")) {
			HTML identifier = new HTML("UniqueID: "+widget.getValue("identifier", true));
			identifierPanel.add(identifier);
		} else if (widget.getName().equals("id") && widget.getType() == TAG_TYPE.ATTRIBUTE) {
			if(!widget.getElements().isEmpty()) {
				//the first one should be a value widget
				String value = widget.getElements().get(0).getName();
				namePanel.setHTML(titleHeadingStartTag+value+titleHeadingEndTag);
			}
		} else if (widget.getName().equals("KeywordList")) {
			keywordPanel.add(widget.getPanel());
		} else if(widget.getType() == TAG_TYPE.ZERO_OR_MORE){
			endPanel.add(widget.getPanel());
		} else {
			startPanel.add(widget.getPanel());
		}
	}

	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorSectionsWidget();
	}
	
	public void setInnerSections(boolean isInnerSection) {
		if(isInnerSection) {
			titleHeadingStartTag = "<h4>";
			titleHeadingEndTag = "</h4>";
			namePanel.removeStyleName("document-title");
			namePanel.addStyleName("document-title-inner");
			lineTitle.addStyleName("sections-line-title-separator-inner");
			lineDescription.addStyleName("sections-line-description-separator-inner");
			descIdKeywordPanel.addStyleName("sections-description-inner");
		} else {
			titleHeadingStartTag = "<h2>";
			titleHeadingEndTag = "</h2>";
			namePanel.removeStyleName("document-title-inner");
			namePanel.addStyleName("document-title");
			lineTitle.removeStyleName("sections-line-title-separator-inner");
			lineDescription.removeStyleName("sections-line-description-separator-inner");
			descIdKeywordPanel.removeStyleName("sections-description-inner");
		}
	}
}
