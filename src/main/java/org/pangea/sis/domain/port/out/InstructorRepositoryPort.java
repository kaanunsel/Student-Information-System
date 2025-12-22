package org.pangea.sis.domain.port.out;

import org.pangea.sis.domain.model.Instructor;

import java.util.List;
import java.util.Optional;

/**
 * Output port for instructor persistence operations.
 * Defines contract for instructor repository without infrastructure details.
 */
public interface InstructorRepositoryPort {
    List<Instructor> findAll();
    Optional<Instructor> findById(Long id);
    Optional<Instructor> findByEmail(String email);
    Instructor save(Instructor instructor);
    void deleteById(Long id);
}
