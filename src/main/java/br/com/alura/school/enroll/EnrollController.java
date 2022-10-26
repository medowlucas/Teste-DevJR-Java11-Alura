package br.com.alura.school.enroll;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;

import javax.validation.Valid;

import static java.lang.String.format;

import java.util.Optional;

@RestController
class EnrollController {

    private final EnrollRepository enrollRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    EnrollController(   EnrollRepository enrollRepository,
                        CourseRepository courseRepository,
                        UserRepository userRepository) {
        this.enrollRepository = enrollRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/courses/{courseCode}/enroll")
    public ResponseEntity<Void> newEnroll(@RequestBody @Valid NewEnrollRequest newEnrollRequest, @PathVariable("courseCode") String courseCode) {
        Course course = courseRepository.findByCode(courseCode)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, format("O curso código %s não foi encontrado", courseCode)));

        User user = userRepository.findByUsername(newEnrollRequest.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, format("O username %s não foi encontrado", newEnrollRequest.getUsername())));

        Optional<Enroll> enrollExists = enrollRepository.findFirstByUserIdAndCourseId(user.getId(), course.getId());

        if (enrollExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Enroll enroll = new Enroll(user, course);
        enrollRepository.save(enroll);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
