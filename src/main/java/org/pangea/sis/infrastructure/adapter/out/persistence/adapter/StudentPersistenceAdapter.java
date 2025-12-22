package org.pangea.sis.infrastructure.adapter.out.persistence.adapter;

import org.pangea.sis.domain.model.Student;
import org.pangea.sis.domain.port.out.StudentRepositoryPort;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.InstructorJpaEntity;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.StudentJpaEntity;
import org.pangea.sis.infrastructure.adapter.out.persistence.mapper.StudentPersistenceMapper;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.InstructorJpaRepository;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.StudentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence adapter implementing StudentRepositoryPort.
 * Bridges domain layer with JPA persistence infrastructure.
 */
@Component
public class StudentPersistenceAdapter implements StudentRepositoryPort {

    private final StudentJpaRepository studentJpaRepository;
    private final InstructorJpaRepository instructorJpaRepository;

    public StudentPersistenceAdapter(StudentJpaRepository studentJpaRepository,
                                     InstructorJpaRepository instructorJpaRepository) {
        this.studentJpaRepository = studentJpaRepository;
        this.instructorJpaRepository = instructorJpaRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentJpaRepository.findAll().stream()
                .map(StudentPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentJpaRepository.findById(id)
                .map(StudentPersistenceMapper::toDomain);
    }

    @Override
    public List<Student> findByNameContaining(String name) {
        return studentJpaRepository.findAllByNameContainingIgnoreCase(name).stream()
                .map(StudentPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findBySurnameContaining(String surname) {
        return studentJpaRepository.findAllBySurnameContainingIgnoreCase(surname).stream()
                .map(StudentPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findByNameAndSurnameContaining(String name, String surname) {
        return studentJpaRepository.findAllByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(name, surname).stream()
                .map(StudentPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return studentJpaRepository.findByEmail(email)
                .map(StudentPersistenceMapper::toDomain);
    }

    @Override
    public Student save(Student student) {
        StudentJpaEntity entity = StudentPersistenceMapper.toEntity(student);
        
        // Handle advisor relationship
        if (student.getAdvisorId() != null) {
            InstructorJpaEntity advisor = instructorJpaRepository.findById(student.getAdvisorId())
                    .orElse(null);
            entity.setAdvisor(advisor);
        }
        
        StudentJpaEntity saved = studentJpaRepository.save(entity);
        return StudentPersistenceMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        studentJpaRepository.deleteById(id);
    }
}
