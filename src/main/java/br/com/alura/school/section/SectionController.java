package br.com.alura.school.section;

import org.springframework.http.HttpStatus;
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
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
class SectionController {

    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final SectionService sectionService;

    SectionController(  SectionRepository sectionRepository,
                        CourseRepository courseRepository,
                        UserRepository userRepository,
                        SectionService sectionService){
        this.sectionRepository = sectionRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.sectionService = sectionService;
    }

    @GetMapping("/courses/{courseCode}/sections/{sectionCode}")
    ResponseEntity<SectionResponse> sectionByCode(@PathVariable("courseCode") String courseCode, @PathVariable("sectionCode") String sectionCode) {
        Course course = courseRepository.findByCode(courseCode).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("O curso código %s não foi encontrado", courseCode)));
        Section section = sectionRepository.findByCodeAndCourseId(sectionCode, course.getId())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("A aula código %s não foi encontrada", sectionCode)));
        return ResponseEntity.ok(new SectionResponse(section));
    }
    
    @GetMapping("/sectionByVideosReport")
    ResponseEntity<List<SectionReportByVideos>> sectionByVideosReport() {
        List<Section> sections = sectionService.SectionByCoursesWithEnrolls();

        if (sections.size() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<SectionReportByVideos> sectionReportByVideos = sectionService.getSectionReportByVideos(sections);
        return ResponseEntity.ok(sectionReportByVideos);
    }

    @PostMapping("/courses/{code}/sections")
    ResponseEntity<Void> newSection(@RequestBody @Valid NewSectionRequest newSectionRequest, @PathVariable("code") String courseCode) {
        Course course = courseRepository.findByCode(courseCode).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("O curso código %s não foi encontrado", courseCode)));
        Section section = newSectionRequest.toEntity();
        User user = userRepository.findByUsername(section.getAuthorUsername())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("O autor %s não foi encontrado", section.getAuthorUsername())));

        if (!user.getUserRole().equals(UserRole.INSTRUCTOR)) {
            throw new ResponseStatusException(NOT_FOUND, format("O autor %s não tem permissão de professor", section.getAuthorUsername()));
        }

        Optional<Section> sectionExists = sectionRepository.findFirstByCodeAndCourse(section.getCode(), course);

        if (sectionExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        section.setCourse(course);
        sectionRepository.save(section);
        URI location = URI.create(format("/courses/%s/sections/%s", courseCode, newSectionRequest.getCode()));
        return ResponseEntity.created(location).build();
    }
}
