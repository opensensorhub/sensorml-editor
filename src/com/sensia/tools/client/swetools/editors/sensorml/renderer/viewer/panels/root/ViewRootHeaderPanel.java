package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.root;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;

public class ViewRootHeaderPanel extends VerticalPanel{

	// gml:name
	protected Panel titlesPanel;
	
	// gml:identifier
	protected Panel identifiersPanel;
	
	// gml:description
	protected Panel descriptionPanel;
	
	// sml:keywords
	protected Panel keywordsPanel;
	
	public ViewRootHeaderPanel() {
		
		setSpacing(5);	
		// 1..~
		titlesPanel = new VerticalPanel();
		
		// 0..1
		descriptionPanel = new SimplePanel();
		
		// 1..~
		keywordsPanel = new VerticalPanel();
		keywordsPanel.add(new HTML("Keywords:"+SMLEditorConstants.HTML_SPACE));
		
		// 1..~
		identifiersPanel = new VerticalPanel();
		
		add(titlesPanel);
		add(new HTML("<hr  style=\"width:100%;\" />"));
		add(identifiersPanel);
		add(descriptionPanel);
		add(keywordsPanel);
		add(new HTML("<hr  style=\"width:100%;\" />"));
		
		addStyleName("header-document");
		
		this.keywordsPanel.setVisible(false);
		this.descriptionPanel.setVisible(false);
		this.identifiersPanel.setVisible(false);
		this.titlesPanel.setVisible(false);
	}
	
	public void addKeywords(Panel keywordsPanel) {
		this.keywordsPanel.add(keywordsPanel);
		this.keywordsPanel.setVisible(true);
	}
	
	public void addTitle(Panel titlePanel) {
		titlesPanel.add(titlePanel);
		titlesPanel.setVisible(true);
	}
	
	public void addIdentifier(Panel identifierPanel) {
		identifiersPanel.add(identifierPanel);
		identifiersPanel.setVisible(true);
	}
	
	public void setDescription(Panel descriptionPanel) {
		this.descriptionPanel.clear();
		this.descriptionPanel.add(descriptionPanel);
		this.descriptionPanel.setVisible(true);
	}

	public Panel getTitlesPanel() {
		return titlesPanel;
	}

	public Panel getIdentifiersPanel() {
		return identifiersPanel;
	}

	public Panel getDescriptionPanel() {
		return descriptionPanel;
	}

	public Panel getKeywordsPanel() {
		return keywordsPanel;
	}
	
}
