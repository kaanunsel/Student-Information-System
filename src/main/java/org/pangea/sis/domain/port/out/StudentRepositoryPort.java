package org.pangea.sis.domain.port.out;

import org.pangea.sis.domain.model.Student;

import java.util.List;
import java.util.Optional;

/**
 * Output port for student persistence operations.
 * Defines contract for student repository without infrastructure details.
 */
public interface StudentRepositoryPort {
    List<Student> findAll();
    Optional<Student> findById(Long id);
    List<Student> findByNameContaining(String name);
    List<Student> findBySurnameContaining(String surname);
    List<Student> findByNameAndSurnameContaining(String name, String surname);
    Optional<Student> findByEmail(String email);
    Student save(Student student);
    void deleteById(Long id);
}
