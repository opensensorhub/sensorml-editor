package com.sensia.tools.client.swetools.editors.sensorml.serialization;

import com.seanchenxi.gwt.storage.client.StorageKey;
import com.seanchenxi.gwt.storage.client.StorageKeyProvider;

import java.util.HashMap;

public interface MyStorageKeyProvider extends StorageKeyProvider {

    @Key("sensormleditor")
    StorageKey<HashMap<String,StorageItem>> valuesMapKey();
}
