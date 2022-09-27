package br.com.alura.school.section;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

class SectionReportByVideos {

    @JsonProperty
    private final String courseName;

    @JsonProperty
    private final String sectionTitle;

    @JsonProperty
    private final String authorName;

    @JsonProperty
    private final Integer totalVideos;

    SectionReportByVideos(Section section) {
        this.courseName = section.getCourse().getName();
        this.sectionTitle = section.getTitle();
        this.authorName = section.getAuthorUsername();
        this.totalVideos = countVideos(section);
    }

    Integer countVideos(Section section) {
        if (Objects.isNull(section) || Objects.isNull(section.getVideoList())) {
            return 0;
        }
        return section.getVideoList().size();
    }

}
