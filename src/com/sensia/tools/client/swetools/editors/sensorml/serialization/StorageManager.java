package com.sensia.tools.client.swetools.editors.sensorml.serialization;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.SerializationException;
import com.seanchenxi.gwt.storage.client.StorageExt;
import com.seanchenxi.gwt.storage.client.StorageKey;
import com.sensia.relaxNG.RNGTag;

import java.util.*;

public class StorageManager {
    public static final String STORAGE_VERSION="1";

    public static final String STORAGE_PREFIX="smleditorstorage"+"$"+STORAGE_VERSION;

    StorageExt localStorage = StorageExt.getLocalStorage();

    private static final MyStorageKeyProvider KEY_PROVIDER = GWT.create(MyStorageKeyProvider.class);

    public StorageManager() {
        /*localStorage.clear();*/
    }

    public void writeData(StorageItem item) {

        if(!localStorage.containsKey(KEY_PROVIDER.valuesMapKey())) {
            try {
                localStorage.put(KEY_PROVIDER.valuesMapKey(),new HashMap<String,StorageItem>());
            } catch (SerializationException e) {
                GWT.log(e.getMessage()+"");
                e.printStackTrace();
            }
        }

        try {
            HashMap<String,StorageItem> map = localStorage.get(KEY_PROVIDER.valuesMapKey());
            map.put(item.getId(),item);

            localStorage.put(KEY_PROVIDER.valuesMapKey(),map);
        } catch (SerializationException e) {
            GWT.log(e.getMessage()+"");
            e.printStackTrace();
        }
    }

    public List<StorageItem> getData() {
        List<StorageItem> items = new ArrayList<>();

        if(localStorage != null && localStorage.containsKey(KEY_PROVIDER.valuesMapKey())) {

            try {
                HashMap<String,StorageItem> map = localStorage.get(KEY_PROVIDER.valuesMapKey());
                items = new ArrayList<>(map.values());

            } catch (SerializationException e) {
                GWT.log(e.getMessage());
                e.printStackTrace();
            }

            Collections.sort(items, new java.util.Comparator<StorageItem>() {
                @Override
                public int compare(StorageItem s1, StorageItem s2) {
                   return s1.getDate().compareTo(s2.getDate());
                }
            });
        }
        return items;
    }

    public void remove(StorageItem item) {
        if(localStorage != null && localStorage.containsKey(KEY_PROVIDER.valuesMapKey())) {
            try {
                HashMap<String,StorageItem> map = localStorage.get(KEY_PROVIDER.valuesMapKey());
                map.remove(item.getId());
                localStorage.put(KEY_PROVIDER.valuesMapKey(),map);
            } catch (SerializationException e) {
                GWT.log(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
