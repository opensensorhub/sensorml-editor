package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel.SPACING;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

public class RNGChoicePanel extends AbstractPanel<RNGChoice>{

	private ListBox choices;
	private Panel patternContainer;
	private Panel selectHeader;
	
	private int currentSelectedIndex = -1;
	
	public RNGChoicePanel(final RNGChoice tag,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		patternContainer = new SMLVerticalPanel();
		
		patternContainer.addStyleName("rng-choice-pattern");
		
		selectHeader = new SMLHorizontalPanel(SPACING.RIGHT);
		//TODO: check that we take the parent tag for the choice box name
		String strLabel = Utils.findLabel(tag);
		HTML htmlLabel = new HTML(strLabel + ": ");
		selectHeader.add(htmlLabel);
		choices = new ListBox();
		
		//TODO: hide the label for now
		htmlLabel.setVisible(strLabel != null && !strLabel.trim().isEmpty());
		selectHeader.add(choices);
		container.add(selectHeader);
		container.add(patternContainer);
		
		choices.addItem("---");
		
		currentSelectedIndex = getTag().getSelectedIndex();
		
		// add children name into the choice and
		List<RNGTag> children = tag.getItems();
		if(children != null){
			for(RNGTag child : children) {
				String label = Utils.findLabel(child);
				choices.addItem(label);
			}
		}
		
		if(getTag().getSelectedIndex() != -1) {
			choices.setSelectedIndex(getTag().getSelectedIndex()+1);
		}
		
		choices.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				if(currentSelectedIndex != choices.getSelectedIndex()+1){
					//TODO: clear previous input
				}
				if(choices.getSelectedIndex() == 0) {
					RNGChoicePanel.this.getTag().setSelectedIndex(-1);
				} else {
					RNGChoicePanel.this.getTag().setSelectedIndex(choices.getSelectedIndex()-1);
				}
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
			}
		});
		patternContainer.setVisible(false);
	}
	
	@Override
	public String getName() {
		if(getTag().getSelectedPattern() != null) {
			return getTag().getSelectedPattern().toString();	
		} else {
			return "RNGChoice";
		}
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGAttribute) {
			// gets the child = RNGData/RNGValue panel
			selectHeader.add(new HTML(SMLEditorConstants.HTML_SPACE+":"+SMLEditorConstants.HTML_SPACE));
			selectHeader.add(element.getElements().get(0).getPanel());
		} else {
			patternContainer.setVisible(true);
			patternContainer.add(element.getPanel());
		}
	}

	@Override
	protected AbstractPanel<RNGChoice> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
