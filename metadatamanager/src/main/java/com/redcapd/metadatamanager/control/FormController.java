package com.redcapd.metadatamanager.control;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class FormController {
    @PersistenceContext(unitName = "MyPersistenceUnit")
    private EntityManager em;
}
