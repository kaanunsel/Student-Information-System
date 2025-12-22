package org.pangea.sis.infrastructure.adapter.out.persistence.mapper;

import org.pangea.sis.domain.model.Course;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.CourseJpaEntity;

/**
 * Mapper for converting between Course domain model and CourseJpaEntity.
 */
public class CoursePersistenceMapper {

    /**
     * Converts a JPA entity to a domain model.
     *
     * @param entity the JPA entity
     * @return domain model
     */
    public static Course toDomain(CourseJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        Course domain = new Course();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setCode(entity.getCode());
        domain.setCredit(entity.getCredit());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setInstructorId(entity.getInstructor() != null ? entity.getInstructor().getId() : null);
        return domain;
    }

    /**
     * Converts a domain model to a JPA entity.
     *
     * @param domain the domain model
     * @return JPA entity
     */
    public static CourseJpaEntity toEntity(Course domain) {
        if (domain == null) {
            return null;
        }
        CourseJpaEntity entity = new CourseJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setCode(domain.getCode());
        entity.setCredit(domain.getCredit());
        entity.setCreatedAt(domain.getCreatedAt());
        // Note: instructor relationship is set separately in the adapter
        return entity;
    }
}
