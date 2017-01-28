package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.map.GenericPointMap;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.ModelHelper;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel.SPACING;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SaveCloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class SWEEditVectorPanel extends EditSubSectionElementPanel{

	protected Panel srsPanel;
	protected Panel coordinatePanel;
	protected Panel iconsPanel;
	
	protected RNGElement latCoordinateElt;
	protected RNGElement lonCoordinateElt;
	protected RNGElement altCoordinateElt;
	protected RNGElement headingCoordinateElt;
	
	protected RNGAttribute referenceFrame;
	
	protected HTML htmlLabel;
	
	public SWEEditVectorPanel(RNGElement element, final IRefreshHandler refreshHandler,boolean defaultName) {
		super(element,refreshHandler,defaultName);
		
		Panel hPanel = new SMLHorizontalPanel(SPACING.RIGHT);
		srsPanel = new SMLHorizontalPanel();
		coordinatePanel = new SMLHorizontalPanel();
		iconsPanel = new SMLHorizontalPanel();
		
		htmlLabel = new HTML("");
		hPanel.add(htmlLabel);
		hPanel.add(new HTML(SMLEditorConstants.HTML_SPACE));
		hPanel.add(new HTML("("));
		hPanel.add(srsPanel);
		hPanel.add(new HTML(")"));
		hPanel.add(new HTML(":"+SMLEditorConstants.HTML_SPACE));
		hPanel.add(coordinatePanel);
		hPanel.add(iconsPanel);
		
		label.clear();
		label.add(hPanel);
		namePanel = hPanel;
		
		srsPanel.setVisible(false);
		coordinatePanel.setVisible(false);
		iconsPanel.setVisible(false);
		
		Image mapIcon = new Image(GWT.getModuleBaseURL()+"images/maps-icon.png");
		
		mapIcon.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GenericPointMap pointMap = getPointMapPanel();
				createDialog(pointMap);
			}
		});
		
		iconsPanel.add(mapIcon);
		
		// default name
		setHtmlLabel("Location");
	}
	public SWEEditVectorPanel(RNGElement element, final IRefreshHandler refreshHandler) {
		this(element,refreshHandler,false);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("referenceFrame")) {
			srsPanel.add(element.getPanel());
			srsPanel.setVisible(true);
			referenceFrame = ((RNGAttribute)element.getTag());
		} else if(element.getName().equals("localFrame")) {
			// skip localFrame
		} else {
			if(element.getName().equals("coordinate")) {
				List<RNGElement> tags = ModelHelper.findTags(SMLEditorConstants.SWE_NS_2, "value", element.getTag());
				if(tags != null && !tags.isEmpty()) {
					coordinatePanel.add(new HTML(tags.get(0).getChildValueText()));
					coordinatePanel.add(new HTML(SMLEditorConstants.HTML_SPACE));
					coordinatePanel.setVisible(true);
					RNGElement coordinateElt = (RNGElement) element.getTag();
					
					if(ModelHelper.isAttributeValue(SMLEditorConstants.LATITUDE_DEFINITION, coordinateElt)) {
						latCoordinateElt = tags.get(0);
					} else if(ModelHelper.isAttributeValue(SMLEditorConstants.LONGIGUTE_DEFINITION, coordinateElt)) {
						lonCoordinateElt = tags.get(0);
					} else if(ModelHelper.isAttributeValue(SMLEditorConstants.ALTITUDE_DEFINITION, coordinateElt)) {
						altCoordinateElt = tags.get(0);
					} else if(ModelHelper.isAttributeValue(SMLEditorConstants.HEADING_DEFINITION, coordinateElt)) {
						headingCoordinateElt= tags.get(0);
					}
					
					if(latCoordinateElt != null && lonCoordinateElt != null && referenceFrame != null) {
						iconsPanel.setVisible(true);
					}
				}
			}
			super.addInnerElement(element);
		}
	}
	
	protected GenericPointMap getPointMapPanel() {
		String split[] = referenceFrame.getChildValueText().split("/");
		String epsg = split[split.length-1];
		
		double lat = Double.parseDouble(latCoordinateElt.getChildValueText());
		double lon = Double.parseDouble(lonCoordinateElt.getChildValueText());
		double heading = 0.0;
		
		if(headingCoordinateElt != null) {
			heading = Double.parseDouble(headingCoordinateElt.getChildValueText());
		}
		
		return new GenericPointMap(lat, lon, heading,epsg, true);
	}
	
	protected void createDialog(final GenericPointMap pointMap) {
		SaveCloseWindow dialog = Utils.displaySaveDialogBox(pointMap.getPanel(), "Point position");
		dialog.addSaveHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// save the position
				double [] latLon = pointMap.getLatLon();
				latCoordinateElt.setChildValueText(""+latLon[0]);
				lonCoordinateElt.setChildValueText(""+latLon[1]);
				
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});
	}
	
	public void setHtmlLabel(String label) {
		this.htmlLabel.setText(label);
	}
	
	public void setHeadingCoordinateElt(RNGElement headingCoordinateElt) {
		this.headingCoordinateElt = headingCoordinateElt;
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
