package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.DisclosureElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.EditIconPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.Renderer;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.utils.CloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class EditSectionElementPanel extends DisclosureElementPanel{

	protected Panel labelPanel;
	protected Panel definitionPanel;
	protected Panel descriptionPanel;
	protected Panel advancedButtonPanel;
	
	protected boolean preventChangeDisclosure;
	
	public EditSectionElementPanel(final RNGElement tag, final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		
		labelPanel = new HorizontalPanel();
		definitionPanel = new HorizontalPanel();
		descriptionPanel = new HorizontalPanel();
		advancedButtonPanel = new SimplePanel();
		
		final Label advancedButton= buildAdvancedButton(new AdvancedRendererSML());
		advancedButtonPanel.add(advancedButton);
		
		Widget currentHeader = sectionPanel.getHeader();
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(currentHeader);
		hPanel.add(labelPanel);
		hPanel.add(definitionPanel);
		hPanel.add(descriptionPanel);
		hPanel.add(advancedButtonPanel);
		
		definitionPanel.setVisible(false);
		
		sectionPanel.setHeader(hPanel);
		advancedButton.addStyleName("rng-advanced-button-section");
		
		sectionPanel.addCloseHandler(new CloseHandler<DisclosurePanel>() {
			
			@Override
			public void onClose(CloseEvent<DisclosurePanel> event) {
				if(preventChangeDisclosure) {
					preventChangeDisclosure = false;
					sectionPanel.setOpen(true);
					sectionPanel.setAnimationEnabled(true);
				} else {
					advancedButton.removeStyleName("rng-advanced-button-section");
				}
			}
		});
		
		sectionPanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {

			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				if(preventChangeDisclosure) {
					preventChangeDisclosure = false;
					sectionPanel.setOpen(false);
					sectionPanel.setAnimationEnabled(true);
				} else {
					advancedButton.addStyleName("rng-advanced-button-section");
				}
				
			}
		});
		container.addStyleName("section-panel");
		
		labelPanel.add(new HTML(Utils.toNiceLabel(tag.getName())));
		
		labelPanel.addStyleName("edit-section-label-panel");
	}
	
	@Override
	protected void advancedButtonClickHandler(RNGElement element, Renderer renderer,final Panel rootPanel) {
		preventChangeDisclosure = true;
		sectionPanel.setAnimationEnabled(false);
		super.advancedButtonClickHandler(element,renderer,rootPanel);
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGData<?>) {
			//TODO:??
			Label label = new Label(Utils.toNiceLabel(getName()));
			HorizontalPanel hPanel = new HorizontalPanel();
			hPanel.add(label);
			hPanel.add(element.getPanel());
		} else if(element.getName().equals("label")){
			//labelPanel.clear();
			labelPanel.add(new HTML(SMLEditorConstants.HTML_SPACE+"("));
			labelPanel.add(element.getPanel());
			labelPanel.add(new HTML(")"));
			
			labelPanel.setVisible(true);
		} else if(element.getName().equals("definition") ||
				 element.getName().equals("role") ||
				 element.getName().equals("arcrole")){
			definitionPanel.add(element.getPanel());
			
			definitionPanel.setVisible(true);
		} else if(element.getName().equals("description")){
			RNGElement tag = (RNGElement) element.getTag();
			EditIconPanel<RNGElement> iconPanel = new EditIconPanel<RNGElement>(tag, 
					new Image(GWT.getModuleBaseURL()+"images/icon_question.png"), "description-icon");
			for(IPanel child : element.getElements()) {
				iconPanel.addElement(child);
			}
			descriptionPanel.add(iconPanel.getPanel());
		} else if(element.getName().equals("id")){
			// skip
		} else if(element.getName().equals("name")){
			labelPanel.clear();
			labelPanel.add(element.getPanel());
			labelPanel.setVisible(true);
		} else {
			super.addInnerElement(element);
		}
	}
}