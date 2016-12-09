package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.OntologyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public abstract class AbstractAdvancedAttributeOntologyIconPanel<T extends RNGTag> extends AbstractPanel<T>{

	private IPanel<? extends RNGTag> valuePanel;
	private Image ontologyImage;
	
	public AbstractAdvancedAttributeOntologyIconPanel(T tag) {
		super(tag);
		
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
								((RNGValue)valuePanel.getTag()).setText(value);
							} else if(valuePanel.getTag() instanceof RNGData<?>) {
								((RNGData<?>)valuePanel.getTag()).setStringValue(value);
							}
							
							ontologyImage.setTitle(value);
						}
					});
					dialogBox.show();
			}
		});
		
		container.add(ontologyImage);
	}
	
	@Override
	public String getName() {
		return getTag().toString();
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		 if(element.getTag() instanceof RNGData<?>) {
			valuePanel =  element;
			ontologyImage.setTitle(((RNGData<?>)valuePanel.getTag()).getStringValue());
		}
	}

	@Override
	protected AbstractPanel<T> newInstance() {
		return null;
	}
}
