package br.com.alura.school.enroll;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

class NewEnrollRequest {

    @Size(max=20)
    @NotBlank(message = "O campo username é obrigatório")
    @JsonProperty
    private final String username;

    @Deprecated
    protected NewEnrollRequest() {
        this.username = "";
    }

    public NewEnrollRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
