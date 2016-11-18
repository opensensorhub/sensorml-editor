/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.gwt.relaxNG;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.relaxNG.RNGGroup;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagList;
import com.sensia.relaxNG.RNGText;
import com.sensia.relaxNG.XSDString;

/**
 * The Class XMLSensorMLParser.
 */
public class XMLSensorMLParser {

	/** The grammar cache. */
	protected static Map<String, RNGGrammar> grammarCache = new HashMap<String, RNGGrammar>();
	
	/** The callback. */
	private RNGParserCallback callback;
	
	/** The grammar. */
	private RNGGrammar grammar;

	/**
	 * Gets the content of the remote file and callback the result.
	 * The request is asynchronous
	 *
	 * @param url the url
	 * @param callback the callback
	 */
	public void parse(final String url, final RNGParserCallback callback) {
		this.callback = callback;

		// if grammar has already been parsed
		grammar = grammarCache.get(url);
		if (grammar != null) {
			callback.onParseDone(grammar);
			return;
		} else {
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
			try {

				builder.sendRequest(null, new RequestCallback() {
					public void onError(Request request, Throwable exception) {
						// Couldn't connect to server (could be timeout, SOP
						// violation, etc.)
						
					}

					public void onResponseReceived(Request request, Response resp) {
						if (200 == resp.getStatusCode()) {
							String text = resp.getText();
							try {
								parse(url, text);
							} catch(Exception ex) {
								Window.alert("An error occured while parsing the file. Check that it is a valid XML file");
							}
						} else {
							// Handle the error. Can get the status text from
							// response.getStatusText()
							Window.alert("Cannot get the file from server, it does not exist or you do not have sufficient security credentials to access it.");
						}
					}
				});
			} catch (RequestException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Parses the xml file.
	 *
	 * @param url the url
	 * @param xml the xml
	 * @return the RNG grammar
	 * @throws Exception the exception
	 */
	public RNGGrammar parse(String url, String xml) throws Exception{
		Document dom = XMLParser.parse(xml);
		XMLParser.removeWhitespace(dom);
		createGrammar(url, dom.getDocumentElement());
		return grammar;
	}

	/**
	 * Creates the grammar.
	 *
	 * @param url the url
	 * @param root the root
	 */
	public void createGrammar(String url, Element root) {
		grammar = new RNGGrammar();
		grammar.setId(url);

		// namespaces
		NamedNodeMap atts = root.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Node node = atts.item(i);
			if (node.getPrefix() != null && node.getPrefix().equals("xmlns")) {
				String prefix = node.getNodeName().substring(node.getNodeName().indexOf(':') + 1);
				String uri = node.getNodeValue();

				if (uri.equals(RNGParser.RNG_NS_URI) || uri.equals(RNGParser.ANNOT_NS_URI))
					continue;

				grammar.addNamespace(prefix, uri);
			}
		}
		RNGGroup startPattern = new RNGGroup();
		
    	RNGElement rngElt = new RNGElement();
    	QName qName= parseName(root.getNodeName());
    	
        rngElt.setName(qName.localName);
        rngElt.setNamespace(qName.namespaceURI);
        ((RNGTagList)startPattern).add(rngElt);
        
		parseChildren(rngElt, root);
		grammar.setStartPattern(startPattern);

		grammarCache.put(grammar.getId(), grammar);
		if(callback != null) {
			callback.onParseDone(grammar);	
		}
	}

	
	/**
	 * Parses the children.
	 *
	 * @param parent the parent
	 * @param element the element
	 */
	protected void parseChildren(RNGTag parent, Node element) {
		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);

            if(node.getNodeType() == Node.TEXT_NODE) {
            	XSDString val = new XSDString();
                //val.setText(node.getNodeValue().replace("\\s+", " "));
            	val.setStringValue(node.getNodeValue());
                ((RNGTagList)parent).add(val);
            } else if(node.getNodeType() == Node.ELEMENT_NODE) {
            	Element elt = (Element)node;
            	RNGElement rngElt = new RNGElement();
            	QName qName= parseName(elt.getNodeName());
            	
                rngElt.setName(qName.localName);
                rngElt.setNamespace(qName.namespaceURI);
                
                //add attributes if any
                if (elt.hasAttributes()) {
                	NamedNodeMap attributes = elt.getAttributes();
				
					for (int j = 0; j < attributes.getLength(); j++) {
						Node attr = attributes.item(j);
						RNGAttribute rngAtt = new RNGAttribute();
						qName = parseName(attr.getNodeName());
						
						rngAtt.setName(qName.localName);
						rngAtt.setNamespace(qName.namespaceURI);
						
						//RNGValue rngValue = new RNGValue();
						XSDString	rngData = new XSDString();
						//rngValue.setText(attr.getNodeValue().replace("\\s+", " "));
						rngData.setStringValue(attr.getNodeValue());
						//add value to attribute
						rngAtt.add(rngData);
						
						//add attribute to element
						rngElt.add(rngAtt);
					}
				}
                ((RNGTagList)parent).add(rngElt);
                parseChildren(rngElt, elt);
            } else if(node.getNodeType() == Node.COMMENT_NODE) {
            	RNGText rngComment = new RNGText();
            	rngComment.setText("\n<!--"+node.getNodeValue()+"-->");
            	((RNGTagList)parent).add(rngComment);
            }
		}
	}
	
	/**
	 * Gets the local name.
	 *
	 * @param node the node
	 * @return the local name
	 */
	protected String getLocalName(Node node) {
		QName qname = parseName(node.getNodeName());
		return qname.localName;
	}

	/**
	 * Parses the name.
	 *
	 * @param qname the qname
	 * @return the q name
	 */
	protected QName parseName(String qname) {
		//split the name space from the name
		String[] tokens = qname.split(":");
		String localName = null, nsUri = null;

		if (tokens.length == 1) {
			nsUri = null;
			localName = tokens[0];
		} else {
			nsUri = grammar.getNsPrefixToUri().get(tokens[0]);
			localName = tokens[1];
		}

		return new QName(nsUri, localName);
	}
}
