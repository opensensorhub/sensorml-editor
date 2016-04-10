/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ICallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.NameRefResolver;

/**
 * The Class SMLSensorSetValueWidget is corresponding to <sml:setValue> element.
 */
public class SMLSensorSetValueWidget extends AbstractSensorElementWidget{

	/** The container. */
	private Panel container;
	
	/** The resolver. */
	private NameRefResolver resolver;
	
	/** The label. */
	private HTML label;
	
	/** The value panel. */
	private Panel valuePanel;
	
	/** The uom panel. */
	private Panel uomPanel;
	
	/** The root. */
	private ISensorWidget root;
	
	/** The grammar. */
	private RNGGrammar grammar;
	
	/**
	 * Instantiates a new SML sensor set value widget.
	 *
	 * @param root the root
	 * @param grammar the grammar
	 */
	public SMLSensorSetValueWidget(ISensorWidget root,final RNGGrammar grammar) {
		super("setValue",TAG_DEF.SML,TAG_TYPE.ELEMENT);
	
		this.root = root;
		this.grammar = grammar;
		container = new HorizontalPanel();
		
		init();
	}

	/**
	 * Inits the widget.
	 */
	private void init() {
		label = new HTML("");
		valuePanel = new HorizontalPanel();
		uomPanel = new HorizontalPanel();
		container.add(label);
		container.add(new HTML(SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE));
		container.add(valuePanel);
		container.add(new HTML(SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE));
		container.add(uomPanel);
		
		//add advanced panel
		Panel advancedPanel = getEditPanel(new IButtonCallback() {
			
			@Override
			public void onClick() {
				refreshChildren(getElements());
				refreshParents(getParent());

				final StringBuffer text = new StringBuffer();
				final Label uom = new Label();
				Panel newValuePanel = null;
				
				//on edit, we have to resolve the path again
				for(ISensorWidget widget : getElements()) {
					if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("ref")) {
						String path = widget.getElements().get(0).getName();
						List<String> pathList = Arrays.asList(path.split("/"));
						
						//resolve the name
						resolver.resolvePath(SMLSensorSetValueWidget.this, pathList, new ICallback<String>() {
							
							@Override
							public void callback(String...result) {
								text.append(result[0]);
								uom.setText(result[1]);
								
							}
						});
					} else if(widget.getType() == TAG_TYPE.VALUE){
						newValuePanel = widget.getPanel();
					}
				}
				
				//if no error after resolving path
				if(text.length() > 0) {
					valuePanel.clear();
					uomPanel.clear();
					
					label.setText(text.toString());
					uomPanel.add(uom);
					valuePanel.add(newValuePanel);
				}
			}
		});
		
		container.add(advancedPanel);
		
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
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("ref")) {
			String path = widget.getElements().get(0).getName();
			List<String> pathList = Arrays.asList(path.split("/"));
			
			//resolve the name
			resolver.resolvePath(this, pathList, new ICallback<String>() {
				
				@Override
				public void callback(String...result) {
					//one the path resolve from remote or current file, set the corresponding values
					label.setText(result[0]);
					uomPanel.add(new Label(result[1]));
					
				}
			});
		} else if(widget.getType() == TAG_TYPE.VALUE){
			valuePanel.add(widget.getPanel());
		}
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLSensorSetValueWidget(root,grammar);
	}

}
