package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.map;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Projection;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.Vector;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class SensorMapWidget {

	public Panel getMapPanel(double lat,double lon,String epsgCode) {
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
}
