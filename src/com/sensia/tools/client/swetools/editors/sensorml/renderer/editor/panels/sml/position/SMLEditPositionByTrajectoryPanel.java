package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.position;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.map.GenericLineMap;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.dataarray.SWEEditDataArrayPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.ModelHelper;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SaveCloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class SMLEditPositionByTrajectoryPanel extends EditSubSectionElementPanel{

	protected String epsg;
	protected Object[][] values;
	protected List<Integer> codesIdx;
	
	public SMLEditPositionByTrajectoryPanel(RNGElement tag, IRefreshHandler refreshHandler) {
		super(tag, refreshHandler);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("DataArray") && element instanceof SWEEditDataArrayPanel) {
			SWEEditDataArrayPanel panel = (SWEEditDataArrayPanel) element;
			panel.setGraphVisible(false);
			
			// build map icon
			
			// get values
			values = panel.getValues();
			
			// looking for EPSG
			RNGAttribute referenceFrame = ModelHelper.findAttributeValue("referenceFrame",element.getTag());
			if(referenceFrame != null) {
				String split[] = referenceFrame.getChildValueText().split("/");
				epsg = split[split.length-1];
			}
			
			codesIdx = panel.getCodesIdx();
			
			Image mapIcon = new Image(GWT.getModuleBaseURL()+"images/maps-icon.png");
			
			mapIcon.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					GenericLineMap lineMap = getLineMapPanel();
					createDialog(lineMap);
				}
			});
			
			SMLHorizontalPanel hPanel = new SMLHorizontalPanel();
			hPanel.add(mapIcon);
			panel.appendToLine(hPanel);
			
		}
		super.addInnerElement(element);
	}
	
	protected GenericLineMap getLineMapPanel() {
		
		// parse Object into Double
		double [] [] latLonValues = new double[values.length][values[0].length];
		for(int i=0;i < values.length;i++){
			int j = 0;
			for(Integer  codeIdx: codesIdx) {
				latLonValues[i] [j++] = Double.parseDouble(values[i][codeIdx].toString().trim());
			}
		}
		return new GenericLineMap(latLonValues, epsg, true);
	}
	
	protected void createDialog(final GenericLineMap lineMap) {
		SaveCloseWindow dialog = Utils.displaySaveDialogBox(lineMap.getPanel(), "Line position");
		dialog.addSaveHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// save the position
				
				//TODO:
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});
	}
}
