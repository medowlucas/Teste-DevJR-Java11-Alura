package br.com.alura.school.video;

import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

class NewVideoRequest {

    @JsonProperty
    @NotBlank(message = "O campo vídeo é obrigatório")
    private final String video;

    @Deprecated
    public NewVideoRequest() {
        this.video = "";
    }

    public NewVideoRequest(String video) {
        this.video = video;
    }

    public String getVideo() {
        return this.video;
    }

    Video toEntity() {
        return new Video(video);
    }
}
