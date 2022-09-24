package br.com.alura.school.video;

import br.com.alura.school.section.Section;
import br.com.alura.school.support.validation.Unique;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="videos")
public class Video {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "O campo video é obrigatório")
    // @Unique(entity = Section.class, field = "video", message = "O vídeo code precisa ser único")
    private String video;

    @ManyToOne
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
