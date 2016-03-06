package com.sensia.tools.client.swetools.editors.sensorml.utils;

public class UtilsNative {

	public static native void consoleLog( String message) /*-{
		console.log( message );
	}-*/;
}
