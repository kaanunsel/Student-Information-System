package org.pangea.sis.infrastructure.adapter.out.persistence.adapter;

import org.pangea.sis.domain.model.Course;
import org.pangea.sis.domain.port.out.CourseRepositoryPort;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.CourseJpaEntity;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.InstructorJpaEntity;
import org.pangea.sis.infrastructure.adapter.out.persistence.mapper.CoursePersistenceMapper;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.CourseJpaRepository;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.InstructorJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence adapter implementing CourseRepositoryPort.
 * Bridges domain layer with JPA persistence infrastructure.
 */
@Component
public class CoursePersistenceAdapter implements CourseRepositoryPort {

    private final CourseJpaRepository courseJpaRepository;
    private final InstructorJpaRepository instructorJpaRepository;

    public CoursePersistenceAdapter(CourseJpaRepository courseJpaRepository,
                                    InstructorJpaRepository instructorJpaRepository) {
        this.courseJpaRepository = courseJpaRepository;
        this.instructorJpaRepository = instructorJpaRepository;
    }

    @Override
    public List<Course> findAll() {
        return courseJpaRepository.findAll().stream()
                .map(CoursePersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseJpaRepository.findById(id)
                .map(CoursePersistenceMapper::toDomain);
    }

    @Override
    public List<Course> findByNameContaining(String name) {
        return courseJpaRepository.findAllByNameContainingIgnoreCase(name).stream()
                .map(CoursePersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> findByCodeContaining(String code) {
        return courseJpaRepository.findAllByCodeContainingIgnoreCase(code).stream()
                .map(CoursePersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Course> findByCode(String code) {
        return courseJpaRepository.findByCode(code)
                .map(CoursePersistenceMapper::toDomain);
    }

    @Override
    public List<Course> findByInstructorId(Long instructorId) {
        return courseJpaRepository.findAllByInstructorId(instructorId).stream()
                .map(CoursePersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Course save(Course course) {
        CourseJpaEntity entity = CoursePersistenceMapper.toEntity(course);
        
        // Handle instructor relationship
        if (course.getInstructorId() != null) {
            InstructorJpaEntity instructor = instructorJpaRepository.findById(course.getInstructorId())
                    .orElse(null);
            entity.setInstructor(instructor);
        }
        
        CourseJpaEntity saved = courseJpaRepository.save(entity);
        return CoursePersistenceMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        courseJpaRepository.deleteById(id);
    }

    @Override
    public void delete(Course course) {
        CourseJpaEntity entity = CoursePersistenceMapper.toEntity(course);
        if (course.getInstructorId() != null) {
            InstructorJpaEntity instructor = instructorJpaRepository.findById(course.getInstructorId())
                    .orElse(null);
            entity.setInstructor(instructor);
        }
        courseJpaRepository.delete(entity);
    }
}
