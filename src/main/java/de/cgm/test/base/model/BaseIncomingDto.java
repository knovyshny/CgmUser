package de.cgm.test.base.model;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class BaseIncomingDto implements Serializable {
    @Size(min = 1, max = 128, message
            = "_id must be between 10 and 200 characters")
    @Field
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
