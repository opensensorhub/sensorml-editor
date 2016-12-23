package com.sensia.tools.client.swetools.editors.sensorml.renderer;

import java.util.ArrayList;
import java.util.List;

import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.controller.IObserver;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;

public abstract class Renderer implements IRefreshHandler, RNGTagVisitor{

	private List<IObserver> observers;
	
	protected IRefreshHandler refreshHandler;
	
	public Renderer() {
		this.observers = new ArrayList<IObserver>();
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
}
