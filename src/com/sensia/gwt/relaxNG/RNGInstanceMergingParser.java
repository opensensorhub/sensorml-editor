/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2017 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package com.sensia.gwt.relaxNG;

import java.util.ListIterator;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Attr;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.ProcessingInstruction;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;
import com.sensia.relaxNG.*;


/**
 * <p>
 * This class opens both an XML instance and the associated RelaxNG profile
 * and attempts to merge in information from the instance into the RelaxNG
 * data model.<br/>
 * This is used to recreate the state of the editor after an XML instance has
 * been saved and reopened.
 * </p>
 *
 * @author Alex Robin <alex.robin@sensiasoftware.com>
 * @since Feb 1, 2017
 */
public class RNGInstanceMergingParser
{
    private final static String MERGED_ATTRIB = "__merged__";
    private final static String MISSING = "Missing Content";
    private final static String INVALID = "Invalid Content: ";
    private RNGGrammar grammar;
    private Document document;
    
    static enum MergeResult
    {
        MERGED,
        NOMATCH,
        ERROR
    }
    
    static class MergeContext
    {
        public Node parentNode;
        public int nextChild = 0;
        public RNGTag errorTag;
        
        public MergeContext(Node parentNode)
        {
            this.parentNode = parentNode;
        }        
        
        public String getNodeName()
        {
            if (parentNode == null)
                return null;
            if (nextChild >= parentNode.getChildNodes().getLength())
                return null;
            return parentNode.getChildNodes().item(nextChild).getNodeName();
        }
        
        public MergeContext clone()
        {
            MergeContext newContext = new MergeContext(parentNode);
            newContext.nextChild = nextChild;
            return newContext;
        }
    }
    

