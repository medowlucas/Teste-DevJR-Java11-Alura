package br.com.alura.school.section;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.alura.school.course.Course;
import br.com.alura.school.video.Video;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints={ @UniqueConstraint(columnNames = {"code", "course"}) }) 
public class Section {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = false)
    public String code;

    @NotBlank
    @Size(min=5)
    @Column(nullable = false, unique = false)
    private String title;
    
    @NotBlank
    private String authorUsername;

    @OneToMany(mappedBy = "section", targetEntity = Video.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Video> videoList = new ArrayList<Video>();

    @ManyToOne
    @JoinColumn(name = "course", nullable = false)
    @JsonIgnore
    private Course course;

    @Deprecated
    protected Section() {
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

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
