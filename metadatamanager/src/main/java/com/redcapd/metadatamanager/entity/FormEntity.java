package com.redcapd.metadatamanager.entity;

import javax.persistence.*;
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
    @ManyToOne
    private ProjectEntity project;
    @OneToMany
    private List<FieldEntity> fields;

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

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public List<FieldEntity> getFields() {
        return fields;
    }

    public void setFields(List<FieldEntity> fields) {
        this.fields = fields;
    }

    public String toString(){
        return String.format("ID : %d, Name : %s, Project_Id : %d", this.id,this.name,this.project.getId());
    }
}
