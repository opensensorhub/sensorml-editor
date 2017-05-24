/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2017 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.utils;

import com.google.gwt.event.dom.client.ClickHandler;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;


public class LoginWindow extends Window
{
    private VLayout mainPanel;
    private DynamicForm form;
    private TextItem login;
    private PasswordItem pwd;
    private ClickHandler confirmHandler;
    
    
    public LoginWindow(String title) {
        setTitle(title);
        setTitle(title);  
        setShowResizer(false);
        setAutoCenter(true);
        setWidth(250);
        setHeight(150);
        setShowMinimizeButton(false);
        setShowMaximizeButton(false);
        setIsModal(true);
        
        mainPanel = new VLayout();
        mainPanel.setPadding(10);
        mainPanel.setMembersMargin(10);
        mainPanel.setWidth100();
        mainPanel.setHeight100();
        mainPanel.setOverflow(Overflow.AUTO);
        mainPanel.setAlign(Alignment.CENTER);
                
        initForm();
        initButtons();
        addItem(mainPanel);
        
        adjustForContent(true);
    }
    
    
    private void initForm() {
                
        login = new TextItem();
        login.setTitle("User");
        login.setShowFocused(true);
        
        pwd = new PasswordItem();
        pwd.setTitle("Password");
        
        form = new DynamicForm();
        form.setFields(login, pwd);
        
        mainPanel.addMember(form);
    }
    
    
    private void initButtons() {
        
        final com.smartgwt.client.widgets.Button okButton = new com.smartgwt.client.widgets.Button("OK");
        okButton.setHeight(30);
        okButton.setWidth(40);
        okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {            
            @Override
            public void onClick(ClickEvent event) {
                if(confirmHandler != null) {
                    confirmHandler.onClick(null);
                    hide();
                    markForDestroy();
                }
            }
        });
        
        mainPanel.addMember(okButton);
    }
    
    @Override
    public void show() {
        super.show();
        form.focus();
    }
    
    public void setConfirmHandler(ClickHandler handler) {
        this.confirmHandler = handler;
    }
    
    public String getUser() {
        return (String)login.getValue();
    }
    
    public String getPassword() {
        return (String)pwd.getValue();
    }
}
