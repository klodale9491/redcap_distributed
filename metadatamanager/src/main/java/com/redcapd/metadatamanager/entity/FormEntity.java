package com.redcapd.metadatamanager.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "form", schema = "redcapd")
public class FormEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    /*
    * Relazione bidirezionale lato padre : . Il mapped-by
    * deve essere usata nella tabella padre, specificando
    * il nome della variabile JAVA nel figlio che si riferisce
    * al padre.
    * */
    @OneToMany(
            mappedBy = "form",
            orphanRemoval = true,
            cascade = CascadeType.REMOVE
    )
    private List<FieldEntity> fields = new ArrayList<>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FieldEntity> getFields() {
        return fields;
    }

    public void setFields(List<FieldEntity> fields) {
        this.fields = fields;
    }

}
