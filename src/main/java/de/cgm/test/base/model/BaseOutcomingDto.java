package de.cgm.test.base.model;

import java.io.Serializable;

public class BaseOutcomingDto implements Serializable {
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
