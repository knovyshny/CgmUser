package de.cgm.test.api.user.model.dto;

import de.cgm.test.base.model.BaseIncomingDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;

public class UserIncomingDto extends BaseIncomingDto {
    @NotBlank(message = "login is mandatory")
    @Size(min = 10, max = 50, message
            = "Login must be between 10 and 200 characters")
    private final String login;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{10,}$", message
            = "Password must contains at least 10 chars and at least one digit and at least one Capital char")
    @Size(min = 10, max = 50)
    private String password;

    @Size(max = 100, message
            = "Name must be between 10 and 200 characters")
    private final String name;

    @Size(max = 100, message
            = "Surname must be between 10 and 200 characters")
    private final String surname;

    public UserIncomingDto(@NonNull String id, @NonNull String login, @NonNull String password, String name, String surname){
        this.setId(id);
        this.password = password;
        this.login = login;
        this.name = name;
        this.surname = surname;
    }

    @NonNull
    public String getLogin() {
        return login;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword( @NonNull String password ) {
        this.password = password;
    }

    @Nullable
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

    @Nullable
    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserIncomingDto user = (UserIncomingDto) o;
        return Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, password, name, surname);
    }
}
