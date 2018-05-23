/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package com.sensia.relaxNG;

import com.sensia.tools.client.swetools.editors.sensorml.controller.Observable;


/**
 * <p><b>Title:</b>
 * RNGTag
 * </p>
 *
 * <p><b>Description:</b><br/>
 * TODO RNGTag type description
 * </p>
 *
 * <p>Copyright (c) 2011</p>
 * @author Alexandre Robin
 * @date Sep 26, 2011
 */
public abstract class RNGTag extends Observable implements  ITag
{
    private static final long serialVersionUID = 984732656936241060L;
    protected String id;
    protected String annotation;
    protected RNGTag parent;
    protected boolean disabled;
    protected boolean collapsed;
    protected String errorMsg;
    
    
    public RNGTag()
    {
        super();
    }
    
    
    public String getId()
    {
        return id;
    }


    public void setId(String id)
    {
        this.id = id;
    }


    public String getAnnotation()
    {
        return annotation;
    }


    public void setAnnotation(String annotation)
    {
        this.annotation = annotation;
    }
    
    
    public RNGTag getParent()
    {
        return parent;
    }


    public void setParent(RNGTag parent)
    {
        this.parent = parent;
    }
    
    
    public boolean isDisabled()
    {
        return disabled;
    }
    

    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }


    public boolean isCollapsed()
    {
        return collapsed;
    }


    public void setCollapsed(boolean collapsed)
    {
        this.collapsed = collapsed;
    }


    public String getErrorMsg()
    {
        return errorMsg;
    }


    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }


    public RNGTag clone()
    {
        RNGTag tag = newInstance();
        tag.id = this.id;
        tag.annotation = this.annotation;
        tag.parent = null;
        tag.disabled = this.disabled;
        return tag;
    }
    
    
    protected abstract RNGTag newInstance();


    public abstract void accept(RNGTagVisitor visitor);
    
}