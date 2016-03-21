package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.map.geojson;

import java.util.UUID;

public abstract class GeoJsonBuilder {

	public static long uniqueFeatureId = 0;
	
	public static final String buildLineGeoJSon(double [][] coordinates,String epsgCode) {
		String jsonGeometryType = "\"type\":\"LineString\"";
		String jsonCoordinates = "\"coordinates\":[";
		
		for(int i=0;i < coordinates.length;i++) {
			jsonCoordinates += "["+coordinates[i][0]+","+coordinates[i][1]+"]";
			
			if(i < coordinates.length -1) {
				jsonCoordinates += ",";
			}
		}
		
		jsonCoordinates += "]";
		return  buildGenericGeoJson(jsonGeometryType, jsonCoordinates, epsgCode);
	}
	
	public static final String buildPointGeoJSon(double lat,double lon,String epsgCode) {
		final String jsonGeometryType = "\"type\":\"Point\"";
		final String jsonCoordinates = "\"coordinates\":["+lat+","+lon+"]";
		return  buildGenericGeoJson(jsonGeometryType, jsonCoordinates, epsgCode);
	}
	
	private static final String buildGenericGeoJson(String jsonGeometryType, String jsonCoordinates,String epsgCode) {
		final StringBuffer geoJsonResult = new StringBuffer();
		geoJsonResult.append("{");
		geoJsonResult.append("\"type\":\"FeatureCollection\",");
		geoJsonResult.append("\"features\":[");
		geoJsonResult.append("{");
		geoJsonResult.append("\"type\":\"Feature\",");
		geoJsonResult.append("\"id\":\"OpenLayers.Feature."+(uniqueFeatureId++)+"\",");
		geoJsonResult.append("\"properties\":{},");
		geoJsonResult.append("\"geometry\":{");
		geoJsonResult.append(jsonGeometryType);
		geoJsonResult.append(",");
		geoJsonResult.append(jsonCoordinates);
		geoJsonResult.append("},");
		//geoJsonResult.append("\"crs\":{");
		//geoJsonResult.append("\"type\":\"name\",");
		//geoJsonResult.append("\"properties\": {");
		//geoJsonResult.append("\"name\":\"EPSG:"+epsgCode+"\"");
		//geoJsonResult.append("}");
		//geoJsonResult.append("}");
		geoJsonResult.append("\"crs\":{\"type\":\"OGC\", \"properties\":{\"urn\":\"urn:ogc:def:crs:OGC:1.3:CRS84\"}}");
		geoJsonResult.append("}");
		geoJsonResult.append("]");
		geoJsonResult.append("}");
		return  geoJsonResult.toString();
	}
}
