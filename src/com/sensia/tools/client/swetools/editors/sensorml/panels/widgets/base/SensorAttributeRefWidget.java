/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ICallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.NameRefResolver;

/**
 * The Class SensorAttributeRefWidget is corresponding to any "ref" attribute.
 */
public class SensorAttributeRefWidget extends AbstractSensorElementWidget{

	/** The container. */
	private Panel container;
	
	/** The label. */
	private HTML label;
	
	/** The resolver. */
	private NameRefResolver resolver;
	
	/** The root. */
	private ISensorWidget root;
	
	/** The grammar. */
	private RNGGrammar grammar;
	
	/**
	 * Instantiates a new sensor attribute ref widget.
	 *
	 * @param root the root
	 * @param grammar the grammar
	 */
	public SensorAttributeRefWidget(ISensorWidget root,RNGGrammar grammar) {
		super("ref", TAG_DEF.RNG, TAG_TYPE.ATTRIBUTE);
		container = new HorizontalPanel();
		this.root = root;
		this.grammar= grammar;
		
		//creates and add a label to the container
		label = new HTML();
		container.add(label);
		
		//creates a resolver
		resolver = new NameRefResolver();
		resolver.build(root,grammar);
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getPanel()
	 */
	@Override
	public Panel getPanel() {
		return container;
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE)
	 */
	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.VALUE) {
			String path = widget.getName();
			List<String> pathList = Arrays.asList(path.split("/"));
			
			//resolve the name
			resolver.resolvePath(this, pathList, new ICallback<String>() {
				
				@Override
				public void callback(String...result) {
					label.setText(result[0]);
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorAttributeRefWidget(root,grammar);
	}

}
