/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package com.sensia.gwt.relaxNG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGDefine;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.relaxNG.RNGGroup;
import com.sensia.relaxNG.RNGList;
import com.sensia.relaxNG.RNGOneOrMore;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGRef;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagList;
import com.sensia.relaxNG.RNGText;
import com.sensia.relaxNG.RNGValue;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.relaxNG.XSDAnyURI;
import com.sensia.relaxNG.XSDBoolean;
import com.sensia.relaxNG.XSDDateTime;
import com.sensia.relaxNG.XSDDecimal;
import com.sensia.relaxNG.XSDDouble;
import com.sensia.relaxNG.XSDInteger;
import com.sensia.relaxNG.XSDString;
import com.sensia.relaxNG.XSDToken;


/**
 * <p><b>Title:</b>
 * RNGParser
 * </p>
 *
 * <p><b>Description:</b><br/>
 * This class parses a RelaxNG document to the corresponding object hierarchy
 * using GWT DOM so that it can be done in web client.
 * </p>
 *
 * <p>Copyright (c) 2011</p>
 * @author Alexandre Robin
 * @date Aug 27, 2011
 */
public class RNGParser
{
    protected final static String RNG_NS_URI = "http://relaxng.org/ns/structure/1.0";
    protected final static String ANNOT_NS_URI = "http://relaxng.org/ns/compatibility/annotations/1.0";
    protected RNGParserContext context;
    protected RNGParserCallback callback;
    protected RNGGrammar grammar;
    protected int numIncludes;
    
    
    static class RNGParserContext
    {
        public Map<String, RNGGrammar> cachedGrammars = new HashMap<String, RNGGrammar>();
        public Map<String, List<RNGParserCallback>> loadingGrammars = new HashMap<String, List<RNGParserCallback>>();
    }
    
    
    public void parseFromUrl(final String url, final RNGParserCallback callback)
    {
        parseFromUrl(url, new RNGParserContext(), callback);
    }
    
    
    protected void parseFromUrl(final String url, final RNGParserContext context, final RNGParserCallback callback)
    {
        this.context = context;
        this.callback = callback;
        
        // get it directly if grammar is in cache
        grammar = context.cachedGrammars.get(url);
        if (grammar != null)
        {
            GWT.log("Using cached grammar: " + url);
            callback.onParseDone(grammar);
            return;
        }
        
        // wait if grammar is already loading
        if (context.loadingGrammars.containsKey(url))
        {
            GWT.log("Waiting for grammar: " + url);
            context.loadingGrammars.get(url).add(callback);
            return;
        }
        
        GWT.log("Parsing grammar: " + url);
        context.loadingGrammars.put(url, new ArrayList<RNGParserCallback>());
        
        // otherwise load included grammar and parse it asynchronously
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
        try {
            
          builder.sendRequest(null, new RequestCallback() {
            public void onError(Request request, Throwable exception) {
               // Couldn't connect to server (could be timeout, SOP violation, etc.)
            }

            public void onResponseReceived(Request request, Response resp) {
              if (200 == resp.getStatusCode()) {
                  String text = resp.getText();
                  parseFromString(url, text, callback);                  
              } else {
                // Handle the error.  Can get the status text from response.getStatusText()
              }
            }
          });
        } catch (RequestException e) {
          e.printStackTrace();
        }        
    }
    
    
    public void parseFromString(String url, String xml, final RNGParserCallback callback)
    {
        this.callback = callback;
        Document dom = XMLParser.parse(xml);
        XMLParser.removeWhitespace(dom);
        parseGrammar(url, dom.getDocumentElement());
    }
    
    
    protected void parseChildren(RNGTag parent, Element parentElt)
    {
        int eltCount = 0;
        NodeList children = parentElt.getChildNodes();
        for (int i = 0; i < children.getLength(); i++)
        {
            Node node = children.item(i);
            if (!(node instanceof Element))
                continue;
            Element elt = (Element)node;
            String eltName = getLocalName(elt);
            String nsUri = node.getNamespaceURI();
            
            // annotation
            if (nsUri != null && nsUri.equals(ANNOT_NS_URI) && eltName.equals("documentation"))
                parent.setAnnotation(getTextValue(node));
            
            // parse nested relaxNG tags
            if (parent instanceof RNGTagList)
            {
                if (!nsUri.equals(RNG_NS_URI))
                    continue;
                
                // selected item
                if (parent instanceof RNGChoice && "true".equalsIgnoreCase(elt.getAttribute("selected")))
                    ((RNGChoice)parent).setSelectedIndex(eltCount);
                eltCount++;
                
                RNGTag tag = null;
                if (eltName.equals("ref"))
                {
                    RNGRef ref = new RNGRef();
                    ref.setParentGrammar(grammar);
                    ref.setPatternName(elt.getAttribute("name"));
                    parseChildren(ref, elt);
                    tag = ref;
                }
                
                else if (eltName.equals("element"))
                {
                    RNGElement rngElt = new RNGElement();
                    QName qname = parseRNGObjectName(elt);
                    rngElt.setName(qname.localName);
                    rngElt.setNamespace(qname.namespaceURI);
                    parseChildren(rngElt, elt);
                    tag = rngElt;
                }
                
                else if (eltName.equals("attribute"))
                {
                    RNGAttribute rngAtt = new RNGAttribute();
                    QName qname = parseRNGObjectName(elt);
                    rngAtt.setName(qname.localName);
                    rngAtt.setNamespace(qname.namespaceURI);
                    parseChildren(rngAtt, elt);
                    tag = rngAtt;
                }
                
                else if (eltName.equals("optional"))
                {
                    tag = new RNGOptional();
                    parseChildren(tag, elt);
                    if ("true".equalsIgnoreCase(elt.getAttribute("selected")))
                        ((RNGOptional)tag).setSelected(true);
                }
                
                else if (eltName.equals("choice"))
                {
                    tag = new RNGChoice();
                    parseChildren(tag, elt);
                }
                
                else if (eltName.equals("zeroOrMore"))
                {
                    tag = new RNGZeroOrMore();
                    parseChildren(tag, elt);
                    if ("true".equalsIgnoreCase(elt.getAttribute("selected")))
                        ((RNGZeroOrMore)tag).newOccurence();
                }
                
                else if (eltName.equals("oneOrMore"))
                {
                    tag = new RNGOneOrMore();
                    parseChildren(tag, elt);
                }
                
                else if (eltName.equals("group"))
                {
                    tag = new RNGGroup();
                    parseChildren(tag, elt);
                }
                
                else if (eltName.equals("list"))
                {
                    tag = new RNGList();
                    parseChildren(tag, elt);
                }
                
                else if (eltName.equals("text"))
                {
                    tag = new RNGText();
                    parseChildren(tag, elt);
                }
                
                else if (eltName.equals("data"))
                {
                    tag = parseDataType(elt);
                    parseChildren(tag, elt);
                }
                
                else if (eltName.equals("value"))
                {
                    RNGValue val = new RNGValue();
                    val.setText(getTextValue(elt));
                    parseChildren(val, elt);
                    tag = val;
                }
                else if (eltName.equals("defaultValue"))
                {
                    RNGValue val = new RNGValue();
                    val.setText(getTextValue(elt));
                    parseChildren(val, elt);
                    tag = val;
                }
                
                if (tag != null)
                    ((RNGTagList)parent).add(tag);
            }
        }
    }
    
    
    protected RNGGrammar parseGrammar(String url, Element grammarElt)
    {
        grammar = new RNGGrammar();
        grammar.setId(url);
        
        // figure out base URL
        int lastSlash = url.lastIndexOf('/');
        String baseUrl = (lastSlash > 0) ? url.substring(0, lastSlash+1) : "";
        
        // namespaces
        NamedNodeMap atts = grammarElt.getAttributes();
        for (int i = 0; i < atts.getLength(); i++)
        {
            Node node = atts.item(i);
            if (node.getPrefix() != null && node.getPrefix().equals("xmlns"))
            {
                String prefix = node.getNodeName().substring(node.getNodeName().indexOf(':')+1);
                String uri = node.getNodeValue();
                
                if (uri.equals(RNGParser.RNG_NS_URI) ||
                    uri.equals(RNGParser.ANNOT_NS_URI))
                    continue;
                
                grammar.addNamespace(prefix, uri);
            }
        }
        
        // get included grammars
        List<Element> includeElts = new ArrayList<Element>();
        NodeList children = grammarElt.getChildNodes();
        for (int i = 0; i < children.getLength(); i++)
        {
            if (children.item(i).getNodeType() != Node.ELEMENT_NODE)
                continue;
            
            Element child = (Element)children.item(i);
            String childName = child.getNodeName();
            
            if (childName.equals("include") && child.hasAttributes())
                includeElts.add(child);
        }
        numIncludes = includeElts.size();
        
        // finish parsing now if no includes
        if (numIncludes == 0)
            finishParsing(grammarElt);
        
        // else parse all included grammar asynchronously
        // we will finish parsing on callback when all includes are loaded
        else for (Element includeElt: includeElts)
            parseIncludedGrammar(baseUrl, includeElt);
                    
        return grammar;
    }
    
    
    protected void parseIncludedGrammar(String baseUrl, final Element includeElt)
    {
        String url = includeElt.getAttribute("href");
        final String cleanUrl = canonicalizeUrl(baseUrl, url);
        
        // add an entry so order of include is preserved
        grammar.getIncludedGrammars().put(cleanUrl, null);
        
        RNGParser parser = new RNGParser();
        parser.parseFromUrl(cleanUrl, context, new RNGParserCallback() {
            @Override
            public void onParseDone(RNGGrammar g)
            {
                GWT.log("Included grammar: " + cleanUrl);
                
                // parse embedded patterns (start, defines)
                parsePatternsAndAddToGrammar(g, includeElt);
                grammar.setStartPattern(g.getStartPattern());
                
                grammar.getIncludedGrammars().put(cleanUrl, g);
                
                // finish when all included grammars are actually loaded
                int count = 0;
                for (RNGGrammar inc: grammar.getIncludedGrammars().values())
                    if (inc != null)
                        count++;                
                if (count == numIncludes)
                    finishParsing((Element)includeElt.getParentNode());
            }
        });      
    }
    
