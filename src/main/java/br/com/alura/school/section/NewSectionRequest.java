package br.com.alura.school.section;

import br.com.alura.school.course.Course;
import br.com.alura.school.support.validation.Unique;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

class NewSectionRequest {

    @Unique(entity = Section.class, field = "code", message = "O campo code dessa Aula já foi cadastrado no Curso")
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

    Section toEntity(Course course) {
        return new Section(code, title, authorUsername, course);
    }
}
