/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.gwt.relaxNG.RNGParser;
import com.sensia.gwt.relaxNG.RNGParserCallback;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.tools.client.swetools.editors.sensorml.IParsingObserver;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;
import com.sensia.tools.client.swetools.editors.sensorml.controller.IObserver;
import com.sensia.tools.client.swetools.editors.sensorml.controller.Observable;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ViewAsRelaxNGButtonClickListener;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ViewAsXMLButtonClickListener;
import com.sensia.tools.client.swetools.editors.sensorml.panels.source.ISourcePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.source.LocalFileSourcePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.source.UrlSourcePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

/**
 * This class is in charge of creating the main Panel. The top elements will 
 * manage the file loading while the center panel display the content of the 
 * transformed XML file into a HTML pretty view.
 * @author Mathieu Dhainaut
 *
 */
public class ViewerPanel extends FlowPanel implements IParsingObserver, IObserver, IRefreshHandler {
	private static final long serialVersionUID = -7684111574093800909L;

	//the panel in charge of displaying the HTML content
	private FlowPanel viewerPanel;
	
	//the checkbox to switch between view and edit mode
	private CheckBox editCheckbox;
	private IPanel root;
	
	//the processor in charge of parsing and create the RNG profile
	private RNGProcessorSML smlEditorProcessor;
	
	private static ViewerPanel instance;
	
	//the default RNG profiles ready to be displayed
	private static Map<String,String> profiles = new HashMap<String,String>();
	
	private ListBox profileListBox;
	
	static {
		profiles.put("Simple Deployment", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/profiles/Simple_Deployment.rng");
		profiles.put("Physical Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/basic_sml/Basic_PhysicalProcess.rng");
		profiles.put("Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/basic_sml/Basic_Process.rng");
		profiles.put("SensorML", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/basic_sml/Basic_SensorML.rng");
		profiles.put("Simple Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/basic_sml/Basic_SimpleProcess.rng");
	}
		
	public enum MODE {
		EDIT,
		VIEW
	}
	
	protected ViewerPanel(final RNGProcessorSML sgmlEditorProcessor){
		sgmlEditorProcessor.addObserver(this);
		this.smlEditorProcessor = sgmlEditorProcessor;
		this.smlEditorProcessor.setRefreshHandler(this);
		final Panel viewXmlPanel = getXMLViewPanel();

		//add View as XML button
		Button viewAsXML = new Button("View as XML");
		viewAsXML.addClickHandler(new ViewAsXMLButtonClickListener(sgmlEditorProcessor));
		
		Button viewAsRNG = new Button("View as RelaxNG");
		viewAsRNG.addClickHandler(new ViewAsRelaxNGButtonClickListener(sgmlEditorProcessor));
		
		Panel viewButtonPanel = new SMLHorizontalPanel(true);
		viewButtonPanel.add(viewAsXML);
		viewButtonPanel.add(viewAsRNG);
		
		Panel profilePanel = getProfilePanel();
		
		//Get the url parameter to load the document where this one is under the form : ?url=DocumentPath
		String passedFile = com.google.gwt.user.client.Window.Location.getParameter("url");
		Panel headerPanel = new SMLHorizontalPanel(true);
		headerPanel.add(viewXmlPanel);
		headerPanel.add(viewButtonPanel);
		headerPanel.add(profilePanel);
		
		if(passedFile != null) {
			//load the file given the url passed as parameter
			//do not display the edit/view options
			smlEditorProcessor.setMode(MODE.VIEW);
			smlEditorProcessor.parse(passedFile);
			
			editCheckbox.setVisible(true);
			
		}
		
		viewerPanel = new FlowPanel();
		
		add(headerPanel);
		add(viewerPanel);
		
		// add styles
		
		viewerPanel.addStyleName("main-content");
		headerPanel.addStyleName("viewer-header");
	}

	// Get the top elements panel for the XML part
	private Panel getXMLViewPanel() {
		final SMLHorizontalPanel panel = new SMLHorizontalPanel(true);
		
		HTML title = new HTML("<b>SensorML XML/RNG:</b>");
		final Button load = new Button("Load");
		
		//init radio buttons choices
		final RadioButton fromLocalFileSystem = new RadioButton("myRadioGroup", "from local");
		final RadioButton fromUrl = new RadioButton("myRadioGroup", "from url");
		
		Panel hPanel = new SMLHorizontalPanel(true);
		hPanel.add(fromLocalFileSystem);
		hPanel.add(fromUrl);
		
		editCheckbox = new CheckBox("Edit");
		
		//container for either local file system input panel or url valueBox
		final SimplePanel fromPanel = new SimplePanel();
		
		//init file upload panel
		final ISourcePanel fileUploadPanel = new LocalFileSourcePanel(smlEditorProcessor, editCheckbox);
		
		//init url load
		final ISourcePanel urlDownloadPanel = new UrlSourcePanel(smlEditorProcessor, editCheckbox);
		
		//add to xml panel
		panel.add(title);
		panel.add(hPanel);
		panel.add(fromPanel);
		panel.add(load);
		panel.add(editCheckbox);
		
		//set from local file system panel as default choice
		fromLocalFileSystem.setChecked(true);
		fromPanel.add(fileUploadPanel.getPanel());
				
		//add listener to handle event
		load.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(fromLocalFileSystem.getValue()) {
					fileUploadPanel.parseContent();
				} else if(fromUrl.getValue()){
					urlDownloadPanel.parseContent();
				}
			}
		});
		
