package de.cgm.test.api.user.model.dto;

import de.cgm.test.base.model.BaseOutcomingDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;

public class UserOutcomingDto extends BaseOutcomingDto {
    private final String login;
    private final String name;
    private final String surname;

    public UserOutcomingDto(@NonNull String id, @NonNull String login, String name, String surname){
        this.setId(id);
        this.login = login;
        this.name = name;
        this.surname = surname;
    }


    @NonNull
    public String getLogin() {
        return login;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Nullable
    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserOutcomingDto user = (UserOutcomingDto) o;
        return Objects.equals(login, user.login) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, name, surname);
    }
}
