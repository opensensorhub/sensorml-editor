/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.chart;

import java.util.List;

import org.moxieapps.gwt.highcharts.client.AxisTitle;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Pane;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.plotOptions.LinePlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.Marker;
import org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.SeriesPlotOptions;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * The Class GenericCurveChart.
 */
public class GenericCurveChart{
	
	/** The chart. */
	private Chart chart;
	
	/**
	 * Instantiates a new generic curve chart.
	 */
	public GenericCurveChart() {
		init();
	}
	
	/**
	 * Inits the.
	 */
	private void init() {
		
	}
	
	/**
	 * Populate table.
	 *
	 * @param title the title
	 * @param axis the axis
	 * @param values the values
	 */
	public void populateTable(final String title,final List<String> axes,final Object[][] values) {
		init();
		if(chart == null) {
			createChart(title, axes);
		} else {
			chart.removeAllSeries();
		}
        
        // use polar chart if x axis is angular
        if (axes.get(0).contains(Utils.DEGREE_SYMBOL)) {
            chart.setPolar(true);
            chart.setPane(new Pane()  
                .setStartAngle(0.)  
                .setEndAngle(360.)  
            );
            chart.getXAxis()
                .setMin(0.)
                .setMax(360.)
                .setTickInterval(30.);
        }
		
		//create X
		String [] xValues = new String[values.length];
		for(int i=0;i < values.length;i++) {
			xValues[i] = values[i][0].toString();
		}
		
		//set X title axis
		chart.getXAxis() 
        	//.setCategories(xValues)
		    .setAxisTitle(
			     new AxisTitle()
			       .setText(axes.get(0))
			       .setAlign(AxisTitle.Align.MIDDLE)
			       .setMargin(20)
			 );
		
		// creates series
		for(int i=1;i < values[0].length;i++) {
			Number[][] axeValues = new Number[values.length][2];
			for(int j=0;j < values.length;j++) {
			    axeValues[j][0] = Double.parseDouble(values[j][0].toString());
				axeValues[j][1] = Double.parseDouble(values[j][i].toString());
			}
			//adds series
			//handles Series
			String serieTitle = axes.get(i);
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
		
		//removes Y axis title
		chart.getYAxis().setAxisTitleText("");
		//displays grid
		chart.getYAxis().setGridLineWidth(1);
		chart.getXAxis().setGridLineWidth(1);
	}
	
	/**
	 * Creates the chart.
	 *
	 * @param title the title
	 * @return the panel
	 */
	public Widget createChart(final String title,final List<String> axes) {
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
		
		//set credits
		chart.setCredits(credits);
		
		chart.setLegend(new Legend()  
	        .setAlign(Legend.Align.LEFT)  
	        .setVerticalAlign(Legend.VerticalAlign.TOP)  
	        .setY(20)  
	        .setFloating(false)  
	        .setBorderWidth(0));
     
		chart.setWidth100();
		chart.setHeight100();
		return chart;
	    
	 /*// test sample polar chart
        chart = new Chart()  
                .setPolar(true)  
                .setChartTitleText("GWT Highcharts Polar Chart")  
                .setPane(new Pane()  
                    .setStartAngle(0)  
                    .setEndAngle(360)  
                )  
                .setSeriesPlotOptions(new SeriesPlotOptions()  
                    .setPointStart(0)  
                    .setPointInterval(45)  
                )  
                .setColumnPlotOptions(new ColumnPlotOptions()  
                    .setPointPadding(0)  
                    .setGroupPadding(0)  
                );  
      
            chart.getXAxis()  
                .setTickInterval(45)  
                .setMin(0)  
                .setMax(360)  
                .setLabels(new XAxisLabels()  
                    .setFormatter(new AxisLabelsFormatter() {  
                        public String format(AxisLabelsData axisLabelsData) {  
                            return axisLabelsData.getValueAsLong() + "°";  
                        }  
                    })  
                );  
      
            chart.getYAxis()  
                .setMin(0);  
      
            chart.addSeries(chart.createSeries()  
                .setType(Series.Type.COLUMN)  
                .setName("Column")  
                .setPoints(new Number[]{8, 7, 6, 5, 4, 3, 2, 1})  
                .setOption("pointPointPlacement", "between")  
            );  
            chart.addSeries(chart.createSeries()  
                .setType(Series.Type.LINE)  
                .setName("Line")  
                .setPoints(new Number[]{1, 2, 3, 4, 5, 6, 7, 8})  
            );  
            chart.addSeries(chart.createSeries()  
                .setType(Series.Type.AREA)  
                .setName("Area")  
                .setPoints(new Number[]{1, 8, 2, 7, 3, 6, 4, 5})  
            );
            return chart;*/
	}
	
	public void redraw() {
		chart.setSizeToMatchContainer();
	}
}
