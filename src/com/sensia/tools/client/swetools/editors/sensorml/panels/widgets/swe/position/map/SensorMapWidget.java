package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.map;

import java.util.ArrayList;
import java.util.List;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Projection;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.format.GeoJSON;
import org.gwtopenmaps.openlayers.client.geometry.LineString;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.JsonLayerCreator;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class SensorMapWidget {

	public Panel getMapPanelWithPoint(double lat,double lon,String epsgCode) {
		SimplePanel mapContainer = new SimplePanel();
		
		MapOptions defaultMapOptions = new MapOptions();
        MapWidget mapWidget = new MapWidget("500px", "500px", defaultMapOptions);

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

        mapContainer.add(mapWidget);
        
		return mapContainer;
	}
	
	public Panel getMapPanelWithTrajectory(double [][] latLonCoordinates ,String epsgCode) {
		SimplePanel mapContainer = new SimplePanel();
		
		MapOptions defaultMapOptions = new MapOptions();
        MapWidget mapWidget = new MapWidget("500px", "500px", defaultMapOptions);

        OSM osmMapnik = OSM.Mapnik("Mapnik");
        OSM osmCycle = OSM.CycleMap("CycleMap");

        osmMapnik.setIsBaseLayer(true);
        osmCycle.setIsBaseLayer(true);

        mapWidget.getMap().addLayer(osmMapnik);
        mapWidget.getMap().addLayer(osmCycle);
        
        Point [] points = new Point[latLonCoordinates.length];
        int j=0;
        for(int i=0;i < latLonCoordinates.length;i++) {
        	LonLat lonLat = new LonLat(latLonCoordinates[i][1], latLonCoordinates[i][0]);
            lonLat.transform("EPSG:"+epsgCode, mapWidget.getMap().getProjection()); //transform lonlat (provided in EPSG:4326) to OSM coordinate system (the map projection)

        	points[j++] = new Point(lonLat.lon(), lonLat.lat());
        }
        
        LineString lineString = new LineString(points);
        Style style = new Style();
        style.setStrokeColor("#0033ff");
        style.setStrokeWidth(3);
        
        final Vector vectorLayer = new Vector("Vectorlayer");
        
        VectorFeature pointFeature = new VectorFeature(lineString, style);

        vectorLayer.addFeature(pointFeature);
        mapWidget.getMap().addLayer(vectorLayer);
        
        mapWidget.getMap().addControl(new LayerSwitcher());

        //zoom to maxExtent
        mapWidget.getMap().zoomToExtent(vectorLayer.getDataExtent(),false);
        
        mapContainer.add(mapWidget);
        
		return mapContainer;
	}
	
	public Panel getMapPanel(String geoJson) {
		SimplePanel mapContainer = new SimplePanel();
		
		MapOptions defaultMapOptions = new MapOptions();
        MapWidget mapWidget = new MapWidget("500px", "500px", defaultMapOptions);

        OSM osmMapnik = OSM.Mapnik("Mapnik");
        OSM osmCycle = OSM.CycleMap("CycleMap");

        osmMapnik.setIsBaseLayer(true);
        osmCycle.setIsBaseLayer(true);

        mapWidget.getMap().addLayer(osmMapnik);
        mapWidget.getMap().addLayer(osmCycle);
        
        final Vector vectorLayer = new Vector("Vectorlayer");
        
        GeoJSON geoJsonReader = new GeoJSON();
        GWT.log(geoJson);
        VectorFeature[] features = geoJsonReader.read(geoJson);
        for(VectorFeature feature : features) {
        	vectorLayer.addFeature(feature);
        }
        
        mapWidget.getMap().addLayer(vectorLayer);

        mapWidget.getMap().addControl(new LayerSwitcher());

        //Center and zoom to a location
        mapWidget.getMap().setMaxExtent(vectorLayer.getDataExtent());
        
        mapContainer.add(mapWidget);
        
		return mapContainer;
	}
}
