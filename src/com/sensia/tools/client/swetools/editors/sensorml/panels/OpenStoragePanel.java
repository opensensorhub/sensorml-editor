package com.sensia.tools.client.swetools.editors.sensorml.panels;

import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ICallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.GenericTable;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

import java.util.Arrays;
import java.util.List;

public class OpenStoragePanel extends SMLVerticalPanel {

    private GenericTable storageTable;
    private Storage storage;

    private List<StorageItem> dataFromStorage;

    public OpenStoragePanel(Storage storage) {
        this.storage = storage;
        storageTable = new GenericTable();
        storageTable.setEditable(true);

        populateFromStorage();

        storageTable.onRemoveHandler(new ICallback<Integer>() {

            @Override
            public void callback(Integer[] result) {
                for(Integer r:result) {
                    storage.remove(dataFromStorage.get(r));
                    dataFromStorage.remove(r);
                }
            }
        });
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
                data[i][2] = item.name;
            }
        }
        Panel panelTable = storageTable.createTable();
        storageTable.populateTable(Arrays.asList("Id","Creation Date","Name"), data);

        add(panelTable);
    }


    public String getSelectedId() {
        String selectedValue = storageTable.getSelectedValue();
        String id = null;
        if(dataFromStorage != null) {
            for(StorageItem item : dataFromStorage) {
                if(item.getId().equals(selectedValue)) {
                    id = item.getId()+"$"+item.getDate()+"$"+item.name;
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
