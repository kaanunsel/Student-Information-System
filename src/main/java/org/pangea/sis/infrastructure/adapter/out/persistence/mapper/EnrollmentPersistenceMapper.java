package org.pangea.sis.infrastructure.adapter.out.persistence.mapper;

import org.pangea.sis.domain.model.Enrollment;
import org.pangea.sis.infrastructure.adapter.out.persistence.entity.EnrollmentJpaEntity;

/**
 * Mapper for converting between Enrollment domain model and EnrollmentJpaEntity.
 */
public class EnrollmentPersistenceMapper {

    /**
     * Converts a JPA entity to a domain model.
     *
     * @param entity the JPA entity
     * @return domain model
     */
    public static Enrollment toDomain(EnrollmentJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        Enrollment domain = new Enrollment();
        domain.setId(entity.getId());
        domain.setStudentId(entity.getStudent() != null ? entity.getStudent().getId() : null);
        domain.setCourseId(entity.getCourse() != null ? entity.getCourse().getId() : null);
        domain.setGrade(entity.getGrade());
        domain.setEnrolledAt(entity.getEnrolledAt());
        return domain;
    }

    /**
     * Converts a domain model to a JPA entity.
     *
     * @param domain the domain model
     * @return JPA entity
     */
    public static EnrollmentJpaEntity toEntity(Enrollment domain) {
        if (domain == null) {
            return null;
        }
        EnrollmentJpaEntity entity = new EnrollmentJpaEntity();
        entity.setId(domain.getId());
        entity.setGrade(domain.getGrade());
        entity.setEnrolledAt(domain.getEnrolledAt());
        // Note: student and course relationships are set separately in the adapter
        return entity;
    }
}
