package br.com.alura.school.user;

import br.com.alura.school.support.validation.Unique;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


class NewUserRequest {

    @Unique(entity = User.class, field = "username", message = "Já existe um usuário com esse username cadastrado")
    @Size(max=20)
    @NotBlank(message = "O campo username é obrigatório")
    @JsonProperty
    private final String username;

    @Unique(entity = User.class, field = "email", message = "Já existe um usuário com esse email cadastrado")
    @NotBlank(message = "O campo email é obrigatório")
    @Email
    @JsonProperty
    private final String email;

    NewUserRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }

    String getUsername() {
        return username;
    }

    User toEntity() {
        return new User(username, email);
    }
}
