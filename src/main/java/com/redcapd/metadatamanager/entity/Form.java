package com.redcapd.metadatamanager.entity;

import com.redcapd.metadatamanager.entity.field.Field;

import java.util.ArrayList;
import java.util.List;

public class Form {
    private List<Field<?>> fields;

    public Form() {
        this.fields = new ArrayList<Field<?>>();
    }

    public void addField(Field<?> field) {
        fields.add(field);
    }

    public List<Field<?>> getFields() {
        return fields;
    }

    public void setFields(List<Field<?>> fields) {
        this.fields = fields;
    }
}
