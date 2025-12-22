package org.pangea.sis.application.service;

import org.pangea.sis.domain.model.Enrollment;
import org.pangea.sis.domain.port.in.EnrollmentUseCase;
import org.pangea.sis.domain.port.out.EnrollmentRepositoryPort;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service implementing enrollment use cases.
 * Depends on repository port interfaces, not concrete implementations.
 */
@Service
public class EnrollmentService implements EnrollmentUseCase {

    private final EnrollmentRepositoryPort enrollmentRepositoryPort;

    public EnrollmentService(EnrollmentRepositoryPort enrollmentRepositoryPort) {
        this.enrollmentRepositoryPort = enrollmentRepositoryPort;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepositoryPort.findAll();
    }

    @Override
    public List<Enrollment> getAllEnrollmentsByStudentAndCourseId(Long studentId, Long courseId) {
        return enrollmentRepositoryPort.findByStudentIdAndCourseId(studentId, courseId).stream().toList();
    }

    @Override
    public List<Enrollment> getByStudentId(Long id) {
        return enrollmentRepositoryPort.findByStudentId(id);
    }

    @Override
    public List<Enrollment> getByCourseId(Long id) {
        return enrollmentRepositoryPort.findByCourseId(id);
    }

    @Override
    @CacheEvict(value = "analytics", allEntries = true)
    public Enrollment createEnrollment(Enrollment enrollment) {
        if (enrollmentRepositoryPort.existsByStudentIdAndCourseId(
                enrollment.getStudentId(),
                enrollment.getCourseId())) {
            throw new IllegalStateException("Student is already enrolled in this course.");
        }
        enrollment.setEnrolledAt(LocalDateTime.now());
        return enrollmentRepositoryPort.save(enrollment);
    }

    @Override
    @CacheEvict(value = "analytics", allEntries = true)
    public Enrollment updateGrade(Long id, Integer grade) {
        Enrollment enrollment = enrollmentRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollment.setGrade(grade);
        return enrollmentRepositoryPort.save(enrollment);
    }

    @Override
    @CacheEvict(value = "analytics", allEntries = true)
    public void deleteEnrollment(Long id) {
        enrollmentRepositoryPort.deleteById(id);
    }
}
