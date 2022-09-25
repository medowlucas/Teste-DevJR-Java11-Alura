package br.com.alura.school.enroll;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;

@Entity
@Table(uniqueConstraints={ @UniqueConstraint(columnNames = {"user", "course"}) })
public class Enroll {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private User user;

    @NotBlank
    @Column(nullable = false, unique = true)
    private Course course;

    @NotBlank
    @Column(nullable = false, unique = true)
    private LocalDateTime enrollDate;

    @Deprecated
    protected Enroll() { }

    public Enroll(Long id, User user, Course course, LocalDateTime enrollDate) {
        this.id = id;
        this.user = user;
        this.course = course;
        this.enrollDate = enrollDate;
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
