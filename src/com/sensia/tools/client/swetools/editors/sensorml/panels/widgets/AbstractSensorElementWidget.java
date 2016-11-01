/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/


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
import com.sensia.relaxNG.RNGValue;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * The Class AbstractSensorElementWidget.
 */
public abstract class AbstractSensorElementWidget implements ISensorWidget{

	
	/** The node name. */
	private String name;
	
	/** The node definition. */
	private TAG_DEF def;
	
	/** The node type. */
	private TAG_TYPE type;
	
	/** The children elements. */
	private List<ISensorWidget> elements = new ArrayList<ISensorWidget>(); 
	
	/** The default mode value. */
	private MODE editorMode = MODE.VIEW;
	
	/** The node parent. */
	private ISensorWidget parent;
	
	/** The default appender value. */
	private APPENDER appender = APPENDER.NONE;
	
	/** The Constant NORMALIZE_DOT_SEPARATOR_SIZE. */
	private static final int NORMALIZE_DOT_SEPARATOR_SIZE = 70;
	
	private RNGTag rngTag;
	
	/**
	 * Instantiates a new abstract sensor element widget.
	 *
	 * @param name the name of the node
	 * @param def the definition of the node
	 * @param type the type of the node
	 */
	protected AbstractSensorElementWidget(String name, TAG_DEF def, TAG_TYPE type){
		this.name = name;
		this.def = def;
		this.type = type;
	}
	
