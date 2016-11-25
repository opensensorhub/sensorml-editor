package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.OntologyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.ViewValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public abstract class AbstractViewAttributeHrefPanel extends AbstractPanel<RNGAttribute>{

	/** The anchor href. */
	private Anchor anchorHref;
	private IPanel<? extends RNGTag> valuePanel;
	private Image ontologyImage;
	
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
		
		//creates an ontology icon
		ontologyImage = new Image(GWT.getModuleBaseURL()+"images/ontology.png");
		ontologyImage.setTitle("Ontology");
		ontologyImage.setStyleName("ontology-icon");
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

	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		return null;
	}
}
