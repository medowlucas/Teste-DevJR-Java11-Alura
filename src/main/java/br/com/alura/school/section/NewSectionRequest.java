package br.com.alura.school.section;

import br.com.alura.school.support.validation.Unique;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

class NewSectionRequest {

    // @Unique(entity = Section.class, field = "code")
    @NotBlank
    @JsonProperty
    private final String code;

    // @Unique(entity = Section.class, field = "title")
    @Size(min=5)
    @NotBlank
    @JsonProperty
    private final String title;

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
