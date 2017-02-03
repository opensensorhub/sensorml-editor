/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.listeners;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.xml.client.Document;
import com.sensia.gwt.relaxNG.RNGInstanceWriter;
import com.sensia.gwt.relaxNG.XMLSerializer;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;
import com.sensia.tools.client.swetools.editors.sensorml.panels.source.FileUploadPanel;
import com.sensia.tools.client.swetools.editors.sensorml.syntaxhighlighter.BrushFactory;
import com.sensia.tools.client.swetools.editors.sensorml.syntaxhighlighter.SyntaxHighlighter;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SaveCloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLPane;

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
public class ViewAsXMLButtonClickListener implements ClickHandler{

	private RNGProcessorSML smlEditorProcessor;
	
	/**
	 * Instantiates a new view as xml button click listener.
	 *
	 * @param sgmlEditorProcessor the sgml editor processor
	 */
	public ViewAsXMLButtonClickListener(final RNGProcessorSML sgmlEditorProcessor) {
		this.smlEditorProcessor = sgmlEditorProcessor;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		RNGGrammar grammar = smlEditorProcessor.getLoadedGrammar();
		if(grammar != null) {		    
		    // write out instance XML and serialize as string
			RNGInstanceWriter instanceWriter = new RNGInstanceWriter();
			Document dom = instanceWriter.writeInstance(grammar);			
			final String xml = XMLSerializer.serialize(dom, grammar.getId());
			
	        // create HTML panel
	        String htmlCode = SyntaxHighlighter.highlight(xml, BrushFactory.newXmlBrush(), false);
	        HTMLPane panel = new HTMLPane();
			HTML html = new HTML(SafeHtmlUtils.fromTrustedString(htmlCode));
			panel.setContents(html.getHTML());
			panel.setWidth100();
			panel.setHeight100();
			panel.setOverflow(Overflow.SCROLL);
			
			// display save dialog box
			final SaveCloseWindow dialog = Utils.displaySaveDialogBox(panel, "SensorML Document");
			dialog.addSaveHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					saveAs(getDefaultFileName(),xml);
				}
			});
			dialog.setExitOnSave(false);
		}
	}
	
	private String getDefaultFileName() {
	    String docUrl = smlEditorProcessor.getDocumentUrl();
	    if (docUrl != null)
	        return docUrl.replaceAll(".*(/|\\\\)", "");
	    else
	        return "sensorml_document.xml";
	}
	
	/**
	 * Save the content of xmlData into a file using HTML5 File API.
	 *
	 * @param path the path
	 * @param xmlData the xml data
	 */
	private void saveAs(String defaultFileName, String xmlData) {
	    saveFromJs(defaultFileName, xmlData);
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
	private native void saveFromJs(String defaultFileName, String xmlData) /*-{
		var blob = new Blob([xmlData], {type: "text/xml;charset=utf-8"});
		$wnd.saveAs(blob, defaultFileName);
	}-*/;
}