	/**
	 * Instantiates a new abstract sensor element widget.
	 *
	 * @param name the name of the node
	 * @param def the definition of the node
	 * @param type the type of the node
	 */
	protected AbstractSensorElementWidget(String name, TAG_DEF def, TAG_TYPE type,RNGTag tag){
		this(name,def,type);
		this.rngTag = tag;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getName()
	 */
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#setName(java.lang.String)
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#switchMode(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE)
	 */
	public void switchMode(MODE mode) {
		editorMode = mode;
		activeMode(mode);
		
		//propagate edit mode
		for(final ISensorWidget sensorWidget : elements) {
			sensorWidget.switchMode(mode);
		}
	}
	
	/**
	 * Switch between different modes.
	 *
	 * @param mode the mode to activate
	 */
	protected abstract void activeMode(MODE mode);
	
	/**
	 * Gets the current running mode.
	 *
	 * @return the mode
	 */
	protected MODE getMode() {
		return editorMode;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getDef()
	 */
	public TAG_DEF getDef() {
		return def;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getType()
	 */
	public TAG_TYPE getType(){
		return type;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getElements()
	 */
	public List<ISensorWidget> getElements() {
		return elements;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getParent()
	 */
	public ISensorWidget getParent() {
		return parent;
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#setParent(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	public void setParent(ISensorWidget parent) {
		this.parent = parent;
	}
	
	/**
	 * Gets the value.
	 *
	 * @param parentName the parent name
	 * @return the value
	 * @deprecated Use {@link #getValue(String,boolean)} instead
	 */
	public String getValue(String parentName) {
		return getValue(parentName, true);
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getValue(java.lang.String, boolean)
	 */
	public String getValue(String parentName, boolean recursive) {
		if(recursive) {
			return findRecursiveValue(this,parentName);
		} else {
			String value = null;
			//iterates over its children
			for(ISensorWidget w : getElements()) {
				if(w.getName().equals(parentName) && w.getType() == TAG_TYPE.ATTRIBUTE) {
					//an attribute contains only one value tag and nothing else
					value = w.getElements().get(0).getName();
				}
			}
			return value;
		}
	}
	
	public RNGValue getRNGValue(String parentName, boolean recursive) {
		if(recursive) {
			return findRecursiveRNGValue(this,parentName);
		} else {
			RNGValue value = null;
			//iterates over its children
			for(ISensorWidget w : getElements()) {
				if(w.getName().equals(parentName) && w.getType() == TAG_TYPE.ATTRIBUTE) {
					//an attribute contains only one value tag and nothing else
					value = (RNGValue) w.getElements().get(0).getRNGTag();
				}
			}
			return value;
		}
	}
	
	/**
	 * Find recursive the value over the sub-elements of the tree.
	 *
	 * @param widget the widget from which the recursive method starts
	 * @param parentName the element parent name
	 * @return the string value found
	 */
	private String findRecursiveValue(ISensorWidget widget,String parentName) {
		//multiple values for choice tag
		if(widget.getType() == TAG_TYPE.VALUE && widget.getParent().getName().equals(parentName)) {
			return widget.getName();
		} else {
			String value = null;
			//iterates over its children
			for(ISensorWidget w : widget.getElements()) {
				value = findRecursiveValue(w, parentName);
				if(value != null) {
					break;
				}
			}
			return value;
		}
	}
	
	private RNGValue findRecursiveRNGValue(ISensorWidget widget,String parentName) {
		//multiple values for choice tag
		if(widget.getType() == TAG_TYPE.VALUE && widget.getParent().getName().equals(parentName)) {
			return (RNGValue) widget.getRNGTag();
		} else {
			RNGValue value = null;
			//iterates over its children
			for(ISensorWidget w : widget.getElements()) {
				value = findRecursiveRNGValue(w, parentName);
				if(value != null) {
					break;
				}
			}
			return value;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#setValue(java.lang.String, java.lang.String)
	 */
	public void setValue(String parentName, String value) {
		for(ISensorWidget w : getElements()) {
			w.setValue(parentName, value);
		}
	}
	
	/**
	 * This is the internal method to add widgets between them.
	 * Thus each widget can control the way that they want handle the child which is appended.
	 *	
	 * @param widget the widget to add
	 */
	protected abstract void addSensorWidget(ISensorWidget widget);
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#addElement(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	public void addElement(ISensorWidget element) {
		addSensorWidget(element);
		elements.add(element);
	}
	
	/**
	 * Gets a line of dots of a length of NORMALIZE_DOT_SEPARATOR_SIZE.
	 *
	 * @return the line of dots.
	 */
	protected String getDotsLine() {
		String newValue = "";
		for(int i=0;i < NORMALIZE_DOT_SEPARATOR_SIZE;i++) {
			newValue += ".";
		}
		newValue +=SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE;
		return newValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().toString()+" [name=" + name + ", def=" + def + ", type=" + type + "]";
	}
	
	/**
	 * Find label.
	 *
	 * @param tag the tag
	 * @return the string
	 */
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

	/**
	 * Transform the String value into a nice label. Thus the String isGeneric becomes Is Generic.
	 * The Upper case characters defines the space and the first character will be transform into upper case.
	 *
	 * @param name the string to transform
	 * @return the string the transformed String
	 */
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
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#cloneSensorWidget()
	 */
	@Override
	public ISensorWidget cloneSensorWidget(){
		AbstractSensorElementWidget clone = newInstance();
		
		//iterates over children and clone each one
		for(final ISensorWidget element : getElements()) {
			ISensorWidget cloneChild = element.cloneSensorWidget();
			if(cloneChild != null) {
				clone.addElement(cloneChild);
			}
		}
		
		return clone;
	}
	
	/**
	 * Creates a new instance of the widget.
	 *
	 * @return the abstract sensor element widget
	 */
	protected abstract AbstractSensorElementWidget newInstance();
	
	/**
	 * Gets a panel represented as a PLUS icon. The panel has the action to create a new panel.
	 *
	 * @param annotation the annotation
	 * @param label the label
	 * @return the adds the button panel
	 */
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
	
	/**
	 * Find a widget given a root position, a name, a definition and a type.
	 * If the widget is not found, a NULL value is returned.
	 * @param widget the widget the root widget
	 * @param name the name the name of the searched widget
	 * @param def the definition of the searched widget
	 * @param type the type the type of the searched widget
	 * @return the ISensorWidget found. This will be a reference to the existing widget of the tree.
	 */
	public static ISensorWidget findWidget(ISensorWidget widget, String name, TAG_DEF def,TAG_TYPE type) {
		ISensorWidget foundWidget = null;
		if(widget.getName().equals(name) && widget.getType() == type && widget.getDef() == def) {
			foundWidget =  widget;
		} else {
			//iterates over children
			for(ISensorWidget w : widget.getElements()) {
				foundWidget = findWidget(w, name, def, type);
				if(foundWidget != null) {
					break;
				}
			}
		}
		
		return foundWidget;
	}
	
	/**
	 * Find a widget given a root position, a name and a type.
	 * If the widget is not found, a NULL value is returned.
	 * @param widget the widget the root widget
	 * @param name the name the name of the searched widget
	 * @param type the type the type of the searched widget
	 * @return the ISensorWidget found. This will be a reference to the existing widget of the tree.
	 */
	public static ISensorWidget findWidget(ISensorWidget widget, String name, TAG_TYPE type) {
		ISensorWidget foundWidget = null;
		if(widget.getName().equals(name) && widget.getType() == type) {
			foundWidget =  widget;
		} else {
			//iterates over children
			for(ISensorWidget w : widget.getElements()) {
				foundWidget = findWidget(w, name, type);
				if(foundWidget != null) {
					break;
				}
			}
		}
		
		return foundWidget;
	}
	
	/**
	 * Find a list of widgets given a root position, a name and a type.
	 * If no widget has been found, a empty list is returned.
	 * @param widget the widget the root widget
	 * @param name the name the name of the searched widget
	 * @param type the type the type of the searched widget
	 * @return the list of ISensorWidget found. This will be a reference to the existing widget of the tree.
	 */
	public static List<ISensorWidget> findWidgets(ISensorWidget root, String name, TAG_DEF def,TAG_TYPE type) {
		List<ISensorWidget> results = new ArrayList<ISensorWidget>();
		findRecursiveWidgets(root, name, def, type, results);
		return results;
	}
	
	/**
	 * Iterates over widgets to find recursive elements.
	 *
	 * @param root the root
	 * @param name the name
	 * @param def the def
	 * @param type the type
	 * @param results the results
	 */
	private static void findRecursiveWidgets(ISensorWidget root, String name, TAG_DEF def,TAG_TYPE type,List<ISensorWidget> results) {
		if(root.getName().equals(name) && root.getType() == type && root.getDef() == def) {
			results.add(root);
		} else {
			for(ISensorWidget widget : root.getElements()) {
				findRecursiveWidgets(widget, name, def, type, results);
			}
		}
	}
	
	/**
	 * Display edit panel. The method only encapsulated a panel and display a Window
	 * with the Save & Close buttons. If the callback parameter is NULL, the window will
	 * only display the Close button.
	 *
	 * @param panel the panel to encapsulate into a Window
	 * @param label the label of the Window
	 * @param cb the cb The callback when the user click on the Save button
	 */
	protected void displayEditPanel(Panel panel,String label,IButtonCallback cb) {
		final DialogBox dialogBox = Utils.createEditDialogBox(panel, label,cb);
		dialogBox.show();
	}
		
	/**
	 * Split and capitalize the String.
	 *
	 * @param str the str
	 * @return the string
	 */
	protected String splitAndCapitalize(final String str) {
		if(str == null || str.isEmpty()) {
			return str;
		} else if(str.length() == 1) {
			return str.toUpperCase();
		} else {
			return toNiceLabel(str).trim();
		}
	}
	
	/**
	 * Gets the edits the panel.
	 *
	 * @param callback the callback
	 * @return the edits the panel
	 */
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
					if(getMode() == MODE.EDIT) {
					displayEditPanel(container, "Edit " + getName(),
							new IButtonCallback() {
								@Override
								public void onClick() {
									refreshChildren(getElements());
									refreshParents(getParent());
									if (callback != null) {
										callback.onClick();
									}
								}
							});
					} else {
						displayEditPanel(container, "View " + getName(),null);
					}
				}
			}
		});

		return wrapper;
	}
	
	/**
	 * Gets the value.
	 *
	 * @param root the root
	 * @return the value
	 */
	protected ISensorWidget getValue(ISensorWidget root) {
		ISensorWidget foundWidget = null;
		if(root.getType() == TAG_TYPE.VALUE) {
			foundWidget =  root;
		} else {
			for(ISensorWidget w : root.getElements()) {
				foundWidget = getValue(w);
				if(foundWidget != null) {
					break;
				}
			}
		}
		
		return foundWidget;
	}
	
	/**
	 * Gets the simple edit panel.
	 *
	 * @param callback the callback
	 * @param advancedTags the advanced tags
	 * @return the simple edit panel
	 */
	protected Panel getSimpleEditPanel(final IButtonCallback callback,final List<String> advancedTags) {
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
				for(ISensorWidget child : getElements()) {
					if(advancedTags.contains(child.getName())) {
						child.getAdvancedPanel(container);
					}
				}

				if (container != null) {
					if(getMode() == MODE.EDIT) {
						displayEditPanel(container, "Edit " + getName(),
							new IButtonCallback() {
								@Override
								public void onClick() {
									refreshChildren(getElements());
									refreshParents(getParent());
									if (callback != null) {
										callback.onClick();
									}
								}
							});
					} else {
						displayEditPanel(container, "View " + getName(),null);
					}
				}
			}
		});

