package com.sensia.tools.client.swetools.editors.sensorml.panels;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.sensia.relaxNG.RNGTag;

import java.io.Serializable;

public class StorageItem implements Serializable, IsSerializable {
    private String id;
    private String date;
    public RNGTag content;
    public String name;

    public StorageItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StorageItem{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", content=" + content +
                '}';
    }
}
