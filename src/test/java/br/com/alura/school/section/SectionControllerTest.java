package br.com.alura.school.section;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.enroll.Enroll;
import br.com.alura.school.enroll.EnrollRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import br.com.alura.school.video.Video;
import br.com.alura.school.video.VideoRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnrollRepository enrollRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Test
    void should_display_no_content_section_report_videos() throws Exception {
        mockMvc.perform(get("/sectionByVideosReport"))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_not_show_duplicated_section_report_video_response_when_has_two_enrolls() throws Exception {

        final String sectionTitle = "Aula de testes";
        final String usernameSample1 = "adriana";
        final String usernameSample2 = "malaquias";
        final String courseCode = "java-5";

        User user1 = userRepository.save(new User(usernameSample1, usernameSample1 + "@email.com"));
        User user2 = userRepository.save(new User(usernameSample2, usernameSample2 + "@email.com"));
        
        Course course = courseRepository.save(new Course(courseCode, "Course of " + courseCode, "Java and Object Orientation: " + courseCode));

        Section section = new Section("Testando Sections in Java", sectionTitle, "alex");
        section.setCourse(course);
        sectionRepository.save(section);

        Video video = new Video("http://teste.video.com.br");
        video.setSection(section);
        videoRepository.save(video);

        enrollRepository.save(new Enroll(user1, course));
        enrollRepository.save(new Enroll(user2, course));

        mockMvc.perform(get("/sectionByVideosReport"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].courseName", is("Course of " + courseCode)))
                .andExpect(jsonPath("$[0].sectionTitle", is(sectionTitle)))
                .andExpect(jsonPath("$[0].authorName", is("alex")))
                .andExpect(jsonPath("$[0].totalVideos", is(1)));
    }
    
}
