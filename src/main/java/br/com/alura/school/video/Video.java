package br.com.alura.school.video;

import br.com.alura.school.section.Section;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(uniqueConstraints={ @UniqueConstraint(columnNames = {"video", "section"}) }) 
public class Video {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "O campo video é obrigatório")
    private String video;

    @ManyToOne
    @JoinColumn(name = "videoList", nullable = false)
    @JsonIgnore
    private Section section;

    protected Video() { }

    Video(String link) {
        this.video = link;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideo() {
        return this.video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Section getSection() {
        return this.section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

}
