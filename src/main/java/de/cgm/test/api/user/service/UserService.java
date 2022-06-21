package de.cgm.test.api.user.service;

import de.cgm.test.api.user.model.entity.User;
import de.cgm.test.base.service.BaseCRUDService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class UserService extends BaseCRUDService<User> implements IUserService {
    public UserService(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public @Nullable
    User findByLoginAndPassword(@NonNull String login, @NonNull String password) {
        return this.getMongoTemplate().findOne(query(where("login").is(login).and("password").is(password)), User.class);
    }
}
