package com.redcapd.metadatamanager.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "form", schema = "redcapd")
public class FormEntity {
    private long id;
    private String name;

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

    private ProjectEntity manyToOne;

    @ManyToOne
    public ProjectEntity getManyToOne() {
        return manyToOne;
    }

    public void setManyToOne(ProjectEntity manyToOne) {
        this.manyToOne = manyToOne;
    }

    private List<ProjectEntity> oneToMany;

    @OneToMany
    public List<ProjectEntity> getOneToMany() {
        return oneToMany;
    }

    public void setOneToMany(List<ProjectEntity> oneToMany) {
        this.oneToMany = oneToMany;
    }
}
