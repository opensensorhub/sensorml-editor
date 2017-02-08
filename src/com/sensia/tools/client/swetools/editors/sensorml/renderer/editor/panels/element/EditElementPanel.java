package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditElementPanel extends AbstractPanel<RNGElement>{

	protected Panel innerContent;
	
	public EditElementPanel(RNGElement element,final IRefreshHandler refreshHandler) {
		this(element,Utils.toNiceLabel(element.getName()),refreshHandler);
	}

	public EditElementPanel(RNGElement element,String label,final IRefreshHandler refreshHandler) {
        super(element,refreshHandler);
        
        container = new SMLVerticalPanel();
        container.addStyleName("element-panel");
        
        // header
        if (label != null && !label.isEmpty()) {
            Panel header = new SMLHorizontalPanel();
            HTML html = new HTML(Utils.toNiceLabel(label));
            html.addStyleName("label");
            header.add(html);
            header.add(buildAdvancedButton(new AdvancedRendererSML()));
            container.add(header);
        }
        
        // content
        innerContent = new SMLVerticalPanel(); 
        innerContent.addStyleName("subsection-inner");        
        container.add(innerContent);
    }
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		innerContent.add(element.getPanel());
	}

    @Override
    public String getName() {
        return getTag().getName();
    }

    @Override
    protected AbstractPanel<RNGElement> newInstance() {
        return null;
    }
}
