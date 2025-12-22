package org.pangea.sis.infrastructure.adapter.out.persistence.repository;

import org.pangea.sis.infrastructure.adapter.out.persistence.entity.InstructorJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA repository interface for managing InstructorJpaEntity.
 * Includes custom methods for searching by email.
 */
@Repository
public interface InstructorJpaRepository extends JpaRepository<InstructorJpaEntity, Long> {

    /**
     * Finds an instructor by their email address.
     *
     * @param email instructor's email
     * @return optional containing the instructor if found
     */
    Optional<InstructorJpaEntity> findByEmail(String email);
}
