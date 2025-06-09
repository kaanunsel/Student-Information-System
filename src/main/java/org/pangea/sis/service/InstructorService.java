package org.pangea.sis.service;

import org.pangea.sis.entity.Instructor;
import org.pangea.sis.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling instructor-related operations.
 */
@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    /**
     * Constructor-based injection for InstructorRepository.
     *
     * @param instructorRepository repository for accessing instructor data
     */
    @Autowired
    public InstructorService(InstructorRepository instructorRepository){
        this.instructorRepository = instructorRepository;
    }

    /**
     * Retrieves an instructor by their ID.
     *
     * @param id instructor ID
     * @return optional containing the instructor if found
     */
    public Optional<Instructor> getInstructorById(Long id){
        return instructorRepository.findById(id);
    }
}