package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
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
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public abstract class AbstractSensorElementWidget implements ISensorWidget{

	
	private String name;
	private TAG_DEF def;
	private TAG_TYPE type;
	private List<ISensorWidget> elements = new ArrayList<ISensorWidget>(); 
	private MODE editorMode = MODE.VIEW;
	private ISensorWidget parent;
	
	private APPENDER appender = APPENDER.NONE;
	
	private static final int NORMALIZE_DOT_SEPARATOR_SIZE = 70;
	
	protected AbstractSensorElementWidget(String name, TAG_DEF def, TAG_TYPE type){
		this.name = name;
		this.def = def;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void switchMode(MODE mode) {
		editorMode = mode;
		activeMode(mode);
		
		//propagate edit mode
		for(final ISensorWidget sensorWidget : elements) {
			sensorWidget.switchMode(mode);
		}
	}
	
	protected abstract void activeMode(MODE mode);
	
	protected MODE getMode() {
		return editorMode;
	}
	
	public TAG_DEF getDef() {
		return def;
	}
	
	public TAG_TYPE getType(){
		return type;
	}
	
	public List<ISensorWidget> getElements() {
		return elements;
	}
	
	public ISensorWidget getParent() {
		return parent;
	}

	public void setParent(ISensorWidget parent) {
		this.parent = parent;
	}

	
	public List<String> getValues(String parentName) {
		final List<String> values = new ArrayList<String>();
		findRecursiveValues(this,parentName,values);
		return values;
	}
	
	/**
	 * @deprecated Use {@link #getValue(String,boolean)} instead
	 */
	public String getValue(String parentName) {
		return getValue(parentName, true);
	}

	public String getValue(String parentName, boolean recursive) {
		if(recursive) {
			return findRecursiveValue(this,parentName);
		} else {
			String value = null;
			for(ISensorWidget w : getElements()) {
				if(w.getName().equals(parentName) && w.getType() == TAG_TYPE.ATTRIBUTE) {
					//an attribute contains only one value tag and nothing else
					value = w.getElements().get(0).getName();
				}
			}
			return value;
		}
	}
	
	private String findRecursiveValue(ISensorWidget widget,String parentName) {
		//multiple values for choice tag
		if(widget.getType() == TAG_TYPE.VALUE && widget.getParent().getName().equals(parentName)) {
			return widget.getName();
		} else {
			String value = null;
			for(ISensorWidget w : widget.getElements()) {
				value = findRecursiveValue(w, parentName);
				if(value != null) {
					break;
				}
			}
			return value;
		}
	}
	
	private void findRecursiveValues(ISensorWidget widget,String parentName,List<String> values) {
		//multiple values for choice tag
		if(widget.getType() == TAG_TYPE.VALUE && widget.getParent().getName().equals(parentName)) {
			values.add(widget.getName());
		}
		if(values.isEmpty()) {
			for(ISensorWidget w : widget.getElements()) {
				findRecursiveValues(w, parentName,values);
			}
		}
	}
	
	public void setValues(String parentName, List<String> values) {
		for(ISensorWidget w : getElements()) {
			w.setValues(parentName, values);
		}
	}
	
	/**
	 * 
	 */
	public void setValue(String parentName, String value) {
		for(ISensorWidget w : getElements()) {
			w.setValue(parentName, value);
		}
	}
	
	protected abstract void addSensorWidget(ISensorWidget widget);
	
	public void addElement(ISensorWidget element) {
		addSensorWidget(element);
		elements.add(element);
	}
	
	protected String getDotsLine() {
		String newValue = "";
		for(int i=0;i < NORMALIZE_DOT_SEPARATOR_SIZE;i++) {
			newValue += ".";
		}
		return newValue;
	}

	protected String getDotsLine(int nb) {
		String newValue = "";
		for(int i=0;i < nb;i++) {
			newValue += ".";
		}
		return newValue;
	}
	
	@Override
	public String toString() {
		return getClass().toString()+" [name=" + name + ", def=" + def + ", type=" + type + "]";
	}
	
	protected String findLabel(RNGTag tag) {
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

		return null;
	}

	public static String toNiceLabel(String name) {
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

	public static String toCamelCase(String s) {
		String s1 = s.substring(0, 1).toUpperCase();
		if (s.length() > 1)
			s1 += s.substring(1);
		return s1;
	}
	
	@Override
	public ISensorWidget cloneSensorWidget(){
		AbstractSensorElementWidget clone = newInstance();
		
		for(final ISensorWidget element : getElements()) {
			ISensorWidget cloneChild = element.cloneSensorWidget();
			if(cloneChild != null) {
				clone.addElement(cloneChild);
			}
		}
		
		return clone;
	}
	
	protected abstract AbstractSensorElementWidget newInstance();
	
	protected Panel getAddButtonPanel(String annotation,final String label) {
		
		Label addButton = new Label(annotation);
		addButton.addStyleName("rng-optional-select");
		
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(!getElements().isEmpty() && getMode() == MODE.EDIT) {
					//concatenates every panels
					VerticalPanel allEditPanels = new VerticalPanel();
					final List<ISensorWidget> clones = new ArrayList<ISensorWidget>();
					
					for(final ISensorWidget panelToAdd : getElements()) {
						ISensorWidget clone = panelToAdd.cloneSensorWidget();
						if(clone != null) {
							clone.switchMode(getMode());
							clones.add(clone);
							allEditPanels.add(clone.getPanel());	
						} else {
							GWT.log("Clone method is not implemented yet for class : "+panelToAdd.getClass().toString());
						}
						
					}
					
					final DialogBox dialogBox = Utils.createAddDialogBox(allEditPanels, "Add "+label,new IButtonCallback() {
						
						@Override
						public void onClick() {
							for(ISensorWidget clone : clones) {
								getParent().addElement(clone);
							}
						}
					});
					dialogBox.show();
				}
			}
		});
		
		final HorizontalPanel panel = new HorizontalPanel();
		panel.add(addButton);
		panel.add(new HTML(label));
		
		return panel;
	}
	
	protected static Panel findPanel(ISensorWidget widget, String name, TAG_DEF def,TAG_TYPE type) {
		Panel panel = null;
		if(widget.getName().equals(name) && widget.getType() == type && widget.getDef() == def) {
			panel =  widget.getPanel();
		} else {
			for(ISensorWidget w : widget.getElements()) {
				panel = findPanel(w, name, def, type);
				if(panel != null) {
					break;
				}
			}
		}
		
		return panel;
	}
	
	public static ISensorWidget findWidget(ISensorWidget widget, String name, TAG_DEF def,TAG_TYPE type) {
		ISensorWidget foundWidget = null;
		if(widget.getName().equals(name) && widget.getType() == type && widget.getDef() == def) {
			foundWidget =  widget;
		} else {
			for(ISensorWidget w : widget.getElements()) {
				foundWidget = findWidget(w, name, def, type);
				if(foundWidget != null) {
					break;
				}
			}
		}
		
		return foundWidget;
	}
	
	public static ISensorWidget findWidget(ISensorWidget widget, String name, TAG_TYPE type) {
		ISensorWidget foundWidget = null;
		if(widget.getName().equals(name) && widget.getType() == type) {
			foundWidget =  widget;
		} else {
			for(ISensorWidget w : widget.getElements()) {
				foundWidget = findWidget(w, name, type);
				if(foundWidget != null) {
					break;
				}
			}
		}
		
		return foundWidget;
	}
	
	//BFS
	public static ISensorWidget findParent(ISensorWidget widget, String name, TAG_DEF def,TAG_TYPE type) {
		ISensorWidget foundWidget = null;
		
		if(widget != null) {
			if(widget.getName().equals(name) && widget.getType() == type && widget.getDef() == def) {
					foundWidget =  widget;
			} else {
				findParent(widget.getParent(), name, def, type);
			}
		}
		return foundWidget;
	}
	
	public static List<ISensorWidget> findWidgets(ISensorWidget root, String name, TAG_DEF def,TAG_TYPE type) {
		List<ISensorWidget> results = new ArrayList<ISensorWidget>();
		findRecursiveWidgets(root, name, def, type, results);
		return results;
	}
	
	private static void findRecursiveWidgets(ISensorWidget root, String name, TAG_DEF def,TAG_TYPE type,List<ISensorWidget> results) {
		if(root.getName().equals(name) && root.getType() == type && root.getDef() == def) {
			results.add(root);
		} else {
			for(ISensorWidget widget : root.getElements()) {
				findRecursiveWidgets(widget, name, def, type, results);
			}
		}
	}
	
	protected void displayEditPanel(Panel panel,String label,IButtonCallback cb) {
		final DialogBox dialogBox = Utils.createEditDialogBox(panel, label,cb);
		dialogBox.show();
	}
		
	protected String splitAndCapitalize(final String str) {
		if(str == null || str.isEmpty()) {
			return str;
		} else if(str.length() == 1) {
			return str.toUpperCase();
		} else {
			return toNiceLabel(str).trim();
		}
	}
	
	protected Panel getEditPanel(final IButtonCallback callback) {
		HorizontalPanel advancedPanel = new HorizontalPanel();
		advancedPanel.addStyleName("rng-advanced-button");
		advancedPanel.setTitle("Edit " + getName());

		FocusPanel wrapper = new FocusPanel();
		wrapper.add(advancedPanel);
		wrapper.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				VerticalPanel container = new VerticalPanel();
				container.addStyleName("advanced-panel");
				getAdvancedPanel(container);
				if (container != null) {
					displayEditPanel(container, "Edit " + getName(),
							new IButtonCallback() {
								@Override
								public void onClick() {
									refresh();
									if (callback != null) {
										callback.onClick();
									}
								}
							});
				}
			}
		});

		return wrapper;
	}
	
	public void refresh() {
		//parentRefresh();
		childRefresh();
	}

	
	protected void childRefresh() {
		for(ISensorWidget child : getElements()) {
			child.refresh();
		}
	}
	
	protected void parentRefresh() {
		if(getParent() != null) {
			getParent().refresh();
		}
	}
	
	@Override
	public APPENDER appendTo() {
		return appender;
	}
	
	public void setAppender(APPENDER appender) {
		this.appender = appender;
	}
	
	public void getAdvancedPanel(Panel container) {
		for(ISensorWidget child : getElements()) {
			child.getAdvancedPanel(container);
		}
	}
	
	public boolean isIcon() {
		return false;
	}
}
