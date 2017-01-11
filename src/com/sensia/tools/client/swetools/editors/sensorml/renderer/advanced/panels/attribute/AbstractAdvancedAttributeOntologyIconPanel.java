package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.OntologyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.CloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public abstract class AbstractAdvancedAttributeOntologyIconPanel<T extends RNGTag> extends AbstractPanel<T>{

	private Panel valuePanel;
	protected Image ontologyImage;
	private RNGData<?> rngData;
	
	public AbstractAdvancedAttributeOntologyIconPanel(T tag,String name,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		
		//creates an ontology icon
		ontologyImage = new Image(GWT.getModuleBaseURL()+"images/ontology.png");
		ontologyImage.setTitle("Ontology");
		ontologyImage.setStyleName("ontology-icon");
		
		//adds ontology handler and open a new Ontology Window
		ontologyImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				final OntologyPanel ontologyPanel = new OntologyPanel();
				CloseWindow dialogBox = Utils.displaySaveDialogBox(ontologyPanel.getPanel(),"SWE Ontology");
				dialogBox.addSaveHandler(new ClickHandler(){
					@Override
					public void onClick(ClickEvent event) {
						String value = ontologyPanel.getSelectedValue();
						if(rngData != null) {
							rngData.setStringValue(value);
						}
						ontologyImage.setTitle(value);
						
						if(refreshHandler != null) {
							refreshHandler.refresh();
						}
					}
				});
			}
		});
		valuePanel = new HorizontalPanel();
		
		container = new HorizontalPanel();
		
		container.add(new HTML(name+":"+SMLEditorConstants.HTML_SPACE));
		container.add(valuePanel);
		container.add(ontologyImage);
		
		container.addStyleName("attribute-panel-advanced");
	}
	
	@Override
	public String getName() {
		return getTag().toString();
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		 if(element.getTag() instanceof RNGData<?>) {
			rngData = (RNGData<?>) element.getTag();
			valuePanel.add(element.getPanel());
			ontologyImage.setTitle(rngData.getStringValue());
		}
	}

	@Override
	protected AbstractPanel<T> newInstance() {
		return null;
	}
}
