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

    // @Test
    // void should_add_new_enroll() throws Exception {
    //     final String courseCodeSample = "java-3";
    //     final String usernameSample = "janaina";

    //     userRepository.save(new User(usernameSample, usernameSample + "@email.com"));
    //     courseRepository.save(new Course(courseCodeSample, "Course of " + courseCodeSample, "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
    //     NewEnrollRequest newEnroll = new NewEnrollRequest(usernameSample);

    //     mockMvc.perform(post("/courses/{courseCode}/enroll", courseCodeSample)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(jsonMapper.writeValueAsString(newEnroll)))
    //             .andDo(print())
    //     .andExpect(status().isCreated());
    // }

    // @ParameterizedTest
    // @CsvSource({
    //         "'',",
    //         "     ,",
    //         "an-username-that-is-really-really-big"
    // })
    // void should_validate_bad_enroll_requests(String username) throws Exception {
    //     NewEnrollRequest newEnroll = new NewEnrollRequest(username);

    //     mockMvc.perform(post("/courses/java-1/enroll")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(jsonMapper.writeValueAsString(newEnroll)))
    //             .andExpect(status().isBadRequest());
    // }

    @Test
    void should_not_show_duplicated_sections_response_when_has_two_enrolls() throws Exception {

        final String sectionTitle = "Aula de testes";
        final String usernameSample1 = "alex";
        final String usernameSample2 = "malaquias";
        final String courseCode = "java-5";

        User user1 = userRepository.save(new User(usernameSample1, usernameSample1 + "@email.com"));
        User user2 = userRepository.save(new User(usernameSample2, usernameSample2 + "@email.com"));
        
        Course course = courseRepository.save(new Course(courseCode, "Course of " + courseCode, "Java and Object Orientation: " + courseCode));

        Section section = new Section("Testando Sections in Java", sectionTitle, "alex");
        section.setCourse(course);
        sectionRepository.save(section);

        enrollRepository.save(new Enroll(user1, course));
        enrollRepository.save(new Enroll(user2, course));

        mockMvc.perform(get("/sectionByVideosReport"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.courseName", is("Course of " + courseCode)))
                .andExpect(jsonPath("$.sectionTitle", is(sectionTitle)))
                .andExpect(jsonPath("$.authorName", is("alex")))
                .andExpect(jsonPath("$.totalVideos", is(0)));
    }
    
}
