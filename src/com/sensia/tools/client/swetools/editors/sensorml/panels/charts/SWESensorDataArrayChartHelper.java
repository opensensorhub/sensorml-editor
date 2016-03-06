package com.sensia.tools.client.swetools.editors.sensorml.panels.charts;

import java.util.List;

import com.sensia.tools.client.swetools.editors.sensorml.panels.charts.versusline.SWESensorDataArrayVersusLineHelper;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;

public class SWESensorDataArrayChartHelper {

	public static ISensorChart createChart(AbstractSensorElementWidget root) {
		//find fields
		List<ISensorWidget> fields = AbstractSensorElementWidget.findWidgets(root, "field", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		ISensorChart chart = null;
		if(fields.size() == 2) {
			chart = SWESensorDataArrayVersusLineHelper.create1LineVersusChart(root,fields.get(0), fields.get(1));
		}
		
		return chart;
	}
}
