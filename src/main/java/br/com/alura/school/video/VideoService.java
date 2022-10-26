package br.com.alura.school.video;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.section.Section;
import br.com.alura.school.section.SectionRepository;

@Service
public class VideoService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private VideoRepository videoRepository;


    public Optional<Section> findSectionByCodeAndCourseId(String sectionCode, Course course) {
        Optional<Section> section = sectionRepository.findByCodeAndCourseId(sectionCode, course.getId());
        
        return section;
    }
    
    public Optional<Course> findCourseByCode(String courseCode) {
        Optional<Course> course = courseRepository.findByCode(courseCode);
        
        return course;
    }

    public Boolean videoExists(NewVideoRequest newVideoRequest, Section section) {
        Optional<Video> videoExists = videoRepository.findFirstByVideoAndSectionId(newVideoRequest.getVideo(), section.getId());
        
        return videoExists.isPresent();
    }

    public URI saveVideo(Video video, Section section) {
        video.setSection(section);
        videoRepository.save(video);
        
        return URI.create("/video/" + section.getCode());
    }

    
}
