package org.pangea.sis.infrastructure.adapter.out.persistence.adapter;

import org.pangea.sis.domain.model.Instructor;
import org.pangea.sis.domain.port.out.InstructorRepositoryPort;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.InstructorJpaEntity;
import org.pangea.sis.infrastructure.adapter.out.persistence.mapper.InstructorPersistenceMapper;
import org.pangea.sis.infrastructure.adapter.out.persistence.repository.InstructorJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence adapter implementing InstructorRepositoryPort.
 * Bridges domain layer with JPA persistence infrastructure.
 */
@Component
public class InstructorPersistenceAdapter implements InstructorRepositoryPort {

    private final InstructorJpaRepository instructorJpaRepository;

    public InstructorPersistenceAdapter(InstructorJpaRepository instructorJpaRepository) {
        this.instructorJpaRepository = instructorJpaRepository;
    }

    @Override
    public List<Instructor> findAll() {
        return instructorJpaRepository.findAll().stream()
                .map(InstructorPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Instructor> findById(Long id) {
        return instructorJpaRepository.findById(id)
                .map(InstructorPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Instructor> findByEmail(String email) {
        return instructorJpaRepository.findByEmail(email)
                .map(InstructorPersistenceMapper::toDomain);
    }

    @Override
    public Instructor save(Instructor instructor) {
        InstructorJpaEntity entity = InstructorPersistenceMapper.toEntity(instructor);
        InstructorJpaEntity saved = instructorJpaRepository.save(entity);
        return InstructorPersistenceMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        instructorJpaRepository.deleteById(id);
    }
}
