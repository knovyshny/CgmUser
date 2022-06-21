package de.cgm.test.api.user.model.entity;

import de.cgm.test.base.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.Objects;

@Document("user")
public class User extends BaseEntity {

    private final String login;
    private final String password;
    private final String name;
    private final String surname;
    // well transient is used here to avoid serialization for gson
    // it is not 100% proper solution in this place,
    // TODO find a better solution
    private transient Date createdAt;

    public User(@NonNull String id, @NonNull String login, @NonNull String password, String name, String surname, Date createdAt){
        this.setId(id);
        this.password = password;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.createdAt = createdAt;
        if (this.createdAt == null){
            this.createdAt = new Date();
        }
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, password, name, surname);
    }
}
