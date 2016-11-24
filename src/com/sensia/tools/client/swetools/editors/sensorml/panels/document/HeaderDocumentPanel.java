package com.sensia.tools.client.swetools.editors.sensorml.panels.document;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HeaderDocumentPanel extends VerticalPanel{

	// gml:name
	protected Panel titlesPanel;
	
	// gml:identifier
	protected Panel identifiersPanel;
	
	// gml:description
	protected Panel descriptionPanel;
	
	// sml:keywords
	protected Panel keywordsPanel;
	
	public HeaderDocumentPanel() {
		
		setSpacing(5);	
		// 1..~
		titlesPanel = new VerticalPanel();
		
		// 0..1
		descriptionPanel = new SimplePanel();
		
		// 1..~
		keywordsPanel = new VerticalPanel();
		
		// 1..~
		identifiersPanel = new VerticalPanel();
		
		add(titlesPanel);
		add(new HTML("<hr  style=\"width:100%;\" />"));
		add(identifiersPanel);
		add(descriptionPanel);
		add(keywordsPanel);
		add(new HTML("<hr  style=\"width:100%;\" />"));
		
		addStyleName("header-document");
	}
	
	public void addKeywords(Panel keywordsPanel) {
		Panel hPanel = new HorizontalPanel();
		hPanel.add(new HTML("Keywords:&nbsp"));
		hPanel.add(keywordsPanel);
		this.keywordsPanel.add(hPanel);
	}
	
	public void addTitle(Panel titlePanel) {
		titlesPanel.add(titlePanel);
	}
	
	public void addIdentifier(Panel identifierPanel) {
		identifiersPanel.add(identifierPanel);
	}
	
	public void setDescription(Panel descriptionPanel) {
		this.descriptionPanel.clear();
		this.descriptionPanel.add(descriptionPanel);
	}
	
}
