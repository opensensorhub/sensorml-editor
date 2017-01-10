package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.line.EditValueGenericLinePanel;

/**
 * codeSpace
 * constraint [0..1]
 * value [0..1]
 * @author mathieu.dhainaut@gmail.com
 *
 */
public class SWEEditCategoryPanel extends EditValueGenericLinePanel{

	protected Panel codeSpace;
	
	public SWEEditCategoryPanel(RNGElement element,IRefreshHandler refreshHandler) {
		super(element,refreshHandler);
		
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
