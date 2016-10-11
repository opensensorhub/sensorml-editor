/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGDefine;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.relaxNG.RNGGroup;
import com.sensia.relaxNG.RNGInterleave;
import com.sensia.relaxNG.RNGList;
import com.sensia.relaxNG.RNGOneOrMore;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGRef;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.relaxNG.RNGText;
import com.sensia.relaxNG.RNGValue;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.relaxNG.XSDAnyURI;
import com.sensia.relaxNG.XSDBoolean;
import com.sensia.relaxNG.XSDDateTime;
import com.sensia.relaxNG.XSDDecimal;
import com.sensia.relaxNG.XSDDouble;
import com.sensia.relaxNG.XSDInteger;
import com.sensia.relaxNG.XSDString;
import com.sensia.tools.client.swetools.editors.sensorml.controller.IObserver;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorAttributeWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorChoiceWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericHorizontalContainerWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericVerticalContainerWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorOptionalWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorValueWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorZeroOrMoreWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.line.SensorGenericLineWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd.SensorXSDAnyURIWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd.SensorXSDDateTimeWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd.SensorXSDDecimalWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd.SensorXSDDoubleWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd.SensorXSDIntegerWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd.SensorXSDStringWidget;

/**
 * <p>
 * <b>Title:</b> RNGRenderer
 * </p>
 * 
 * <p>
 * <b>Description:</b><br/>
 * Renders content of an RNG grammar using GWT widgets
 * </p>
 * 
 * <p>
 * Copyright (c) 2011
 * </p>.
 *
 * @author Alexandre Robin
 * @date Aug 27, 2011
 */
public abstract class RNGRenderer implements RNGTagVisitor {
	
	/** The stack. */
	private Stack<ISensorWidget> stack;
	
	/** The grammar. */
	private RNGGrammar grammar;
	
	private List<IObserver> observers;
	
	/**
	 * Instantiates a new RNG renderer.
	 */
	public RNGRenderer() {
		stack = new Stack<ISensorWidget>();
		this.observers = new ArrayList<IObserver>();
	}

	/**
	 * Push.
	 *
	 * @param element the element
	 */
	public void push(ISensorWidget element) {
		stack.push(element);
	}
	
	/**
	 * Peek.
	 *
	 * @return the i sensor widget
	 */
	public ISensorWidget peek() {
		if(!stack.isEmpty()) {
			return stack.peek();
		}else {
			return null;
		}
	}
	
	/**
	 * Pop.
	 *
	 * @return the i sensor widget
	 */
	public ISensorWidget pop() {
		return stack.pop();
	}
	
