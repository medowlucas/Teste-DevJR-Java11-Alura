package br.com.alura.school.video;

import br.com.alura.school.section.Section;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "videos", uniqueConstraints={ @UniqueConstraint(columnNames = {"video", "section_id"}) }) 
public class Video {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String video;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "section_id")
    private Section section;

    @Deprecated
    protected Video() { }

    public Video(String video) {
        this.video = video;
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
