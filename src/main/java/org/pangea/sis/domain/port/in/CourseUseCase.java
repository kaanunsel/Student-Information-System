package org.pangea.sis.domain.port.in;

import org.pangea.sis.domain.model.Course;

import java.util.List;
import java.util.Optional;

/**
 * Input port (use case interface) for course operations.
 * Defines business operations available for courses.
 */
public interface CourseUseCase {
    List<Course> getAllCourses();
    List<Course> getCourseById(Long id);
    List<Course> getCoursesByName(String name);
    List<Course> getCoursesByCode(String code);
    List<Course> getCoursesByInstructorId(Long instructorId);
    Course addCourse(Course course);
    Optional<Course> updateCourse(String code, Course updatedCourse);
    void deleteCourseById(Long id);
    void deleteCourseByCode(String code);
}
