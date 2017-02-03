/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.tools.client.swetools.editors.sensorml.IParsingObserver;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;
import com.sensia.tools.client.swetools.editors.sensorml.controller.IObserver;
import com.sensia.tools.client.swetools.editors.sensorml.controller.Observable;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ViewAsRelaxNGButtonClickListener;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ViewAsXMLButtonClickListener;
import com.sensia.tools.client.swetools.editors.sensorml.panels.source.ISourcePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.source.LocalFileSourcePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.source.UrlListSourcePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.source.UrlSourcePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

/**
 * This class is in charge of creating the main Panel. The top elements will 
 * manage the file loading while the center panel display the content of the 
 * transformed XML file into a HTML pretty view.
 * @author Mathieu Dhainaut
 *
 */
public class ViewerPanel extends Composite implements IParsingObserver, IObserver, IRefreshHandler {
	private static final long serialVersionUID = -7684111574093800909L;

	//the panel in charge of displaying the HTML content
	private SMLVerticalPanel mainPanel;
	
	//the checkbox to switch between view and edit mode
	private CheckBox editCheckbox;
	private IPanel<?> root;
	
	//the processor in charge of parsing and create the RNG profile
	private RNGProcessorSML smlEditorProcessor;
	
	private static ViewerPanel instance;
	
	public enum MODE {
		EDIT,
		VIEW
	}
	
	protected ViewerPanel(final RNGProcessorSML sgmlEditorProcessor){
		sgmlEditorProcessor.addObserver(this);
		this.smlEditorProcessor = sgmlEditorProcessor;
		this.smlEditorProcessor.setRefreshHandler(this);
		final Panel viewXmlPanel = getXMLViewPanel();

		final SMLVerticalPanel verticalPanel = new SMLVerticalPanel();
		
		//add View as XML button
		Button viewAsXML = new Button("Save as XML");
		viewAsXML.addClickHandler(new ViewAsXMLButtonClickListener(sgmlEditorProcessor));
		
		Button viewAsRNG = new Button("Save as RelaxNG");
		viewAsRNG.addClickHandler(new ViewAsRelaxNGButtonClickListener(sgmlEditorProcessor));
		
		//Get the url parameter to load the document where this one is under the form : ?url=DocumentPath
		String passedFile = com.google.gwt.user.client.Window.Location.getParameter("url");
		SMLHorizontalPanel panel = new SMLHorizontalPanel();
		panel.add(viewXmlPanel);
		panel.add(viewAsXML);
		panel.add(viewAsRNG);
		
		verticalPanel.add(panel);
		
		if(passedFile != null) {
			//load the file given the url passed as parameter
			//do not display the edit/view options
			smlEditorProcessor.setMode(MODE.VIEW);
			smlEditorProcessor.parse(passedFile);			
			editCheckbox.setVisible(true);			
		}
		
		mainPanel = new SMLVerticalPanel();
		mainPanel.addStyleName("main-content");
		
		verticalPanel.add(mainPanel);
		initWidget(verticalPanel);
		
	}

