package br.com.alura.school.enroll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.school.course.Course;

import java.util.Optional;
import java.util.Set;

public interface EnrollRepository extends JpaRepository<Enroll, Long> {
    Optional<Enroll> findByUserId(Long userId);

    Optional<Enroll> findFirstByCourseId(Long courseId);

    Optional<Enroll> findFirstByUserIdAndCourseId(Long userId, Long courseId);

    @Query("select course from Enroll")
    Set<Course> findAllCourses();
}
