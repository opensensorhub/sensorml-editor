package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml;

import com.google.gwt.user.client.ui.Anchor;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.AbstractViewAttributeHrefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.element.ViewSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

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
public class SMLViewComponentPanel extends ViewSectionElementPanel{

	public SMLViewComponentPanel(RNGElement tag) {
		super(tag,null);
		container.addStyleName("component");
	}
	
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("name")) {
			labelPanel.clear();
			labelPanel.add(element.getPanel());
			labelPanel.setVisible(true);
		} else {
			if(element instanceof AbstractViewAttributeHrefPanel) {
				Anchor anchor = ((AbstractViewAttributeHrefPanel)element).getAnchorHref();
				anchor.setHref(Utils.getCurrentURL(anchor.getHref()));
			}
			super.addInnerElement(element);
		}
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
