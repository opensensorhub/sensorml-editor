package com.sensia.tools.client.swetools.editors.sensorml.panels.gml.view;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

// xsd:string
// http://www.datypic.com/sc/niem21/e-gml32_identifier.html
public class GMLIdentifierPanel extends AbstractPanel<RNGElement>{

	protected static final String CODE_SPACE_LABEL = "codeSpace";
	
	// codeSpace attribute xsd:anyURI, 	[1..1]
	
	private Panel valuePanel;
	private Panel codeSpacePanel;
	
	public GMLIdentifierPanel(RNGElement element) {
		super(element);
		
		container = new HorizontalPanel();
		
		valuePanel = new SimplePanel();
		codeSpacePanel = new HorizontalPanel();
		container.add(codeSpacePanel);
		container.add(valuePanel);
	}
	
	@Override
	public void addInnerElement(IPanel element) {
		if(element.getTag() instanceof RNGValue || element.getTag() instanceof RNGData<?>){
			valuePanel.add(element.getPanel());
		} else if(element.getTag() instanceof RNGAttribute){
			codeSpacePanel.add(element.getPanel());
			codeSpacePanel.add(new HTML(":&nbsp"));
		} 
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return getTag().getName();
	}

}
