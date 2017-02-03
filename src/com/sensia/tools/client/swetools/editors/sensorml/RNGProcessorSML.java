/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml;

import java.util.ArrayList;
import java.util.List;
import com.sensia.gwt.relaxNG.RNGInstanceMergingParser;
import com.sensia.gwt.relaxNG.RNGParser;
import com.sensia.gwt.relaxNG.RNGParserCallback;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.controller.IObserver;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.ViewerPanel.MODE;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.EditRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.ViewRendererSML;

/**
 * The Class RNGProcessorSML is in charge of parsing a document.
 * The processor can parse a document from local storage (file) or from an url.
 */
public class RNGProcessorSML {

	/** The observers. */
	private List<IObserver> observers;
	
	/** The loaded grammar. */
	private RNGGrammar loadedGrammar;
	
	/** URL of loaded XML document **/
	private String documentUrl;
	
	/** The mode. */
	private MODE mode = MODE.VIEW;
	
	protected IRefreshHandler refreshHandler;
	
	/**
	 * Instantiates a new RNG processor sml.
	 */
	public RNGProcessorSML(){
		this.observers = new ArrayList<IObserver>();
	}
	
	/**
	 * Parses the document from a url
	 * @param url
	 */
	public void parse(final String url) {
		if (url.toLowerCase().endsWith(".rng")) {
		    // if the document is a RNG instance
		    RNGParser.clearCache();
	        final RNGParser parser = new RNGParser();
			parser.parseFromUrl(url, new RNGParserCallback() {
				@Override
				public void onParseDone(final RNGGrammar grammar) {
					displayRNG(grammar);
				}
			});
		} else if(url.toLowerCase().endsWith(".xml")) {
			// if the document is an XML instance
			// merge it with associated profile
		    final RNGInstanceMergingParser parser = new RNGInstanceMergingParser();
			parser.parseFromUrl(url, new RNGParserCallback() {				
				@Override
				public void onParseDone(final RNGGrammar grammar) {
					documentUrl = url;
				    displayRNG(grammar);
				}
			});
		}
	}

	/**
	 * Parses the document from a local file.
	 *
	 * @param fileName the file name
	 * @param xmlContent the xml content
	 * @throws Exception the exception
	 */
	public void parse(final String fileName, final String xmlContent) throws Exception {
	    if (fileName.toLowerCase().endsWith(".rng")) {
	        RNGParser.clearCache();
	        final RNGParser parser = new RNGParser();
	        parser.parseFromString(fileName, xmlContent, new RNGParserCallback() {              
	            @Override
	            public void onParseDone(final RNGGrammar grammar) {
	                displayRNG(grammar);
	            }
	        });
	    } else if(fileName.toLowerCase().endsWith(".xml")) {
	        // merge XML document into RNG profile
	        final RNGInstanceMergingParser parser = new RNGInstanceMergingParser();
	        parser.parseFromString(fileName, xmlContent, new RNGParserCallback() {              
	            @Override
	            public void onParseDone(final RNGGrammar grammar) {
	                documentUrl = fileName;
	                displayRNG(grammar);
	            }
	        }); 
	    }
	}
	
	/**
	 * Displays the RelaxNG grammar
	 * @param grammar the grammar
	 */
	private void displayRNG(final RNGGrammar grammar) {
		setLoadedGrammar(grammar);
		IPanel<?> root  = null;
		
		if(mode == MODE.EDIT){
			EditRendererSML renderer = new EditRendererSML();
			renderer.setObservers(observers);
			renderer.setRefreshHandler(refreshHandler);
			renderer.visit(grammar);
			root = renderer.getRoot();
		} else if(mode == MODE.VIEW) {
			ViewRendererSML renderer = new ViewRendererSML();
			renderer.setRefreshHandler(refreshHandler);
			renderer.setObservers(observers);
			renderer.visit(grammar);
			root = renderer.getRoot();
		}
		
		for(final IObserver observer : observers) {
			observer.parseDone(root);
		}
	}
	
	/**
	 * Displays only a subset of the global document
	 * @param url
	 */
	public IPanel<?> displayRNG(RNGTag tag) {
		if(mode == MODE.VIEW){
			ViewRendererSML renderer = new ViewRendererSML();
			renderer.visit(tag);
			if(tag instanceof RNGGrammar) {
				setLoadedGrammar((RNGGrammar) tag);
			}
			return renderer.getRoot();
		} else {
			EditRendererSML renderer = new EditRendererSML();
			renderer.setRefreshHandler(refreshHandler);
			renderer.visit(tag);
			if(tag instanceof RNGGrammar) {
				setLoadedGrammar((RNGGrammar) tag);
			}
			return renderer.getRoot();
		}
	}
	
	/**
	 * Gets the loaded grammar.
	 *
	 * @return the loaded grammar
	 */
	public RNGGrammar getLoadedGrammar() {
		return loadedGrammar;
	}

	/**
	 * Sets the loaded grammar.
	 *
	 * @param loadedGrammar the new loaded grammar
	 */
	public void setLoadedGrammar(RNGGrammar loadedGrammar) {
		this.loadedGrammar = loadedGrammar;
	}

	public String getDocumentUrl()
    {
        return documentUrl;
    }

    /**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public MODE getMode() {
		return mode;
	}

	/**
	 * Sets the mode.
	 *
	 * @param mode the new mode
	 */
	public void setMode(MODE mode) {
		this.mode = mode;
	}
	
	public void addObserver(IObserver observer) {
		this.observers.add(observer);
	}
	
	public IRefreshHandler getRefreshHandler() {
		return refreshHandler;
	}

	public void setRefreshHandler(IRefreshHandler refreshHandler) {
		this.refreshHandler = refreshHandler;
	}
}
