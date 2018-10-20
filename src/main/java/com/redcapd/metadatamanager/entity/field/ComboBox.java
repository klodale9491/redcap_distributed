package com.redcapd.metadatamanager.entity.field;

public class ComboBox<E> extends MultipleValueField<E> {

    public boolean validate() {
        return false;
    }
}