		return wrapper;
	}
	
	/**
	 * Gets the edits the panel.
	 *
	 * @param callback the callback
	 * @param widget the widget
	 * @return the edits the panel
	 */
	protected Panel getEditPanel(final IButtonCallback callback,final ISensorWidget widget) {
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
				getAdvancedPanel(container,widget);
				if (container != null) {
					if(getMode() == MODE.EDIT) {
					displayEditPanel(container, "Edit " + getName(),
							new IButtonCallback() {
								@Override
								public void onClick() {
									refreshChildren(widget.getElements());
									refreshParents(widget.getParent());
									if (callback != null) {
										callback.onClick();
									}
								}
							});
					} else {
						displayEditPanel(container, "View " + getName(),null);
					}
				}
			}
		});

		return wrapper;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#refresh()
	 */
	public void refresh() {
		
	}
	
	/**
	 * Refresh children.
	 *
	 * @param children the children
	 */
	public void refreshChildren(List<ISensorWidget> children) {
		for(ISensorWidget child : children) {
			refreshChildren(child.getElements());
			child.refresh();
		}
	}

	/**
	 * Refresh parents.
	 *
	 * @param parent the parent
	 */
	public void refreshParents(ISensorWidget parent) {
		if(parent != null) {
			parent.refresh();
			refreshParents(parent.getParent());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#appendTo()
	 */
	@Override
	public APPENDER appendTo() {
		return appender;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#setAppender(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.APPENDER)
	 */
	public void setAppender(APPENDER appender) {
		this.appender = appender;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getAdvancedPanel(com.google.gwt.user.client.ui.Panel)
	 */
	public void getAdvancedPanel(Panel container) {
		for(ISensorWidget child : getElements()) {
			child.getAdvancedPanel(container);
		}
	}
	
	/**
	 * Gets the advanced panel. The advanced panel is computed from each 
	 * child. The tree is walked from the root widget given as parameter.
	 * Each child widget can add its advanced elements by adding them to the container.
	 *
	 * @param container the container to append advanced elements
	 * @param widget the root widget to start from
	 */
	public void getAdvancedPanel(Panel container,ISensorWidget widget) {
		for(ISensorWidget child : widget.getElements()) {
			child.getAdvancedPanel(container);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#isIcon()
	 */
	public boolean isIcon() {
		return false;
	}
	
	public void setRNGTag(RNGTag tag){
		this.rngTag = tag;
	}
	
	@Override
	public RNGTag getRNGTag() {
		return this.rngTag;
	}
}
