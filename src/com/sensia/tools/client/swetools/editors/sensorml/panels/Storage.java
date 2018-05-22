package com.sensia.tools.client.swetools.editors.sensorml.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.SerializationException;
import com.seanchenxi.gwt.storage.client.StorageExt;
import com.seanchenxi.gwt.storage.client.StorageKey;
import com.sensia.relaxNG.RNGTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Storage {
    public static final String STORAGE_VERSION="1";

    public static final String STORAGE_PREFIX="smleditorstorage"+"$"+STORAGE_VERSION;

    StorageExt localStorage = StorageExt.getLocalStorage();

    public Storage() {
        localStorage.clear();
    }

    public void writeData(String currentStorageId, RNGTag object) {
        StorageKey<RNGTag> key = new StorageKey<>(Storage.STORAGE_PREFIX+"$"+currentStorageId,RNGTag.class);
        if (localStorage != null) {
            try {
                localStorage.put(key, object);
            } catch (SerializationException e) {
                GWT.log(e.getMessage()+"");
                e.printStackTrace();
            }
        }
    }

    public RNGTag getData(StorageKey<RNGTag> key) {
        RNGTag tag = null;
        if (localStorage != null){
            try {
                tag = localStorage.get(key);
            } catch (SerializationException e) {
                e.printStackTrace();
            }
        }
        return tag;
    }

    public List<StorageItem> getData() {
        List<StorageItem> items = new ArrayList<>();

        if (localStorage != null){
            for (int i = 0; i < localStorage.size(); i++){
                String key = localStorage.key(i);
                if(key.startsWith(STORAGE_PREFIX)) {
                    String[] split = key.split("\\$");

                    StorageItem item = new StorageItem();
                    item.setId(split[2]);
                    item.setDate(split[3]);
                    try {
                        RNGTag tag = localStorage.get(new StorageKey<>(key,RNGTag.class));
                        item.content = tag;
                    } catch (SerializationException e) {
                        e.printStackTrace();
                    }
                    items.add(item);
                }
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
}
