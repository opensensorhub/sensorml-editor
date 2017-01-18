package com.sensia.tools.client.swetools.editors.sensorml.panels.line;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;

public abstract class AbstractGenericLinePanel<T extends RNGTag> extends AbstractPanel<T>{

	protected Panel beforeDotsPanel;
	protected Panel labelPanel;
	protected Panel defPanel;
	
	protected Panel afterDotsPanel;
	protected Panel dotsPanel;
	
	protected Panel line;
	
	/** The Constant NORMALIZE_DOT_SEPARATOR_SIZE. */
	private static final int NORMALIZE_DOT_SEPARATOR_SIZE = 50;
	
	protected  AbstractGenericLinePanel(T tag) {
		super(tag);
		
		labelPanel = new SimplePanel();
		defPanel = new SimplePanel();
		
		beforeDotsPanel = new HorizontalPanel();
		afterDotsPanel = new HorizontalPanel();
		dotsPanel = new SimplePanel();
		
		HorizontalPanel labelAndDefPanel = new HorizontalPanel();
		labelAndDefPanel.add(labelPanel);
		labelAndDefPanel.add(defPanel);
		SimplePanel labelAndDefPanelForCorrectDots = new SimplePanel();
		labelAndDefPanelForCorrectDots.add(labelAndDefPanel);

		beforeDotsPanel.add(labelAndDefPanelForCorrectDots);
		dotsPanel.add(new HTML(getDotsLine()));
		
		line = new HorizontalPanel();
		
		line.add(beforeDotsPanel);
		line.add(dotsPanel);
		line.add(afterDotsPanel);
		
		container.add(line);
		
		// add styles
		beforeDotsPanel.addStyleName("line-generic-panel-before");
		labelPanel.addStyleName("line-generic-label-panel-before");
		defPanel.addStyleName("line-generic-definition-panel-before");
		afterDotsPanel.addStyleName("horizontal-panel");
		container.addStyleName("line-generic");
	}
	
	@Override
	public String getName() {
		return getTag().toString();
	}


	@Override
	protected AbstractPanel<T> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected String getDotsLine() {
		String newValue = "";
		for(int i=0;i < NORMALIZE_DOT_SEPARATOR_SIZE;i++) {
			newValue += ".";
		}
		newValue +=SMLEditorConstants.HTML_SPACE+SMLEditorConstants.HTML_SPACE+SMLEditorConstants.HTML_SPACE+SMLEditorConstants.HTML_SPACE;
		return newValue;
	}
	
	public boolean isLabeled() {
		return labelPanel.getElement().hasChildNodes();
	}
	
	public void setLabel(Panel panel) {
		labelPanel.clear();
		labelPanel.add(panel);
	}
	
	public void setDefinition(Panel panel) {
		defPanel.clear();
		defPanel.add(panel);
	}
	
	public void appendToLine(Panel panel) {
		afterDotsPanel.add(panel);
	}
}
