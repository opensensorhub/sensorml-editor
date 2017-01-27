package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

public class GMLEditEnvelopePanel extends EditSimpleElementPanel{

	private Panel srsPanel;
	private Panel lowerCoordinatesPanel;
	private Panel upperCoordinatesPanel;
	
	public GMLEditEnvelopePanel(RNGElement element, final IRefreshHandler refreshHandler) {
		super(element);
		container = new SMLHorizontalPanel();
		srsPanel = new SMLHorizontalPanel();
		lowerCoordinatesPanel = new SMLHorizontalPanel();
		upperCoordinatesPanel = new SMLHorizontalPanel();
		
		container.addStyleName("Envelope-panel");
		
		container.add(new HTML("Envelope"));
		container.add(srsPanel);
		container.add(new HTML(":"));
		container.add(lowerCoordinatesPanel);
		container.add(upperCoordinatesPanel);
		
		srsPanel.setVisible(false);
		lowerCoordinatesPanel.setVisible(false);
		upperCoordinatesPanel.setVisible(false);
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "Point";
	}

	@Override
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("srsName")) {
			srsPanel.add(new HTML("("));
			srsPanel.add(element.getPanel());
			srsPanel.add(new HTML(")"));
			srsPanel.setVisible(true);
		} else if(element.getName().equals("lowerCorner")) {
			lowerCoordinatesPanel.add(element.getPanel());
			lowerCoordinatesPanel.setVisible(true);
		} else if(element.getName().equals("upperCorner")) {
			upperCoordinatesPanel.add(element.getPanel());
			upperCoordinatesPanel.setVisible(true);
		}
	}
}