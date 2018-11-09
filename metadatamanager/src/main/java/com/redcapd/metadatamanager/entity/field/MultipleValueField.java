package com.redcapd.metadatamanager.entity.field;

import java.util.HashMap;

public abstract class MultipleValueField<E> extends Field<E>{
    private HashMap<String,E> values; // Possibili valori del campo


    public HashMap<String,E> getValues() {
        return values;
    }

    public void setValues(HashMap<String,E> values) {
        this.values = values;
    }

}
