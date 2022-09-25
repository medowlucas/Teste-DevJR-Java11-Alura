package br.com.alura.school.video;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.alura.school.section.Section;

class VideoResponse {

    @JsonProperty
    private final List<Video> videos;

    VideoResponse(Section section) {
        this.videos = section.getVideoList();
    }

}
