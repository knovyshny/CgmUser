package de.cgm.test.api.user.service;

import de.cgm.test.api.user.model.entity.User;
import de.cgm.test.base.service.IBaseCRUDService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface IUserService extends IBaseCRUDService<User> {
    @Nullable
    User findByLoginAndPassword(@NonNull String login, @NonNull String password);
}
