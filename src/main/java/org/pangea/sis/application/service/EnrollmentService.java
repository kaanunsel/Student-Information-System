package org.pangea.sis.application.service;

import org.pangea.sis.domain.model.Enrollment;
import org.pangea.sis.domain.port.in.EnrollmentUseCase;
import org.pangea.sis.domain.port.out.EnrollmentRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service implementing enrollment use cases.
 * Depends on repository port interfaces, not concrete implementations.
 */
@Service
@Transactional(readOnly = true)
public class EnrollmentService implements EnrollmentUseCase {

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentService.class);

    private final EnrollmentRepositoryPort enrollmentRepositoryPort;

    public EnrollmentService(EnrollmentRepositoryPort enrollmentRepositoryPort) {
        this.enrollmentRepositoryPort = enrollmentRepositoryPort;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        logger.info("Fetching all enrollments");
        return enrollmentRepositoryPort.findAll();
    }

    @Override
    public List<Enrollment> getAllEnrollmentsByStudentAndCourseId(Long studentId, Long courseId) {
        logger.info("Fetching enrollments for student id: {} and course id: {}", studentId, courseId);
        return enrollmentRepositoryPort.findByStudentIdAndCourseId(studentId, courseId).stream().toList();
    }

    @Override
    public List<Enrollment> getByStudentId(Long id) {
        logger.info("Fetching enrollments for student id: {}", id);
        return enrollmentRepositoryPort.findByStudentId(id);
    }

    @Override
    public List<Enrollment> getByCourseId(Long id) {
        logger.info("Fetching enrollments for course id: {}", id);
        return enrollmentRepositoryPort.findByCourseId(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "analytics", allEntries = true)
    public Enrollment createEnrollment(Enrollment enrollment) {
        logger.info("Creating enrollment for student {} in course {}", enrollment.getStudentId(),
                enrollment.getCourseId());
        if (enrollmentRepositoryPort.existsByStudentIdAndCourseId(
                enrollment.getStudentId(),
                enrollment.getCourseId())) {
            logger.warn("Student {} already enrolled in course {}", enrollment.getStudentId(),
                    enrollment.getCourseId());
            throw new IllegalStateException("Student is already enrolled in this course.");
        }
        enrollment.setEnrolledAt(LocalDateTime.now());
        return enrollmentRepositoryPort.save(enrollment);
    }

    @Override
    @Transactional
    @CacheEvict(value = "analytics", allEntries = true)
    public Enrollment updateGrade(Long id, Integer grade) {
        logger.info("Updating grade for enrollment id: {} to {}", id, grade);
        Enrollment enrollment = enrollmentRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollment.setGrade(grade);
        return enrollmentRepositoryPort.save(enrollment);
    }

    @Override
    @Transactional
    @CacheEvict(value = "analytics", allEntries = true)
    public void deleteEnrollment(Long id) {
        logger.info("Deleting enrollment id: {}", id);
        enrollmentRepositoryPort.deleteById(id);
    }
}
