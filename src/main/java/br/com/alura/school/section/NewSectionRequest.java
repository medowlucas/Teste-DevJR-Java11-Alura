package br.com.alura.school.section;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

class NewSectionRequest {

    @NotBlank(message="O campo code é obrigatório")
    @JsonProperty
    private final String code;

    @Size(min=5, message = "O campo title deve ter no mínimo 5 caracteres")
    @NotBlank(message="O campo title é obrigatório")
    @JsonProperty
    private final String title;

    @NotBlank(message="O campo authorUsername é obrigatório")
    @JsonProperty
    private final String authorUsername;

    public NewSectionRequest(String code, String title, String authorUsername) {
        this.code = code;
        this.title = title;
        this.authorUsername = authorUsername;
    }  

    public String getCode() {
        return code;
    }

    Section toEntity() {
        return new Section(code, title, authorUsername);
    }
}
