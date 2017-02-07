package com.sensia.tools.client.swetools.editors.sensorml.panels.line;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel.SPACING;

public abstract class AbstractGenericLinePanel<T extends RNGTag> extends AbstractPanel<T>{

	protected Panel beforeDotsPanel;
	protected Panel labelPanel;
	protected Panel defPanel;
	
	protected Panel afterDotsPanel;
	protected Panel dotsPanel;
	
	/** The Constant NORMALIZE_DOT_SEPARATOR_SIZE. */
	private static final int NORMALIZE_DOT_SEPARATOR_SIZE = 50;
	
	protected  AbstractGenericLinePanel(T tag) {
		super(tag);
		
		labelPanel = new SimplePanel();
		defPanel = new SimplePanel();
		
		beforeDotsPanel = new SMLHorizontalPanel(SPACING.RIGHT);
		afterDotsPanel = new SMLHorizontalPanel(SPACING.RIGHT);
		dotsPanel = new SimplePanel();
		
		SMLHorizontalPanel labelAndDefPanel = new SMLHorizontalPanel(SPACING.RIGHT);
		labelAndDefPanel.add(labelPanel);
		labelAndDefPanel.add(defPanel);
		SimplePanel labelAndDefPanelForCorrectDots = new SimplePanel();
		labelAndDefPanelForCorrectDots.add(labelAndDefPanel);

		beforeDotsPanel.add(labelAndDefPanelForCorrectDots);
		dotsPanel.add(new HTML(getDotsLine()));
		
		container = new SMLHorizontalPanel(SPACING.RIGHT);		
		container.add(beforeDotsPanel);
		container.add(dotsPanel);
		container.add(afterDotsPanel);
		
		// add styles
		beforeDotsPanel.addStyleName("panel-abstract-generic-line before");
		labelPanel.addStyleName("panel-abstract-generic-line label");
		defPanel.addStyleName("panel-abstract-generic-line def");
		afterDotsPanel.addStyleName("panel-abstract-generic-line after");
		container.addStyleName("panel-abstract-generic-line");
	}
	
	@Override
	public String getName() {
		return getTag().toString();
	}


	@Override
	protected AbstractPanel<T> newInstance() {
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
