package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.dataarray;

import java.util.List;

import org.moxieapps.gwt.highcharts.client.AxisTitle;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.plotOptions.LinePlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.Marker;
import org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.SeriesPlotOptions;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class GenericCurveChart{
	
	private Chart chart;
	
	public GenericCurveChart() {
		init();
	}
	
	private void init() {
		
	}
	
	public void poupulateTable(final String title,final List<String> axis,final Object[][] values) {
		init();
		if(chart == null) {
			createChart(title);
		} else {
			chart.removeAllSeries();
		}
		
		//create X
		String [] xValues = new String[values.length];
		for(int i=0;i < values.length;i++) {
			xValues[i] = values[i][0].toString();
		}
		
		chart.getXAxis() 
        	.setCategories(xValues)
        	.setAxisTitle(
			     new AxisTitle()
			       .setText(axis.get(0))
			       .setAlign(AxisTitle.Align.MIDDLE)
			       .setMargin(20)
			 );
		
		//create series
		for(int i=1;i < values[0].length;i++) {
			Number [] axeValues = new Number[values.length];
			for(int j=0;j < values.length;j++) {
				axeValues[j] = Double.parseDouble(values[j][i].toString());
			}
			//add series
			//handle Series
			String serieTitle = axis.get(i);
			chart.addSeries(chart.createSeries()  
		        .setName(serieTitle)
		        .setPlotOptions(new LinePlotOptions()  
                .setLineWidth(4)  
                .setMarker(new Marker()  
                    .setRadius(4)  
                )  
            ) 
		        .setPoints(axeValues)  
		    );  
		}
		
		chart.getYAxis().setAxisTitleText(""); 
		chart.getYAxis().setGridLineWidth(1);
		chart.getXAxis().setGridLineWidth(1);
	}
	
	public Panel createChart(final String title) {
		chart = new Chart()  
        .setType(Series.Type.LINE)  
        .setChartTitle(new ChartTitle()  
            .setText(title)  
        )  
        .setToolTip(new ToolTip()  
                .setShared(true)  
                .setCrosshairs(true)  
        )  
		.setSeriesPlotOptions(new SeriesPlotOptions()  
        .setCursor(PlotOptions.Cursor.POINTER)  
        .setMarker(new Marker()  
            .setLineWidth(1)  
        )); 
		
		Credits credits = new Credits();
		credits.setText("");
		
		chart.setCredits(credits);
		
		chart.setLegend(new Legend()  
	        .setAlign(Legend.Align.LEFT)  
	        .setVerticalAlign(Legend.VerticalAlign.TOP)  
	        .setY(20)  
	        .setFloating(false)  
	        .setBorderWidth(0));
     
	    ScrollPanel sPanel = new ScrollPanel();
	    chart.setStyleName("chart-panel");
		sPanel.add(chart);
		
		return sPanel;
	}
}
