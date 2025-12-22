package org.pangea.sis.infrastructure.adapter.out.persistence.repository;

import org.pangea.sis.infrastructure.adapter.out.persistence.entity.StudentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository interface for managing StudentJpaEntity.
 * Includes custom methods for searching by name, surname, and email.
 */
@Repository
public interface StudentJpaRepository extends JpaRepository<StudentJpaEntity, Long> {

    /**
     * Finds all students with the given first name.
     *
     * @param name student's first name
     * @return list of matching students
     */
    List<StudentJpaEntity> findAllByNameContainingIgnoreCase(String name);

    /**
     * Finds all students with the given surname.
     *
     * @param surname student's last name
     * @return list of matching students
     */
    List<StudentJpaEntity> findAllBySurnameContainingIgnoreCase(String surname);

    /**
     * Finds all students matching both a name and a surname, case-insensitively.
     *
     * @param name    The first name or part of it to search for.
     * @param surname The last name or part of it to search for.
     * @return A list of students matching both criteria.
     */
    List<StudentJpaEntity> findAllByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(String name, String surname);

    /**
     * Finds a student by their email address.
     *
     * @param email student's email
     * @return optional containing the student if found
     */
    Optional<StudentJpaEntity> findByEmail(String email);
}
