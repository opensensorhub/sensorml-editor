package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;

/**
 * 
 * Handle  a link pointing to a new viewer using the current xlink ref.
 * attributes:
 * - name
 * - xlink:title
 * - xlink:role
 * - xlink:href
 *
 */
public class SMLEditComponentPanel extends EditSubSectionElementPanel{

	public SMLEditComponentPanel(RNGElement tag) {
		super(tag);
		container.addStyleName("component");
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
