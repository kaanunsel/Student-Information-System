package org.pangea.sis.infrastructure.adapter.out.persistence.mapper;

import org.pangea.sis.domain.model.Student;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.StudentJpaEntity;

/**
 * Mapper for converting between Student domain model and StudentJpaEntity.
 */
public class StudentPersistenceMapper {

    /**
     * Converts a JPA entity to a domain model.
     *
     * @param entity the JPA entity
     * @return domain model
     */
    public static Student toDomain(StudentJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        Student domain = new Student();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setSurname(entity.getSurname());
        domain.setEmail(entity.getEmail());
        domain.setBirthDate(entity.getBirthDate());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setAdvisorId(entity.getAdvisor() != null ? entity.getAdvisor().getId() : null);
        return domain;
    }

    /**
     * Converts a domain model to a JPA entity.
     *
     * @param domain the domain model
     * @return JPA entity
     */
    public static StudentJpaEntity toEntity(Student domain) {
        if (domain == null) {
            return null;
        }
        StudentJpaEntity entity = new StudentJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setSurname(domain.getSurname());
        entity.setEmail(domain.getEmail());
        entity.setBirthDate(domain.getBirthDate());
        entity.setCreatedAt(domain.getCreatedAt());
        // Note: advisor relationship is set separately in the adapter
        return entity;
    }
}
