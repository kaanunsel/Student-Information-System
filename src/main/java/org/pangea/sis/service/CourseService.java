package org.pangea.sis.service;

import org.pangea.sis.entity.Course;
import org.pangea.sis.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public List<Course> getCourseById(Long id){
        return courseRepository.findById(id).stream().toList();
    }

    public List<Course> getCoursesByName(String name){
        return courseRepository.findAllByNameContainingIgnoreCase(name);
    }

    public List<Course> getCoursesByCode(String code){
        return courseRepository.findAllByCodeContainingIgnoreCase(code);
    }

    public Course addCourse(Course course){
        course.setCreatedAt(LocalDateTime.now());
        return courseRepository.save(course);
    }

    public void deleteCourseById(Long id){
        courseRepository.deleteById(id);
    }

    public Optional<Course> updateCourse(Long id, Course updatedCourse) {

        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            existingCourse.setName(updatedCourse.getName());
            existingCourse.setCode(updatedCourse.getCode());
            existingCourse.setCredit(updatedCourse.getCredit());
            Course saved = courseRepository.save(existingCourse);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }
}
