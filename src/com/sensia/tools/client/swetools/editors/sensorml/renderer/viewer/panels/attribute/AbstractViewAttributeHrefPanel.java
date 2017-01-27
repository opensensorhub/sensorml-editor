package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute;

import com.google.gwt.user.client.ui.Anchor;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;

public abstract class AbstractViewAttributeHrefPanel extends AbstractPanel<RNGAttribute>{

	/** The anchor href. */
	private Anchor anchorHref;
	private IPanel<? extends RNGTag> valuePanel;
	
	//TODO: add textbox to edit value??
	
	public AbstractViewAttributeHrefPanel(RNGAttribute tag) {
		super(tag);
		
		anchorHref = new Anchor();
		//display the href as a nice label
		//anchorHref.setText(""+Utils.toNiceLabel(getLinkName())+"");
		//anchorHref.setHref(getValue());
		anchorHref.setText("");
		anchorHref.setHref("");
		anchorHref.addStyleName("xlink");
		//opens in a new tab
		anchorHref.setTarget("_blank");
		
		container.add(anchorHref);
	}
	
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGValue) {
			valuePanel = element;
			((ViewValuePanel)valuePanel).setNiceLabel(false);
			RNGValue tag = (RNGValue) valuePanel.getTag();
			anchorHref.setText(tag.getText());
			anchorHref.setHref(tag.getText());
		} 
	}

	public Anchor getAnchorHref() {
		return anchorHref;
	}
	
	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		return null;
	}
}
