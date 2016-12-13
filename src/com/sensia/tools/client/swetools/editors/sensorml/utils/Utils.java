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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
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
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ILoadFileCallback;

public class Utils {

	private Utils(){}
	
	public static final DialogBox createCustomDialogBox(final Panel panel,final String title,final Widget... widgets){
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText(title);
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		
		//create Panel
		Panel main = new VerticalPanel();
		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		for(Widget widget : widgets) {
			buttonsPanel.add(widget);
		}
		
		buttonsPanel.setSpacing(5);
		
		main.add(panel);
		main.add(buttonsPanel);
		
		dialogBox.add(main);
		dialogBox.center();
         
		return dialogBox;
	}
	
	public static final DialogBox createAddDialogBox(final Panel panel,final String title,final IButtonCallback addCB){
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText(title);
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		
		//create Panel
		Panel main = new VerticalPanel();
		
		Button close = new Button("Close");
		close.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
        	   dialogBox.hide();
           }
        });
        
		Button add = new Button("Add");
		add.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
        	   addCB.onClick();
        	   dialogBox.hide();
           }
        });
		
		HorizontalPanel buttons = new HorizontalPanel();
		buttons.add(add);
		buttons.add(close);
		buttons.setSpacing(5);
		
		main.add(panel);
		main.add(buttons);
		
		dialogBox.add(main);
		dialogBox.center();
         
		return dialogBox;
	}
	
	public static final CloseDialog displayDialogBox(final Panel panel,final String title){
		final CloseDialog dialogBox = new CloseDialog(title);
		dialogBox.setContent(panel);
		dialogBox.center();
		/*dialogBox.setText(title);
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		
		//create Panel
		Panel main = new VerticalPanel();
		
		Button close = new Button("Close");
		close.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
        	   dialogBox.hide();
           }
        });
        
		HorizontalPanel buttons = new HorizontalPanel();
		
		if(addCB != null) {
			Button add = new Button("Save");
			add.addClickHandler(new ClickHandler() {
	           public void onClick(ClickEvent event) {
	        	   addCB.onClick();
	        	   dialogBox.hide();
	           }
	        });
			
			buttons.add(add);
		}
		
		
		buttons.add(close);
		buttons.setSpacing(5);
		
		main.add(panel);
		main.add(buttons);
		
		ScrollPanel sPanel = new ScrollPanel();
		sPanel.setStyleName("dialog");
		sPanel.add(main);
		
		dialogBox.add(sPanel);
		dialogBox.center();*/
		return dialogBox;
	}
	
	public static final DialogBox createDialogBox(final Panel panel,final String title,final IButtonCallback addCB){
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText(title);
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		
		//create Panel
		VerticalPanel main = new VerticalPanel();
		
		Button close = new Button("Close");
		close.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
        	   dialogBox.hide();
           }
        });
        
		HorizontalPanel buttons = new HorizontalPanel();
		buttons.add(close);
		buttons.setSpacing(5);
		
		main.add(panel);
		main.add(buttons);
		
		main.setSpacing(10);
		dialogBox.add(main);
		dialogBox.center();
         
		return dialogBox;
	}
	
	private static final String DEGREE  = "\u00b0";
	private static final String OHM = "\u2126";
	
	public static String getUOMSymbol(String uom) {
		if(uom.equals("deg")) {
			return DEGREE;
		} else if(uom.equals("cel")) {
			return DEGREE+"C";
		} else if(uom.equals("kohm")) {
			return "k"+OHM;
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
}
