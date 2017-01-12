package com.sensia.tools.client.swetools.editors.sensorml.panels.chart;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.Canvas;

public class SmartGWTGenericCurveChart extends GenericCurveChart{

	@Override
	public Widget createChart(final String title) {
		Widget panel = super.createChart(title);
		Canvas canvas = new Canvas();
		canvas.setStyleName("chart-panel-smartgwt");
		canvas.adjustForContent(true);
		canvas.addChild(panel);
		return canvas;
	}
}
