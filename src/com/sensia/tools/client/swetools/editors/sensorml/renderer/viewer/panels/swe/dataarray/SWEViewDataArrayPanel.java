package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.dataarray;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.dataarray.SWEEditDataArrayLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.dataarray.SWEEditDataArrayPanel;

/**
 * This class is in charge of building the data array line + icons.
 * Several elements have to be found before creating graphic + table structures.
 * These elements are:
 * - At least two fields containing a quantity
 * - values (from swe:values)
 * - encoding (from swe:TextEncoding) providing  token, block and decimal separators 
 * @author mathieu.dhainaut@gmail.com
 *
 */
public class SWEViewDataArrayPanel extends SWEEditDataArrayPanel{

	public SWEViewDataArrayPanel(RNGElement tag) {
		super(tag,null);
	}
	
	protected SWEEditDataArrayLinePanel getLinePanel(RNGElement tag) {
		return new SWEViewDataArrayLinePanel(tag);
	}
}
