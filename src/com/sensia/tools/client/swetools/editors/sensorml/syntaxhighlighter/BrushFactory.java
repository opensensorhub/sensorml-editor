package com.sensia.tools.client.swetools.editors.sensorml.syntaxhighlighter;

import com.google.gwt.core.client.JavaScriptObject;

public class BrushFactory {
    public native static JavaScriptObject newJavaBrush() /*-{
        return new $wnd.SyntaxHighlighter.brushes.Java();
    }-*/;

    public native static JavaScriptObject newXmlBrush() /*-{
        return new $wnd.SyntaxHighlighter.brushes.Xml();
    }-*/;
}
