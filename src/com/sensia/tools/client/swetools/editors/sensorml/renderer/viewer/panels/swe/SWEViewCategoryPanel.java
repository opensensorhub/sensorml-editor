package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.line.EditValueGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.line.ViewValueGenericLinePanel;

/**
 * codeSpace
 * constraint [0..1]
 * value [0..1]
 * @author mathieu.dhainaut@gmail.com
 *
 */
public class SWEViewCategoryPanel extends ViewValueGenericLinePanel{

	protected Panel codeSpace;
	
	public SWEViewCategoryPanel(RNGElement element) {
		super(element);
		
		codeSpace = new SimplePanel();
		
		afterDotsPanel.add(codeSpace);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("codeSpace")){
			codeSpace.add(element.getPanel());
		} else {
			super.addInnerElement(element);
		}
	}
}
