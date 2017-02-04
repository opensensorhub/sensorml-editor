package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.XSDBoolean;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;

public class XSDBooleanPanel extends AbstractPanel<XSDBoolean>{

    private ListBox listBox;
        
	public XSDBooleanPanel(final XSDBoolean data, final IRefreshHandler refreshHandler) {
		super(data, refreshHandler);
		
		// drop down with true or false
		listBox = new ListBox();
		listBox.addItem("true");
		listBox.addItem("false");
		listBox.addChangeHandler(new ChangeHandler() {            
            @Override
            public void onChange(ChangeEvent event) {
                String val = listBox.getSelectedItemText();
                data.setStringValue(val);
                if(refreshHandler != null)
                    refreshHandler.refresh();
            }
        });
		
		// apply current value
		if ("true".equalsIgnoreCase(data.getStringValue()))
		    listBox.setSelectedIndex(0);
		else
		    listBox.setSelectedIndex(1);
		
		container.add(listBox);
	}

    @Override
    public String getName()
    {
        return "boolean";
    }

    @Override
    protected void addInnerElement(IPanel<? extends RNGTag> element)
    {
        // Do nothing        
    }

    @Override
    protected AbstractPanel<XSDBoolean> newInstance()
    {
        return null;
    }
}
