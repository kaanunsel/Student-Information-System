package org.pangea.sis.repository;

import org.pangea.sis.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing Instructor entities.
 * Provides methods to find instructors by specific attributes.
 */
@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    /**
     * Finds an instructor by their name.
     *
     * @param name instructor's name
     * @return optional containing the instructor if found
     */
    Optional<Instructor> findByName(String name);
}