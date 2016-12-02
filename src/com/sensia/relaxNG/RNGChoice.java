/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package com.sensia.relaxNG;

import java.util.ArrayList;
import java.util.List;


/**
 * <p><b>Title:</b>
 * RNGChoice
 * </p>
 *
 * <p><b>Description:</b><br/>
 * TODO RNGChoice type description
 * </p>
 *
 * <p>Copyright (c) 2011</p>
 * @author Alexandre Robin
 * @date Sep 26, 2011
 */
public class RNGChoice extends RNGTagList
{
    private static final long serialVersionUID = -644155943333464599L;
    protected int selectedIndex = -1;
        
    
    public List<RNGTag> getItems()
    {
        return children;
    }
    
    
    public int getSelectedIndex()
    {
        return selectedIndex;
    }
    
    
    public void setSelectedIndex(int item)
    {
        this.selectedIndex = item;
        notifyObservers();
    }
    
    
    public boolean isSelected()
    {
        return (selectedIndex >= 0 && selectedIndex < children.size());
    }
    
    
    public RNGTag getSelectedPattern()
    {
        if (isSelected())
            return children.get(selectedIndex);
        else
            return null;
    }
    
    
    @Override
    public void accept(RNGTagVisitor visitor)
    {
        visitor.visit(this);        
    }
    
    
    @Override
    public RNGChoice clone()
    {
        RNGChoice newTag = (RNGChoice)super.clone();
        newTag.selectedIndex = this.selectedIndex;
        return newTag;
    }


    @Override
    protected RNGTag newInstance()
    {
        return new RNGChoice();
    }
    
    
    public void combineNestedChoices()
    {
        ArrayList<RNGTag> allItems = new ArrayList<RNGTag>();
        for (RNGTag item: children)
        {
            if (item instanceof RNGRef)
                collectItems((RNGRef)item, allItems);
            else
                allItems.add(item);
        }
        
        children.clear();
        children.addAll(allItems);
    }
    
    
    protected void collectItems(RNGRef ref, List<RNGTag> allItems)
    {
        RNGDefine pattern = ref.getPattern();
        if (pattern.getAnnotation() == null)
        {
            for (RNGTag item: pattern.getChildren())
            {
                if (item instanceof RNGRef)
                    collectItems((RNGRef)item, allItems);
                else
                    allItems.add(item);
            }
        }
        else
            allItems.add(pattern);
    }
    
}
