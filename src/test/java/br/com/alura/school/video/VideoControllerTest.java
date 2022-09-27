package br.com.alura.school.video;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.section.Section;
import br.com.alura.school.section.SectionRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VideoControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SectionRepository sectionRepository;
    
    @Autowired
    private VideoRepository videoRepository;

    @Test
    void should_validate_bad_empty_videos() throws Exception {
        final String sectionTitle = "Aula de Videos";
        final String courseCode = "java-7";
        final String emptyVideoString = " ";
        
        Course course = courseRepository.save(new Course(courseCode, "Another " + courseCode, "Java and Object Orientation: " + courseCode));

        Section section = new Section("Testando Videos in Java", sectionTitle, "alex");
        section.setCourse(course);
        sectionRepository.save(section);

        NewVideoRequest newVideoRequest = new NewVideoRequest(emptyVideoString);

        mockMvc.perform(post("/courses/{courseCode}/sections/{sectionCode}", courseCode, section.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newVideoRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_bad_request_two_equals_videos_in_same_section() throws Exception {
        final String sectionTitle = "Aula de Videos";
        final String courseCode = "java-8";
        final String video1 = "http://sameAsAnother";
        final String video2 = video1;
        
        Course course = courseRepository.save(new Course(courseCode, "Another " + courseCode, "Java and Object Orientation: " + courseCode));

        Section section = new Section("videos-iguais", sectionTitle, "alex");
        section.setCourse(course);
        sectionRepository.save(section);

        //Pre-saving-an-equal-video
        Video originalVideo = new Video(video1);
        originalVideo.setSection(section);
        videoRepository.save(originalVideo);

        NewVideoRequest newVideoRequest = new NewVideoRequest(video2);

        mockMvc.perform(post("/courses/{courseCode}/sections/{sectionCode}", courseCode, section.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newVideoRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_add_two_equals_videos_in_different_section() throws Exception {
        final String sectionTitle = "Videos Iguais Aulas Diferentes";
        final String courseCode = "java-9";
        final String video = "http://sameAsAnother";
        
        Course course = courseRepository.save(new Course(courseCode, "Another " + courseCode, "Java Orientation: " + courseCode));

        Section section1 = new Section("aula-video-1", sectionTitle, "alex");
        section1.setCourse(course);
        sectionRepository.save(section1);

        Section section2 = new Section("aula-video-2", sectionTitle, "alex");
        section2.setCourse(course);
        sectionRepository.save(section2);

        //Pre-saving-an-equal-video
        Video originalVideo = new Video(video);
        originalVideo.setSection(section1);
        videoRepository.save(originalVideo);

        NewVideoRequest newVideoRequest = new NewVideoRequest(video);

        mockMvc.perform(post("/courses/{courseCode}/sections/{sectionCode}", courseCode, section2.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newVideoRequest)))
                .andExpect(status().isCreated());
    }
    
    @Test
    void should_add_two_different_videos_in_same_section() throws Exception {
        final String sectionTitle = "Aula2 de Videos";
        final String courseCode = "java-10";
        final String video1 = "http://sameAsAnother";
        final String video2 = "http://imDifferentOne";
        
        Course course = courseRepository.save(new Course(courseCode, "Different " + courseCode, "Java and Different Videos: " + courseCode));

        Section section = new Section("videos-diferentes", sectionTitle, "alex");
        section.setCourse(course);
        sectionRepository.save(section);

        //Pre-saving-an-different-video
        Video originalVideo = new Video(video1);
        originalVideo.setSection(section);
        videoRepository.save(originalVideo);

        NewVideoRequest newVideoRequest = new NewVideoRequest(video2);

        mockMvc.perform(post("/courses/{courseCode}/sections/{sectionCode}", courseCode, section.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newVideoRequest)))
                .andExpect(status().isCreated());
    }

}