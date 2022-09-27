package br.com.alura.school.enroll;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;

import java.util.Optional;

public interface EnrollRepository extends JpaRepository<Enroll, Long> {
    Optional<Enroll> findByUserId(Long userId);

    Optional<Enroll> findByCourseId(Long courseId);

    Enroll findFirstByUserAndCourse(User user, Course course);
}
