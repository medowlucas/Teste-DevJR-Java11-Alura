package br.com.alura.school.section;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.school.course.Course;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findByCode(String code);

    Optional<Section> findByCodeAndCourseId(String code, Long courseId);

    Optional<Section> findFirstByCodeAndCourse(String code, Course course);
}
