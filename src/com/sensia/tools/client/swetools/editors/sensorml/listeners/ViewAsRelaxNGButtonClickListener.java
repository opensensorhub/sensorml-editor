/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.listeners;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.xml.client.Document;
import com.sensia.gwt.relaxNG.RNGInstanceWriter;
import com.sensia.gwt.relaxNG.RNGWriter;
import com.sensia.gwt.relaxNG.XMLSerializer;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;
import com.sensia.tools.client.swetools.editors.sensorml.panels.source.FileUploadPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * The listener interface for receiving viewAsXMLButtonClick events.
 * The class that is interested in processing a viewAsXMLButtonClick
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addViewAsXMLButtonClickListener<code> method. When
 * the viewAsXMLButtonClick event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ViewAsXMLButtonClickEvent
 */
public class ViewAsRelaxNGButtonClickListener implements ClickHandler{

	/** The sgml editor processor. */
	private RNGProcessorSML sgmlEditorProcessor;
	
	/**
	 * Instantiates a new view as xml button click listener.
	 *
	 * @param sgmlEditorProcessor the sgml editor processor
	 */
	public ViewAsRelaxNGButtonClickListener(final RNGProcessorSML sgmlEditorProcessor) {
		this.sgmlEditorProcessor = sgmlEditorProcessor;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		RNGGrammar grammar = sgmlEditorProcessor.getLoadedGrammar();
		if(grammar != null) {
			RNGWriter instanceWriter = new RNGWriter();
			Document dom = instanceWriter.writeSchema(grammar, true);
			
			final String xml = XMLSerializer.serialize(dom);
			
			//replace xml special chars
			String xmlText = xml.replaceAll("<", "&#60;");
	        xmlText = xmlText.replaceAll(">", "&#62;");
	        Label labelXml = new HTML("<pre>" + xmlText + "</pre>", false);
			
	        //creates main panel
	        ScrollPanel panel = new ScrollPanel(labelXml);
			panel.setHeight("550px");
			panel.setWidth("1024px");
			
			//adds Validate, Close and Save buttons
			final Button validateButton = new Button("Validate");
			final Button closeButton = new Button("Close");
			final Button saveButton = new Button("Save");
			final FileUploadPanel saveFile = new FileUploadPanel();
			
			//init file upload panel
			final FileUploadPanel fileUploadPanel = new FileUploadPanel();
			final HTML schemaLabel = new HTML("<b>Schema:</b>");
			
			//TODO: functionality is disabled for now
			validateButton.setVisible(false);
			schemaLabel.setVisible(false);
			fileUploadPanel.getPanel().setVisible(false);
			
			final DialogBox dialogBox = Utils.createCustomDialogBox(panel, "Sensor ML document",validateButton,saveButton,closeButton,schemaLabel,fileUploadPanel.getPanel());
			
			//adds listener to Close button
			closeButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					dialogBox.hide();
				}
			});
			
			//adds listener to Validate button
			validateButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					validate(xml,fileUploadPanel.getContents());
				}
			});
			
			
			//adds listener to Save button
			saveButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					//save(xml);
					saveFile.onBrowseValidate(new IButtonCallback() {
						
						@Override
						public void onClick() {
							saveAs(saveFile.getFileName(),xml);
						}
					});
					//save the result into a file
					saveAs(saveFile.getFileName(),xml);
					//saveFile.click();
				}
			});
		}
	}
	
	/**
	 * Save the content of xmlData into a file using HTML5 File API.
	 *
	 * @param path the path
	 * @param xmlData the xml data
	 */
	private void saveAs(String path,String xmlData) {
		save(xmlData);
	}
	
	/**
	 * Save the content of xmlData into a file using HTML5 File API.
	 *
	 * @param xmlData the xml data
	 */
	private void save(String xmlData) {
		saveFromJs(xmlData);
	}
	
	/**
	 * Validate.
	 *
	 * @param xmlData the xml data
	 * @param xmlSchema the xml schema
	 */
	private void validate(String xmlData,String xmlSchema) {
		validateFromJs(xmlData,xmlSchema);
	}

	/**
	 * Validate from js.
	 *
	 * @param xmlData the xml data
	 * @param schemaData the schema data
	 * @return the string
	 */
	private native String validateFromJs(String xmlData,String schemaData) /*-{
		//create an object
		var Module = {
		      xml: xmlData,
		      schema: schemaData,
		      arguments: ["--noout", "--schema", "1", "2"]
		};
		 
		//and call function
		var result = $wnd.validateXML(Module);
		
	}-*/;
	
	/**
	 * Save from js.
	 *
	 * @param xmlData the xml data
	 */
	private native void saveFromJs(String xmlData) /*-{
		var blob = new Blob([xmlData], {type: "text/xml;charset=utf-8"});
		$wnd.saveAs(blob, "SensorML_XML_result_document.xml");
	}-*/;
}
