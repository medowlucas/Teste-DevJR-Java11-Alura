package br.com.alura.school.section;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
class SectionController {

    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;

    SectionController(SectionRepository sectionRepository, CourseRepository courseRepository) {
        this.sectionRepository = sectionRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses/{courseCode}/sections/{sectionCode}")
    ResponseEntity<SectionResponse> courseByCode(@PathVariable("courseCode") String courseCode, @PathVariable("sectionCode") String sectionCode) {
        Course course = courseRepository.findByCode(courseCode).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("O curso código %s não foi encontrado", courseCode)));;
        Long courseId = course.getId();
        Section section = sectionRepository.findByCodeAndCourseId(sectionCode, courseId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("A aula código %s não foi encontrada", sectionCode)));
        return ResponseEntity.ok(new SectionResponse(section));
    }

    @PostMapping("/courses/{code}/sections")
    ResponseEntity<Void> newCourse(@RequestBody @Valid NewSectionRequest newSectionRequest, @PathVariable("code") String courseCode) {
        Course course = courseRepository.findByCode(courseCode).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("O curso código %s não foi encontrado", courseCode)));;
        Section section = newSectionRequest.toEntity();
        section.setCourse(course);
        sectionRepository.save(section);
        URI location = URI.create(format("/courses/%s/sections/%s", courseCode, newSectionRequest.getCode()));
        return ResponseEntity.created(location).build();
    }
}
