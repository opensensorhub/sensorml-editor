package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng;

import com.google.gwt.xml.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.sensia.relaxNG.RNGInvalidContent;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagList;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

public class RNGErrorPanel extends AbstractPanel<RNGInvalidContent>{

	public RNGErrorPanel(final RNGInvalidContent tag,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		
		container = new SMLHorizontalPanel();
		container.addStyleName("rng-error-panel");
		
		HTML removeButton = new HTML();
		removeButton.addStyleName("remove-button");
        container.add(removeButton);
            
		container.add(new Label("Invalid Content:"));
		Element badElt = (Element)tag.getInvalidContent();
		container.add(new Label(badElt.getNodeName()));
		//container.add(new Label(", Expected " + tag.getPattern().toString()));
		
		removeButton.addClickHandler(new ClickHandler() {          
            @Override
            public void onClick(ClickEvent event) {
                ((RNGTagList)tag.getParent()).getChildren().remove(tag);
                if (refreshHandler != null)
                    refreshHandler.refresh();
            }
        });
	}
	
	@Override
	public String getName() {
		return "error";
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
	}
	
	protected AbstractPanel<RNGInvalidContent> newInstance() {
		return null;
	}
}
