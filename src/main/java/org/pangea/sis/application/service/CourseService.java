package org.pangea.sis.application.service;

import org.pangea.sis.domain.model.Course;
import org.pangea.sis.domain.port.in.CourseUseCase;
import org.pangea.sis.domain.port.out.CourseRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service implementing course use cases.
 * Depends on repository port interfaces, not concrete implementations.
 */
@Service
public class CourseService implements CourseUseCase {

    private final CourseRepositoryPort courseRepositoryPort;

    public CourseService(CourseRepositoryPort courseRepositoryPort) {
        this.courseRepositoryPort = courseRepositoryPort;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepositoryPort.findAll();
    }

    @Override
    public List<Course> getCourseById(Long id) {
        return courseRepositoryPort.findById(id).stream().toList();
    }

    @Override
    public List<Course> getCoursesByName(String name) {
        return courseRepositoryPort.findByNameContaining(name);
    }

    @Override
    public List<Course> getCoursesByCode(String code) {
        return courseRepositoryPort.findByCodeContaining(code);
    }

    @Override
    public List<Course> getCoursesByInstructorId(Long instructorId) {
        return courseRepositoryPort.findByInstructorId(instructorId);
    }

    @Override
    public Course addCourse(Course course) {
        course.setCreatedAt(LocalDateTime.now());
        return courseRepositoryPort.save(course);
    }

    @Override
    public Optional<Course> updateCourse(String code, Course updatedCourse) {
        Optional<Course> optionalCourse = courseRepositoryPort.findByCode(code);

        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            existingCourse.setName(updatedCourse.getName());
            existingCourse.setCode(updatedCourse.getCode());
            existingCourse.setCredit(updatedCourse.getCredit());
            existingCourse.setInstructorId(updatedCourse.getInstructorId());
            Course saved = courseRepositoryPort.save(existingCourse);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteCourseById(Long id) {
        courseRepositoryPort.deleteById(id);
    }

    @Override
    public void deleteCourseByCode(String code) {
        Optional<Course> courseToDelete = courseRepositoryPort.findByCode(code);
        courseToDelete.ifPresent(courseRepositoryPort::delete);
    }
}
