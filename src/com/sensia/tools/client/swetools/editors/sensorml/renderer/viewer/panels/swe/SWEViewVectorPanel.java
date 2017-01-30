package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.map.GenericPointMap;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditVectorPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class SWEViewVectorPanel extends SWEEditVectorPanel {

	public SWEViewVectorPanel(RNGElement element, IRefreshHandler refreshHandler) {
		super(element, refreshHandler);
	}
	
	@Override
	protected GenericPointMap getPointMapPanel() {
		String split[] = referenceFrame.getChildValueText().split("/");
		String epsg = split[split.length-1];
		
		double lat = Double.parseDouble(latCoordinateElt.getChildValueText());
		double lon = Double.parseDouble(lonCoordinateElt.getChildValueText());
		double heading = 0.0;
		
		if(headingCoordinateElt != null) {
			heading = Double.parseDouble(headingCoordinateElt.getChildValueText());
		}
		
		return new GenericPointMap(lat, lon, heading,epsg, false);
	}
	
	@Override
	protected void createDialog(final GenericPointMap pointMap) {
		Utils.displayDialogBox(pointMap.getPanel(), "Point position");
	}
}
