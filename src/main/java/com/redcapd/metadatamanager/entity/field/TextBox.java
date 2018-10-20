package com.redcapd.metadatamanager.entity.field;

public class TextBox<E> extends Field<E>{



    public boolean validate() {
        E value = this.getValue();

        if(value instanceof String){
            // Validazione stringa.
            System.out.println("String");
        }
        else if(value instanceof Integer){
            // Validazione intero.
            System.out.println("Intero");
        }
        else if(value instanceof Double){
            // Validazione double.
            System.out.println("Double");
        }
        return false;
    }
}
