package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.map;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Pixel;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.DragFeature;
import org.gwtopenmaps.openlayers.client.control.DragFeatureOptions;
import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;
import org.gwtopenmaps.openlayers.client.control.DragFeature.DragFeatureListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class SensorMapPointWidget {

	private double lat;
	private double lon;
	private String epsgCode;
	private boolean drag;
	
	private Panel container;
	private MapWidget mapWidget;
	
	public SensorMapPointWidget(double lat,double lon,String epsgCode, boolean drag) {
		this.lat = lat;
		this.lon = lon;
		this.epsgCode = epsgCode;
		this.drag = drag;
		
		init();
	}
	
	private void init() {
		container = new SimplePanel();
		
		MapOptions defaultMapOptions = new MapOptions();
        mapWidget = new MapWidget("500px", "500px", defaultMapOptions);

        OSM osmMapnik = OSM.Mapnik("Mapnik");
        OSM osmCycle = OSM.CycleMap("CycleMap");

        osmMapnik.setIsBaseLayer(true);
        osmCycle.setIsBaseLayer(true);

        mapWidget.getMap().addLayer(osmMapnik);
        mapWidget.getMap().addLayer(osmCycle);

        LonLat lonLat = new LonLat(lon, lat);
        lonLat.transform("EPSG:"+epsgCode, mapWidget.getMap().getProjection()); //transform lonlat (provided in EPSG:4326) to OSM coordinate system (the map projection)
        mapWidget.getMap().setCenter(lonLat, 12);

        final Vector vectorLayer = new Vector("Vectorlayer");

        Point point = new Point(lonLat.lon(), lonLat.lat());

        Style pointStyle = new Style();
        pointStyle.setExternalGraphic("https://maps.google.com/mapfiles/ms/icons/blue-dot.png");
        pointStyle.setGraphicSize(32, 37);
        pointStyle.setGraphicOffset(-16, -37); //anchor on bottom center
        pointStyle.setFillOpacity(1.0);

        VectorFeature pointFeature = new VectorFeature(point, pointStyle);

        vectorLayer.addFeature(pointFeature);
        mapWidget.getMap().addLayer(vectorLayer);

        mapWidget.getMap().addControl(new LayerSwitcher());

        container.add(mapWidget);
        
        if(drag) {
        	 DragFeatureOptions dragFeatureOptions = new DragFeatureOptions();
             dragFeatureOptions.onComplete(new DragFeatureListener() {
            	 
                 public void onDragEvent(VectorFeature vectorFeature,
                         Pixel pixel) {
                 	//do callback
                	 lat = vectorFeature.getCenterLonLat().lat();
                	 lon = vectorFeature.getCenterLonLat().lon();
                 }
              });
             
             DragFeature dragFeature = new DragFeature(vectorLayer, dragFeatureOptions);
        	mapWidget.getMap().addControl(dragFeature);
        	dragFeature.activate();
        } 
	}
	
	public Panel getPanel() {
		return container;
	}
	
	public double[] getLatLon() {
		LonLat lonLat = new LonLat(lon, lat);
		lonLat.transform( mapWidget.getMap().getProjection(),"EPSG:"+epsgCode); 
		return new double[]{lonLat.lat(),lonLat.lon()};
	}
}