    /**
     * Parse and merge the XML instance with the associated profile.<br/>
     * The profile URL must be specified in the document.
     * @param documentUrl
     * @param callback
     */
    public void parseFromUrl(final String documentUrl, final RNGParserCallback callback)
    {
        parseFromUrl(documentUrl, null, callback);
    }
    
    
    /**
     * Parse and merge the XML instance with the specified profile
     * @param documentUrl URL of instance document
     * @param profileUrl URL of RelaxNG grammar document
     * @param callback notified when parsing and merging is done
     */
    public void parseFromUrl(final String documentUrl, final String profileUrl, final RNGParserCallback callback)
    {
        // load instance document
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(documentUrl));
        try
        {
            builder.sendRequest(null, new RequestCallback()
            {
                public void onError(Request request, Throwable exception)
                {
                    Window.alert("Error while connecting to " + documentUrl + ": " + exception.getMessage());
                }

                public void onResponseReceived(Request request, Response resp)
                {
                    int statusCode = resp.getStatusCode();
                    if (statusCode == 200)
                    {
                        // parse XML instance document
                        String xml = resp.getText();
                        parseFromString(documentUrl, xml, profileUrl, callback);
                    }
                    else
                    {
                        Window.alert("Cannot load " + documentUrl + ": HTTP " + statusCode);
                    }
                }
            });
        }
        catch (RequestException e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void parseFromString(String documentUrl, String xml, RNGParserCallback callback)
    {
        parseFromString(documentUrl, xml, null, callback);
    }
    
    
    public void parseFromString(String documentUrl, String xml, String profileUrl, RNGParserCallback callback)
    {
        Document dom = XMLParser.parse(xml);
        XMLParser.removeWhitespace(dom);
        document = XMLParser.parse(xml);
        
        // extract schema URL
        String schemaUrl = profileUrl;
        if (profileUrl == null)
            schemaUrl = getSchemaUrl();
        parseRelaxNg(schemaUrl, callback);
    }
    
    
    protected String getSchemaUrl()
    {
        NodeList children = document.getChildNodes();
        for (int i = 0; i < children.getLength(); i++)
        {
            Node node = children.item(i);
            if (node instanceof ProcessingInstruction)
            {
                if ("xml-model".equals(node.getNodeName()))
                {
                    String piData = ((ProcessingInstruction)node).getData();                    
                    int urlStartIndex = piData.indexOf("href=\"");
                    if (urlStartIndex < 0)
                        return null;
                    urlStartIndex += 6;
                    int urlEndIndex = piData.indexOf('"', urlStartIndex);
                    String schemaUrl = piData.substring(urlStartIndex, urlEndIndex);
                    GWT.log(schemaUrl);
                    return schemaUrl;
                }
            }
        }
        
        return null;
    }
    
    
    protected void parseRelaxNg(String schemaUrl, final RNGParserCallback callback)
    {
        RNGParser parser = new RNGParser();
        parser.parseFromUrl(schemaUrl, new RNGParserCallback() {
            @Override
            public void onParseDone(final RNGGrammar grammar) {
                RNGInstanceMergingParser.this.grammar = grammar;
                mergeIntoGrammar();
                callback.onParseDone(grammar);
            }
        });
    }


    protected void mergeIntoGrammar()
    {
        merge(grammar.getStartPattern(), new MergeContext(document));
    }
    
    
    protected MergeResult merge(RNGTag tag, MergeContext context)
    {
        if (tag instanceof RNGElement)
            return merge(((RNGElement)tag), context);
        else if (tag instanceof RNGAttribute)
            return merge(((RNGAttribute)tag), context);
        else if (tag instanceof RNGGroup)
            return merge(((RNGGroup)tag), context);
        else if (tag instanceof RNGDefine)
            return merge(((RNGDefine)tag), context);
        else if (tag instanceof RNGInterleave)
            return merge(((RNGInterleave)tag), context);
        else if (tag instanceof RNGOptional)
            return merge(((RNGOptional)tag), context);
        else if (tag instanceof RNGChoice)
            return merge(((RNGChoice)tag), context);
        else if (tag instanceof RNGZeroOrMore)
            return merge(((RNGZeroOrMore)tag), context);
        else if (tag instanceof RNGOneOrMore)
            return merge(((RNGOneOrMore)tag), context);
        else if (tag instanceof RNGRef)
            return merge(((RNGRef)tag), context);
        else if (tag instanceof RNGData)
            return merge(((RNGData<?>)tag), context);
        else if (tag instanceof RNGList)
            return merge(((RNGList)tag), context);
        else if (tag instanceof RNGValue)
            return merge(((RNGValue)tag), context);
        else
            return MergeResult.ERROR;
    }
    
    
    protected MergeResult merge(RNGGroup group, MergeContext context)
    {
        MergeResult res = MergeResult.MERGED;
        
        for (RNGTag tag: group.getChildren())
        {
            // a single error causes the whole group to be in error
            if (merge(tag, context) == MergeResult.ERROR)
                res = MergeResult.ERROR;
        }
        
        return res;
    }
    
    
    protected MergeResult merge(RNGInterleave interleave, MergeContext context)
    {
        return MergeResult.ERROR;
    }
    
    
    protected MergeResult merge(RNGOptional optional, MergeContext context)
    {
        int savedIndex = context.nextChild;
        
        RNGGroup group = new RNGGroup(optional);
        if (merge(group, context) == MergeResult.MERGED)
        {
            optional.setSelected(true);
            //clearErrors(optional);
            return MergeResult.MERGED;
        }
        else
        {
            context.nextChild = savedIndex;
            return MergeResult.NOMATCH;
        }
    }
    
    
    protected MergeResult merge(RNGChoice choice, MergeContext context)
    {
        int savedIndex = context.nextChild;
        
        // at least one choice item must match!!
        choice.combineNestedChoices();
        for (int i=0; i<choice.getItems().size(); i++)
        {
            RNGTag tag = choice.getItems().get(i);
            if (merge(tag, context) == MergeResult.MERGED)
            {
                choice.setSelectedIndex(i);
                return MergeResult.MERGED;
            }
            else
                context.nextChild = savedIndex;
        }
        
        // nothing matched
        choice.setErrorMsg(INVALID + context.getNodeName());
        return MergeResult.ERROR;
    }


    protected MergeResult merge(RNGZeroOrMore zeroOrMore, MergeContext context)
    {
        int beginIndex = context.nextChild;
        int savedIndex = 0;
        RNGGroup group0 = new RNGGroup(zeroOrMore);
        
        MergeResult res;
        do
        {
            savedIndex = context.nextChild;
            
            // try to merge into a new instance of the pattern
            RNGGroup group = group0.clone();
            res = merge(group, context);
            if (res == MergeResult.MERGED)
                zeroOrMore.addOccurence(group.getChildren());
        }
        while (res == MergeResult.MERGED);
        
        // restore index to end of last matched pattern
        context.nextChild = savedIndex;
        
        // ok if at least one occurence was found
        if (context.nextChild > beginIndex)
            return MergeResult.MERGED;
        else
            return MergeResult.NOMATCH;            
    }


    protected MergeResult merge(RNGOneOrMore oneOrMore, MergeContext context)
    {
        MergeResult res = merge((RNGZeroOrMore)oneOrMore, context);
        if (res == MergeResult.NOMATCH)
            return MergeResult.ERROR;
        return MergeResult.MERGED;
    }
    
    
    protected MergeResult merge(RNGElement rngElt, MergeContext context)
    {
        int savedIndex = context.nextChild;
        MergeResult result = MergeResult.ERROR;
        GWT.log("Trying to merge " + rngElt.getName());
        
        // find next element
        Element domElt = null;
        NodeList nodeList = context.parentNode.getChildNodes();
        while (context.nextChild < nodeList.getLength())
        {
            Node nextNode = nodeList.item(context.nextChild);
            context.nextChild++;
            
            if (nextNode instanceof Element)
            {
                domElt = (Element)nextNode;
                result = MergeResult.ERROR;
                GWT.log("Testing " + domElt.getNodeName());
                
                // check element name and namespace
                String localName = domElt.getNodeName();
                localName = localName.substring(localName.indexOf(':') + 1);
                if (rngElt.getName().equals(localName) &&
                    (rngElt.getNamespace() != null && rngElt.getNamespace().equals(domElt.getNamespaceURI())))
                {
                    result = MergeResult.MERGED;
                    
                    // if element is valid, try to merge its content using a new context
                    MergeContext newContext = new MergeContext(domElt);
                    ListIterator<RNGTag> it = rngElt.getChildren().listIterator();
                    int startCollectIndex = 0;
                    while (it.hasNext())
                    {
                        RNGTag child = it.next();                                                
                        if (merge(child, newContext) == MergeResult.ERROR)
                            result = MergeResult.ERROR;
                                                
                        // any unmatched content remaining is marked as error
                        collectErrors(rngElt, it, newContext, startCollectIndex, newContext.nextChild);
                        startCollectIndex = newContext.nextChild;
                    }
                    
                    // collect any unmatched content left
                    collectErrors(rngElt, it, newContext, startCollectIndex, domElt.getChildNodes().getLength());
                    
                    // if everything merged, stop here
                    if (result == MergeResult.MERGED)
                        break;
                    
                    // otherwise, keep going to see if we find a later match 
                }     
            }
        }
        
        // if nothing matched
        if (result == MergeResult.ERROR)
        {
            if (domElt != null)
                context.nextChild = savedIndex;
            GWT.log("Error on " + rngElt.getName());
            return MergeResult.ERROR;
        }
        
        domElt.setAttribute(MERGED_ATTRIB, Boolean.TRUE.toString());
        GWT.log("Merged " + rngElt.getName());
        return MergeResult.MERGED;
    }
    
    
    protected MergeResult merge(RNGAttribute rngAttrib, MergeContext context)
    {
        Element parentElt = (Element)context.parentNode;
                
        NamedNodeMap attribs = parentElt.getAttributes();
        for (int i=0; i<attribs.getLength(); i++)
        {
            Attr attr = (Attr)attribs.item(i);            
            String localName = getLocalName(attr);
            
            if (rngAttrib.getName().equals(localName) &&
                (rngAttrib.getNamespace() == null || rngAttrib.getNamespace().equals(attr.getNamespaceURI())))
            {
                //GWT.log(attr.getNodeName());
                
                // if attribute is valid, merge its content using a new context
                MergeContext newContext = new MergeContext(attr);
                for (RNGTag child: rngAttrib.getChildren())
                {
                    if (merge(child, newContext) == MergeResult.ERROR)
                        return MergeResult.ERROR;
                }
                
                GWT.log("Merged " + rngAttrib.getName());
                return MergeResult.MERGED;
            }
            
        }
        
        return MergeResult.ERROR;
    }
    
    
    protected MergeResult merge(RNGData<?> data, MergeContext context)
    {
        String value = getNodeValue(context);        
        data.setStringValue(value);
        return MergeResult.MERGED;
    }
    
    
    protected MergeResult merge(RNGValue rngVal, MergeContext context)
    {
        String value = getNodeValue(context);
        
        if (value == null || !value.equals(rngVal.getText()))
            return MergeResult.ERROR;
        
        return MergeResult.MERGED;
    }
    
    
    protected MergeResult merge(RNGDefine define, MergeContext context)
    {
        // TODO handle combine attribute
        return merge(new RNGGroup(define), context);
    }
    
    
    protected MergeResult merge(RNGRef ref, MergeContext context)
    {
        return merge(ref.getPattern(), context);
    }
    
    
    protected String getLocalName(Node node)
    {
        String fullName = node.getNodeName();
        return fullName.substring(fullName.indexOf(':') + 1);
    }
    
    
    protected String getNodeValue(MergeContext context)
    {
        Node node = context.parentNode;
        
        if (node instanceof Element)
        {
            NodeList nodeList = node.getChildNodes();
            while (context.nextChild < nodeList.getLength())
            {
                Node nextNode = nodeList.item(context.nextChild);
                context.nextChild++;                
                if (nextNode instanceof Text)
                    return nextNode.getNodeValue();
            }
        }
        else if (node instanceof Attr)
        {
            return node.getNodeValue();
        }
        
        return null;
    }
    
    
    protected void collectErrors(RNGElement rngElt, ListIterator<RNGTag> tagsIt, MergeContext context, int startIndex, int stopIndex)
    {
        // collect invalid content
        Element domElt = null;
        NodeList nodeList = context.parentNode.getChildNodes();
        
        for (int i = startIndex; i < stopIndex; i++)
        {
            Node nextNode = nodeList.item(i);
            
            if (nextNode instanceof Element)
            {
                domElt = (Element)nextNode;                
                if (!domElt.hasAttribute(MERGED_ATTRIB))
                {
                    GWT.log(INVALID + '\n' + domElt.toString());
                    RNGTag errorTag = generateInvalidContent(null, domElt);
                    errorTag.setParent(rngElt);
                    tagsIt.previous();
                    tagsIt.add(errorTag);
                    tagsIt.next();
                }
            }
        }
        
        // collect missing content
        
    }
    
    
    protected void clearErrors(RNGTag tag)
    {
        tag.setErrorMsg(null);
        
        if (tag instanceof RNGRef)
        {
            clearErrors(((RNGRef)tag).getPattern());
        }
        else if (tag instanceof RNGTagList)
        {
            ListIterator<RNGTag> it = ((RNGTagList) tag).getChildren().listIterator();
            while (it.hasNext())
            {
                RNGTag child = it.next();
                if (child instanceof RNGInvalidContent) {
                    it.remove();
                    GWT.log(INVALID + ((RNGInvalidContent)child).getInvalidContent().toString());
                }
                clearErrors(child);
            }
        }
    }
    
    
    protected RNGTag generateInvalidContent(RNGElement expectedElt, Element domElt)
    {
        /*RNGGroup newTag = new RNGGroup();
        parseInvalidChildren(newTag, domElt);
        newTag.getChildren().get(0).setErrorMsg(INVALID);
        return newTag.getChildren().get(0);*/
        RNGInvalidContent errorTag = new RNGInvalidContent();
        errorTag.setExpectedPattern(expectedElt);
        errorTag.setInvalidContent(domElt);
        return errorTag;
    }
    
    
    protected void parseInvalidChildren(RNGTag parent, Node element)
    {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++)
        {
            Node node = children.item(i);

            if (node.getNodeType() == Node.TEXT_NODE)
            {
                String val = node.getNodeValue();
                if (val != null && !val.trim().isEmpty())
                {
                    XSDString rngString = new XSDString();
                    rngString.setStringValue(val);
                    ((RNGTagList) parent).add(rngString);
                }
            }
            else if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element elt = (Element) node;
                RNGElement rngElt = new RNGElement();
                QName qName = parseName(elt.getNodeName());

                rngElt.setName(qName.localName);
                rngElt.setNamespace(qName.namespaceURI);

                //add attributes if any
                if (elt.hasAttributes())
                {
                    NamedNodeMap attributes = elt.getAttributes();

                    for (int j = 0; j < attributes.getLength(); j++)
                    {
                        Node attr = attributes.item(j);
                        RNGAttribute rngAtt = new RNGAttribute();
                        qName = parseName(attr.getNodeName());

                        rngAtt.setName(qName.localName);
                        rngAtt.setNamespace(qName.namespaceURI);

                        //RNGValue rngValue = new RNGValue();
                        XSDString rngData = new XSDString();
                        //rngValue.setText(attr.getNodeValue().replace("\\s+", " "));
                        rngData.setStringValue(attr.getNodeValue());
                        //add value to attribute
                        rngAtt.add(rngData);

                        //add attribute to element
                        rngElt.add(rngAtt);
                    }
                }
                ((RNGTagList) parent).add(rngElt);
                parseInvalidChildren(rngElt, elt);
            }
        }
    }
    
    
    protected QName parseName(String qname)
    {
        //split the name space from the name
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
}