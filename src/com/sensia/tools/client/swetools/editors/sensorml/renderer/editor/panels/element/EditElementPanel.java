package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element;

import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.ElementPanel;

public class EditElementPanel extends ElementPanel{

	protected Panel innerContent;
	
	public EditElementPanel(RNGElement element,final IRefreshHandler refreshHandler) {
		super(element,refreshHandler);
	}

	/*
	 * NOT WORKING YET
	 */
	/*public EditElementPanel(final RNGElement element, final IRefreshHandler refreshHandler) {
		super(element,refreshHandler);
		
		final Label label = new Label("");
		label.addStyleName("rng-advanced-button");
		
		label.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// create a new Renderer
				final AdvancedRendererSML renderer = new AdvancedRendererSML();
				final Panel rootPanel = new FlowPanel();
				
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
				
				CloseDialog dialogBox = Utils.displayDialogBox(rootPanel, "Edit "+element.getName());
				dialogBox.addSaveHandler(new ClickHandler(){
					@Override
					public void onClick(ClickEvent event) {
						if(refreshHandler != null) {
							refreshHandler.refresh();
						}
					}
				});
			}
		});

		String panelName = Utils.toNiceLabel(element.getName());
		
		SMLHorizontalPanel hPanel = new SMLHorizontalPanel();
		hPanel.add(new HTML(panelName));
		hPanel.add(label);
		
		innerContent = new FlowPanel();
		
		container.add(hPanel);
		container.add(innerContent);
		
		label.addStyleName("rng-advanced-button-section");
		
		container.addStyleName("section-panel");
	}*/
	
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(innerContent != null) {
			innerContent.add(element.getPanel());
		} else {
			container.add(element.getPanel());
		}
	}
}
