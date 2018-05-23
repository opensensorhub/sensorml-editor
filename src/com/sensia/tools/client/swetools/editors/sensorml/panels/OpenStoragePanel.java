package com.sensia.tools.client.swetools.editors.sensorml.panels;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ICallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.GenericTable;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.Property;
import com.sensia.tools.client.swetools.editors.sensorml.serialization.StorageItem;
import com.sensia.tools.client.swetools.editors.sensorml.serialization.StorageManager;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

import java.util.Arrays;
import java.util.List;

public class OpenStoragePanel extends SMLVerticalPanel {

    private GenericTable storageTable;
    private StorageManager storage;

    private List<StorageItem> dataFromStorage;

    public OpenStoragePanel(StorageManager storage) {
        this.storage = storage;
        storageTable = new StorageItemTable();
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
                data[i][1] = item.name;
                data[i][2] = item.getDate();
            }
        }
        Panel panelTable = storageTable.createTable();
        storageTable.populateTable(Arrays.asList("Id","Name","Last modified"), data);

        add(panelTable);
    }


    public StorageItem getSelectedItem() {
        String selectedValue = storageTable.getSelectedValue();
        StorageItem resultItem = null;
        if(dataFromStorage != null) {
            for(StorageItem item : dataFromStorage) {
                if(item.getId().equals(selectedValue)) {
                    resultItem = item;
                    break;
                }
            }
        }
        return resultItem;
    }

    public class StorageItemTable extends GenericTable {
        public Panel createTable() {
            if(table == null) {
                table  = new CellTable<Property>(10,tableRes);
                table.setStyleName("ontology-table-result");

                dataProvider.addDataDisplay(table);

                table.setSkipRowHoverCheck(true);
                table.setSkipRowHoverFloatElementCheck(true);
                table.setSkipRowHoverStyleUpdate(true);
                table.setVisibleRange(0, 100000);
                table.setWidth("100%", true);

                // Add a selection model to handle user selection.
                final SingleSelectionModel<Property> selectionModel = new SingleSelectionModel<Property>();
                table.setSelectionModel(selectionModel);
                selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
                    public void onSelectionChange(SelectionChangeEvent event) {
                        Property selected = selectionModel.getSelectedObject();
                        if (selected != null) {
                            selectedProperty = selected;
                        }
                    }
                });
            }

            SMLVerticalPanel vPanel = new SMLVerticalPanel();

            vPanel.add(table);

            return vPanel;
        }
    }

}
