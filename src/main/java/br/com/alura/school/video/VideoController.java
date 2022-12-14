package br.com.alura.school.video;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.section.Section;
import br.com.alura.school.section.SectionRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
class VideoController {

    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;
    private final VideoService videoService;

    VideoController(    VideoRepository videoRepository,
                        SectionRepository sectionRepository,
                        CourseRepository courseRepository,
                        VideoService videoService) {
        this.sectionRepository = sectionRepository;
        this.courseRepository = courseRepository;
        this.videoService = videoService;
    }

    @GetMapping("/course/{courseCode}/video/{sectionCode}")
    ResponseEntity<VideoResponse> videoByCode(@PathVariable("courseCode") String courseCode, @PathVariable("sectionCode") String sectionCode) {
        Course course = courseRepository.findByCode(courseCode)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("O curso código %s não foi encontrado", courseCode)));

        Section section = sectionRepository.findByCodeAndCourseId(sectionCode, course.getId())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("A aula código %s não foi encontrada", sectionCode)));

        if (Objects.isNull(section.getVideoList())) {
            throw new ResponseStatusException(NOT_FOUND, format("Não foram encontrados vídeos para essa Aula e Curso"));
        } 
        return ResponseEntity.ok(new VideoResponse(section));
    }

    @PostMapping("/courses/{courseCode}/sections/{sectionCode}")
    ResponseEntity<Void> newVideo(@RequestBody @Valid NewVideoRequest newVideoRequest, @PathVariable("courseCode") String courseCode, @PathVariable("sectionCode") String sectionCode) {
        Course course = videoService.findCourseByCode(courseCode)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("O curso código %s não foi encontrado", courseCode)));

        Section section = videoService.findSectionByCodeAndCourseId(sectionCode, course)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("A aula código %s não foi encontrada", sectionCode)));

        Video video = newVideoRequest.toEntity();

        Boolean videoExists = videoService.videoExists(newVideoRequest, section);

        if (videoExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        URI response = videoService.saveVideo(video, section);

        return ResponseEntity.created(response).build();
    }
}
