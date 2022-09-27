package br.com.alura.school.enroll;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
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
class EnrollControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnrollRepository enrollRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void should_add_new_enroll() throws Exception {
        NewEnrollRequest newEnroll = new NewEnrollRequest("ana");

        mockMvc.perform(post("/courses/{courseCode}/enroll", "java-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newEnroll)))
                .andDo(print())
        .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvSource({
            "'',",
            "     ,",
            "an-username-that-is-really-really-big"
    })
    void should_validate_bad_enroll_requests(String username) throws Exception {
        NewEnrollRequest newEnroll = new NewEnrollRequest(username);

        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newEnroll)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_allow_duplication_of_username_in_same_course() throws Exception {
        
        User user = userRepository.findByUsername("alex").get();
        Course course = courseRepository.findByCode("java-1").get();

        enrollRepository.save(new Enroll(user, course));

        NewEnrollRequest newEnroll = new NewEnrollRequest("alex");

        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newEnroll)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}