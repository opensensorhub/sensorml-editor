package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.gml;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.map.GenericPointMap;
import com.sensia.tools.client.swetools.editors.sensorml.utils.CloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class GMLViewPointPanel extends AbstractPanel<RNGElement>{

	private Panel srsPanel;
	private Panel coordinatesPanel;
	private Image mapIcon;
	
	private Double lat;
	private Double lon;
	private String epsg;
	
	private RNGElement coordinateElt;
	
	public GMLViewPointPanel(RNGElement element) {
		super(element);
		container = new HorizontalPanel();
		srsPanel = new HorizontalPanel();
		coordinatesPanel = new HorizontalPanel();
		mapIcon = new Image(GWT.getModuleBaseURL()+"images/maps-icon.png");
		
		container.addStyleName("Point-panel");
		
		container.add(new HTML("Point"));
		container.add(srsPanel);
		container.add(new HTML(":"));
		container.add(coordinatesPanel);
		container.add(mapIcon);
		
		srsPanel.setVisible(false);
		coordinatesPanel.setVisible(false);
		
		//open a new Window pointing to the name href given by the attribute name
		mapIcon.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(coordinateElt != null && epsg != null) {
					String coordinatesStr = coordinateElt.getChildValueText();
					String [] split = coordinatesStr.split(" ");
					lat = Double.parseDouble(split[0]);
					lon = Double.parseDouble(split[1]);
					
					final GenericPointMap pointMap = new GenericPointMap(lat, lon, epsg, false);
					Utils.displayDialogBox(pointMap.getPanel(), "Point position");
				}
			}
		});
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "Point";
	}

	@Override
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("srsName")) {
			srsPanel.add(new HTML("("));
			srsPanel.add(element.getPanel());
			srsPanel.add(new HTML(")"));
			srsPanel.setVisible(true);
			
			String split[] = ((RNGAttribute)element.getTag()).getChildValueText().split("/");
			epsg = split[split.length-1];
		} else if(element.getName().equals("coordinates")) {
			coordinatesPanel.add(element.getPanel());
			coordinatesPanel.setVisible(true);
			coordinateElt = (RNGElement) element.getTag();
		}
	}
}