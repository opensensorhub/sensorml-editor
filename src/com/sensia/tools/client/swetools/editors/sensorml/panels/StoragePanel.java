package com.sensia.tools.client.swetools.editors.sensorml.panels;

import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.GenericTable;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

import java.util.Arrays;
import java.util.List;

public class StoragePanel extends SMLVerticalPanel {

    private GenericTable storageTable;
    private Storage storage;

    private List<StorageItem> dataFromStorage;

    public StoragePanel(Storage storage) {
        this.storage = storage;
        storageTable = new GenericTable();
        storageTable.setEditable(false);

        populateFromStorage();
    }

    private void populateFromStorage() {
      Object[][] data = new Object[0][0];
        if (storage != null){
            dataFromStorage = storage.getData();
            data = new Object[dataFromStorage.size()][2];

            for(int i=0;i < dataFromStorage.size();i++) {
                StorageItem item = dataFromStorage.get(i);
                data[i][0] = item.getId();
                data[i][1] = item.getDate();
                data[i][2] = item.content;
            }
        }
        Panel panelTable = storageTable.createTable();
        storageTable.populateTable(Arrays.asList("Id","Creation Date","Content"), data);

        add(panelTable);
    }


    public String getSelectedId() {
        String selectedValue = storageTable.getSelectedValue();
        String id = null;
        if(dataFromStorage != null) {
            for(StorageItem item : dataFromStorage) {
                if(item.getId().equals(selectedValue)) {
                    id = item.getId()+"$"+item.getDate();
                    break;
                }
            }
        }
        return id;
    }

    public RNGTag getSelectedTag() {
        String selectedValue = storageTable.getSelectedValue();
        RNGTag tag = null;
        if(dataFromStorage != null) {
            for(StorageItem item : dataFromStorage) {
                if(item.getId().equals(selectedValue)) {
                    tag = item.content;
                    break;
                }
            }
        }
        return tag;
    }

}
