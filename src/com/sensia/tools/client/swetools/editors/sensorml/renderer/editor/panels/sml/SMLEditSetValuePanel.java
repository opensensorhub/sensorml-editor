package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ICallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.NameRefResolver;

public class SMLEditSetValuePanel extends AbstractGenericLinePanel<RNGElement>{

	
	protected HTML value;
	protected HTML label;
	protected HTML uom;
	
	private NameRefResolver resolver;
	
	public SMLEditSetValuePanel(RNGElement tag, NameRefResolver resolver) {
		super(tag);
		value = new HTML("");
		label = new HTML("");
		uom = new HTML("");
		
		labelPanel.add(label);
		afterDotsPanel.add(value);
		afterDotsPanel.add(uom);
		
		this.resolver = resolver;
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("ref")) {
			RNGAttribute att = (RNGAttribute) element.getTag();
			List<String> pathList = Arrays.asList(att.getChildValueText().split("/"));
			
			//resolve the name
			resolver.resolvePath(this, pathList, new ICallback<String>() {
				
				@Override
				public void callback(String...result) {
					//one the path resolve from remote or current file, set the corresponding values
					label.setText(result[0]);
					uom.setText(result[1]);
					
				}
			});
		} else if(element.getTag() instanceof RNGValue){
			value.setText(((RNGValue)element.getTag()).getText());
		} else if(element.getTag() instanceof RNGData){
			value.setText(((RNGData)element.getTag()).getStringValue());
		} 
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
