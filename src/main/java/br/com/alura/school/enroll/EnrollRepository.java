package br.com.alura.school.enroll;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollRepository extends JpaRepository<Enroll, Long> {
    Optional<Enroll> findByUserId(Long userId);

    Optional<Enroll> findFirstByCourseId(Long courseId);

    Optional<Enroll> findFirstByUserIdAndCourseId(Long userId, Long courseId);
}
