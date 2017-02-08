/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2017 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.element;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.DisclosureElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;


public class AdvancedElementPanel extends DisclosureElementPanel
{

    public AdvancedElementPanel(final RNGElement tag, final IRefreshHandler refreshHandler)
    {
        this(tag, Utils.toNiceLabel(tag.getName()), refreshHandler);
    }
    
    
    public AdvancedElementPanel(final RNGElement tag, final String label, final IRefreshHandler refreshHandler)
    {
        super(tag,refreshHandler);
        
        // need to wrap old header because it's a table and it doesn't style well
        Widget currentHeader = sectionPanel.getHeader();
        SimplePanel oldHeader = new SimplePanel();
        oldHeader.add(currentHeader);
        
        SMLHorizontalPanel hPanel = new SMLHorizontalPanel();
        //hPanel.addNoSpacing(oldHeader);
        hPanel.add(oldHeader);
        hPanel.add(new Label(Utils.toNiceLabel(label)));        
        sectionPanel.setHeader(hPanel);
        
        // save and restore collapsed state
        sectionPanel.setOpen(!tag.isCollapsed());
        sectionPanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {
            public void onOpen(OpenEvent<DisclosurePanel> event)
            {
                tag.setCollapsed(false);                
            }
        });
        sectionPanel.addCloseHandler(new CloseHandler<DisclosurePanel>() {
            public void onClose(CloseEvent<DisclosurePanel> event)
            {
                tag.setCollapsed(true);                
            }
        });
    }

}
