package org.pangea.sis.application.service;

import org.pangea.sis.domain.model.Instructor;
import org.pangea.sis.domain.port.in.InstructorUseCase;
import org.pangea.sis.domain.port.out.InstructorRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service implementing instructor use cases.
 * Depends on repository port interfaces, not concrete implementations.
 */
@Service
@Transactional(readOnly = true)
public class InstructorService implements InstructorUseCase {

    private static final Logger logger = LoggerFactory.getLogger(InstructorService.class);

    private final InstructorRepositoryPort instructorRepositoryPort;

    public InstructorService(InstructorRepositoryPort instructorRepositoryPort) {
        this.instructorRepositoryPort = instructorRepositoryPort;
    }

    @Override
    public List<Instructor> getAllInstructors() {
        logger.info("Fetching all instructors");
        return instructorRepositoryPort.findAll();
    }

    @Override
    public Optional<Instructor> getInstructorById(Long id) {
        logger.info("Fetching instructor by id: {}", id);
        return instructorRepositoryPort.findById(id);
    }

    @Override
    @Transactional
    public Instructor addInstructor(Instructor instructor) {
        logger.info("Adding new instructor: {} {}", instructor.getName(), instructor.getSurname());
        instructor.setCreatedAt(LocalDateTime.now());
        return instructorRepositoryPort.save(instructor);
    }

    @Override
    @Transactional
    public Optional<Instructor> updateInstructor(Long id, Instructor updatedInstructor) {
        logger.info("Updating instructor with id: {}", id);
        Optional<Instructor> optionalInstructor = instructorRepositoryPort.findById(id);

        if (optionalInstructor.isEmpty()) {
            logger.warn("Instructor with id: {} not found for update", id);
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

        if (updatedInstructor.getDepartment() != null) {
            existingInstructor.setDepartment(updatedInstructor.getDepartment());
        }

        if (updatedInstructor.getHiringDate() != null) {
            existingInstructor.setHiringDate(updatedInstructor.getHiringDate());
        }

        Instructor savedInstructor = instructorRepositoryPort.save(existingInstructor);
        return Optional.of(savedInstructor);
    }

    @Override
    @Transactional
    public void deleteInstructor(Long id) {
        logger.info("Deleting instructor with id: {}", id);
        instructorRepositoryPort.deleteById(id);
    }
}
