package de.cgm.test.api.user.controller;

import de.cgm.test.api.user.model.dto.UserOutcomingDto;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserGraphQLController {

    @SchemaMapping(typeName = "Query",value = "userById")
    public UserOutcomingDto userById(@Argument String id){
        return new UserOutcomingDto(id, "test", null, null);
    }
}
