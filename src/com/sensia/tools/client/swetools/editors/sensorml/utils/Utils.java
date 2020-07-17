package com.sensia.tools.client.swetools.editors.sensorml.utils;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGDefine;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGGroup;
import com.sensia.relaxNG.RNGOneOrMore;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGRef;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagList;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ILoadFileCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.Renderer;

public class Utils {

	private Utils(){}
	
	public static final CloseWindow displayDialogBox(final Panel panel,final String title){
		final CloseWindow dialogBox = new CloseWindow(title,false);
		dialogBox.setContent(panel);

		dialogBox.show();
		return dialogBox;
	}
	
	public static final CloseWindow displayDialogBox(final Widget panel,final String title,String css){
		final CloseWindow dialogBox = new CloseWindow(title,false,css);
		dialogBox.setContent(panel);

		dialogBox.show();
		return dialogBox;
	}
	
	public static final SaveCloseWindow displaySaveDialogBox(final Panel panel,final String title,String css){
		final SaveCloseWindow dialogBox = new SaveCloseWindow(title,false,css);
		
		dialogBox.setContent(panel);
		dialogBox.show();
		
		return dialogBox;
	}
	
	public static final SaveCloseWindow displaySaveDialogBox(final Widget panel,final String title,String css){
		final SaveCloseWindow dialogBox = new SaveCloseWindow(title,false,css);
		
		dialogBox.setContent(panel);
		dialogBox.show();
		
		return dialogBox;
	}
	
	public static final SaveCloseWindow displaySaveDialogBox(final Panel panel,final String title){
		final SaveCloseWindow dialogBox = new SaveCloseWindow(title,false);
		
		dialogBox.setContent(panel);
		dialogBox.show();
		
		return dialogBox;
	}
	
	public static final SaveCloseWindow displaySaveDialogBox(final Widget panel,final String title, boolean allowUpload){
		final SaveCloseWindow dialogBox = new SaveCloseWindow(title,false);
		
		dialogBox.setAllowUpload(allowUpload);
		dialogBox.setContent(panel);
		dialogBox.show();
		
		return dialogBox;
	}
	
	public static final SaveCloseWindow displaySaveDialogBox(final RNGTagList rootTag,final IRefreshHandler refreshHandler,
			final Panel rootPanel,final String title, final Renderer renderer){
		
		renderer.setRefreshHandler(new IRefreshHandler() {
			
			@Override
			public void refresh() {
				renderer.reset();
				rootPanel.clear();
				renderer.visitChildren(rootTag.getChildren());
				rootPanel.add(renderer.getRoot().getPanel());
				
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});

		renderer.visitChildren(rootTag.getChildren());
		rootPanel.add(renderer.getRoot().getPanel());
		
		renderer.getRoot().getPanel().addStyleName("advanced-panel");
		
		final SaveCloseWindow dialogBox = new SaveCloseWindow(title,false);
		dialogBox.addSaveHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});
		
		dialogBox.setContent(rootPanel);
		dialogBox.show();
		
