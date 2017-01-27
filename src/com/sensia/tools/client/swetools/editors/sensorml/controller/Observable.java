package com.sensia.tools.client.swetools.editors.sensorml.controller;

/**
 * 
 */
import java.io.Serializable;

import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class Observable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6949554734105646912L;
	private long uid;
	
	public Observable(){
		 uid = Utils.getUID();
	}
	

    public long getUID() {
    	return uid;
    }
    
	protected IObserver observer;

	/**
	 * Add an Observer
	 * 
	 * @param observer
	 *            Add Observer who is interested in this Observable
	 * 
	 */
	public void addObserver(IObserver observer) {
		this.observer = observer;
	}

	/**
	 * Remove an Observer
	 * 
	 * @param observer
	 *            Remove Observer who is no longer interested in this Observable
	 */
	/*public void removeObserver(IObserver observer) {
		if (observers != null) {
			observers.remove(observer);
		}
	}*/

	/**
	 * Notify all the Observer of a change
	 * 
	 */
	public void notifyObservers() {
		notifyObservers(null);
	}

	/**
	 * Notify all the Observer of a change along with an Hint Object
	 * 
	 * @param hint
	 *            Hint to the Observers as to what they should do.
	 */
	public void notifyObservers(Object hint) {
		if (observer != null) {
			/*Iterator<IObserver> iter = this.observers.iterator();
			while (iter.hasNext()) {
				IObserver observer = (IObserver) iter.next();
				observer.update(this, hint);
			}*/
			observer.update(this, hint);
		}
	}

}
