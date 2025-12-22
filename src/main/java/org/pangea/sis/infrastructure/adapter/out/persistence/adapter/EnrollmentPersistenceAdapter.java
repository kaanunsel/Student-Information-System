package org.pangea.sis.infrastructure.adapter.out.persistence.adapter;

import org.pangea.sis.domain.model.Enrollment;
import org.pangea.sis.domain.port.out.EnrollmentRepositoryPort;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.CourseJpaEntity;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.EnrollmentJpaEntity;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.StudentJpaEntity;
import org.pangea.sis.infrastructure.adapter.out.persistence.mapper.EnrollmentPersistenceMapper;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.CourseJpaRepository;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.EnrollmentJpaRepository;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.StudentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence adapter implementing EnrollmentRepositoryPort.
 * Bridges domain layer with JPA persistence infrastructure.
 */
@Component
public class EnrollmentPersistenceAdapter implements EnrollmentRepositoryPort {

    private final EnrollmentJpaRepository enrollmentJpaRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final CourseJpaRepository courseJpaRepository;

    public EnrollmentPersistenceAdapter(EnrollmentJpaRepository enrollmentJpaRepository,
                                        StudentJpaRepository studentJpaRepository,
                                        CourseJpaRepository courseJpaRepository) {
        this.enrollmentJpaRepository = enrollmentJpaRepository;
        this.studentJpaRepository = studentJpaRepository;
        this.courseJpaRepository = courseJpaRepository;
    }

    @Override
    public List<Enrollment> findAll() {
        return enrollmentJpaRepository.findAll().stream()
                .map(EnrollmentPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        return enrollmentJpaRepository.findById(id)
                .map(EnrollmentPersistenceMapper::toDomain);
    }

    @Override
    public List<Enrollment> findByStudentId(Long studentId) {
        return enrollmentJpaRepository.findByStudentId(studentId).stream()
                .map(EnrollmentPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Enrollment> findByCourseId(Long courseId) {
        return enrollmentJpaRepository.findByCourseId(courseId).stream()
                .map(EnrollmentPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId) {
        return enrollmentJpaRepository.findByStudentIdAndCourseId(studentId, courseId)
                .map(EnrollmentPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByStudentIdAndCourseId(Long studentId, Long courseId) {
        return enrollmentJpaRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public List<Enrollment> findByGradeIsNotNull() {
        return enrollmentJpaRepository.findByGradeIsNotNull().stream()
                .map(EnrollmentPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        EnrollmentJpaEntity entity = EnrollmentPersistenceMapper.toEntity(enrollment);
        
        // Handle student relationship
        if (enrollment.getStudentId() != null) {
            StudentJpaEntity student = studentJpaRepository.findById(enrollment.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            entity.setStudent(student);
        }
        
        // Handle course relationship
        if (enrollment.getCourseId() != null) {
            CourseJpaEntity course = courseJpaRepository.findById(enrollment.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            entity.setCourse(course);
        }
        
        EnrollmentJpaEntity saved = enrollmentJpaRepository.save(entity);
        return EnrollmentPersistenceMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        enrollmentJpaRepository.deleteById(id);
    }
}
