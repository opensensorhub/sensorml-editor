/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package com.sensia.relaxNG;


public class RNGText extends XSDString
{
    private static final long serialVersionUID = 5994833804497952034L;
    
    
    @Override
    public void accept(RNGTagVisitor visitor)
    {
        visitor.visit(this);        
    }
    
    
    @Override
    public RNGText clone()
    {
        RNGText newTag = (RNGText)super.clone();
        newTag.value = this.value;
        return newTag;
    }


    @Override
    protected RNGTag newInstance()
    {
        return new RNGText();
    }    
}
