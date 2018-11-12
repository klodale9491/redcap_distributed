package com.redcapd.metadatamanager.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "project", schema = "redcapd", catalog = "")
public class ProjectEntity {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @OneToMany
    private List<FormEntity> forms;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FormEntity> getForms() {
        return forms;
    }

    public void setForms(List<FormEntity> forms) {
        this.forms = forms;
    }
}