	/**
	 * Gets the stack size.
	 *
	 * @return the stack size
	 */
	protected int getStackSize() {
		return stack.size();
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGGrammar)
	 */
	@Override
	public void visit(RNGGrammar grammar) {
		if (grammar.getStartPattern() == null) {
			throw new IllegalStateException("Grammar has no 'start' pattern and cannot be used to create a new instance");
		}
		this.grammar = grammar;
		grammar.getStartPattern().accept(this);
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGElement)
	 */
	@Override
	public void visit(RNGElement elt) {
		pushAndVisitChildren(new SensorGenericHorizontalContainerWidget(elt.getName(), TAG_DEF.RNG, TAG_TYPE.ELEMENT), elt.getChildren());
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGChoice)
	 */
	@Override
	public void visit(RNGChoice choice) {
		RNGTag selectedPattern = choice.getSelectedPattern();
		ISensorWidget widget = new SensorChoiceWidget(choice);
		push(widget);
		if(selectedPattern != null) {
			visit(selectedPattern);
		}
		
		makeTagObservable(choice);
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGOptional)
	 */
	@Override
	public void visit(RNGOptional optional) {
		ISensorWidget widget = new SensorOptionalWidget(optional);
		push(widget);
		if(optional.isSelected()){
			this.visitChildren(optional.getChildren());
		}
		
		
		makeTagObservable(optional);
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGAttribute)
	 */
	@Override
	public void visit(RNGAttribute attribute) {
		ISensorWidget widget = new SensorAttributeWidget(attribute.getName());
		pushAndVisitChildren(widget, attribute.getChildren());
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGRef)
	 */
	@Override
	public void visit(RNGRef ref) {
		if (ref.getPattern() != null) {
			ref.getPattern().accept(this);
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGDefine)
	 */
	@Override
	public void visit(RNGDefine pattern) {
		this.visitChildren(pattern.getChildren());
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGOneOrMore)
	 */
	@Override
	public void visit(RNGOneOrMore oneOrMore) {
		this.visit((RNGZeroOrMore) oneOrMore);
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGZeroOrMore)
	 */
	@Override
	public void visit(RNGZeroOrMore zeroOrMore) {
		ISensorWidget widget = new SensorZeroOrMoreWidget(zeroOrMore);
		push(widget);
		// display current instances
		List<List<RNGTag>> patternInstances = zeroOrMore.getPatternInstances();
		for(List<RNGTag> tags : patternInstances) {
			this.visitChildren(tags);
		}
		
		makeTagObservable(zeroOrMore);
		
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGGroup)
	 */
	@Override
	public void visit(RNGGroup group) {
		this.visitChildren(group.getChildren());
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGInterleave)
	 */
	@Override
	public void visit(RNGInterleave interleave) {
		this.visitChildren(interleave.getChildren());
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGText)
	 */
	@Override
	public void visit(RNGText text) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGValue)
	 */
	@Override
	public void visit(RNGValue val) {
		push(new SensorValueWidget(val.getText(),val));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGList)
	 */
	@Override
	public void visit(RNGList list) {
		visitChildren(list.getChildren());
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGData)
	 */
	@Override
	public void visit(RNGData<?> data) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDString)
	 */
	@Override
	public void visit(XSDString data) {
		push(new SensorXSDStringWidget(data));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDBoolean)
	 */
	@Override
	public void visit(XSDBoolean data) {
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDecimal)
	 */
	@Override
	public void visit(XSDDecimal data) {
		push(new SensorXSDDecimalWidget(data));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDouble)
	 */
	@Override
	public void visit(XSDDouble data) {
		push(new SensorXSDDoubleWidget(data));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDInteger)
	 */
	@Override
	public void visit(XSDInteger data) {
		push(new SensorXSDIntegerWidget(data));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDAnyURI)
	 */
	@Override
	public void visit(XSDAnyURI data) {
		push(new SensorXSDAnyURIWidget(data));
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDateTime)
	 */
	@Override
	public void visit(XSDDateTime data) {
		push(new SensorXSDDateTimeWidget(data));
		
	}
	
	public void visit(RNGTag tag) {
		tag.accept(this);
	}
	
	/**
	 * Visit children.
	 *
	 * @param tags the tags
	 */
	protected void visitChildren(List<RNGTag> tags) {
		int stackSize = getStackSize();
		ISensorWidget peek = peek();
		
		for (RNGTag tag : tags) {
			if (tag != null) {
				tag.accept(this);
			}
			if(peek != null){
				if(stackSize < getStackSize()){
					ISensorWidget child = pop();
					child.setParent(peek);
					peek.addElement(child);
				}
			}
		}
	}
	
	/**
	 * Push and visit children.
	 *
	 * @param widget the widget
	 * @param tags the tags
	 */
	protected void pushAndVisitChildren(ISensorWidget widget, List<RNGTag> tags) {
		push(widget);
		int stackSize = getStackSize();
		
		for (RNGTag tag : tags) {
			if (tag != null) {
				tag.accept(this);
			}
			if(stackSize < getStackSize()){
				ISensorWidget child = pop();
				child.setParent(widget);
				widget.addElement(child);
			}
		}
	}
	
	/**
	 * Render vertical widget.
	 *
	 * @param name the name
	 * @param def the def
	 * @param type the type
	 * @return the i sensor widget
	 */
	protected ISensorWidget renderVerticalWidget(String name, TAG_DEF def, TAG_TYPE type) {
		return new SensorGenericVerticalContainerWidget(name, def, type);
	}
	
	/**
	 * Render horizontal widget.
	 *
	 * @param name the name
	 * @param def the def
	 * @param type the type
	 * @return the i sensor widget
	 */
	protected ISensorWidget renderHorizontalWidget(String name, TAG_DEF def, TAG_TYPE type) {
		return new SensorGenericHorizontalContainerWidget(name, def, type);
	}
	
	/**
	 * Render line widget.
	 *
	 * @param name the name
	 * @param def the def
	 * @param type the type
	 * @return the i sensor widget
	 */
	protected ISensorWidget renderLineWidget(String name, TAG_DEF def, TAG_TYPE type) {
		return new SensorGenericLineWidget(name, def, type);
	}
	
	/**
	 * Gets the widget.
	 *
	 * @param name the name
	 * @return the widget
	 */
	protected ISensorWidget getWidget(final String name) {
		return null;
	}
	
	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public ISensorWidget getRoot() {
		ISensorWidget root = null;
		if(!stack.isEmpty()) {
			root = stack.get(0);
		} 
		return root;
	}
	
	/**
	 * Gets the grammar.
	 *
	 * @return the grammar
	 */
	public RNGGrammar getGrammar() {
		return  grammar;
	}
	
	public void setObservers(List<IObserver> observer) {
		this.observers = observer;
	}
	
	public void makeTagObservable(RNGTag tag) {
		for(IObserver o : this.observers) {
			tag.addObserver(o);
		}
	}
}