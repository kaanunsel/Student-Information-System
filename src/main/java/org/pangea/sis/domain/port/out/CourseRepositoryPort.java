package org.pangea.sis.domain.port.out;

import org.pangea.sis.domain.model.Course;

import java.util.List;
import java.util.Optional;

/**
 * Output port for course persistence operations.
 * Defines contract for course repository without infrastructure details.
 */
public interface CourseRepositoryPort {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    List<Course> findByNameContaining(String name);
    List<Course> findByCodeContaining(String code);
    Optional<Course> findByCode(String code);
    List<Course> findByInstructorId(Long instructorId);
    Course save(Course course);
    void deleteById(Long id);
    void delete(Course course);
}
