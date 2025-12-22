package org.pangea.sis.infrastructure.adapter.out.persistence.mapper;

import org.pangea.sis.domain.model.Instructor;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.InstructorJpaEntity;

/**
 * Mapper for converting between Instructor domain model and InstructorJpaEntity.
 */
public class InstructorPersistenceMapper {

    /**
     * Converts a JPA entity to a domain model.
     *
     * @param entity the JPA entity
     * @return domain model
     */
    public static Instructor toDomain(InstructorJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        Instructor domain = new Instructor();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setSurname(entity.getSurname());
        domain.setEmail(entity.getEmail());
        domain.setPassword(entity.getPassword());
        domain.setCreatedAt(entity.getCreatedAt());
        return domain;
    }

    /**
     * Converts a domain model to a JPA entity.
     *
     * @param domain the domain model
     * @return JPA entity
     */
    public static InstructorJpaEntity toEntity(Instructor domain) {
        if (domain == null) {
            return null;
        }
        InstructorJpaEntity entity = new InstructorJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setSurname(domain.getSurname());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
