package com.sensia.tools.client.swetools.editors.sensorml.renderer.root;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGOptionalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class RNGOptionalPanelNoContent extends RNGOptionalPanel{

	public RNGOptionalPanelNoContent(final RNGOptional tag,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		
		if(tag.isSelected()) {
            String label = Utils.findLabel(tag);
            HTML htmlLabel = new HTML(Utils.toNiceLabel(label));
            container.add(htmlLabel);
        }		
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {	    
	}

}
