package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.map;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Pixel;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.DragFeature;
import org.gwtopenmaps.openlayers.client.control.DragFeatureOptions;
import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;
import org.gwtopenmaps.openlayers.client.control.ModifyFeature;
import org.gwtopenmaps.openlayers.client.control.DragFeature.DragFeatureListener;
import org.gwtopenmaps.openlayers.client.event.VectorFeatureModifiedListener;
import org.gwtopenmaps.openlayers.client.event.VectorFeatureModifiedListener.FeatureModifiedEvent;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Geometry;
import org.gwtopenmaps.openlayers.client.geometry.LineString;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class SensorMapLineStringWidget {

	private double [][] latLonCoordinates;
	private String epsgCode;
	private boolean drag;
	
	private Panel container;
	private MapWidget mapWidget;
	
	private VectorFeature lineFeature;
	
	public SensorMapLineStringWidget(double [][] latLonCoordinates,String epsgCode, boolean drag) {
		this.latLonCoordinates = latLonCoordinates;
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
        
        lineFeature = new VectorFeature(lineString, style);

        vectorLayer.addFeature(lineFeature);
        mapWidget.getMap().addLayer(vectorLayer);
        
        mapWidget.getMap().addControl(new LayerSwitcher());

        //zoom to maxExtent
        mapWidget.getMap().zoomToExtent(vectorLayer.getDataExtent(),false);
        
        container.add(mapWidget);
        
        /*if(drag) {
        	ModifyFeature modifyFeature = new ModifyFeature(vectorLayer);
        	mapWidget.getMap().addControl(modifyFeature);
        	modifyFeature.activate();
        	
        	vectorLayer
    		.addVectorFeatureModifiedListener(new VectorFeatureModifiedListener() {

    		    public void onFeatureModified(FeatureModifiedEvent eventObject) {
    		    	//TODO callback
    		    }
    		});
        }*/ 
	}
	
	public Panel getPanel() {
		return container;
	}
	
	public double[][] getLatLonCoordinates() {
		//LonLat lonLat = new LonLat(lon, lat);
		//lonLat.transform( mapWidget.getMap().getProjection(),"EPSG:"+epsgCode); 
		//return new double[]{lonLat.lat(),lonLat.lon()};
		Point[] points = lineFeature.getGeometry().getVertices(true);
		double[][] coordinates = new double[points.length][2];
		
		int i=0;
		for(Point p : points) {
			LonLat lonLat = new LonLat(p.getX(), p.getY());
			lonLat.transform( mapWidget.getMap().getProjection(),"EPSG:"+epsgCode); 
			coordinates[i][0] = lonLat.lat();
			coordinates[i][1] = lonLat.lon();
		}
		
		return coordinates;
	}
}
