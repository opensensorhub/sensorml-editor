package com.sensia.tools.client.swetools.editors.sensorml.renderer;

import java.util.*;

import com.google.gwt.xml.client.Element;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.relaxNG.RNGInvalidContent;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.controller.IObserver;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.DisclosureElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.GenericVerticalContainerPanel;
import com.sensia.tools.client.swetools.editors.sensorml.state.State;
import com.sensia.tools.client.swetools.editors.sensorml.utils.NameRefResolver;

public abstract class Renderer implements IRefreshHandler, RNGTagVisitor{

	private List<IObserver> observers;
	/** The stack. */
	protected Stack<IPanel<? extends RNGTag>> stack;
	
	/** The grammar. */
	private RNGGrammar grammar;
	
	protected IRefreshHandler refreshHandler;
	
	protected NameRefResolver resolver;
	
	protected IPanel rootPanel;

	public static State state = new State();

	public Renderer() {
		stack = new Stack<>();
		resolver = new NameRefResolver();
		this.observers = new ArrayList<>();
		
		GenericVerticalContainerPanel rootAdvanced = new GenericVerticalContainerPanel();
		rootAdvanced.getPanel().addStyleName("advanced-dialog");
		
		GenericVerticalContainerPanel rootAdvanced2 = new GenericVerticalContainerPanel();
		rootAdvanced2.getPanel().addStyleName(getDefaultStyle());
		rootAdvanced2.getPanel().add(rootAdvanced.getPanel());
		push(rootAdvanced2);
	}
	
	@Override
	public void refresh() {
		if(getRefreshHandler() != null) {
			getRefreshHandler().refresh();
		}
	}
	
	public void setObservers(List<IObserver> observer) {
		this.observers = observer;
	}
	
	public IRefreshHandler getRefreshHandler() {
		return refreshHandler;
	}

	public void setRefreshHandler(IRefreshHandler refreshHandler) {
		this.refreshHandler = refreshHandler;
	}
	
	/**
	 * Push.
	 *
	 * @param element the element
	 */
	public void push(IPanel<? extends RNGTag> element) {
		stack.push(element);
	}
	
	/**
	 * Peek.
	 *
	 * @return the i sensor widget
	 */
	public IPanel<? extends RNGTag> peek() {
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
	public IPanel<? extends RNGTag> pop() {
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
		resolver.setCurrentGrammar(this.grammar);
		grammar.getStartPattern().accept(this);
	}	

	@Override
    public void visit(RNGInvalidContent tag) {
	    // skip it by default
	}
    
	
	protected void pushAndVisitChildren(IPanel<? extends RNGTag> widget, RNGTag tag) {
		push(widget);
		int stackSize = getStackSize();
		
		if (tag != null) {
			tag.accept(this);
		}
		while(stackSize < getStackSize()){
			IPanel<? extends RNGTag> child = pop();
			//child.setParent(widget);
			widget.addElement(child);
		}
	}
	/**
	 * Visit children.
	 *
	 * @param tags the tags
	 */
	public void visitChildren(List<RNGTag> tags) {
		int stackSize = getStackSize();
		IPanel<? extends RNGTag> peek = peek();
		
		for (RNGTag tag : tags) {
			if (tag != null) {
				tag.accept(this);
			}
			if(peek != null){
			    while(stackSize < getStackSize()){
					IPanel<? extends RNGTag> child = pop();
					//child.setParent(peek);
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
	protected void pushAndVisitChildren(IPanel<? extends RNGTag> widget, List<RNGTag> tags) {
		push(widget);
		int stackSize = getStackSize();
		
		for (RNGTag tag : tags) {
			if (tag != null) {
				tag.accept(this);
			}
			while(stackSize < getStackSize()){
				IPanel<? extends RNGTag> child = pop();
				//child.setParent(widget);
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
	protected IPanel<RNGTag> renderSMLVerticalPanel(RNGTag tag) {
		return new GenericVerticalContainerPanel(tag);
	}
	
	protected IPanel<RNGElement> renderDisclosure(RNGElement tag) {
		//return new SectionPanel(tag);
		return new DisclosureElementPanel(tag);
	}
	
	/**
	 * Render line widget.
	 *
	 * @param name the name
	 * @param def the def
	 * @param type the type
	 * @return the i sensor widget
	 */
	protected IPanel<RNGTag> renderLineWidget(RNGTag tag) {
		//return new SensorGenericLineWidget(tag);
		return null;
	}
	
	
	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public IPanel<? extends RNGTag> getRoot() {
		IPanel<? extends RNGTag> root = null;
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
	
	public void reset() {
		stack = new Stack<IPanel<? extends RNGTag>>();
		GenericVerticalContainerPanel rootAdvanced = new GenericVerticalContainerPanel();
		rootAdvanced.getPanel().addStyleName("advanced-dialog");
		
		GenericVerticalContainerPanel rootAdvanced2 = new GenericVerticalContainerPanel();
		rootAdvanced2.getPanel().addStyleName(getDefaultStyle());
		rootAdvanced2.getPanel().add(rootAdvanced.getPanel());
		push(rootAdvanced2);
	}
	
	protected abstract String getDefaultStyle();
}
