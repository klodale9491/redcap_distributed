package com.redcapd.metadatamanager.entity;

import javax.persistence.*;


@Entity
@Table(name = "form", schema = "redcapd")
public class FormEntity {
    private long id;
    private String name;
    private ProjectEntity project;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @ManyToOne
    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

}
