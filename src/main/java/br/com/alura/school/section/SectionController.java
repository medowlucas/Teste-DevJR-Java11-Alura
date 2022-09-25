package br.com.alura.school.section;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.UserRepository;
import br.com.alura.school.user.UserRole;
import br.com.alura.school.user.User;

import javax.validation.Valid;
import java.net.URI;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
class SectionController {

    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;
    public final UserRepository userRepository;

    SectionController(  SectionRepository sectionRepository,
                        CourseRepository courseRepository,
                        UserRepository userRepository){
        this.sectionRepository = sectionRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/courses/{courseCode}/sections/{sectionCode}")
    ResponseEntity<SectionResponse> courseByCode(@PathVariable("courseCode") String courseCode, @PathVariable("sectionCode") String sectionCode) {
        Course course = courseRepository.findByCode(courseCode).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("O curso código %s não foi encontrado", courseCode)));
        Section section = sectionRepository.findByCodeAndCourseId(sectionCode, course.getId())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("A aula código %s não foi encontrada", sectionCode)));
        return ResponseEntity.ok(new SectionResponse(section));
    }

    @PostMapping("/courses/{code}/sections")
    ResponseEntity<Void> newCourse(@RequestBody @Valid NewSectionRequest newSectionRequest, @PathVariable("code") String courseCode) {
        Course course = courseRepository.findByCode(courseCode).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("O curso código %s não foi encontrado", courseCode)));
        Section section = newSectionRequest.toEntity();
        User user = userRepository.findByUsername(section.getAuthorUsername())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("O autor %s não foi encontrado", section.getAuthorUsername())));

        if (!user.getUserRole().equals(UserRole.INSTRUCTOR)) {
            throw new ResponseStatusException(NOT_FOUND, format("O autor %s não tem permissão de professor", section.getAuthorUsername()));
        }

        section.setCourse(course);
        sectionRepository.save(section);
        URI location = URI.create(format("/courses/%s/sections/%s", courseCode, newSectionRequest.getCode()));
        return ResponseEntity.created(location).build();
    }
}
