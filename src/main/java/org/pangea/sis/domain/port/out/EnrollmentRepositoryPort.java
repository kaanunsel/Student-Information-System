package org.pangea.sis.domain.port.out;

import org.pangea.sis.domain.model.Enrollment;

import java.util.List;
import java.util.Optional;

/**
 * Output port for enrollment persistence operations.
 * Defines contract for enrollment repository without infrastructure details.
 */
public interface EnrollmentRepositoryPort {
    List<Enrollment> findAll();
    Optional<Enrollment> findById(Long id);
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findByCourseId(Long courseId);
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    List<Enrollment> findByGradeIsNotNull();
    Enrollment save(Enrollment enrollment);
    void deleteById(Long id);
}
