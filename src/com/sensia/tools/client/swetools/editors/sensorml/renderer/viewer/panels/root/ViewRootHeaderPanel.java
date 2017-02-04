package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.root;

import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.ModelHelper;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

public class ViewRootHeaderPanel extends SMLVerticalPanel {

	// gml:name
	protected Panel titlesPanel;
	
	// gml:identifier
	protected Panel identifiersPanel;
	
	// gml:description
	protected Panel descriptionPanel;
	
	// sml:keywords
	protected Panel keywordsPanel;
	
	protected Label keywordsLabel;
	
	public ViewRootHeaderPanel() {
		super(true);
		// 1..~
		titlesPanel = new SMLVerticalPanel();
		
		// 0..1
		descriptionPanel = new SimplePanel();
		
		// 1..~
		keywordsPanel = new SMLVerticalPanel();
		keywordsPanel.addStyleName("keywords-panel");
		keywordsLabel = new Label("Keywords: ");
		keywordsPanel.add(keywordsLabel);
		keywordsLabel.addStyleName("keyword-label");
		
		// 1..~
		identifiersPanel = new SMLVerticalPanel(true);
		
		add(titlesPanel);
		add(new HTML("<hr  style=\"width:100%;\" />"));
		add(identifiersPanel);
		add(descriptionPanel);
		add(keywordsPanel);
		add(new HTML("<hr  style=\"width:100%;\" />"));
		
		this.keywordsPanel.setVisible(false);
		this.descriptionPanel.setVisible(false);
		this.identifiersPanel.setVisible(false);
		this.titlesPanel.setVisible(false);
		
		titlesPanel.addStyleName("document-title");
		descriptionPanel.addStyleName("document-description");
	}
	
	public void addKeywords(IPanel keywordsPanel) {
		List<RNGElement>  keywordElements = (List<RNGElement>) ModelHelper.findTags(SMLEditorConstants.SML_NS_2, "keyword", keywordsPanel.getTag());
		if(keywordElements != null && keywordElements.size() > 0) {
			for(RNGElement elt : keywordElements){
				if(elt.getChildValueText() != null) {
					keywordsLabel.setText(keywordsLabel.getText()+" "+elt.getChildValueText());
				}
			}
		}
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
