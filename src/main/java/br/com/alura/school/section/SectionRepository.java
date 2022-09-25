package br.com.alura.school.section;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.school.course.Course;

import java.util.Optional;

interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findByCode(String code);

    Optional<Section> findByCodeAndCourse(String code, Course course);
}
