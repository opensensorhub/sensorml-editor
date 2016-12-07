package com.sensia.tools.client.swetools.editors.sensorml.panels.gml.view;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

/**
 *  http://www.datypic.com/sc/niem21/e-gml32_description.html
 *  Attributes:
 * 	xlink:href	[0..1]	xsd:anyURI		from group xlink:simpleLink
 *	xlink:role	[0..1]	xsd:anyURI		from group xlink:simpleLink
 *	xlink:arcrole	[0..1]	xsd:anyURI		from group xlink:simpleLink
 *	xlink:title	[0..1]	xsd:string		from group xlink:simpleLink
 *
 */
public class GMLDescriptionPanel extends AbstractPanel<RNGElement>{

	private Panel valuePanel;

	public GMLDescriptionPanel(RNGElement element) {
		super(element);
		
		container = new HorizontalPanel();
		valuePanel = new SimplePanel();
		
		// ensure element order
		container.add(valuePanel);
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGValue || element.getTag() instanceof RNGData<?>) {
			valuePanel.add(element.getPanel());
		} 
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
