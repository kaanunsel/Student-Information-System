package org.pangea.sis.infrastructure.adapter.out.persistence.repository;

import org.pangea.sis.infrastructure.adapter.out.persistence.entity.CourseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository interface for managing CourseJpaEntity.
 * Includes custom methods for searching by name, code, and instructor.
 */
@Repository
public interface CourseJpaRepository extends JpaRepository<CourseJpaEntity, Long> {

    /**
     * Finds all courses with the given name (case-insensitive partial match).
     *
     * @param name course name
     * @return list of matching courses
     */
    List<CourseJpaEntity> findAllByNameContainingIgnoreCase(String name);

    /**
     * Finds all courses with the given code (case-insensitive partial match).
     *
     * @param code course code
     * @return list of matching courses
     */
    List<CourseJpaEntity> findAllByCodeContainingIgnoreCase(String code);

    /**
     * Finds a course by its exact code.
     *
     * @param code course code
     * @return optional containing the course if found
     */
    Optional<CourseJpaEntity> findByCode(String code);

    /**
     * Finds all courses taught by a specific instructor.
     *
     * @param instructorId instructor's ID
     * @return list of courses
     */
    List<CourseJpaEntity> findAllByInstructorId(Long instructorId);
}
