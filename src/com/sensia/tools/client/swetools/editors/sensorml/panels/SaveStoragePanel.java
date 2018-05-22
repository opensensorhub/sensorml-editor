package com.sensia.tools.client.swetools.editors.sensorml.panels;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

public class SaveStoragePanel extends SMLVerticalPanel {

    private TextBox customNameBox;
    private TextBox idBox;
    private TextBox dateBox;

    public SaveStoragePanel(String key) {

        String[] split = key.split("\\$");
        addIdField(split[0]);
        addDateField(split[1]);
        addCustomName(split[2]);
        setStyleName("save-storage-panel");
    }

    public void addIdField(String id) {
        HTML idLabel = new HTML("Id");
        idBox = new TextBox();
        idBox.setValue(id);
        idBox.setEnabled(false);

        SMLHorizontalPanel hPanel = new SMLHorizontalPanel();
        hPanel.add(idLabel);
        hPanel.add(idBox);

        add(hPanel);
    }

    public void addDateField(String date) {
        HTML dateLabel = new HTML("Creation date");
        dateBox = new TextBox();
        dateBox.setValue(date);
        dateBox.setEnabled(false);

        SMLHorizontalPanel hPanel = new SMLHorizontalPanel();
        hPanel.add(dateLabel);
        hPanel.add(dateBox);

        add(hPanel);
    }

    public void addCustomName(String name) {
        HTML customNameLabelLabel = new HTML("Custom Name");
        customNameBox = new TextBox();
        customNameBox.setValue(name);
        customNameBox.setEnabled(true);

        SMLHorizontalPanel hPanel = new SMLHorizontalPanel();
        hPanel.add(customNameLabelLabel);
        hPanel.add(customNameBox);

        add(hPanel);
    }

    public String getId() {
        return idBox.getValue();
    }

    public String getDate() {
        return dateBox.getValue();
    }

    public String getCustomName(){
        return customNameBox.getValue();
    }
}
