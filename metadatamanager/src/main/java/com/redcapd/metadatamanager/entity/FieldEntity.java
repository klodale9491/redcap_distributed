package com.redcapd.metadatamanager.entity;

import javax.persistence.*;

@Entity
@Table(name = "field", schema = "redcapd", catalog = "")
public class FieldEntity {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "label", nullable = false, length = 255)
    private String label;
    @Basic
    @Column(name = "variable", nullable = false, length = 255)
    private String variable;
    @ManyToOne
    private FormEntity form;


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public FormEntity getForm() {
        return form;
    }

    public void setForm(FormEntity form) {
        this.form = form;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldEntity that = (FieldEntity) o;

        if (id != that.id) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (variable != null ? !variable.equals(that.variable) : that.variable != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (variable != null ? variable.hashCode() : 0);
        return result;
    }
}
