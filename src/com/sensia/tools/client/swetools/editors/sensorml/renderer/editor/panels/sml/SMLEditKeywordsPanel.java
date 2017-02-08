package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

public class SMLEditKeywordsPanel extends AbstractPanel<RNGElement>{

    SMLHorizontalPanel keywordsPanel;
        
    public SMLEditKeywordsPanel(RNGElement element,final IRefreshHandler refreshHandler) {
        super(element,refreshHandler);     
        
        container = new SMLHorizontalPanel();
        container.addStyleName("keywords-panel");
        
        HTML html = new HTML("Keywords:");
        html.addStyleName("label");
        container.add(html);
        
        keywordsPanel = new SMLHorizontalPanel();
        container.add(keywordsPanel);
        
        container.add(buildAdvancedButton(new AdvancedRendererSML()));
	}

	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if ("value".equals(element.getName()))
	        keywordsPanel.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
	
}
