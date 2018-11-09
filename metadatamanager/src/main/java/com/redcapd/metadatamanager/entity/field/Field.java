package com.redcapd.metadatamanager.entity.field;


public abstract class Field<E> {

    private String name; // Nome del campo
    private String label; // Etichetta del campo
    private E value; // Vslore dell'oggetto da andare a castare
    private boolean isRequired;


    public void setRequired(boolean required) {
        isRequired = required;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public boolean isRequired() {
        return isRequired;
    }

    //
    // Metodo di validazione che cambia per
    // ogni tipo di campo.
    //
    public abstract boolean validate();
}
