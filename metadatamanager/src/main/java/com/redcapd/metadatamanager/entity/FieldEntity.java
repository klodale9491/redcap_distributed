package com.redcapd.metadatamanager.entity;

import org.json.JSONPropertyIgnore;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;


@Entity
@Table(name = "field", schema = "redcapd", catalog = "")
public class FieldEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "label", nullable = false, length = 255)
    private String label;

    @Basic
    @Column(name = "variable", nullable = false, length = 255)
    private String variable;

    @Basic
    @Column(name = "position", nullable = false)
    private int position;

    /*
    * relazione biderezionale lato figlio : mi basta specificare
    * la colonna di join del figlio verso il padre.
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")

    private FormEntity form;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @JsonbTransient
    public FormEntity getForm() {
        return form;
    }

    public void setForm(FormEntity form) {
        this.form = form;
    }

}
