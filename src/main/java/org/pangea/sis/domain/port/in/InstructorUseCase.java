package org.pangea.sis.domain.port.in;

import org.pangea.sis.domain.model.Instructor;

import java.util.List;
import java.util.Optional;

/**
 * Input port (use case interface) for instructor operations.
 * Defines business operations available for instructors.
 */
public interface InstructorUseCase {
    List<Instructor> getAllInstructors();
    Optional<Instructor> getInstructorById(Long id);
    Instructor addInstructor(Instructor instructor);
    Optional<Instructor> updateInstructor(Long id, Instructor updatedInstructor);
    void deleteInstructor(Long id);
}
