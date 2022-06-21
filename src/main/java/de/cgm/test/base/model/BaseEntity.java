package de.cgm.test.base.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

public class BaseEntity implements Serializable {
    @Id
    @Field
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
