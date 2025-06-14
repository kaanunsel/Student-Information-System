package org.pangea.sis.repository;

import org.pangea.sis.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Student entities.
 * Includes custom methods for searching by name, surname, and email.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Finds all students with the given first name.
     *
     * @param name student's first name
     * @return list of matching students
     */
    List<Student> findAllByNameContainingIgnoreCase(String name);

    /**
     * Finds all students with the given surname.
     *
     * @param surname student's last name
     * @return list of matching students
     */
    List<Student> findAllBySurnameContainingIgnoreCase(String surname);

    List<Student> findAllByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(String name, String surname);

    /**
     * Finds a student by their email address.
     *
     * @param email student's email
     * @return optional containing the student if found
     */
    Optional<Student> findByEmail(String email);
}