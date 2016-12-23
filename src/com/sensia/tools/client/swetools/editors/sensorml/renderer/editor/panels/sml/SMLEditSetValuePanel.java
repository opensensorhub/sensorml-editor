package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ICallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.line.EditGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.NameRefResolver;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class SMLEditSetValuePanel extends EditGenericLinePanel<RNGElement>{

	
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
					label.setText(Utils.toNiceLabel(result[0]));
					uom.setText(Utils.getUOMSymbol(result[1]));
					
				}
			});
		} else if(element.getTag() instanceof RNGValue){
			String text = ((RNGValue)element.getTag()).getText();
			value.setText(Utils.toNiceLabel(text));
		} else if(element.getTag() instanceof RNGData){
			String text = ((RNGData)element.getTag()).getStringValue();
			value.setText(Utils.toNiceLabel(text));
		} 
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
