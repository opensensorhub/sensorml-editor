package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.OntologyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SaveCloseWindow;
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
				SaveCloseWindow dialogBox = Utils.displaySaveDialogBox(ontologyPanel.getPanel(),"SWE Ontology");
				
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
		valuePanel = new SMLHorizontalPanel();
		
		container = new SMLHorizontalPanel();
		
		container.add(new HTML(Utils.toNiceLabel(name)+":"+SMLEditorConstants.HTML_SPACE));
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
