package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class AdvancedAttributePanel extends AbstractPanel<RNGAttribute> {

	public AdvancedAttributePanel(RNGAttribute tag,final IRefreshHandler refreshHandler) {
		this(tag,Utils.findLabel(tag),refreshHandler);
	}
	
	public AdvancedAttributePanel(RNGAttribute tag,String label,final IRefreshHandler refreshHandler) {
        super(tag);
        container = new SMLHorizontalPanel();
        container.add(new HTML(Utils.toNiceLabel(label)+":"));
        container.addStyleName("attribute-panel-advanced");
    }

	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		container.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
