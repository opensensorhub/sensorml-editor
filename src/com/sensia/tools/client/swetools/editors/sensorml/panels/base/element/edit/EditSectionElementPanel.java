package com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.edit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.DisclosureElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditSectionElementPanel extends DisclosureElementPanel{

	public EditSectionElementPanel(final RNGElement tag, final IRefreshHandler refreshHander) {
		super(tag);
		
		final Label label = new Label("");
		label.addStyleName("rng-advanced-button");
		
		label.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// create a new Renderer
				final AdvancedRendererSML renderer = new AdvancedRendererSML();
				final Panel rootPanel = new VerticalPanel();
				
				renderer.setRefreshHandler(new IRefreshHandler() {
					
					@Override
					public void refresh() {
						renderer.reset();
						rootPanel.clear();
						renderer.visitChildren(tag.getChildren());
						rootPanel.add(renderer.getRoot().getPanel());
						
						if(refreshHander != null) {
							refreshHander.refresh();
						}
					}
				});

				renderer.visitChildren(tag.getChildren());
				rootPanel.add(renderer.getRoot().getPanel());
				
				DialogBox dialogBox = Utils.createEditDialogBox(rootPanel, "Edit "+tag.getName(), new IButtonCallback(){

					@Override
					public void onClick() {
						if(refreshHander != null) {
							refreshHander.refresh();
						}
					}
					
				});
			}
		});

		/*container.clear();
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(sectionPanel);
		hPanel.add(label);
		container.add(hPanel);*/
		Widget currentHeader = sectionPanel.getHeader();
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(currentHeader);
		hPanel.add(label);
		
		sectionPanel.setHeader(hPanel);
		
		label.addStyleName("rng-advanced-button-section");
		
		sectionPanel.addCloseHandler(new CloseHandler<DisclosurePanel>() {
			
			@Override
			public void onClose(CloseEvent<DisclosurePanel> event) {
				label.removeStyleName("rng-advanced-button-section");
			}
		});
		
		sectionPanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {

			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				label.addStyleName("rng-advanced-button-section");
				
			}
		});
		container.addStyleName("section-panel");
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		//innerContent.add(element.getPanel());
		if(element.getTag() instanceof RNGData<?>) {
			Label label = new Label(Utils.toNiceLabel(getName()));
			HorizontalPanel hPanel = new HorizontalPanel();
			hPanel.add(label);
			hPanel.add(element.getPanel());
		} else {
			super.addInnerElement(element);
		}
	}
}