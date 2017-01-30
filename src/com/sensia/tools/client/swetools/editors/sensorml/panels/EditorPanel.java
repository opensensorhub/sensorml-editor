package com.sensia.tools.client.swetools.editors.sensorml.panels;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.xml.client.Document;
import com.sensia.gwt.relaxNG.RNGInstanceWriter;
import com.sensia.gwt.relaxNG.RNGParser;
import com.sensia.gwt.relaxNG.RNGParserCallback;
import com.sensia.gwt.relaxNG.XMLSerializer;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.tools.client.swetools.editors.sensorml.IParsingObserver;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;
import com.sensia.tools.client.swetools.editors.sensorml.controller.IController;
import com.sensia.tools.client.swetools.editors.sensorml.controller.IObserver;
import com.sensia.tools.client.swetools.editors.sensorml.controller.Observable;
import com.sensia.tools.client.swetools.editors.sensorml.old.RNGRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.panels.ViewerPanel.MODE;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

public class EditorPanel  extends Composite  implements IParsingObserver, IObserver{

	//the default RNG profiles ready to be displayed
	private static Map<String,String> profiles = new HashMap<String,String>();
	
	private ListBox profileListBox;
	
	static {
		profiles.put("Simple Deployment", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/profiles/Simple_Deployment.rng");
		profiles.put("Frame Sensor Model (CSM)", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/profiles/RemoteSensing/frame-sensor-model.rng");
        profiles.put("Basic Physical Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/basic_sml/Basic_PhysicalProcess.rng");
		profiles.put("Basic Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/basic_sml/Basic_Process.rng");
		profiles.put("Basic Simple Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/basic_sml/Basic_SimpleProcess.rng");
        profiles.put("Simple Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/sml/SimpleProcess.rng");
        profiles.put("Physical Process", "https://raw.githubusercontent.com/opensensorhub/sensorml-relaxng/master/sml/PhysicalProcess.rng");
	}
	
	//the processor in charge of parsing and create the RNG profile
	//the panel in charge of displaying the HTML content
	private Panel rngPanel;
	private PopupPanel popup = new PopupPanel();
	private RNGGrammar loadedGrammar;
    private IController controller;
    
	public EditorPanel(final RNGProcessorSML sgmlEditorProcessor,IController controller){
		this.controller = controller;
        
		final SMLVerticalPanel verticalPanel = new SMLVerticalPanel();
		rngPanel = new ResizeLayoutPanel();
		rngPanel.setHeight("99999");
		final Panel viewXmlPanel = getProfilePanel();
		
		verticalPanel.add(viewXmlPanel);
		verticalPanel.add(rngPanel);
		
		rngPanel.addStyleName("editor-panel");
		viewXmlPanel.addStyleName("config-editor-panel");
		verticalPanel.addStyleName("editor-v-main-panel");
		
		initWidget(verticalPanel);
	}
	
	private Panel getProfilePanel() {
		final SMLHorizontalPanel panel = new SMLHorizontalPanel();
		
		profileListBox = new ListBox(false);
		
		profileListBox.addItem("");
		for(final String profileName : profiles.keySet()) {
			profileListBox.addItem(profileName);
		}
		
		HTML titleProfile = new HTML("<b>Profiles:</b>");
		
		//button to load the selected profile
		Button load = new Button("Load");
		
		Button b2 = new Button("View", new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (loadedGrammar == null)
                    return;
                RNGInstanceWriter writer = new RNGInstanceWriter();
                Document dom = writer.writeInstance(loadedGrammar);
                
                /*TabLayoutPanel tabs = (TabLayoutPanel)rngPanel.getWidget();
                                
                if (area == null)
                {
                    area = new TextArea();
                    area.setWidth("100%");
                    area.setHeight("100%");
                    tabs.add(area, "SensorML Output");
                }
                
                //area.setText(dom.toString());*/
                String xmlDoc = XMLSerializer.serialize(dom);
                //area.setText(xmlDoc);
                //tabs.selectTab(tabs.getWidgetCount()-1);
                controller.parse(xmlDoc);
            }
        });
        
		Button b3 = new Button("Load into View", new ClickHandler() {
            public void onClick(ClickEvent event) {
            	EditorPanel.this.loadProfile(true);
            }
        });
		
		panel.add(titleProfile);
		panel.add(profileListBox);
		panel.add(load);
		panel.add(b2);
		panel.add(b3);
		
		panel.addStyleName("editor-panel");
		load.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				EditorPanel.this.loadProfile(false);
			}
		});
		
		return panel;
	}
	
	public void loadProfile(final boolean inView){
		String key = profileListBox.getValue(profileListBox.getSelectedIndex());
		
		if(key != null && !key.isEmpty()){
			RNGParser parser = new RNGParser();
			parser.clearCache();
	        parser.parse(profiles.get(key), new RNGParserCallback() {
	            @Override
	            public void onParseDone(RNGGrammar grammar)
	            {
	                if(inView) {
	                	ViewerPanel.getInstance(null).setMode(MODE.EDIT);
	                	controller.parse(grammar);
	                } else {
	                	RNGRendererSML renderer = new RNGRendererSML();
		                renderer.visit(grammar);
		                rngPanel.clear();
		                rngPanel.add(renderer.getWidgets().get(0));
	                }
	                popup.hide();
	                loadedGrammar = grammar;
	            }
	        });
		}
	}

	@Override
	public void update(Observable model, Object hint) {
		//smlEditorProcessor.parse(profiles.get(profileListBox.getValue(profileListBox.getSelectedIndex())));
		
	}

	@Override
	public void parseDone(IPanel topElement) {
		// TODO Auto-generated method stub
		
	}
}
