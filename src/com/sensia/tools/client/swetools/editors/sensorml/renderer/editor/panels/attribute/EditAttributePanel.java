package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditAttributePanel extends AbstractPanel<RNGAttribute> {

    public EditAttributePanel(RNGAttribute attrib) {
        this(attrib, true);
    }
    
    public EditAttributePanel(RNGAttribute attrib, boolean useAttributeNameAsLabel) {
        this(attrib, useAttributeNameAsLabel ? Utils.findLabel(attrib) : null);		
	}
    
    public EditAttributePanel(RNGAttribute attrib, String label) {
        super(attrib);
        container = new SMLHorizontalPanel();
        if (label != null && !label.isEmpty())
            container.add(new HTML(Utils.toNiceLabel(label)+":"));
        container.addStyleName("attribute-panel");
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
		return null;
	}

}
