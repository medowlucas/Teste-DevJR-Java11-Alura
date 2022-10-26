package br.com.alura.school.section;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.enroll.Enroll;
import br.com.alura.school.enroll.EnrollRepository;

@Service
public class SectionService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EnrollRepository enrollRepository;

    public List<Section> SectionByCoursesWithEnrolls() {
        List<Course> allCourses = courseRepository.findAll();
        List<Section> sectionReports = new ArrayList<Section>();

        for(Course course : allCourses) {
            Optional<Enroll> courseEnroll = enrollRepository.findFirstByCourseId(course.getId());
            if (courseEnroll.isPresent()) {
                sectionReports.addAll(course.getSectionList());
            }  
        }
        return sectionReports;
    }

    public List<SectionReportByVideos> getSectionReportByVideos(List<Section> sections) {
        return sections.stream()
        .map(SectionReportByVideos::new)
        .collect(Collectors.toList());
    }

    public Set<Course> findAllCourses() {
        return enrollRepository.findAllCourses();
    }
    
}
