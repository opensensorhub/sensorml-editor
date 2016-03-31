package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.map;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.OpenLayers;
import org.gwtopenmaps.openlayers.client.Pixel;
import org.gwtopenmaps.openlayers.client.Projection;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.DragFeature;
import org.gwtopenmaps.openlayers.client.control.DragFeature.DragFeatureListener;
import org.gwtopenmaps.openlayers.client.control.DragFeatureOptions;
import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.GoogleV3;
import org.gwtopenmaps.openlayers.client.layer.GoogleV3MapType;
import org.gwtopenmaps.openlayers.client.layer.GoogleV3Options;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.TransitionEffect;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.layer.WMS;
import org.gwtopenmaps.openlayers.client.layer.WMSOptions;
import org.gwtopenmaps.openlayers.client.layer.WMSParams;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class SensorMapPointWidget {

	private double lat;
	private double lon;
	private String epsgCode;
	private boolean drag;
	
	private double trueHeading;
	
	private Panel container;
	private MapWidget mapWidget;
	
	public SensorMapPointWidget(double lat,double lon,String epsgCode, boolean drag) {
		this.lat = lat;
		this.lon = lon;
		this.epsgCode = epsgCode;
		this.drag = drag;
		
		init();
	}
	
	public SensorMapPointWidget(double lat,double lon,double trueHeading,String epsgCode, boolean drag) {
		this.lat = lat;
		this.lon = lon;
		this.epsgCode = epsgCode;
		this.drag = drag;
		this.trueHeading = trueHeading;
		
		init();
	}
	
	private void init() {
		container = new SimplePanel();
		
		MapOptions defaultMapOptions = new MapOptions();
		defaultMapOptions.setNumZoomLevels(22);
        mapWidget = new MapWidget("500px", "500px", defaultMapOptions);

        //Create some Google Layers
        GoogleV3Options gHybridOptions = new GoogleV3Options();
        gHybridOptions.setIsBaseLayer(true);
        gHybridOptions.setType(GoogleV3MapType.G_HYBRID_MAP);
        GoogleV3 gHybrid = new GoogleV3("Google Hybrid", gHybridOptions);
 
        GoogleV3Options gNormalOptions = new GoogleV3Options();
        gNormalOptions.setIsBaseLayer(true);
        gNormalOptions.setType(GoogleV3MapType.G_NORMAL_MAP);
        GoogleV3 gNormal = new GoogleV3("Google Normal", gNormalOptions);
 
        GoogleV3Options gSatelliteOptions = new GoogleV3Options();
        gSatelliteOptions.setIsBaseLayer(true);
        gSatelliteOptions.setType(GoogleV3MapType.G_SATELLITE_MAP);
        GoogleV3 gSatellite = new GoogleV3("Google Satellite", gSatelliteOptions);
 
        GoogleV3Options gTerrainOptions = new GoogleV3Options();
        gTerrainOptions.setIsBaseLayer(true);
        gTerrainOptions.setType(GoogleV3MapType.G_TERRAIN_MAP);
        GoogleV3 gTerrain = new GoogleV3("Google Terrain", gTerrainOptions);
 
        //And add them to the map
        mapWidget.getMap().addLayer(gSatellite);
        mapWidget.getMap().addLayer(gHybrid);
        mapWidget.getMap().addLayer(gNormal);
        mapWidget.getMap().addLayer(gTerrain);
        
        LonLat lonLat = new LonLat(lon, lat);
        lonLat.transform("EPSG:"+epsgCode, mapWidget.getMap().getProjection()); //transform lonlat (provided in EPSG:4326) to OSM coordinate system (the map projection)
        mapWidget.getMap().setCenter(lonLat, 10);

        final Vector vectorLayer = new Vector("Vectorlayer");

        Point point = new Point(lonLat.lon(), lonLat.lat());

        Style pointStyle = new Style();
        pointStyle.setExternalGraphic(GWT.getModuleBaseURL()+"images/arrow-direction.png");
        pointStyle.setGraphicSize(28, 33);
        pointStyle.setGraphicOffset(-16, -37); //anchor on bottom center
        pointStyle.setFillOpacity(1.0);
        pointStyle.setRotation(""+trueHeading);
        
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
