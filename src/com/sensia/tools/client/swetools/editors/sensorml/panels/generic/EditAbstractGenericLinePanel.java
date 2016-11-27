package com.sensia.tools.client.swetools.editors.sensorml.panels.generic;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;

public abstract class EditAbstractGenericLinePanel<T extends RNGTag> extends AbstractPanel<T>{

	protected Panel labelPanel;
	protected Panel defPanel;
	
	protected Panel afterDotsPanel;
	protected Panel dotsPanel;
	
	/** The Constant NORMALIZE_DOT_SEPARATOR_SIZE. */
	private static final int NORMALIZE_DOT_SEPARATOR_SIZE = 70;
	
	protected  EditAbstractGenericLinePanel(T tag) {
		super(tag);
		
		labelPanel = new SimplePanel();
		defPanel = new SimplePanel();
		
		HorizontalPanel beforeDotsPanel = new HorizontalPanel();
		afterDotsPanel = new HorizontalPanel();
		dotsPanel = new SimplePanel();
		
		beforeDotsPanel.add(labelPanel);
		beforeDotsPanel.add(defPanel);
		dotsPanel.add(new HTML(getDotsLine()));
		
		// add styles
		beforeDotsPanel.addStyleName("line-generic-panel-before");
		labelPanel.addStyleName("line-generic-label-panel-before");
		defPanel.addStyleName("line-generic-definition-panel-before-edit");
		afterDotsPanel.addStyleName("horizontal-panel");
		dotsPanel.addStyleName("line-generic-dots-edit");
		container.addStyleName("line-generic-edit");
		
		Panel line = new HorizontalPanel();
		
		line.add(beforeDotsPanel);
		line.add(dotsPanel);
		line.add(afterDotsPanel);
		
		container.add(line);
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
}
