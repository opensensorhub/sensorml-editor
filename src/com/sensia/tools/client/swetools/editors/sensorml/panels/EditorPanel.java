package com.sensia.tools.client.swetools.editors.sensorml.panels;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
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
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class EditorPanel  extends Composite  implements IParsingObserver, IObserver{

	//the default RNG profiles ready to be displayed
	private static Map<String,String> profiles = new HashMap<String,String>();
	
	private ListBox profileListBox;
	
	static {
		//profiles.put("Gamma2070","sensormleditor/rng1.0/profiles/CSM/gamma.rng");
		profiles.put("Anemometer","sensormleditor/rng1.0/profiles/CSM/anemometer.rng");
		profiles.put("Thermometer","sensormleditor/rng1.0/profiles/CSM/thermometer-minimal-view.rng");
		profiles.put("PhysicalProcess","sensormleditor/sensorml-relaxng/sml/PhysicalProcess.rng");
		profiles.put("PhysicalProcess-minimal","sensormleditor/sensorml-relaxng/sml/PhysicalProcess-minimal.rng");
		profiles.put("RNG 1.0","sensormleditor/rng1.0/profiles/CSM/frame-sensor-model.rng");
	}
	
	//the processor in charge of parsing and create the RNG profile
	//the panel in charge of displaying the HTML content
	private VerticalPanel mainPanel;
	private ResizeLayoutPanel rngPanel;
	private PopupPanel popup = new PopupPanel();
	private RNGGrammar loadedGrammar;
	private TextArea area;
    private IController controller;
    
	public EditorPanel(final RNGProcessorSML sgmlEditorProcessor,IController controller){
		this.controller = controller;
		final VerticalPanel verticalPanel = new VerticalPanel();
		
		final Panel viewXmlPanel = getProfilePanel();
		
		mainPanel = new VerticalPanel();
		verticalPanel.add(viewXmlPanel);
		verticalPanel.add(mainPanel);
		
		VerticalPanel panel = new VerticalPanel();
        
        // resize panel to hold tabs
        // tabs don work correctly if we don have it
        rngPanel = new ResizeLayoutPanel();
        rngPanel.setPixelSize(1024, 768);        
        panel.add(rngPanel);
        
        verticalPanel.add(panel);
        
		initWidget(verticalPanel);
	}
	
	private Panel getProfilePanel() {
		final HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(20);
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
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
        
		panel.add(titleProfile);
		panel.add(profileListBox);
		panel.add(load);
		panel.add(b2);
		
		load.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				EditorPanel.this.loadProfile();
			}
		});
		
		return panel;
	}
	
	public void loadProfile(){
		String key = profileListBox.getValue(profileListBox.getSelectedIndex());
		
		if(key != null && !key.isEmpty()){
			RNGParser parser = new RNGParser();
			parser.clearCache();
	        parser.parse(profiles.get(key), new RNGParserCallback() {
	            @Override
	            public void onParseDone(RNGGrammar grammar)
	            {
	                System.out.println("Rendering schema");
	                RNGRendererSML renderer = new RNGRendererSML();
	                renderer.visit(grammar);
	                rngPanel.clear();
	                rngPanel.add(renderer.getWidgets().get(0));
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
	public void parseDone(ISensorWidget topElement) {
		// TODO Auto-generated method stub
		
	}
}
