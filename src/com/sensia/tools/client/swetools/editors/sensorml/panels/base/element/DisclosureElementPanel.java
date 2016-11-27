package com.sensia.tools.client.swetools.editors.sensorml.panels.base.element;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.ViewAbstractGenericLinePanel;

/**
 * Creates a disclosure panel if name or label is provided
 * name: attribute
 * definition: attribute
 * label: element
 * @author mathieu.dhainaut@gmail.com
 *
 */
public class DisclosureElementPanel extends AbstractPanel<RNGElement>{

	private DisclosurePanel listPanel;
	private Panel contentPanel;
	private HorizontalPanel headerPanel;
	
	private Panel label=null; // can be name or/label. prior to label
	private Panel attributes=null;
	
	public DisclosureElementPanel(RNGElement element) {
		super(element);
		//container.addStyleName("vertical-generic-disclosure-panel subsection-panel");
		label = new SimplePanel();
		attributes = new HorizontalPanel();
		
		container.addStyleName("disclosure-generic-simple");
	}
	
	protected void initDisclosure(String defaultHeader) {
		container.clear();
		label.clear();
		attributes.clear();
		
		if(defaultHeader != null) {
			label.add(new HTML(defaultHeader));
		}
		
		container = new VerticalPanel();
		container.addStyleName("disclosure-generic disclosure-noborder");
		
		//listPanel = new DisclosurePanel(Utils.toNiceLabel(getTag().getName()));

		listPanel = new DisclosurePanel("");
		//listPanel.addStyleName("disclosure-panel");
		listPanel.setOpen(true);
		listPanel.setAnimationEnabled(true);
		contentPanel = new VerticalPanel();
		//contentPanel.addStyleName("disclosure-generic-content-panel");
		
		listPanel.setContent(contentPanel);
		container.add(listPanel);
		//container.addStyleName("subsection-panel");
		
		
		headerPanel = new HorizontalPanel();
		Widget currentHeader = listPanel.getHeader();
		headerPanel.add(currentHeader);
		
		// add label/name
		headerPanel.add(label);
		
		// add definition
		headerPanel.add(attributes);
		listPanel.setHeader(headerPanel);
		
		reset();
	}

	
	protected void initNormalPanel() {
		container.clear();
		listPanel = null;
		reset();
	}
	
	private void reset() {
		// add current added panel
		for(IPanel child : children) {
			this.addInnerElement(child);
		}
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGElement && element.getName().equals("label")){
			label.clear();
			label.add(element.getPanel());
			element.getPanel().removeStyleName("disclosure-generic-simple");
			updateHeader();
		}else if(element.getTag() instanceof RNGAttribute && element.getName().equals("name")){
			if(!label.getElement().hasChildNodes()){
				label.add(element.getPanel());
				element.getPanel().removeStyleName("disclosure-generic-simple");
				updateHeader();
			}
		} else if(element.getTag() instanceof RNGAttribute && (element.getName().equals("definition") ||
				element.getName().equals("arcrole") ||
				element.getName().equals("role"))){
			attributes.add(element.getPanel());
			//updateHeader();
		} else {
			addContent(element.getPanel());
		}
		checkLine(element);
	}

	private void checkLine(IPanel<? extends RNGTag> element){
		if(element instanceof ViewAbstractGenericLinePanel){
			ViewAbstractGenericLinePanel cast = (ViewAbstractGenericLinePanel) element;
			if(!cast.isLabeled() && label != null) {
				cast.setLabel(label);
				initNormalPanel();
			}
		}
	}
	protected void addContent(Widget panel) {
		if(listPanel != null) {
			contentPanel.add(panel);
		} else {
			container.add(panel);
		}
	}
	
	public void setHeaderStr(String headerStr) {
		if(listPanel == null) {
			initDisclosure(headerStr);
		} 
	}
	
	public void updateHeader() {
		if(listPanel == null) {
			initDisclosure(null);
		} 

		//headerPanel.add(panel);
	}
	
	protected void clear() {
		if(listPanel != null) {
			contentPanel.clear();
		} else {
			container.clear();
		}
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
