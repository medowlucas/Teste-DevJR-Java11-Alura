package br.com.alura.school.enroll;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;

@Entity
@Table(uniqueConstraints={ @UniqueConstraint(columnNames = {"user_id", "course_id"}) })
public class Enroll {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private Course course;

    @Column(nullable = false, unique = false)
    private LocalDateTime enrollDate;

    @Deprecated
    protected Enroll() { }

    public Enroll(User user, Course course) {
        this.user = user;
        this.course = course;
        this.enrollDate = LocalDateTime.now();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getEnrollDate() {
        return this.enrollDate;
    }

    public void setEnrollDate(LocalDateTime enrollDate) {
        this.enrollDate = enrollDate;
    }

}
