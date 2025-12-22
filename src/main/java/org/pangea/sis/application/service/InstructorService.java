package org.pangea.sis.application.service;

import org.pangea.sis.domain.model.Instructor;
import org.pangea.sis.domain.port.in.InstructorUseCase;
import org.pangea.sis.domain.port.out.InstructorRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service implementing instructor use cases.
 * Depends on repository port interfaces, not concrete implementations.
 */
@Service
public class InstructorService implements InstructorUseCase {

    private final InstructorRepositoryPort instructorRepositoryPort;

    public InstructorService(InstructorRepositoryPort instructorRepositoryPort) {
        this.instructorRepositoryPort = instructorRepositoryPort;
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepositoryPort.findAll();
    }

    @Override
    public Optional<Instructor> getInstructorById(Long id) {
        return instructorRepositoryPort.findById(id);
    }

    @Override
    public Instructor addInstructor(Instructor instructor) {
        instructor.setCreatedAt(LocalDateTime.now());
        return instructorRepositoryPort.save(instructor);
    }

    @Override
    public Optional<Instructor> updateInstructor(Long id, Instructor updatedInstructor) {
        Optional<Instructor> optionalInstructor = instructorRepositoryPort.findById(id);

        if (optionalInstructor.isEmpty()) {
            return Optional.empty();
        }

        Instructor existingInstructor = optionalInstructor.get();

        if (updatedInstructor.getName() != null) {
            existingInstructor.setName(updatedInstructor.getName());
        }

        if (updatedInstructor.getSurname() != null) {
            existingInstructor.setSurname(updatedInstructor.getSurname());
        }

        if (updatedInstructor.getEmail() != null) {
            existingInstructor.setEmail(updatedInstructor.getEmail());
        }

        if (updatedInstructor.getPassword() != null) {
            existingInstructor.setPassword(updatedInstructor.getPassword());
        }

        Instructor savedInstructor = instructorRepositoryPort.save(existingInstructor);
        return Optional.of(savedInstructor);
    }

    @Override
    public void deleteInstructor(Long id) {
        instructorRepositoryPort.deleteById(id);
    }
}