    private RNGParserContext getContext() {
        if(context == null) {
            context = new RNGParserContext();
        }
        return context;
    }

    protected void finishParsing(Element grammarElt)
    {
        parsePatternsAndAddToGrammar(grammar, grammarElt);
        
        // add grammar to cache
        getContext().cachedGrammars.put(grammar.getId(), grammar);
        
        // call main callback
        GWT.log("Done parsing: " + grammar.getId());
        callback.onParseDone(grammar);
        
        // also call all callbacks registered while grammar was loading
        List<RNGParserCallback> callbacks = getContext().loadingGrammars.remove(grammar.getId());
        if (callbacks != null) 
        {
            for (RNGParserCallback callback: callbacks)
                callback.onParseDone(grammar);
        }
    }
    
    
    protected void parsePatternsAndAddToGrammar(RNGGrammar grammar, Element parentElt)
    {
        NodeList children = parentElt.getChildNodes();
        for (int i = 0; i < children.getLength(); i++)
        {
            Node node = children.item(i);
            if (!(node instanceof Element))
                continue;
            
            Element elt = (Element)node;
            String eltName = getLocalName(elt);
            
            String nsUri = node.getNamespaceURI();
            if (!nsUri.equals(RNG_NS_URI))
                continue;
            
            if (eltName.equals("define"))
            {
                RNGDefine pattern = new RNGDefine();
                pattern.setId(elt.getAttribute("name"));
                parseChildren(pattern, elt);
                grammar.getPatterns().put(pattern.getId(), pattern);
            }
            
            else if (eltName.equals("start"))
            {
                RNGGroup startPattern = new RNGGroup();
                parseChildren(startPattern, elt);
                grammar.setStartPattern(startPattern);
            }
        }
    }
    
    
    protected String canonicalizeUrl(String baseUrl, String url)
    {
        if (!url.startsWith("http"))
            url = baseUrl + url;
        
        // remove all . and ..
        String[] path = url.split("/");
        List<String> newPath = new ArrayList<String>();
        for (int i = 0; i < path.length; i++)
        {
            if (path[i].equals("."))
               continue;
            
            if (path[i].equals("..") && newPath.size() > 0)
                newPath.remove(newPath.get(newPath.size()-1));
            else
                newPath.add(path[i]);
        }
        
        // rebuild URL
        StringBuilder cleanUrl = new StringBuilder();
        for (String part: newPath)
        {
            cleanUrl.append(part);
            cleanUrl.append('/');
        }
        cleanUrl.deleteCharAt(cleanUrl.length()-1);
        
        return cleanUrl.toString();
    }
    
    
    protected RNGData<?> parseDataType(Element elt)
    {
        RNGData<?> data = null;
        String dataType = elt.getAttribute("type");
       
        // instantiate the right class
        if (dataType.equals("boolean"))
            data = new XSDBoolean();
        else if (dataType.equals("dateTime") || dataType.equals("date"))
            data = new XSDDateTime();
        else if (dataType.equals("decimal"))
            data = new XSDDecimal();
        else if (dataType.equals("double"))
            data = new XSDDouble();
        else if (dataType.equals("integer") || dataType.equals("positiveInteger"))
            data = new XSDInteger();
        else if (dataType.equals("string"))
            data = new XSDString();
        else if (dataType.equals("anyURI"))
            data = new XSDAnyURI();
        else if (dataType.equals("token") || dataType.equals("ID"))
            data = new XSDToken();
        else
            data = new RNGData<String>();
         
        // read params
        NodeList paramElts = elt.getElementsByTagName("param");
        for (int i = 0; i < paramElts.getLength(); i++)
        {
            Element paramElt = (Element)paramElts.item(i);
            String name = paramElt.getAttribute("name");
            String value = getTextValue(paramElt);
            data.getParams().put(name, value);
        }

        // check for value
        if(elt.hasChildNodes() && elt.getFirstChild().getNodeName().equalsIgnoreCase("value")) {
            String value = getTextValue(elt.getFirstChild());
            data.setStringValue(value);
        }
        data.setType(dataType);
        return data;
    }
    
    
    protected QName parseRNGObjectName(Element elt)
    {
        String qname = elt.getAttribute("name");
        if (qname != null)
        {
            return parseName(qname);
        }
        else
        {
            NodeList children = elt.getChildNodes();
            for (int i = 0; i < children.getLength(); i++)
            {
                Node node = children.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && getLocalName(node).equals("name"))
                {
                    String nsUri = ((Element)node).getAttribute("ns");
                    String localName = getTextValue(elt);
                    return new QName(nsUri, localName);
                }
            }
        }
        
        return null;
    }
    
    
    protected QName parseName(String qname)
    {
        String[] tokens = qname.split(":");
        String localName = null, nsUri = null;
        
        if (tokens.length == 1)
        {
            nsUri = null;
            localName = tokens[0];
        }
        else
        {
            nsUri = grammar.getNsPrefixToUri().get(tokens[0]);
            localName = tokens[1];
        }
        
        return new QName(nsUri, localName);
    }
    
    
    protected String getLocalName(Node node)
    {
        QName qname = parseName(node.getNodeName());
        return qname.localName;
    }
    
    
    protected String getTextValue(Node node)
    {
        if (!node.hasChildNodes() || node.getFirstChild().getNodeType() != Node.TEXT_NODE)
            return null;

        String textValue = node.getFirstChild().getNodeValue();

        if (textValue != null)
            return textValue.replace("\\s+", " ");
        else
            return null;
    }
}
