package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.OntologyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SaveCloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class AbstractAdvancedAttributeOntologyIconPanel extends AdvancedAttributePanel{

	public final static String ONTOLOGY_TAB_NAME = "sml.ontology";
    private Panel valuePanel;
	protected Image ontologyImage;
	private RNGData<?> rngData;
	
	public AbstractAdvancedAttributeOntologyIconPanel(RNGAttribute tag,final IRefreshHandler refreshHandler) {
	    this(tag,Utils.findLabel(tag),refreshHandler);
	}	
	
	public AbstractAdvancedAttributeOntologyIconPanel(RNGAttribute tag,String label,final IRefreshHandler refreshHandler) {
		super(tag,label,refreshHandler);
		
		//creates an ontology icon
		ontologyImage = new Image(GWT.getModuleBaseURL()+"images/ontology.png");
		ontologyImage.setTitle("Select from Ontology");
		ontologyImage.setStyleName("ontology-icon");
		ontologyImage.setVisible(false);
		
		//adds ontology handler and open a new Ontology Window
		ontologyImage.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				final OntologyPanel ontologyPanel = new OntologyPanel();
				SaveCloseWindow dialogBox = Utils.displaySaveDialogBox(ontologyPanel.getPanel(),"SWE Ontology");
				dialogBox.setWidth(1024);
				dialogBox.setHeight(800);
				dialogBox.addSaveHandler(new ClickHandler(){
					@Override
					public void onClick(ClickEvent event) {
						String value = ontologyPanel.getSelectedValue();
						if(rngData != null) {
							rngData.setStringValue(value);
						}						
						if(refreshHandler != null) {
							refreshHandler.refresh();
						}
					}
				});
			}
		});
		
		valuePanel = new SMLHorizontalPanel();
		container.add(valuePanel);
		container.add(ontologyImage);
	}
	
	@Override
	public String getName() {
		return getTag().toString();
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
	    if (element instanceof EditValuePanel) {
            ((EditValuePanel) element).setTextBoxSize(50);
            ((EditValuePanel) element).setPlaceholderText("Enter URI or select from ontology");
			rngData = (RNGData<?>) element.getTag();
			ontologyImage.setVisible(true);
		}
		else if (element.getTag() instanceof RNGValue) {
		    String uriVal = ((RNGValue)element.getTag()).getText();
		    valuePanel.add(new Anchor(uriVal, uriVal, ONTOLOGY_TAB_NAME));
		    return;
		}
		valuePanel.add(element.getPanel());
	}
}