		editCheckbox.setVisible(true);
		
		//after clicking on the checkbox, the mode is sent to the tree hierarchy starting from the Root element
		editCheckbox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(root != null){
					MODE mode = (editCheckbox.isChecked()) ? MODE.EDIT : MODE.VIEW;
					smlEditorProcessor.setMode(mode);
					redraw();
				}
			}
		});
		
		//add listener to handle from local file system handler
		fromLocalFileSystem.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if(event.getValue()) {
					fromPanel.clear();
					fromPanel.add(fileUploadPanel.getPanel());
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
	public void parseDone(final IPanel topElement) {
		//One the parsing done, the viewer is reset and displays the new content
		viewerPanel.clear();
		viewerPanel.add(topElement.getPanel());
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
		MODE mode = (editCheckbox.isChecked()) ? MODE.EDIT : MODE.VIEW;
		redraw((editCheckbox.getValue())? MODE.EDIT:MODE.VIEW);
	}
	
	public void redraw(MODE mode) {
		viewerPanel.clear();
		IPanel newNode = smlEditorProcessor.parseRNG(smlEditorProcessor.getLoadedGrammar());
		viewerPanel.add(newNode.getPanel());
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
	
	protected Panel getProfilePanel() {
		Panel panel = new SMLHorizontalPanel(true);
		
		profileListBox = new ListBox(false);
		
		profileListBox.addItem("");
		for(final String profileName : profiles.keySet()) {
			profileListBox.addItem(profileName);
		}
		
		HTML titleProfile = new HTML("<b>Profiles:</b>");
		
		Button b3 = new Button("Load relaxNG", new ClickHandler() {
            public void onClick(ClickEvent event) {
            	String key = profileListBox.getValue(profileListBox.getSelectedIndex());
        		
        		if(key != null && !key.isEmpty()){
        			RNGParser parser = new RNGParser();
        			parser.clearCache();
        	        parser.parse(profiles.get(key), new RNGParserCallback() {
        	            @Override
        	            public void onParseDone(RNGGrammar grammar)
        	            {
    	                	setMode(MODE.EDIT);
    	                	parse(grammar);
        	            }
        	        });
        		}
            }
        });
		
		panel.add(titleProfile);
		panel.add(profileListBox);
		panel.add(b3);

		return panel;
	}
}
