package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ICallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.NameRefResolver;

public class SMLSensorSetValueWidget extends AbstractSensorElementWidget{

	private Panel container;
	private NameRefResolver resolver;
	
	private HTML label;
	private ISensorWidget root;
	
	public SMLSensorSetValueWidget(ISensorWidget root) {
		super("setValue",TAG_DEF.SML,TAG_TYPE.ELEMENT);
	
		this.root = root;
		label = new HTML("");
		container = new HorizontalPanel();
		container.add(label);
		resolver = new NameRefResolver();
		resolver.build(root);
	}

	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("ref")) {
			String path = widget.getElements().get(0).getName();
			List<String> pathList = Arrays.asList(path.split("/"));
			
			//resolve the name
			resolver.resolvePath(this, pathList, new ICallback<String>() {
				
				@Override
				public void callback(String result) {
					// TODO Auto-generated method stub
					
				}
			});
		} else {
			container.add(widget.getPanel());
		}
		
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLSensorSetValueWidget(root);
	}

}