	// Get the top elements panel for the XML part
	private Panel getXMLViewPanel() {
		final SMLHorizontalPanel panel = new SMLHorizontalPanel();
		
		HTML title = new HTML("<b>SensorML XML/RNG:</b>");
		final Button load = new Button("Load");
		
		//init radio buttons choices
		final RadioButton fromList = new RadioButton("myRadioGroup", "from list");
		final RadioButton fromUrl = new RadioButton("myRadioGroup", "from url");
		final RadioButton fromLocal = new RadioButton("myRadioGroup", "from local");
        		
		SMLHorizontalPanel hPanel = new SMLHorizontalPanel();
		
		SMLVerticalPanel choicePanel = new SMLVerticalPanel();
		choicePanel.add(fromList);
		choicePanel.add(fromUrl);
		choicePanel.add(fromLocal);
        hPanel.add(choicePanel);
		
		editCheckbox = new CheckBox("Edit");
		editCheckbox.setVisible(true);
        editCheckbox.setValue(true);
        
		//container for either local file system input panel or url valueBox
		final SimplePanel fromPanel = new SimplePanel();
		
		//init file upload panel
		final ISourcePanel urlListPanel = new UrlListSourcePanel(smlEditorProcessor, editCheckbox);
		final ISourcePanel urlDownloadPanel = new UrlSourcePanel(smlEditorProcessor, editCheckbox);
		final ISourcePanel fileUploadPanel = new LocalFileSourcePanel(smlEditorProcessor, editCheckbox);
        
		//add to xml panel
		panel.add(title);
		panel.add(hPanel);
		panel.add(fromPanel);
		panel.add(load);
		panel.add(editCheckbox);
		
		//set from list panel as default choice
		fromList.setValue(true);
		fromPanel.add(urlListPanel.getPanel());
				
		//add listener to handle event
		load.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
			    showLoadingIndicator();
			    if (fromList.getValue()) {
                    urlListPanel.parseContent();
                } else if (fromLocal.getValue()) {
					fileUploadPanel.parseContent();
				} else if(fromUrl.getValue()){
					urlDownloadPanel.parseContent();
				}
			}
		});
		
		//after clicking on the checkbox, the mode is sent to the tree hierarchy starting from the Root element
		editCheckbox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(root != null){
					MODE mode = (editCheckbox.getValue()) ? MODE.EDIT : MODE.VIEW;
					smlEditorProcessor.setMode(mode);
					redraw();
				}
			}
		});
		
		//add listener to handle from local file system handler
        fromList.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                if(event.getValue()) {
                    fromPanel.clear();
                    fromPanel.add(urlListPanel.getPanel());
                }
            }
        });
        
		//add listener to handle from local file system handler
		fromUrl.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if(event.getValue()) {
					fromPanel.clear();
					fromPanel.add(urlDownloadPanel.getPanel());
				}
			}
		});
		
		//add listener to handle from local file system handler
        fromLocal.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                if(event.getValue()) {
                    fromPanel.clear();
                    fromPanel.add(fileUploadPanel.getPanel());
                }
            }
        });
        
        return panel;
	}
	
	public void parse(RNGGrammar grammar) {
		smlEditorProcessor.setLoadedGrammar(grammar);
		/*mainPanel.clear();
		IPanel newNode = smlEditorProcessor.parseRNG(grammar);
		mainPanel.add(newNode.getPanel());
		root = newNode;*/
		redraw();
	}
	
	public void parse(String xmlDoc) {
		// use MVC
		smlEditorProcessor.parseString(xmlDoc);
	}
	
	/************* TODO **********************/
	// REMOVE / IMPROVE load grammar, parseRNG etc..
	// Use MVC or MVP or Singleton
	// make the system consistent
	
	/*
	 * (non-Javadoc)
	 * @see com.sensia.swetools.editors.sensorml.client.IParsingObserver#parseDone(com.sensia.swetools.editors.sensorml.client.panels.model.INodeWidget)
	 */
	@Override
	@SuppressWarnings("rawtypes")
    public void parseDone(final IPanel topElement) {
		//One the parsing done, the viewer is reset and displays the new content
		mainPanel.clear();
		mainPanel.add(topElement.getPanel());
		root = topElement;
	}
	
	@Override
	public void update(Observable model, Object hint) {
		GWT.log("redraw");
		//IPanel newNode = smlEditorProcessor.parseRNG(((RNGTag) model).getParent());
		// replace the old corresponding node by the new node
		redraw();
		//smlEditorProcessor.parse(profiles.get(profileListBox.getValue(profileListBox.getSelectedIndex())));
	}
	
	public void redraw() {
		// check styles
		MODE mode = (editCheckbox.getValue()) ? MODE.EDIT : MODE.VIEW;
		redraw((editCheckbox.getValue())? MODE.EDIT:MODE.VIEW);
	}
	
	public void redraw(MODE mode) {
		mainPanel.clear();
		IPanel<?> newNode = smlEditorProcessor.displayRNG(smlEditorProcessor.getLoadedGrammar());
		mainPanel.add(newNode.getPanel());
		root = newNode;
	}
	
	public static ViewerPanel getInstance(final RNGProcessorSML sgmlEditorProcessor){
		if(instance == null) {
			instance = new ViewerPanel(sgmlEditorProcessor);
		} 
		return instance;
	}
	
	public void setMode(MODE mode){
		smlEditorProcessor.setMode(mode);
		editCheckbox.setValue((mode == MODE.EDIT)?true:false);
	}

	@Override
	public void refresh() {
		redraw((editCheckbox.getValue())? MODE.EDIT:MODE.VIEW);
	}
	
	protected void showLoadingIndicator()
	{
	    mainPanel.clear();
	    mainPanel.add(new Image(GWT.getModuleBaseURL()+"images/ajax-loader.gif"));
	}
}
