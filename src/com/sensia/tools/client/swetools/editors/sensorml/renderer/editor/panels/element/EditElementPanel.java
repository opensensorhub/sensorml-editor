package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
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
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.ElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditElementPanel extends ElementPanel{

	protected Panel innerContent;
	
	public EditElementPanel(RNGElement element) {
		super(element);
	}

	/*
	 * NOT WORKING YET
	 */
	public EditElementPanel(final RNGElement element, final IRefreshHandler refreshHandler) {
		super(element);
		
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
						renderer.visitChildren(element.getChildren());
						rootPanel.add(renderer.getRoot().getPanel());
						
						if(refreshHandler != null) {
							refreshHandler.refresh();
						}
					}
				});

				renderer.visitChildren(element.getChildren());
				rootPanel.add(renderer.getRoot().getPanel());
				
				DialogBox dialogBox = Utils.createEditDialogBox(rootPanel, "Edit "+element.getName(), new IButtonCallback(){

					@Override
					public void onClick() {
						if(refreshHandler != null) {
							refreshHandler.refresh();
						}
					}
					
				});
			}
		});

		String panelName = Utils.toNiceLabel(element.getName());
		
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(new HTML(panelName));
		hPanel.add(label);
		
		innerContent = new VerticalPanel();
		
		container.add(hPanel);
		container.add(innerContent);
		
		label.addStyleName("rng-advanced-button-section");
		
		container.addStyleName("section-panel");
	}
	
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(innerContent != null) {
			innerContent.add(element.getPanel());
		} else {
			container.add(element.getPanel());
		}
	}
}
