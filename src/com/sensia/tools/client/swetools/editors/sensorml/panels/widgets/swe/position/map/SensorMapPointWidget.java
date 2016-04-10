/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.map;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Pixel;
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
import org.gwtopenmaps.openlayers.client.layer.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * The Class SensorMapPointWidget is responsible of creating a map displaying a single point.
 */
public class SensorMapPointWidget {

	/** The lat. */
	private double lat;
	
	/** The lon. */
	private double lon;
	
	/** The epsg code. */
	private String epsgCode;
	
	/** The drag. */
	private boolean drag;
	
	/** The true heading. */
	private double trueHeading;
	
	/** The container. */
	private Panel container;
	
	/** The map widget. */
	private MapWidget mapWidget;
	
	/**
	 * Instantiates a new sensor map point widget.
	 *
	 * @param lat the lat
	 * @param lon the lon
	 * @param epsgCode the epsg code
	 * @param drag the drag
	 */
	public SensorMapPointWidget(double lat,double lon,String epsgCode, boolean drag) {
		this.lat = lat;
		this.lon = lon;
		this.epsgCode = epsgCode;
		this.drag = drag;
		
		init();
	}
	
	/**
	 * Instantiates a new sensor map point widget.
	 *
	 * @param lat the lat
	 * @param lon the lon
	 * @param trueHeading the true heading
	 * @param epsgCode the epsg code
	 * @param drag the drag
	 */
	public SensorMapPointWidget(double lat,double lon,double trueHeading,String epsgCode, boolean drag) {
		this.lat = lat;
		this.lon = lon;
		this.epsgCode = epsgCode;
		this.drag = drag;
		this.trueHeading = trueHeading;
		
		init();
	}
	
	/**
	 * Inits the.
	 */
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
        
        //edit mode
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
	
	/**
	 * Gets the panel.
	 *
	 * @return the panel
	 */
	public Panel getPanel() {
		return container;
	}
	
	/**
	 * Gets the lat lon.
	 *
	 * @return the lat lon
	 */
	public double[] getLatLon() {
		LonLat lonLat = new LonLat(lon, lat);
		lonLat.transform( mapWidget.getMap().getProjection(),"EPSG:"+epsgCode); 
		return new double[]{lonLat.lat(),lonLat.lon()};
	}
}
