package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute;

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
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.DataValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.ValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public abstract class AbstractAttributeHrefPanel extends AbstractPanel<RNGAttribute>{

	/** The anchor href. */
	private Anchor anchorHref;
	private IPanel<? extends RNGTag> valuePanel;
	private Image ontologyImage;
	
	//TODO: add textbox to edit value??
	
	public AbstractAttributeHrefPanel(RNGAttribute tag) {
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
		
		//adds ontology handler and open a new Ontology Window
		ontologyImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
					final OntologyPanel ontologyPanel = new OntologyPanel();
					final DialogBox dialogBox = Utils.createEditDialogBox(ontologyPanel.getPanel(),"SWE Ontology",new IButtonCallback() {
						
						@Override
						public void onClick() {
							String value = ontologyPanel.getSelectedValue();
							if(valuePanel.getTag() instanceof RNGValue) {
								RNGValue tag = (RNGValue) valuePanel.getTag();
								tag.setText(value);
								ontologyImage.setTitle(tag.getText());
							} else if(valuePanel.getTag() instanceof RNGData<?>) {
								RNGData<?> tag = (RNGData<?>) valuePanel.getTag();
								tag.setStringValue(value);
								ontologyImage.setTitle(tag.getStringValue());
							}
							
						}
					});
					dialogBox.show();
			}
		});
	}
	
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGValue) {
			valuePanel = element;
			((ValuePanel)valuePanel).setNiceLabel(false);
			RNGValue tag = (RNGValue) valuePanel.getTag();
			anchorHref.setText(tag.getText());
			anchorHref.setHref(tag.getText());
		} else if(element.getTag() instanceof RNGData<?>) {
			valuePanel = element;
			RNGData<?> tag = (RNGData<?>) valuePanel.getTag();
			((DataValuePanel)valuePanel).setNiceLabel(false);
			anchorHref.setText(tag.getStringValue());
			anchorHref.setHref(tag.getStringValue());
		}
	}

	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		return null;
	}

	@Override
	protected void activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel.MODE mode) {
		//TODO: use ontology icon to edit definition
		container.clear();
		
		String text = "";
		
		if(valuePanel.getTag() instanceof RNGValue) {
			text = ((RNGValue)valuePanel.getTag()).getText();
		} else if(valuePanel.getTag() instanceof RNGData<?>) {
			text = ((RNGData<?>)valuePanel.getTag()).getStringValue();
		}
		// update html value with edited value
		anchorHref.setText(text);
		anchorHref.setHref(text);
		
		if(mode == MODE.EDIT) {
			container.add(ontologyImage);
			ontologyImage.setTitle(text);
		} else if (mode == MODE.VIEW) {
			container.add(anchorHref);
		}
	}
}