		return dialogBox;
	}
	
	public static final SaveCloseWindow displaySaveDialogBox(final List<RNGTag> rootTag,final IRefreshHandler refreshHandler,
			final Panel rootPanel,final String title, final Renderer renderer){
		
		renderer.setRefreshHandler(new IRefreshHandler() {
			
			@Override
			public void refresh() {
				renderer.reset();
				rootPanel.clear();
				renderer.visitChildren(rootTag);
				rootPanel.add(renderer.getRoot().getPanel());
				
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});

		renderer.visitChildren(rootTag);
		rootPanel.add(renderer.getRoot().getPanel());
		
		renderer.getRoot().getPanel().addStyleName("advanced-panel");
		
		final SaveCloseWindow dialogBox = new SaveCloseWindow(title,false);
		dialogBox.addSaveHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});
		
		dialogBox.setContent(rootPanel);
		dialogBox.show();
		
		return dialogBox;
	}
	public static final String DEGREE_SYMBOL  = "\u00b0";
	public static final String OHM_SYMBOL = "\u2126";
	
	public static String getUOMSymbol(String uom) {
		if(uom.equals("deg")) {
			return DEGREE_SYMBOL;
		} else if(uom.equals("Cel")) {
			return DEGREE_SYMBOL+"C";
		} else if(uom.contains("ohm")) {
			return uom.replace("ohm", OHM_SYMBOL);
		} else {
			return uom;
		}
	}
	
	public static void getFile(String url, final ILoadFileCallback callback) {
		RequestBuilder requestBuilder = new RequestBuilder( RequestBuilder.GET, url );
	    try {
	        requestBuilder.sendRequest( null, new RequestCallback(){
	            public void onError(Request request, Throwable exception) {
	                GWT.log( "failed file reading", exception );
	            }
	
	            public void onResponseReceived(Request request, Response response) {
	                callback.onLoad(response.getText());
	
	            }} );
	    } catch (RequestException e) {
	        GWT.log( "failed file reading", e );
	    }
	}
	
	public static String getCurrentURL(String value) {
		UrlBuilder builder = Window.Location.createUrlBuilder();
		builder.setParameter("url", value);
		return builder.buildString();
	}
	
	private static long uniqueIncrement = 0;
	
	public static long getUID() {
		return uniqueIncrement++;
	}
	
	/**
	 * Transform the String value into a nice label. Thus the String isGeneric becomes Is Generic.
	 * The Upper case characters defines the space and the first character will be transform into upper case.
	 *
	 * @param name the string to transform
	 * @return the string the transformed String
	 */
	public static String toNiceLabel(String name) {
		if(name == null || name.isEmpty()) {
			GWT.log("Skip label");
			return "";
		}
		String label = toCamelCase(name).replace('_', ' ');
		StringBuilder b = new StringBuilder(label);

		if (label.length() > 1) {
			boolean space = true;

			for (int i = 1; i < b.length(); i++) {
				char c = b.charAt(i);
				if (!space && Character.isUpperCase(c) && Character.isLowerCase(b.charAt(i - 1))) {
					b.insert(i, ' ');
					space = true;
					i++;
				}

				else if (c == ' ')
					space = true;

				else
					space = false;
			}
		}

		return b.toString();
	}
	

	/**
	 * Find label.
	 *
	 * @param tag the tag
	 * @return the string
	 */
	public static String findLabel(RNGTag tag) {
		String annot = tag.getAnnotation();

		if (tag instanceof RNGElement) {
			return toNiceLabel(((RNGElement) tag).getName());
		}

		else if (tag instanceof RNGAttribute) {
			return toNiceLabel(((RNGAttribute) tag).getName());
		}

		else if (tag instanceof RNGData) {
			return annot;
		}

		else if (tag instanceof RNGDefine || tag instanceof RNGGroup || tag instanceof RNGOptional || tag instanceof RNGZeroOrMore
				|| tag instanceof RNGOneOrMore) {
			if (annot != null)
				return annot;

			List<RNGTag> children = ((RNGTagList) tag).getChildren();
			if (children.size() == 1)
				return findLabel(children.get(0));
		}

		else if (tag instanceof RNGRef) {
			if (annot != null)
				return annot;

			// try to get label from referenced pattern
			RNGDefine def = ((RNGRef) tag).getPattern();
			if (def != null)
				return findLabel(def);
			
		}

		return annot;
	}
	
	/**
	 * To camel case.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String toCamelCase(String s) {
		String s1 = s.substring(0, 1).toUpperCase();
		if (s.length() > 1)
			s1 += s.substring(1);
		return s1;
	}
	
	public static int indexOfIgnoreCase(List<String> list, String value) {
		int idx = -1;
		
		int i =0;
		for(String item : list) {
			if(item.toLowerCase().equals(value.toLowerCase())) {
				idx = i;
				break;
			} else {
				i++;
			}
		}
		return idx;
	}
}
