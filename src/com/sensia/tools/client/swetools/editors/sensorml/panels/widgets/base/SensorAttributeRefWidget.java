package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ICallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.NameRefResolver;

public class SensorAttributeRefWidget extends AbstractSensorElementWidget{

	private Panel container;
	private HTML label;
	private NameRefResolver resolver;
	private ISensorWidget root;
	
	public SensorAttributeRefWidget(ISensorWidget root) {
		super("ref", TAG_DEF.RNG, TAG_TYPE.ATTRIBUTE);
		container = new HorizontalPanel();
		this.root = root;
		
		label = new HTML();
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

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorAttributeRefWidget(root);
	}

}
