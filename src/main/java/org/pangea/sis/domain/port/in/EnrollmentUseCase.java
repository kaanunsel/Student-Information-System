package org.pangea.sis.domain.port.in;

import org.pangea.sis.domain.model.Enrollment;

import java.util.List;

/**
 * Input port (use case interface) for enrollment operations.
 * Defines business operations available for enrollments.
 */
public interface EnrollmentUseCase {
    List<Enrollment> getAllEnrollments();
    List<Enrollment> getAllEnrollmentsByStudentAndCourseId(Long studentId, Long courseId);
    List<Enrollment> getByStudentId(Long id);
    List<Enrollment> getByCourseId(Long id);
    Enrollment createEnrollment(Enrollment enrollment);
    Enrollment updateGrade(Long id, Integer grade);
    void deleteEnrollment(Long id);
}
