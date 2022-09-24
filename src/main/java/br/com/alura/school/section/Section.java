package br.com.alura.school.section;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.alura.school.course.Course;
import br.com.alura.school.support.validation.*;
import br.com.alura.school.video.Video;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "O campo code é obrigatório")
    @Column(nullable = false, unique = true)
    // @Unique(entity = Course.class, field = "code", message = "O campo code precisa ser único")
    public String code;

    @NotBlank(message = "O campo title é obrigatório")
    @Size(min=5)
    @Column(nullable = false, unique = true)
    private String title;
    
    @NotBlank(message = "O campo author é obrigatório")
    private String authorUsername;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    private List<Video> videoList = new ArrayList<Video>();

    @ManyToOne
    private Course course;

    public Section() {
    }

    public Section(String code, String title, String authorUsername) {
        this.code = code;
        this.title = title;
        this.authorUsername = authorUsername;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorUsername() {
        return this.authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public List<Video> getVideoList() {
        return this.videoList;
    }

    public void addVideo(Video video) {
        this.videoList.add(video);
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
